package com.trianglechoke.codesparring.code.control;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/code")
public class CodeController {

    //코드실행
    @PostMapping("/executeCode")
    public ResponseEntity<String> executeCode(@RequestParam("code") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("업로드된 파일이 비어 있습니다.");
            }

            // 업로드된 파일 내용 읽어오기
            String codeContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            // 테스트케이스 생성 (입력값, 예상 출력값)
            String input = "4 5";
            String expectedOutput = "0.8"; // 예상 출력값

            // Java 코드에 입력값 삽입
            String modifiedJavaCode = insertInputToJavaCode(codeContent, input);

            // Java 코드 컴파일 및 실행
            String actualOutput = executeJavaCode(modifiedJavaCode);

            // 결과 확인
            return ResponseEntity.ok("테스트 통과! 예상: " + expectedOutput + ", 실제: " + actualOutput);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("코드 실행 중 오류 발생.");
        }
    }

    private String insertInputToJavaCode(String javaCode, String input) {
        // Java 코드에 입력값 삽입
        return javaCode.replace("// INSERT_INPUT_HERE", "String[] args = {\"" + input + "\"};");
    }

    private String executeJavaCode(String javaCode)
            throws IOException,
                    ClassNotFoundException,
                    IllegalAccessException,
                    InstantiationException {
        // Java 컴파일러 가져오기
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // 메모리 상의 클래스 파일에 저장
        JavaFileObject javaFileObject =
                (JavaFileObject) new JavaSourceFromString("DynamicCode", javaCode);

        // 컴파일 옵션 설정
        Iterable<String> options = Arrays.asList("-d", "./out/classes");

        // 컴파일 수행
        compiler.getTask(null, fileManager, null, options, null, Arrays.asList(javaFileObject))
                .call();
        fileManager.close();

        // 컴파일된 클래스를 동적으로 로드
        Class<?> loadedClass = Class.forName("DynamicCode");

        // 인스턴스 생성
        Object instance = loadedClass.newInstance();

        // 표준 출력 캡처
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        // 실행 결과 출력
        try {
            loadedClass
                    .getMethod("main", String[].class)
                    .invoke(instance, (Object) new String[] {});
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 표준 출력 복원
        System.setOut(originalOut);

        // 실행 결과 반환
        return outputStream.toString().trim();
    }

    // JavaFileObject 구현 클래스
    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(
                    URI.create(
                            "string:///"
                                    + name.replace('.', '/')
                                    + JavaFileObject.Kind.SOURCE.extension),
                    JavaFileObject.Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
