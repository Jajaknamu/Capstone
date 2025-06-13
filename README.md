# 📖교수님 백과사전[v1, v2]

> Spring Boot 기반 강의 평가 / 교수 추천 서비스

> 📬 이메일: heyfer6867@gmail.com  
> 💼 포트폴리오: [노션 링크](https://unique-income-725.notion.site/13bdee6a325180cfafb8da847116b416?v=fd4325b653464aff8d04999ec724b9c1&pvs=4)  
---

## 💡 프로젝트 소개
교수 정보, 강의 후기, 추천 및 연구실 정보를 사용자들과 공유하는 사이트입니다.  
학생들이 직접 교수님에 대한 평가를 남기고, 관심 있는 연구실 정보를 확인하며 지원할 수 있도록 도와줍니다.   
Spring Boot 기반으로 회원가입, 로그인, 교수 CRUD, 게시판, 마이페이지 등 핵심 기능을 구현하였으며, 
Spring Security를 이용한 인증/인가 처리와 JPA를 활용한 데이터베이스 연동 구조로 설계되었습니다.  
추천/관심 기능과 마이페이지는 서버 사이드 렌더링(SSR)을 기반으로 하되 일부 동적 기능은 JavaScript와 Ajax를 활용하여 사용자 경험을 개선했습니다.

## 👥 개발 인원 및 기간

### 1️⃣교수님 백과사전.v1 
#### ⏱️ 개발 기간 - 2023.02.28 ~ 2023.06.25
### 📌 역할 및 기능 - 팀 프로젝트
- 팀장-정지은(백엔드) : 회원가입, 관리자, 게시판 총괄개발(CRUD), 통합 및 관리, 발표, 기획
- 팀원-진oo(프론트엔드) : 회원가입 및 로그인 CSS,문서 작성
- 팀원-이oo(프론트엔드) : 게시판, 마이페이지, 교수 프로필 그 외 모든 CSS,PPT 작성, 기획, 문서 작성
  
### 2️⃣교수님 백과사전.v2 
#### ⏱️ 개발 기간 - 2024.10.03 ~ 2025.02.21
### 📌 기능 추가 - 개인 프로젝트 
- 스프링 시큐리티 기반 로그인 및 소셜 로그인
- 회원가입 중복 검사, 회원정보 수정 및 탈퇴
- 관리자 전용 교수/연구실 CRUD 기능
- 교수 추천 기능 (중복 방지, Ajax 처리)
- 연구실 관심 기능 (토글 방식, Ajax 처리)
- 게시글 검색, 교수 선택 강의평 등록
- 로그인 사용자만 댓글 작성 가능 (Ajax 처리)

## ⚙️ 개발 환경
- **Language** : Java 17
- **Framework** : Spring Boot 3.2.5
- **Template Engine** : Thymeleaf
- **ORM** : Spring Data JPA
- **Build Tool** : Gradle
- **Database** : MySQL
- **IDE** : IntelliJ IDEA
- **Version Control** : Git / GitHub

---

## 🧩 기술 스택

| Category    | Stack                                      |
|-------------|--------------------------------------------|
| Language    | Java 17                                    |
| Framework   | Spring Boot, Spring Security               |
| DB          | MySQL, Spring Data JPA                     |
| Frontend    | Thymeleaf, HTML/CSS, JavaScript, AJAX      |
| Tools       | IntelliJ, Git, GitHub, MySQL Workbench     |

---

## 📌 주요 기능 / 시연 이미지

### ✅ 회원 기능
- 회원가입 / 로그인 (소셜 로그인 포함)
- 마이페이지: 이름/비밀번호 수정, 회원 탈퇴
- 내가 작성한 게시글 목록 확인 및 삭제/수정 가능
<img width="400" alt="image" src="https://github.com/user-attachments/assets/718401a0-47fa-43a2-be79-ccdfb49a5d19" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/72ea203e-2c22-45d1-a253-9aa19bc5451d" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/8cc22e47-8912-43a6-b0ca-8fbbf32632da" />

### ✅ 교수 상세 보기 + 추천 + 연구실 관심
- 교수 등록 / 수정 / 삭제 (관리자 전용)
- 교수 상세 페이지에서 추천 수, 평균 평가 점수, 연구실 관심 수 확인 가능
- 교수별 강의평 게시글 리스트 제공
- - 교수 추천: 1인 1추천, 중복 추천 방지
  - 이미 추천한 경우 "추천을 취소하시겠습니까?" 확인창
- 연구실 관심 추가: 중복 여부 확인 및 토글 처리
- 모두 실시간 Ajax 처리 (화면 리로딩 없이 반영됨)
<img width="400" alt="image" src="https://github.com/user-attachments/assets/38ed675a-e300-42a3-85b7-a851745aace6" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/aa347816-e723-47a8-8f0c-43c443c72b30" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/12c82aab-85df-409d-9ef8-630c9b854d20" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/8bfd54f7-0829-43ce-9fd0-997d2101872d" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/d7a83600-8217-4008-98f4-06b03993e05d" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/2457bcd1-ce5d-4f6f-a649-df4ab2eee791" />

### ✅ 게시판 기능 + 댓글 기능(강의 평가)
- 교수 선택 후 강의평 등록 (출석, 강의력, 성적 평가 포함)
- 게시글 리스트 조회 / 상세보기 / 수정 / 삭제
- 제목 + 내용 기반 검색 기능 구현
- 로그인한 사용자만 댓글 작성 가능
- 실시간 비동기 댓글 등록/출력 (Ajax 활용)
<img width="400" alt="image" src="https://github.com/user-attachments/assets/6e07c69d-f41c-44a3-8778-e9e5b4c05fb4" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/ccda7d6b-7fa3-4b53-ba95-c0a04a0aa572" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/4c59438c-5907-4d38-b60c-59c1c12507b1" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/0ed9f4d4-3a3b-4f6d-a66a-316ce8110a05" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/dc31f2f0-e473-47bb-b916-4a18cf838783" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/d5a4d2b0-ac9b-4073-a21d-eef130fb8a90" />

### ✅ 관리자 전용
- 모든 회원 조회 및 삭제
- 모든 교수 조회, 수정, 삭제, 추가
- 모든 연구실 조회, 수정, 삭제, 추가

<img width="400" alt="스크린샷 2025-04-11 172241" src="https://github.com/user-attachments/assets/a6cc2402-aa7b-41b3-a4c5-16a5821ff352" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/80096104-d3ef-44d7-9a89-ae99f8da49b0" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/a94f6d20-a10e-4e29-a8ec-0a5dd8ba0c3e" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/2fa52ff3-b573-4d90-bdf8-63148acbae0b" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/3c61d520-a62a-4fc3-a25d-e92a12f8e71b" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/d6d9c1df-7cab-418e-b893-4a00fff54f18" />

---

## 📌 향후 개선 예정 사항

- 소셜 로그인 (카카오, 깃허브 등) 추가 + 비밀번호 및 아이디 찾기 
- 연구실 지원하기 추가, 지원자 수 조회 기능 추가하기
- 추후에는 REST API를 활용해 데이터를 JSON 형식으로 전달하고 프론트엔드와 백엔드를 명확히 분리된 구조로 구성 → 현재는 서버에서 데이터를 직접 View에 렌더링하여 출력하는 방식(MVC 구조)
- AWS나 배포 환경 구성 해서 직접 배포, 운영까지 해보는게 목표

