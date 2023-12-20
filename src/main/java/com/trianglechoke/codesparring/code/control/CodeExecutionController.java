package com.trianglechoke.codesparring.code.control;

import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.code.service.CodeService;
import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// 코드실행(테스트케이스 3개)
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeExecutionController {

    @Value("${file.filePATH}")
    private String filePATH;

    private final CodeService service;
    StringBuilder responseResult;
    int numberCount;

    @PostMapping("/executeCode")
    public ResponseEntity<?> executeCode(
            @RequestPart("quizNo") String quizNo, @RequestPart("Main") MultipartFile file)
            throws IOException {

        responseResult = new StringBuilder();
        numberCount = 0;
        if (file.isEmpty()) {
            // return "업로드된 파일이 비어 있습니다.";
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 파일 저장
        String fileName = file.getName(); // Main
        String filePath = filePATH;
        File f = new File(filePath, fileName + ".java");

        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.FILE_NOT_SAVED);
        }

        if (!f.exists()) {
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 문제번호에 해당하는 테스트케이스 가져오기(input, expectedOutput에 넣어주기)
        List<CodeTestcaseDTO> list = service.findByQuizNo(quizNo);

        int count = 0;
        for (CodeTestcaseDTO dto : list) {
            if (count < 3) {
                String output = dto.getTestcaseOutput();
                String input = dto.getTestcaseInput();

                executeCode2(fileName, f, output, input);
                count++;
            }
        }

        // 파일 이름에서 마지막 '.' 이후의 문자열 제거(.java제거)
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }

        // 파일삭제
        Files.delete(Path.of(filePath + fileName + ".java"));
        Files.delete(Path.of(filePath + fileName + ".class"));

        String msg = String.valueOf(responseResult);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public void executeCode2(String fileName, File f, String output, String input) {

        String input2 = " " + input; // 입력값
        String expectedOutput = " " + output; // 예상 출력값
        String result = "";

        // -------------------------------컴파일 시작-------------------------------
        String cmd = "cmd.exe";
        String arg = "/c";
        ProcessBuilder pb =
                new ProcessBuilder(cmd, arg, "javac " + f.getAbsolutePath() + " -encoding UTF8");

        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("컴파일 Process start exitCode=" + exitCode);

            InputStream is = p.getInputStream();
            Scanner sc = new Scanner(is);
            StringBuilder compileBuilder = new StringBuilder();
            // true라는 것은 오류 메시지가 발생했을때 (컴파일 성공하면 메시지가 안뜸)
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
                compileBuilder.append(line).append(System.getProperty("line.separator"));
            }
            // 컴파일 실패된 경우
            if (!compileBuilder.toString().isEmpty()) {
                return;
            }

            sc.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // -------------------------------컴파일 끝-------------------------------

        // -------------------------------실행 시작-------------------------------
        // 파일 이름에서 마지막 '.' 이후의 문자열 제거(.java제거)
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }
        cmd = "cmd.exe";
        arg = "/c";
        pb = new ProcessBuilder(cmd, arg, "java -cp " + filePATH + " " + fileName);

        try {
            Process p = pb.start();
            OutputStream os = p.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));


            writer.write(input);
            writer.flush();
            writer.close();

            int exitCode = p.waitFor();
            System.out.println("실행용 Process start exitCode=" + exitCode);

            InputStream is = p.getInputStream();
            Scanner sc = new Scanner(is);
            StringBuilder resultBuilder = new StringBuilder();

            // 결과값 얻어오기
            while (sc.hasNextLine()) {
                String resultLine = sc.next();
                System.out.println(sc.nextLine());
                resultBuilder.append(resultLine).append(System.getProperty("line.separator"));
            }
            result = resultBuilder.toString();

            // 실행 결과를 한번에 리턴하기 위해 StringBuilder사용
            if (result.trim().equals(expectedOutput.trim())) {
                responseResult
                        .append("테스트 ")
                        .append(numberCount += 1)
                        .append(": 통과!")
                        .append(System.getProperty("line.separator"))
                        .append("       출력값:")
                        .append(System.getProperty("line.separator"))
                        .append("       " + result)
                        .append(System.getProperty("line.separator"))
                        .append("--------------------------------------")
                        .append(System.getProperty("line.separator"));
            } else {
                responseResult
                        .append("테스트 ")
                        .append(numberCount += 1)
                        .append(": 실패!")
                        .append(System.getProperty("line.separator"))
                        .append("       예상 출력값:")
                        .append(System.getProperty("line.separator"))
                        .append("       " + expectedOutput)
                        .append(System.getProperty("line.separator"))
                        .append("       실제 출력값:")
                        .append(System.getProperty("line.separator"))
                        .append("       " + result)
                        .append(System.getProperty("line.separator"))
                        .append("--------------------------------------")
                        .append(System.getProperty("line.separator"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // -------------------------------실행 끝-------------------------------

    }
}
