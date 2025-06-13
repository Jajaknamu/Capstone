package com.capstone.member.controller;

import com.capstone.board.dto.BoardDTO;
import com.capstone.board.service.BoardService;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.Role;
import com.capstone.member.service.MemberService;
import com.capstone.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    private final BoardService boardService;

    //html 폼에서 넘어온 값으로 회원가입 요청
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO, Model model) {
        System.out.println("회원가입 요청 도착!"); // 로그 추가
        System.out.println("회원가입 이메일: " + memberDTO.getMemberEmail()); // 로그 추가
        memberService.save(memberDTO);

        model.addAttribute("message", "회원가입 완료");
        model.addAttribute("url", "/");
        return "/layouts/message";
    }

    //내 정보 수정하기 페이지 호출
    @GetMapping("/update")
    public String updateForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
//        String myEmail = (String) session.getAttribute("loginEmail");

        System.out.println("로그인된 사용자: " + customUserDetails.getUsername());

        MemberDTO myEmail = MemberDTO.toMemberDTO(customUserDetails.getMemberEntity());
        String memberEmail = myEmail.getMemberEmail();

        MemberDTO memberDTO = memberService.updateForm(memberEmail);
        model.addAttribute("updateMember", memberDTO);

        //사용자가 작성한 게시글 목록 가져오기
        List<BoardDTO> myPosts = boardService.findPostsByMemberID(memberDTO.getId());
        model.addAttribute("myPosts", myPosts);
        return "/member/updatePage";
    }

    //회원정보 수정 업데이트 폼 호출
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/update";
    }

    //로그아웃 요청 - 세션 삭제
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        System.out.println("로그아웃 되었습니다.");
        model.addAttribute("message", "로그아웃 됐습니다.");
        model.addAttribute("url", "/");
        return "/layouts/message";
    }

    //회원탈퇴 페이지 요청
    @GetMapping("/delete/{id}")
    public String deleteMemberPage(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        //필요한 경우 사용자 정보를 로드하여 DTO에 세팅
        model.addAttribute("member", memberDTO);
        return "layouts/error-cancel";
    }

    //회원탈퇴 처리
    @PostMapping("/delete")
    public String deleteMember(MemberDTO memberDTO, Model model) {
        memberService.deleteMember(memberDTO.getId());
        model.addAttribute("message", "회원 탈퇴가 완료되었습니다.감사합니다.");
        model.addAttribute("url", "/");
        return "layouts/message";
    }

    //이메일 체크
    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {

        return memberService.emailCheck(memberEmail);
    }

    //관리자용 - 모든 회원 리스트
    @GetMapping("/list")
    public String listMember(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberDTOList", memberDTOList);
        return "admin/memberInfo";
    }

    //관리자용 - 회원 삭제
    @PostMapping("/deleteMember/{id}")
    public String adminDeleteMember(@ModelAttribute MemberDTO memberDTO,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable("id") Long id) {

        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "접근 권한x");
        }
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("message", "회원 삭제 완료");
        return "redirect:/member/list";
    }
}

/**
 * 필요없어진 코드들
 */
/* //이메일 체크 겸 로그인 체크- 세션 부과 -> 스프링시큐리티 적용하면서 필요없어짐
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("role", loginResult.getRole().name()); //역할 role 저장
            return "redirect:/home";
        } else { //실패
            return "redirect://";
        }
    }*/