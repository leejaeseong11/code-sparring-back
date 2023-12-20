package com.trianglechoke.codesparring.code.control;

import com.trianglechoke.codesparring.code.dto.CodeTestcaseDTO;
import com.trianglechoke.codesparring.code.dto.NormalDTO;
import com.trianglechoke.codesparring.code.dto.RankDTO;
import com.trianglechoke.codesparring.code.service.AwsS3Service;
import com.trianglechoke.codesparring.code.service.CodeService;
import com.trianglechoke.codesparring.exception.ErrorCode;
import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.util.SecurityUtil;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/submit")
public class CodeSubmissionController {

    @Value("${file.filePATH}")
    private String filePATH;

    private final CodeService service;
    private final AwsS3Service awsS3Service;

    StringBuilder responseResult;
    int answerCount;
    int numberCount;

    @PostMapping("/normalMode")
    public ResponseEntity<?> normalMode(
            @RequestPart(value = "Main") MultipartFile file,
            @RequestPart(value = "dto") NormalDTO dto)
            throws IOException {

        responseResult = new StringBuilder();
        numberCount = 0;
        answerCount = 0;

        if (file.isEmpty()) {
            // return "업로드된 파일이 비어 있습니다.";
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 파일 저장
        String fileName = file.getName(); // Main
        String filePath = filePATH;
        File f = new File(filePath, fileName + ".java");

        // 사용자 번호에 해당하는 폴더 생성
        String bucketPath = "/" + SecurityUtil.getCurrentMemberNo();
        // S3서버에 제출한 코드 파일 저장(.txt)
        String fileUrl =
                awsS3Service.uploadImage(
                        file,
                        bucketPath,
                        SecurityUtil.getCurrentMemberNo().toString(),
                        dto.getQuizNo().toString());

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
            String output = ctdto.getTestcaseOutput();
            String input = ctdto.getTestcaseInput();
            executeCode2(fileName, f, output, input);
        }
        // 파일 이름에서 마지막 '.' 이후의 문자열 제거(.java제거)
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }

        // 파일삭제
        Files.delete(Path.of(filePath + fileName + ".java"));
        Files.delete(Path.of(filePath + fileName + ".class"));

        Integer correct = 0;
        if (answerCount == list.size()) correct = 1;
        service.writeMemberCode(dto.getQuizNo(), correct, fileUrl);

        String result = String.valueOf(responseResult);

        Map<String, String> result2 = new HashMap<>();
        result2.put("result", result);
        result2.put("gameResult", String.valueOf(correct));
        return new ResponseEntity<>(result2, HttpStatus.OK);
    }

    @PostMapping("/rankMode")
    public ResponseEntity<?> rankMode(
            @RequestPart(value = "Main") MultipartFile file,
            @RequestPart(value = "dto") RankDTO dto)
            throws IOException {

        responseResult = new StringBuilder();
        numberCount = 0;
        answerCount = 0;

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
            // return "파일 저장 중 오류가 발생했습니다.";
            throw new MyException(ErrorCode.FILE_NOT_SAVED);
        }

        if (!f.exists()) {
            throw new MyException(ErrorCode.FILE_NOT_FOUND);
        }

        // 문제번호에 해당하는 테스트케이스 가져오기(input, expectedOutput에 넣어주기)
        List<CodeTestcaseDTO> list = service.findByQuizNo(String.valueOf(dto.getQuizNo()));

        for (CodeTestcaseDTO ctdto : list) {
            String output = ctdto.getTestcaseOutput();
            String input = ctdto.getTestcaseInput();
            executeCode2(fileName, f, output, input);
        }
        // 파일 이름에서 마지막 '.' 이후의 문자열 제거(.java제거)
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }

        // 파일삭제
        Files.delete(Path.of(filePath + fileName + ".java"));
        Files.delete(Path.of(filePath + fileName + ".class"));

        Integer correct = 0;
        if (answerCount == list.size()) correct = 1;
        service.modifyQuizSubmit(dto.getQuizNo(), correct);

        String result = String.valueOf(responseResult);

        Map<String, String> result2 = new HashMap<>();
        result2.put("result", result);
        result2.put("gameResult", String.valueOf(correct));
        return new ResponseEntity<>(result2, HttpStatus.OK);
    }

    public void executeCode2(String fileName, File f, String output, String input) {

        String expectedOutput = " " + output; // 예상 출력값
        String result = "";
        // -------------------------------컴파일 시작-------------------------------
        String osName = System.getProperty("os.name").toLowerCase();
        String cmd;
        String arg;
        if (osName.contains("win")) {
            cmd = "cmd.exe";
            arg = "/c";
        } else {
            cmd = "sh";
            arg = "-c";
        }
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
        // 파일 이름에서 마지막 '.' 이후의 문자열 제거(.java제거)
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }

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
                        .append(System.getProperty("line.separator"));
                answerCount += 1;
            } else {
                responseResult
                        .append("테스트 ")
                        .append(numberCount += 1)
                        .append(": 실패!")
                        .append(System.getProperty("line.separator"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // -------------------------------실행 끝-------------------------------

    }

    @GetMapping("/{quizNo}")
    public ResponseEntity<?> testcase(@PathVariable Long quizNo) {
        List<CodeTestcaseDTO> list = service.findByQuizNo(String.valueOf(quizNo));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
