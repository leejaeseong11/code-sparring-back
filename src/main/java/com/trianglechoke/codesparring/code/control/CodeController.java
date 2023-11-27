package com.trianglechoke.codesparring.code.control;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/code")
public class CodeController {

    // 코드실행
    @PostMapping("/executeCode")
    public String executeCode(@RequestPart("Main")
                                  @RequestPart MultipartFile file) {
        // 전달받은 파일이 비었는지 확인
        if (file.isEmpty()) {
            return "업로드된 파일이 비어 있습니다.";
        }

        // 파일 저장
        String fileName = file.getName(); // 원하는 파일명으로 변경
        String filePath =
                "C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/";
        File f = new File(filePath,  fileName + ".java");

        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 저장 중 오류가 발생했습니다.";
        }

        if(!f.exists()){
            return "파일이 존재하지 않음!";
        }

        String input = "4 5"; //입력값
        String expectedOutput = "0.8"; //예상 출력값
        String compileResult = "";
        String result = "";


        //-------------------------------컴파일 시작-------------------------------
        String cmd = "cmd.exe";
        String arg = "/c";
        ProcessBuilder pb = new ProcessBuilder(cmd, arg,
                "javac "+ f.getAbsolutePath() +" -encoding UTF8");

        try{
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("Process start exitCode="+ exitCode);

            InputStream is = p.getInputStream();
            Scanner sc = new Scanner(is);
            StringBuilder compileBuilder = new StringBuilder();
            //true라는 것은 오류 메시지가 발생했을때 (컴파일 성공하면 메시지가 안뜸)
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
                compileBuilder.append(line).append(System.getProperty("line.separator"));
            }
            //컴파일 실패된 경우
            if (!compileBuilder.toString().isEmpty()) {
                return "컴파일 실패 = " + compileBuilder.toString();
            }

            sc.close();
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        //-------------------------------컴파일 끝-------------------------------


        //-------------------------------실행 시작-------------------------------
        cmd = "cmd.exe";
        arg = "/c";
        pb = new ProcessBuilder(cmd, arg,
                "java -cp C:/KOSA202307/GitHub/code-sparring-back/src/main/resources/ "+ fileName + " 4 5");

        try{
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("실행용 Process start exitCode="+ exitCode);

            InputStream is = p.getInputStream();
            Scanner sc = new Scanner(is);
            StringBuilder resultBuilder = new StringBuilder();

            //결과값 얻어오기
            while(sc.hasNextLine()){
                String resultLine = sc.next();
                System.out.println(sc.nextLine());
                resultBuilder.append(resultLine).append(System.getProperty("line.separator"));
            }
            result = resultBuilder.toString();

            if(result.trim().equals(expectedOutput.trim())){
                return "테스트 통과! 출력값:" + result;
            }else{
                return "테스트 실패! 예상 출력값:" + expectedOutput + ", 실제 출력값:" + result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //-------------------------------실행 끝-------------------------------


        return "";
    }
}