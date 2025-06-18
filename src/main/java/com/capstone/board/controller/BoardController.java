package com.capstone.board.controller;

import com.capstone.board.service.BoardService;
import com.capstone.board.service.CommentService;
import com.capstone.board.dto.BoardDTO;
import com.capstone.board.dto.CommentDTO;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.service.MemberService;
import com.capstone.professor.dto.ProfessorDTO;
import com.capstone.professor.service.ProfessorService;
import com.capstone.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final ProfessorService professorService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    //새 글 작성 - 글 작성 페이지 호출
    @GetMapping("/Writing_Form")
    public String saveForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        MemberEntity loginEmail = customUserDetails.getMemberEntity();

        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());

            //교수님 목록 가져오기
            List<ProfessorDTO> professors = professorService.findAll();
            //전공 목록 가져오기
            List<String> allMajors = professorService.getAllMajors();

            model.addAttribute("professors", new ArrayList<>());
            model.addAttribute("allMajors", allMajors);
            model.addAttribute("memberDTO", memberDTO);
            return "/board/Writing_Form";
        }else {
            return "/member/login";
        }
    }

    //새 글 작성 - 게시글 작성 폼 전달
    @PostMapping("/create")
    public String createBoard(@ModelAttribute BoardDTO boardDTO, Model model,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails)throws IOException {
        MemberEntity loginEmail = customUserDetails.getMemberEntity();

        //인증된 사용자 있을 시
        if (loginEmail != null) {

            MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());
            boardDTO.setMemberDTO(memberDTO);
            boardDTO.setBoardWriter(memberDTO.getMemberName());

            //총점 자동 계산(JavaScript에서 계산된 값이 넘어오므로 추가 검증 가능)
            Integer totalScore = boardDTO.getTotalScore();
            boardDTO.setTotalScore(totalScore);

            boardService.save(boardDTO);

            model.addAttribute("message", "글 작성 완료");
            model.addAttribute("url", "/board/paging");
            return "/layouts/message";
        } else {
            return "/member/login";
        }
    }

    //글 수정 페이지 - 수정 버튼 클릭 시 수정 폼 호출
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        MemberEntity loginEmail = customUserDetails.getMemberEntity();
        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());

            //글 id 해당 게시글 가져옴
            BoardDTO boardDTO = boardService.findById(id);
            //모든 교수님 목록 가져옴
            List<ProfessorDTO> professors = professorService.findAll();
            //전공 목록 가져옴
            List<String> allMajors = professorService.getAllMajors();

            model.addAttribute("allMajors", allMajors);
            model.addAttribute("professors", new ArrayList<>());
            model.addAttribute("boardUpdate", boardDTO);
            model.addAttribute("memberDTO", memberDTO);
            return "board/update";
        } else {
            return "/member/login";
        }

    }
    //글 수정
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        MemberEntity loginEmail = customUserDetails.getMemberEntity();
        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());

            BoardDTO originalBoard = boardService.findById(boardDTO.getId());
            //사용자 비밀번호 검증
            if(!originalBoard.getBoardPassword().equals(boardDTO.getBoardPassword())) {
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("url", "/board/update/" + boardDTO.getId());
                return "layouts/message";
            }

            boardDTO.setMemberDTO(memberDTO); //로그인한 사용자 정보 memberDTO 설정
            BoardDTO updatedBoard = boardService.update(boardDTO);

            model.addAttribute("board", updatedBoard);
            model.addAttribute("loginEmail", memberDTO);
            model.addAttribute("updateMember", memberDTO);
            return "redirect:/member/update";
        } else {
            return "member/login";
        }
    }

    //전공 선택 후 교수님 목록 가져오기 - 자바스크립트에 사용
    @GetMapping("professors")
    @ResponseBody
    public List<ProfessorDTO> getProfessorsByMajor(@RequestParam("major") String major) {

        List<ProfessorDTO> professorByMajor = professorService.getProfessorsByMajor(major);

        professorByMajor.forEach(professorByMajors -> System.out.println(professorByMajors.getProfessorName()));
        return professorByMajor;
    }

    //해당 게시글 디테일 보기 - 해당 게시글의 조회수를 하나 올리고 게시글 데이터 가져와서 /board/detail.html에 출력
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MemberEntity loginEmail = customUserDetails.getMemberEntity();
        //조회수 증가
        boardService.updateHits(id);
        //게시글 정보 조회
        BoardDTO boardDTO = boardService.findById(id);
        //로그인한 사용자 정보 조회
        MemberDTO memberDTO = memberService.findByEmail(loginEmail.getMemberEmail());
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id, memberDTO.getId());

        model.addAttribute("loggedInMember", memberDTO);//로그인한 사용자 정보 전달
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "/board/detail";
    }

    //글 삭제 요청
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/member/update";
    }

    // 모든 게시글 요청 - /board/paging?page=1 페이징 처리
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/board/paging";

    }

    //게시글 검색 요청
    @GetMapping("/search")
    public String searchBoards(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<BoardDTO> searchResult;

        if (keyword == null || keyword.trim().isEmpty()) {
            searchResult = boardService.findAll();
        } else {
            searchResult = boardService.searchBoard(keyword);
        }
        model.addAttribute("boards", searchResult);

        return "board/searchList";
    }
}