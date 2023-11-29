package com.trianglechoke.codesparring.code.control;

import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.code.dto.NormalDTO;
import com.trianglechoke.codesparring.code.dto.RankDTO;
import com.trianglechoke.codesparring.code.service.CodeService;
import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

// 코드제출(테스트케이스 10개)
@RestController
@RequestMapping("/submit")
public class CodeSubmissionController {

    @Autowired private CodeService service;
    StringBuilder responseResult;

    // 테스트케이스 실행결과 정답 수
    int answerCount;

    @PostMapping("/normalMode")
    public ResponseEntity<?> normalMode(
            @RequestPart(value = "Main") MultipartFile file,
            @RequestPart(value = "dto") NormalDTO dto)
            throws IOException {

        responseResult = new StringBuilder();
        answerCount = 0;
        String output = "";
        String input = "";

        if (file.isEmpty()) {
            // return "업로드된 파일이 비어 있습니다.";
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 파일 저장
        String fileName = file.getName(); // value값으로 지정
        String filePath = "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/";
        File f = new File(filePath, fileName + ".java");

        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
            // return "파일 저장 중 오류가 발생했습니다.";
            throw new MyException(ErrorCode.FILE_NOT_SAVED);
        }

        if (!f.exists()) {
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 문제번호에 해당하는 테스트케이스 가져오기(input, expectedOutput에 넣어주기)
        List<CodeTestcaseDTO> list = service.findByQuizNo(String.valueOf(dto.getQuizNo()));

        for (CodeTestcaseDTO ctdto : list) {
            output = ctdto.getTestcaseOutput();
            input = ctdto.getTestcaseInput();
            executeCode2(fileName, f, output, input);
        }

        // 파일삭제
        Files.delete(
                Path.of(
                        "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/"
                                + fileName
                                + ".java"));
        Files.delete(
                Path.of(
                        "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/"
                                + fileName
                                + ".class"));

        Integer correct=0;
        if(answerCount==list.size()) correct=1;
        service.writeMemberCode(dto.getMemberNo(), dto.getQuizNo(), correct);

        // Quiz테이블의 문제 제출 횟수, 문제 정답 횟수 수정
        // return responseResult + ", " + answerCount;
        String msg = responseResult + ", " + answerCount;
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/rankMode")
    public String rankMode(@RequestPart MultipartFile file, @RequestPart RankDTO dto) {

        return "";
    }

    public void executeCode2(String fileName, File f, String output, String input) {

        String input2 = " " + input; // 입력값
        String expectedOutput = "0.8"; // 예상 출력값
        String compileResult = "";
        String result = "";
        // -------------------------------컴파일 시작-------------------------------
        String cmd = "cmd.exe";
        String arg = "/c";
        ProcessBuilder pb =
                new ProcessBuilder(cmd, arg, "javac " + f.getAbsolutePath() + " -encoding UTF8");

        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("컴파일 Process start exitCode=" + exitCode); // process가 정상 동작:0, 실패:1

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
        cmd = "cmd.exe";
        arg = "/c";
        pb =
                new ProcessBuilder(
                        cmd,
                        arg,
                        "java -cp C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/ "
                                + fileName
                                + input2);

        try {
            Process p = pb.start();
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
                responseResult.append("테스트 통과! 출력값:").append(result);
                answerCount += 1;
            } else {
                responseResult
                        .append("테스트 실패! 예상 출력값:")
                        .append(expectedOutput)
                        .append(", 실제 출력값:")
                        .append(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // -------------------------------실행 끝-------------------------------

    }
}
