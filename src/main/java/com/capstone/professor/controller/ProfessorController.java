package com.capstone.professor.controller;

import com.capstone.board.dto.BoardDTO;
import com.capstone.board.service.BoardService;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.Role;
import com.capstone.member.service.MemberService;
import com.capstone.professor.dto.ProfessorDTO;
import com.capstone.professor.dto.ProfessorLabDTO;
import com.capstone.professor.service.ProfessorService;
import com.capstone.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;
    private final MemberService memberService;
    private final BoardService boardService;

    //관리자용 - 교수 정보 추가 폼 페이지 호출
    @GetMapping("/add")
    public String showAddProfessor(Model model) {
        model.addAttribute("professor", new ProfessorDTO()); // 빈 폼 객체 전달 -> 폼 작성해서 채워 보내주면됨
        return "admin/addProfessor";
    }
    //관리자용 - 교수 정보 추가 처리
    @PostMapping("/add")
    public String addProfessor(@ModelAttribute ProfessorDTO professorDTO) {
        professorService.addProfessor(professorDTO); // 서비스 호출
        return "redirect:/professor/list";
    }

    //관리자용 - 모든 교수님 리스트 호출
    @GetMapping("/list")
    public String listProfessor(Model model) {
//        List<ProfessorDTO> professorDTOList = professorService.findAll();
        List<ProfessorDTO> professorDTOList = professorService.findAllProfessorWithDetails();
        model.addAttribute("professors", professorDTOList);
        System.out.println("교수님 목록 페이지 호출 성공");
        return "admin/professorList";
    }

    //관리자용 - 모든 연구실 리스트 호출
    @GetMapping("/labList")
    public String listProfessorLab(Model model) {
//        List<ProfessorLabDTO> allLab = professorService.findAllLab();
        List<ProfessorLabDTO> allLab = professorService.findAllWithProfessorAndMember();
        model.addAttribute("professorLabs", allLab);
        System.out.println("모든 연구실 목록 호출");
        return "admin/professorLabList";
    }

    //관리자용 - 교수 삭제
    @PostMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  RedirectAttributes redirectAttributes) {
        //관리자 권한 체크
        if(customUserDetails.getMemberEntity().getRole() != Role.ADMIN){
            redirectAttributes.addFlashAttribute("error", "접근 권한x");
            return "redirect:/professor/list";
        }
        System.out.println("교수 삭제 시도: ID = " + id);
        professorService.deleteProfessorById(id);
        redirectAttributes.addFlashAttribute("message", "교수님 삭제 완료");
        return "redirect:/professor/list";
    }

    //관리자용 - 수정 폼 요청
    @GetMapping("/edit/{id}")
    public String showEditProfessorForm(@PathVariable("id") Long id, Model model,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            return "redirect:/";
        }
        ProfessorDTO professorDTO = professorService.getProfessorById(id);
        model.addAttribute("professor", professorDTO);
        return "admin/editProfessor";
    }
    //관리자용 - 수정 요청 처리
    @PostMapping("/update/{id}")
    public String updateProfessor(@PathVariable("id") Long id,
                                  @ModelAttribute ProfessorDTO professorDTO,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "접근 권한이 없습니다.");
            return "redirect:/professor/list";
        }
        professorService.updateProfessor(id, professorDTO);
        redirectAttributes.addFlashAttribute("message", "교수 정보가 수정되었습니다.");
        return "redirect:/professor/list";
    }

    //관리자용 - 연구실 수정 폼 요청
    @GetMapping("/editLab/{id}")
    public String showEditLabForm(@PathVariable("id") Long labId, Model model,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            return "redirect:/";
        }
        ProfessorLabDTO professorLabDTO = professorService.findLabById(labId);
        model.addAttribute("lab", professorLabDTO);
        return "admin/editLab";
    }
    //관리자용 - 연구실 수정 요청 처리
    @PostMapping("/editLab/{id}")
    public String updateLab(@PathVariable("id") Long labId,
                            @ModelAttribute("lab") ProfessorLabDTO professorLabDTO,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "권하니 없습니다.");
            return "redirect:/";
        }
        professorService.updateLab(labId, professorLabDTO);
        redirectAttributes.addFlashAttribute("message", "연구실 정보가 수정되었습니다.");
        return "redirect:/professor/labList";
    }
    //관리자용 - 연구실 삭제 처리
    @PostMapping("/deleteLab/{id}")
    public String deleteLab(@PathVariable("id") Long labId,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                            RedirectAttributes redirectAttributes) {
        if (customUserDetails.getMemberEntity().getRole() != Role.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "권한이 없음");
            return "redirect:/";
        }
        professorService.deleteLabById(labId);
        redirectAttributes.addFlashAttribute("message", "연구실 삭제 완료");
        return "redirect:/professor/labList";
    }
    //교수님 상세페이지
    @GetMapping("/detail/{id}")
    public String professorDetail(@PathVariable("id") Long id, Model model,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Role role = customUserDetails.getMemberEntity().getRole();

        ProfessorDTO professor = professorService.getProfessorById(id);

        //각 교수마다 작성된 게시글 불러오기
        List<BoardDTO> boardList = boardService.professorByBoard(id);

        //교수가 작성한 연구실 소개글 불러오기
        ProfessorLabDTO professorLab = professorService.findByLab(id);
        //로그 확인용
        System.out.println("professorLab정보 = " + professorLab);

        //교수마다 평가된 점수 평균점수 불러오기
        Double avgScore = professorService.getAverageScoreForProfessor(id);
        //교수의 연구실 관심 수 불러오기
        int labLikesCount = professorService.getlabLikesByProfessor(id);

        model.addAttribute("role", role);
        model.addAttribute("professor", professor);
        model.addAttribute("boardList", boardList);
        model.addAttribute("professorLab", professorLab);
        model.addAttribute("avgScore", avgScore);
        model.addAttribute("labLikesCount", labLikesCount);
        return "board/professorDetail";
    }

    //추천 요청 처리
    @PostMapping("/recommend/{id}")
    @ResponseBody
    public String recommendProfessor(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //로그인된 회원 정보 가져오기
        String loginEmail = customUserDetails.getMemberEntity().getMemberEmail();
        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail);
            boolean isAdded = professorService.recommendProfessor(id, memberDTO.getId());

            return isAdded ? "added" : "canceled"; // JS에서 이 응답을 보고 알림 띄움
        } else {
            return "unauthorized";
        }
    }
    //추천 수 가져오는 api 요청
    @GetMapping("/likesCount/{id}")
    @ResponseBody
    public int getLikesCount(@PathVariable("id") Long id) {
        return professorService.getLikesCount(id);
    }
    //추천 여부 확인하는 api 요청
    @GetMapping("/isRecommended/{id}")
    @ResponseBody
    public boolean isRecommended(@PathVariable Long id,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getMemberEntity().getMemberEmail();
        MemberDTO member = memberService.findByEmail(email);
        return professorService.hasRecommended(id, member.getId());
    }

    //연구실 관심 추가 처리
    @PostMapping("/lab/recommend/{id}")
