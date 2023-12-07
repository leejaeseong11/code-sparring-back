package com.trianglechoke.codesparring.code.control;


import com.trianglechoke.codesparring.code.service.CodeService;
import com.trianglechoke.codesparring.membercode.dto.MemberCodeDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyCodeController {
    private final CodeService service;

    @GetMapping("/mycode/{memberNo}")
    public ResponseEntity<?> myCode(@PathVariable Long memberNo) {
        List<MemberCodeDTO> list = service.findByMemberNo(memberNo);
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/mycode/{memberNo}/{quizNo}")
    public ResponseEntity<?> myCodeInfo(@PathVariable Long memberNo, @PathVariable Long quizNo){
        String msg = service.findByMemberCodeInfo(memberNo, quizNo);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


}
