package com.capstone.member.controller;

import com.capstone.board.dto.BoardDTO;
import com.capstone.board.service.BoardService;
import com.capstone.member.service.MemberService;
import com.capstone.professor.dto.ProfessorDTO;
import com.capstone.professor.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final ProfessorService professorService;
    private final MemberService memberService;

    //첫 화면 - 로그인/회원가입 페이지
    @GetMapping("/")
    public String index() {
        return "/member/login";
    }

    //로그인 후 보이는 페이지 - Home
    @GetMapping("/home")
    public String home(Model model) {
        //최근글 보기 칸에 게시글 보여지기 위함
        List<BoardDTO> board = boardService.findAll();
        model.addAttribute("board", board);
        System.out.println("HomeController에서 실행된 home.html");

        //교수님 추천수 가장 많은 순 보이기
        List<ProfessorDTO> professors = professorService.professorSortedByLikes();
        model.addAttribute("professorLike", professors);

        //현재 스프링 시큐리티 컨텍스트에서 인증된 사용자의 이름(userEmail)을 가져옴
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);

        //현재 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication);

        //사용자의 권한(roles) 컬렉션을 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("authorities = " + authorities);

        //권한 컬렉션을 반복하기 위한 Iterator 생성
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();

        //권한 컬렉션에서 첫 번째 권한을 가져옴
        GrantedAuthority auth = iter.next();
        System.out.println("auth = " + auth);

        //가져온 권한의 문자열 값을 얻어옴(사용자의 role 값이 담겨있음)
        String role = auth.getAuthority();
        System.out.println("role = " + role);

        model.addAttribute("role", role);
        model.addAttribute("username", username);
        return "/layouts/home";
    }

    //추천 수 많은 교수님 차례대로 리스트 요청
    @GetMapping("/likesList")
    public String likeListProfessors(Model model) {
        List<ProfessorDTO> professorLikes = professorService.professorSortedByLikes();
        model.addAttribute("professorLikesList", professorLikes);
        return "layouts/home";
    }

    //경영
    @GetMapping("/home/management")
    public String management(Model model) {
        List<Long> managementProfessorIds = List.of(5L, 6L, 7L, 8L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(managementProfessorIds);
        model.addAttribute("professors", professors);
        return "/Humanities/Management";
    }

    //사복
    @GetMapping("/home/socialwelfare")
    public String socialwelfare(Model model) {
        List<Long> socialwelfareProfessorIds = List.of(9L, 10L, 11L, 12L, 13L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(socialwelfareProfessorIds);
        model.addAttribute("professors", professors);
        return "/Humanities/Socialwelfare";
    }

    //유교
    @GetMapping("/home/childhood")
    public String childhood(Model model) {
        List<Long> childhoodProfessorIds = List.of(17L, 18L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(childhoodProfessorIds);
        model.addAttribute("professors", professors);
        return "/Humanities/Childhood";
    }

    //법경
    @GetMapping("/home/police")
    public String police(Model model) {
        List<Long> policeProfessorIds = List.of(14L, 15L, 16L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Humanities/Police";
    }

    //건축
    @GetMapping("/home/Architecture")
    public String Architecture(Model model) {
        List<Long> architectureProfessorIds = List.of(24L, 25L, 26L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(architectureProfessorIds);
        model.addAttribute("professors", professors);
        return "/Technology/Architecture";
    }

    //화공
    @GetMapping("/home/Chemistry")
    public String Chemistry(Model model) {
        List<Long> chemistryProfessorIds = List.of(27L, 30L, 31L, 32L, 33L);//id가 화공교수인 것
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(chemistryProfessorIds);
        model.addAttribute("professors", professors);
        return "/Technology/Chemistry";
    }

    //컴공
    @GetMapping("/home/Computer")
    public String Computer(Model model) {
        List<Long> computerProfessorIds = List.of(19L, 20L, 21L, 22L, 23L);//id가 컴공교수인 것
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(computerProfessorIds);
        model.addAttribute("professors", professors);
        return "/Technology/Computer";
    }

    //전기전자
    @GetMapping("/home/Electronic")
    public String Electronic(Model model) {
        List<Long> electronicProfessorIds = List.of(27L, 28L);//id가 전자교수인 것
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(electronicProfessorIds);
        model.addAttribute("professors", professors);
        return "/Technology/Electronic";
    }

    //간호
    @GetMapping("/home/Nurse")
    public String Nurse(Model model) {
        List<Long> policeProfessorIds = List.of(14L, 15L, 16L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Health/Nurse";
    }
    //물치
    @GetMapping("/home/PhysicalTherapy")
    public String PhysicalTherapy(Model model) {
        List<Long> policeProfessorIds = List.of(14L, 15L, 16L, 11L, 18L, 34L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Health/PhysicalTherapy";
    }
    //제약
    @GetMapping("/home/Pharmaceutical")
    public String Pharmaceutical(Model model) {
        List<Long> policeProfessorIds = List.of(14L, 15L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Health/Pharmaceutical";
    }
    //임상
    @GetMapping("/home/ClinicalPathology")
    public String ClinicalPathology(Model model) {
        List<Long> policeProfessorIds = List.of(2L, 9L, 12L, 13L, 17L, 20L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Health/ClinicalPathology";
    }

    //디자인
    @GetMapping("/home/Design")
    public String Design(Model model) {
        List<Long> policeProfessorIds = List.of(2L, 9L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Arts/Design";
    }
    //사체
    @GetMapping("/home/Physical")
    public String Physical(Model model) {
        List<Long> policeProfessorIds = List.of(23L, 21L, 17L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Arts/Physical";
    }
    //공연
    @GetMapping("/home/PerformingArts")
    public String PerformingArts(Model model) {
        List<Long> policeProfessorIds = List.of(4L, 5L, 11L);
        List<ProfessorDTO> professors = professorService.getProfessorsByIds(policeProfessorIds);
        model.addAttribute("professors", professors);
        return "/Arts/PerformingArts";
    }

    //사이트맵
    @GetMapping("/home/Sitemap")
    public String SiteMap() {
        return "/layouts/sitemap";
    }
}
