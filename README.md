# 📖교수님 백과사전[v1, v2] - 교수 평가/추천 시스템(Spring Boot 기반 프로젝트)

**Java / Spring Boot / Spring Data JPA / Thymeleaf / OAuth2 / AJAX 기반 메인 프로젝트**

> 📬 이메일: heyfer6867@gmail.com

> 💼 포트폴리오:  [(v1)-자세히보기](https://unique-income-725.notion.site/v2-35886184f1134aaebc02db5441b9508d?source=copy_link) / [(v2)-자세히보기](https://unique-income-725.notion.site/v1-141042ddfb21400796d8bfb57cb196e8?source=copy_link) 
---

## 💡 프로젝트 소개
> **[v1]** : Spring Boot 기반으로 회원가입, 로그인, 교수 CRUD, 게시판, 마이페이지 등 핵심 기능을 구현  
> **[v2]** : v1보다 더 다양한 기능 추가 및 인증/인가 처리 추가  
> Spring Boot 기반의 교수 정보, 강의 후기, 연구실 소개 및 추천 기능을 제공하는 웹 애플리케이션입니다. 교수 정보, 강의 후기, 추천 및 연구실 정보를 사용자들과 공유하는 사이트입니다.

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
#### 개발 기간 - 2024.10.03 ~ 2025.02.21
#### 📌 기능 추가 - 개인 프로젝트 
- 스프링 시큐리티 기반 로그인 및 소셜 로그인
- 회원가입 중복 검사, 회원정보 수정 및 탈퇴
- 관리자 전용 교수/연구실 CRUD 기능
- 교수 추천 기능 (중복 방지, Ajax 처리)
- 연구실 관심 기능 (토글 방식, Ajax 처리)
- 게시글 검색, 교수 선택 강의평 등록
- 로그인 사용자만 댓글 작성 가능 (Ajax 처리)

## 🔗 구현 기능 / 제약 조건

<img alt="image" src="https://github.com/user-attachments/assets/f0bf9ba3-c52d-42e6-a263-51f7e94c1443" />
<img alt="image" src="https://github.com/user-attachments/assets/56e22cb7-04f7-4ba3-b629-6af9a59e1e5e" />


## 📌 프로젝트 목표 및 학습 포인트
- 다양한 JPA 연관관계 매핑
- N + 1 문제 해결을 위한 쿼리 튜닝
- 기능확장과 성능 최적화
- 인증 및 보안 강화

## 📎 기타 참고
- MVC 구조를 벗어나 RESTful API 형식으로 구성 필요 -> 백엔드와 프론트엔드 분리 필요요
- CI/CD, 배포는 포함되지 않음 (추후 EC2 + Docker 목표)

## 🧩 시연 영상




### ✅ 회원 기능
- 회원가입 / 로그인 (소셜 로그인 포함)
- 마이페이지: 이름/비밀번호 수정, 회원 탈퇴
- 내가 작성한 게시글 목록 확인 및 삭제/수정 가능
  
<img width="400" alt="image" src="https://github.com/user-attachments/assets/4acbb1fc-8f18-44a4-a7d6-834d1e93b43f" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/a5820f6b-e1dd-4c52-b4ef-69cfd48d90bf" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/d6ae5e00-62dd-4855-8cf9-538de12ae0ed" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/1d961392-cfc1-47ec-b525-73345c0da859" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/c81bc6c9-eb30-45e0-bd28-8bb9b1cf5eac" />


### ✅ 교수 상세 보기 + 추천 + 연구실 관심
- 교수 상세 페이지에서 추천 수, 평균 평가 점수, 연구실 관심 수 확인 가능
- 교수별 강의평 게시글 리스트 제공
  - 교수 추천: 1인 1추천, 중복 추천 방지
  - 이미 추천한 경우 "추천을 취소하시겠습니까?" 확인창
- 연구실 관심 추가: 중복 여부 확인 및 토글 처리
- 모두 실시간 Ajax 처리 (화면 리로딩 없이 반영됨)
  
<img width="400" alt="image" src="https://github.com/user-attachments/assets/a24bc665-6cd8-4c90-8165-06aaac69ca5b" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/3b3f7ea6-b927-48a3-afe8-f35d753cf360" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/9eeebcae-74fe-4ade-9817-402721d938a0" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/61830623-9a7c-4a83-b606-8081c28f5b0b" />



### ✅ 게시판 기능 + 댓글 기능(강의 평가)
- 교수 선택 후 강의평 등록 (출석, 강의력, 성적 평가 포함)
- 게시글 리스트 조회 / 상세보기 / 수정 / 삭제
- 제목 + 내용 기반 검색 기능 구현
- 로그인한 사용자만 댓글 작성 가능
- 실시간 비동기 댓글 등록/출력 (Ajax 활용)
<img width="400" alt="image" src="https://github.com/user-attachments/assets/6c4595e4-e3dd-46e0-8ff7-debe1d2a7b97" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/e6b64dbb-c6ff-44b8-a38b-28fe57694be6" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/97469cda-be90-40ce-a2b8-e3751b6ca6dd" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/7b1b2dd7-3974-4cc2-8ba9-529e376f9564" />


### ✅ 관리자 전용
- 모든 회원 조회 및 삭제
- 모든 교수 조회, 수정, 삭제, 추가
- 모든 연구실 조회, 수정, 삭제, 추가

<img width="400" alt="image" src="https://github.com/user-attachments/assets/b0ade6d4-d334-46d7-9c94-bb7b32badf71" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/b4c0222b-21c2-48df-96cb-bbb27e3e5df4" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/29f06420-f35a-483a-9823-14ff1608005d" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/80320159-d2aa-458e-8517-32eff9ceb681" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/75b23ceb-a04a-4805-a718-9bb80ef11f77" />


---

## 📌 향후 개선 예정 사항

- 소셜 로그인 (카카오, 깃허브 등) 추가 + 비밀번호 및 아이디 찾기 
- 연구실 지원하기 추가, 지원자 수 조회 기능 추가하기
- 추후에는 REST API를 활용해 데이터를 JSON 형식으로 전달하고 프론트엔드와 백엔드를 명확히 분리된 구조로 구성 → 현재는 서버에서 데이터를 직접 View에 렌더링하여 출력하는 방식(MVC 구조)
- AWS나 배포 환경 구성 해서 직접 배포, 운영까지 해보는게 목표

