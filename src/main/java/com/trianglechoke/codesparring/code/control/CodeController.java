package com.trianglechoke.codesparring.code.control;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


@RestController
@RequestMapping("/code")
public class CodeController {

    // 코드실행
    @PostMapping("/executeCode")
    public String executeCode(@RequestParam("code") MultipartFile file) {
        // 파일이 비었는지 확인
        if (file.isEmpty()) {
            return "업로드된 파일이 비어 있습니다.";
        }

        //-----------------------------------------------------------------------------------------------
        // 파일 저장(저장해야 그 파일을 실행시키니까?)
//        String fileName = "Main"; // 원하는 파일명으로 변경
//        String filePath =
//                "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/" + fileName + ".java";
//        try {
//            file.transferTo(new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "파일 저장 중 오류가 발생했습니다.";
//        }
        //-----------------------------------------------------------------------------------------------


        // 쉘 스크립트 실행
        String cmd = "cmd.exe";
        String arg = "/c";
        String scriptPath = "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/java.sh";
        ProcessBuilder pb = new ProcessBuilder(cmd, arg, scriptPath);

//        java.sh로 code.txt파일을 실행시킬때 "4 5"값을 전달하면 0.8의 결과값이 나온다 이를 비교한다
        String result = "";
//        String inputString  = "4 5";
        String expectedOutput = "0.8"; // 예상 출력값


        try{
            // MultipartFile에서 스트림을 읽어서 프로세스에 전달
//            pb.redirectInput(ProcessBuilder.Redirect.from(input));
//            pb.redirectInput(ProcessBuilder.Redirect.PIPE);
//            pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
            Process process = pb.start();
//            try(OutputStream outputStream = process.getOutputStream()){
//                outputStream.write(inputString.getBytes(StandardCharsets.UTF_8));
//            }
            // 프로세스의 출력 스트림을 읽어 결과를 얻습니다.
//            try (InputStream inputStream = process.getInputStream();
//                 Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
//                result = scanner.hasNext() ? scanner.next() : "";
//            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            System.out.println("???");
            System.out.println(reader.readLine());
            while ( (line = reader.readLine()) != null) {
                System.out.println(line);
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            result = builder.toString();

            System.out.println("스크립트 정상 실행!"+result);

            // 결과값이 예상 출력값과 동일한지 확인
            if (result.trim().equals(expectedOutput.trim())) {
                return "테스트 통과! 출력값: " + result;
            } else {
                return "테스트 실패! 예상 출력값: " + expectedOutput + ", 실제 출력값: " + result;
            }


        } catch (IOException e) {
            System.out.println("에러");
            e.printStackTrace();
            return "스크립트 실행 중 오류가 발생했습니다.";
        } catch (Exception e) {
            System.out.println("에러");
            e.printStackTrace();
            return "입력 리디렉션 중 오류가 발생했습니다.";
        }
    }
}