//    @ResponseBody
    public String recommendLab(@PathVariable("id") Long labId,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails,
                               RedirectAttributes redirectAttributes) {
        if(customUserDetails != null) {
            MemberDTO memberDTO = memberService.findByEmail(customUserDetails.getMemberEntity().getMemberEmail());

            System.out.println("로그인한 사용 ID: " + memberDTO.getId());
            //labId → professorId 리턴 받음
            Long professorId = professorService.recommendLab(labId, memberDTO.getId());

            redirectAttributes.addAttribute("id", professorId);
            return "redirect:/professor/" + professorId + "/lab";
        }else {
            return "redirect:/";
        }
    }
    //연구실 정보 페이지 호출
    @GetMapping("{id}/lab")
    public String showLabProfessor(@PathVariable("id") Long professorId, Model model,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String loginEmail = customUserDetails.getMemberEntity().getMemberEmail();
        ProfessorLabDTO professorLabDTO = professorService.findByLab(professorId);
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);

        //로그 확인용
        System.out.println("professorId = " + professorId);

        model.addAttribute("professorLabDTO", professorLabDTO);
        model.addAttribute("loginEmail", memberDTO);
        return "board/professorLab";
    }

    // 연구실 생성 폼 요청 - 교수 선택 포함
    @GetMapping("/createLab")
    public String showCreateLab(Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String loginEmail = customUserDetails.getMemberEntity().getMemberEmail();
        if (loginEmail != null) {
            List<ProfessorDTO> professors = professorService.findAll();
            MemberDTO memberDTO = memberService.findByEmail(loginEmail);

            model.addAttribute("professors", professors);
            model.addAttribute("memberDTO", memberDTO);
            model.addAttribute("professorLabDTO", new ProfessorLabDTO());
            return "admin/addLabs";
        }else {
            return "redirect:/";
        }
    }

    //연구실 작성 폼 전달
    @PostMapping("/createLab/{id}")
    public String createLab(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @ModelAttribute ProfessorLabDTO professorLabDTO,
                            @PathVariable("id") Long professorId,
                            Model model) {
        String loginEmail = customUserDetails.getMemberEntity().getMemberEmail();
        if (loginEmail != null) {
            //교수 정보 가져옴
            ProfessorDTO professorById = professorService.getProfessorById(professorId);
            MemberDTO memberDTO = memberService.findByEmail(loginEmail);

            professorLabDTO.setProfessorDTO(professorById);
            professorLabDTO.setMemberDTO(memberDTO);

            //로그 확인용
            System.out.println("저장 전, professorLabDTO: " + professorById);
            ProfessorLabDTO savedDTO = professorService.save(professorLabDTO);
            System.out.println("저장 후, professorLabDTO: " + savedDTO);

            model.addAttribute("message", "연구실 정보 입력 완료");
            model.addAttribute("url", "/professor/labList");
            return "layouts/message";
        } else {
            return "redirect:/member/login";
        }
    }
    //필요없어진 코드
    /*@PostMapping("/recommend/{id}")
    @ResponseBody // JSON 응답을 위해 @ResponseBody 추가
    public ResponseEntity recommendProfessor(@PathVariable("id") Long id,
                                             @RequestParam("")
                                             HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail);
            professorService.recommendProfessor(id, memberDTO.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }*/
    /*
    //연구실 추천 수 가져오는 api 요청
    @GetMapping("/labLikesCount/{id}")
    @ResponseBody
    public int getLabLikesCount(@PathVariable("id") Long id) {
        return professorService.getLabLikesCount(id);
    }
    //연구실 추천 여부 확인하는 api요청
    @GetMapping("/isLabRecommended/{id}")
    @ResponseBody
    public boolean isLabRecommended(@PathVariable Long id,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getMemberEntity().getMemberEmail();
        MemberDTO member = memberService.findByEmail(email);
        return professorService.hasLabRecommended(id, member.getId());
    }*/
}

