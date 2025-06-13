package com.capstone.board.controller;

import com.capstone.board.dto.CommentDTO;
import com.capstone.board.service.CommentService;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.service.MemberService;
import com.capstone.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println("댓글 작성한 내용 파라미터로 받아서 잘 넘어옴");
        MemberEntity loginEmail = customUserDetails.getMemberEntity();

        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());
            commentDTO.setCommentWriter(memberDTO.getMemberName()); //작성자 이름을 회원이름으로 설정
            commentDTO.setMemberId(memberDTO.getId());//작성자 ID 설정
            //댓글 저장
            Long saveResult = commentService.save(commentDTO);

            if (saveResult != null) {
                //댓글 작성 성공 -> 댓글 목록을 가져와서 리턴
                List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId(), commentDTO.getMemberId());//댓글 가져올땐 게시글id가 기준이 됨,memberId도 같이 가져옴

                return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
    }

}
