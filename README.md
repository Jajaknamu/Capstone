# 📖교수님 백과사전[v1, v2] - 교수 평가/추천 시스템(Spring Boot 기반 프로젝트)

**Java / Spring Boot / Spring Data JPA / Thymeleaf / OAuth2 / AJAX 기반 메인 프로젝트**

> 📬 이메일: heyfer6867@gmail.com

> 💼 포트폴리오:  [v1-자세히보기(Notion)](https://unique-income-725.notion.site/v1-141042ddfb21400796d8bfb57cb196e8?source=copy_link) / [v2-자세히보기(Notion)](https://unique-income-725.notion.site/v2-35886184f1134aaebc02db5441b9508d?source=copy_link) 
---

## 💡 프로젝트 소개
> **[v1]** : Spring Boot 기반으로 회원가입, 로그인, 교수 CRUD, 게시판, 마이페이지 등 핵심 기능을 구현  
> **[v2]** : v1보다 더 다양한 기능 추가 및 인증/인가 처리 추가

> Spring Boot 기반의 교수 정보, 강의 후기, 연구실 소개 및 추천 기능을 제공하는 사이트트입니다. 교수 정보, 강의 후기, 추천 및 연구실 정보를 사용자들과 공유하는 사이트입니다.

## ⚙️ 개발 환경 및 기술 스택

| Category            | Stack                                                                 |
|-----------------    |-----------------------------------------------------------------------|
| **Language**        | Java 17                                                               |
| **Backend**         | Spring Boot 3.3.3, Spring Security, OAuth2 (Google/Naver)             |
| **Frontend**        | Thymeleaf, HTML/CSS, JavaScript, AJAX                                 |
| **Template Engine** | Thymeleaf                                                             |
| **Database / ORM**  | MySQL, Spring Data JPA                                                |
| **Build Tool**      | Gradle                                                                |
| **IDE**             | IntelliJ IDEA                                                         |
| **Tools**           | Git, GitHub, MySQL Workbench                                          |
| **Testing**         |JUnit                                                                  |


## 👥 개발 인원 및 기간

### 1️⃣교수님 백과사전[v1]
#### 개발 기간 - 2023.02.28 ~ 2023.06.25
#### 📌 역할 및 기능 - 팀 프로젝트
- 팀장-정지은(백엔드) : 회원가입, 관리자, 게시판 총괄개발(CRUD), 통합 및 관리, 발표, 기획
- 팀원-진oo(프론트엔드) : 회원가입 및 로그인 CSS,문서 작성
- 팀원-이oo(프론트엔드) : 게시판, 마이페이지, 교수 프로필 그 외 모든 CSS,PPT 작성, 기획, 문서 작성
  
### 2️⃣교수님 백과사전[v2]
#### 개발 기간 - 2024.11.03 ~ 2025.02.21
#### 📌 기능 추가 - 개인 프로젝트 
- 스프링 시큐리티 기반 로그인 및 소셜 로그인
- 회원가입 중복 검사, 회원정보 수정 및 탈퇴
- 관리자 전용 교수/연구실 CRUD 기능
- 교수 추천 기능 (중복 방지, Ajax 처리)
- 연구실 관심 기능 (토글 방식, Ajax 처리)
- 게시글 검색, 교수 선택 강의평 등록
- 로그인 사용자만 댓글 작성 가능 (Ajax 처리)

## 🔗 구현 기능 / 제약 조건

<img alt="image" src="https://github.com/user-attachments/assets/56e22cb7-04f7-4ba3-b629-6af9a59e1e5e" />


## 📌 프로젝트 목표 및 학습 포인트
- 다양한 JPA 연관관계 매핑
- N + 1 문제 해결을 위한 쿼리 튜닝
- 기능확장과 성능 최적화
- 인증 및 보안 강화
- 관리자 전용 페이지 분리

## 📎 기타 참고
- MVC 구조를 벗어나 RESTful API 형식으로 구성 필요 -> 백엔드와 프론트엔드 분리 필요
- CI/CD, 배포는 포함되지 않음 (추후 EC2 + Docker 목표)

## 🧩 시연 영상

> 관리자기능 및 다른 기능의 자세한 시연 화면은 노션 포트폴리오 [v1](https://unique-income-725.notion.site/v1-141042ddfb21400796d8bfb57cb196e8?source=copy_link) / [v2](https://unique-income-725.notion.site/v2-35886184f1134aaebc02db5441b9508d?source=copy_link) 에 포함되어 있습니다.

> https://github.com/user-attachments/assets/18bcb774-ca14-41a3-8e56-a774ed02c42d

## 📌 일부 코드 예시 -> 교수 추천 기능

- 로그인한 사용자가 교수 추천 버튼을 누르면 이미 추천했는지 여부에 따라 추천/추천 취소 가능

```java
// 교수 추천 요청 처리
    @PostMapping("/recommend/{id}")
    @ResponseBody
    public String recommendProfessor(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //로그인된 회원 정보 가져오기
        String loginEmail = customUserDetails.getMemberEntity().getMemberEmail();
        if (loginEmail != null) {
            MemberDTO memberDTO = memberService.findByEmail(loginEmail); // 회원 정보 조회
            boolean isAdded = professorService.recommendProfessor(id, memberDTO.getId()); // 추천 or 추천 취소 처리
  
            return isAdded ? "added" : "canceled"; // // JS 측에서 응답 값을 기준으로 UI 처리
        } else {
            return "unauthorized"; // 비로그인 사용자 처리
        }
    }
```

```java
//교수님 추천 기능 : 이미 추천했으면 취소, 아니면 추천 등록
    @Transactional
    public boolean recommendProfessor(Long professorId, Long memberId) {
        // 이미 추천했는지 확인
        boolean alreadyRecommended = recommendationRepository.existsByProfessorIdAndMemberId(professorId, memberId);

        //교수, 회원 정보 조회
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("교수님 찾을 수 없음"));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 찾을 수 없음"));

        if (alreadyRecommended) {
            // 추천을 이미 했으면 추천 취소
            recommendationRepository.deleteByProfessorIdAndMemberId(professorId, memberId);
            professor.setLikesCount(Math.max(0, professor.getLikesCount() - 1)); //추천 수 감소 (최소 0으로 방어)
            professorRepository.save(professor);
            return false; //추천 취소됨
        } else {
            // 추천하지 않은 경우 → 추천 등록
            ProfessorRecommendationEntity recommendation = ProfessorRecommendationEntity.builder()
                    .professor(professor)
                    .member(member)
                    .build();
            recommendationRepository.save(recommendation);
            professor.setLikesCount(professor.getLikesCount() + 1);  // 추천 수 증가
            professorRepository.save(professor);
            return true;// 추천됨
        }
    }
```


