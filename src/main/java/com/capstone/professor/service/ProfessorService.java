package com.capstone.professor.service;

import com.capstone.board.repository.BoardRepository;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import com.capstone.professor.dto.*;
import com.capstone.professor.entity.*;
import com.capstone.professor.repository.ProfessorInterestLabRepository;
import com.capstone.professor.repository.ProfessorLabRepository;
import com.capstone.professor.repository.ProfessorRecommendationRepository;
import com.capstone.professor.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorRecommendationRepository recommendationRepository;
    private final MemberRepository memberRepository;
    private final ProfessorLabRepository professorLabRepository;
    private final ProfessorInterestLabRepository interestLabRepository;
    private final BoardRepository boardRepository;


    //관리자용 - 모든 교수님 리스트 가져오기 메서드 -> 기존 코드
    @Transactional
    public List<ProfessorDTO> findAll() {
        List<ProfessorEntity> professorEntityList = professorRepository.findAll();
        List<ProfessorDTO> professorDTOList = new ArrayList<>();
        for (ProfessorEntity professorEntity : professorEntityList) {
            professorDTOList.add(ProfessorDTO.toProfessorDTO(professorEntity));
        }
        return professorDTOList;
    }

    //관리자용 - 모든 교수 리스트(최적화ver)
    public List<ProfessorDTO> findAllProfessorWithDetails() {
        List<ProfessorEntity> entityList= professorRepository.findAllWithDetails();
        return entityList.stream()
                .map(ProfessorDTO::toProfessorDTO)
                .collect(Collectors.toList());
    }

    //교수님 추천 메서드
    @Transactional
    public boolean recommendProfessor(Long professorId, Long memberId) {
        boolean alreadyRecommended = recommendationRepository.existsByProfessorIdAndMemberId(professorId, memberId);

        //교수, 멤버조회
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("교수님 찾을 수 없음"));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 찾을 수 없음"));

        if (alreadyRecommended) {
            //추천 취소
            recommendationRepository.deleteByProfessorIdAndMemberId(professorId, memberId);
            professor.setLikesCount(Math.max(0, professor.getLikesCount() - 1));// 0 아래로 내려가는거 방지
            professorRepository.save(professor);
            return false; //추천 취소됨
        } else {
            //추천 증가
            ProfessorRecommendationEntity recommendation = ProfessorRecommendationEntity.builder()
                    .professor(professor)
                    .member(member)
                    .build();
            recommendationRepository.save(recommendation);
            professor.setLikesCount(professor.getLikesCount() + 1);
            professorRepository.save(professor);
            return true;// 추천됨
        }
    }
    //추천 수 가져오는 메서드
    public int getLikesCount(Long professorId) {
        return professorRepository.findById(professorId)
                .map(ProfessorEntity::getLikesCount)
                .orElse(0);
    }
    //추천 여부 확인 메서드
    public boolean hasRecommended(Long professorId, Long memberId) {
        return recommendationRepository.existsByProfessorIdAndMemberId(professorId, memberId);
    }

    @Transactional
    //연구실 추천 메서드
    public Long recommendLab(Long professorLabId, Long memberId) {

        System.out.println("💡 전달받은 memberId = " + memberId); //확인
        System.out.println("💡 전달받은 professorLabId = " + professorLabId);

        boolean alreadyRecommended  = interestLabRepository.existsByProfessorLabIdAndMemberId(professorLabId, memberId);

        //연구실 조회
        ProfessorLabEntity professorLabEntity = professorLabRepository.findById(professorLabId)
                .orElseThrow(() -> new IllegalArgumentException("연구실을 찾을 수 없음"));

        //회원 조회
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        MemberEntity memberEntity = optionalMemberEntity.get();

        if(alreadyRecommended) {
            //이미 추천함-> 추천 취소
            interestLabRepository.deleteByProfessorLabIdAndMemberId(professorLabId, memberId);
            professorLabEntity.setLabLikes(Math.max(0,professorLabEntity.getLabLikes() - 1)); // 음수 방지
            System.out.println("추천 취소");
        } else {
            //추천 안함 -> 추천 추가
            ProfessorInterestLab interestLab = new ProfessorInterestLab();
            interestLab.setProfessorLab(professorLabEntity);
            interestLab.setMember(memberEntity);

            interestLabRepository.save(interestLab);
            System.out.println("✅ 추천 저장됨: " + interestLabRepository.findAll());
            professorLabEntity.setLabLikes(professorLabEntity.getLabLikes() + 1);
            System.out.println("추천 추가");
        }
        //연구실 추천 수 업데이트
        professorLabRepository.save(professorLabEntity);

        return professorLabEntity.getProfessor().getId();
    }

    //교수님 상세페이지 메서드
    @Transactional
    public ProfessorDTO getProfessorById(Long id) { //엔티티 dto로 변환해서 사용해야 LazyInitializationException 방지가능
        ProfessorEntity professorEntity = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("교수님 정보 없음"));
        return ProfessorDTO.toProfessorDTO(professorEntity);
    }

    //특정 id를 가진 교수님 목록을 반환하는 메서드
    @Transactional
    public List<ProfessorDTO> getProfessorsByIds(List<Long> ids) { // 여러 교수님의 id 리스트 전달
        //이 리스트에 포함된 id들을 기반으로 db에서 해당 교수님들 조회
        List<ProfessorEntity> professors = professorRepository.findByIdIn(ids);
        return professors.stream()
                //엔티티들 dto로 변환
                .map(ProfessorDTO::toProfessorDTO)
                .collect(Collectors.toList());
    }

    //추천 수 많은 순서대로 교수님 정렬 서비스 메서드
    @Transactional
    public List<ProfessorDTO> professorSortedByLikes() {
        List<ProfessorEntity> professorSorted = professorRepository.findAllByOrderByLikesCountDesc();
        return professorSorted.stream()
                .sorted((p1, p2) -> p2.getLikesCount() - p1.getLikesCount()) //추천 수로 내림차순 정렬
                .limit(3) //상위 3개만 가져오기
                .map(ProfessorDTO::toProfessorDTO)
                .collect(Collectors.toList());
    }

    //모든 전공 목록 가져오는 메소드(단, 전공이 선택된 경우)
    @Transactional
    public List<String> getAllMajors() {
        return Arrays.asList("인문대학 경영학과", "인문대학 사회복지학과", "인문대학 법경찰행정학과", "인문대학 유아교육과",
                             "공과대학 컴퓨터공학과", "공과대학 건축학과", "공과대학 전기공학과", "공과대학 화학공학과");
    }

    //전공별 교수님 조회 서비스
    @Transactional
    public List<ProfessorDTO> getProfessorsByMajor(String major) {
       List<ProfessorEntity> professorByMajor = professorRepository.findByMajor(major);
       return professorByMajor.stream()
               .map(ProfessorDTO::toProfessorDTO)
               .collect(Collectors.toList());
    }
    @Transactional
    //교수님 연구실 정보 가져오는 메서드
    public ProfessorLabDTO findByLab(Long professorId) {
        return professorLabRepository.findByProfessorId(professorId)
                .map(ProfessorLabDTO::toProfessorLabDTO)
                .orElse(null);// 연구실 정보 없을 시, null 반환
    }

    //교수 평가 평균 점수 메서드
    public Double getAverageScoreForProfessor(Long professorId) {
        Double avgScore = boardRepository.findAverageByProfessorId(professorId);
        if (avgScore == null) {
            return 0.0;
        }
        return avgScore;
    }

    //교수 별 관심 연구실 수 조회수 메서드
    public int getlabLikesByProfessor(Long professorId) {
        return professorLabRepository.findLabLikesByProfessorId(professorId)
                .orElse(0);
    }

    /**
     * 관리자 전용 메서드들
     */
    //관리자 전용 - 교수님 연구실 정보 저장 메서드
    public ProfessorLabDTO save(ProfessorLabDTO professorLabDTO) {
        if (professorLabDTO.getProfessorDTO() == null) {
            throw new IllegalArgumentException("교수 정보 필요");
        }
        //여기서 professorId를 가져와서 교수 엔티티 가져옴
        Long professorId = professorLabDTO.getProfessorDTO().getId();
        if (professorId == null) {
            throw new IllegalArgumentException("유효하지 않은 교수 ID");
        }
        Optional<ProfessorEntity> professorEntity = professorRepository.findById(professorId);

        if (professorEntity.isPresent()) {
            ProfessorLabEntity professorLabEntity = ProfessorLabEntity.toProfessorLabEntity(professorLabDTO);
            professorLabRepository.save(professorLabEntity);

            return ProfessorLabDTO.toProfessorLabDTO(professorLabEntity);
        } else {
            throw new IllegalArgumentException("해당 교수님 존재x");
        }
    }
    //관리자용 - 교수 정보 저장 메서드
    public void addProfessor(ProfessorDTO professorDTO) {
        ProfessorEntity professorEntity = ProfessorEntity.builder()
                .professorName(professorDTO.getProfessorName())
                .professorEmail(professorDTO.getProfessorEmail())
                .major(professorDTO.getMajor())
                .likesCount(0)
                .build();
        //커리어 리스트 초기화
        if (professorEntity.getCareers() == null){
            professorEntity.setCareers(new HashSet<>());
        }
        //커리어 추가
        for (ProfessorCareersDTO careerDTO : professorDTO.getCareers()) {
            ProfessorCareersEntity careersEntity = ProfessorCareersEntity.builder()
                    .description(careerDTO.getDescription())
                    .professor(professorEntity)
                    .build();
            professorEntity.getCareers().add(careersEntity);
        }
        //교육 리스트 초기화
        if (professorEntity.getEducations() == null) {
            professorEntity.setEducations(new HashSet<>());
        }
        for (ProfessorEducationDTO educationDTO : professorDTO.getEducations()) {
            ProfessorEducationEntity educationEntity = ProfessorEducationEntity.builder()
                    .education(educationDTO.getEducation())
                    .professor(professorEntity)
                    .build();
            professorEntity.getEducations().add(educationEntity);
        }
        //논문 리스트 초기화
        if (professorEntity.getPublications() == null) {
            professorEntity.setPublications(new HashSet<>());
        }
        for (ProfessorPublicationsDTO publicationsDTO : professorDTO.getPublications()) {
            ProfessorPublicationsEntity publicationsEntity = ProfessorPublicationsEntity.builder()
                    .title(publicationsDTO.getTitle())
                    .contents(publicationsDTO.getContents())
                    .professor(professorEntity)
                    .build();
            professorEntity.getPublications().add(publicationsEntity);
        }
        professorRepository.save(professorEntity);
    }

    //관리자용 - 모든 연구실 목록
    @Transactional
    public List<ProfessorLabDTO> findAllLab() {
        List<ProfessorLabEntity> professorLabEntityList = professorLabRepository.findAll();
        List<ProfessorLabDTO> professorLabDTOList = new ArrayList<>();
        for (ProfessorLabEntity professorLabEntity : professorLabEntityList) {
            professorLabDTOList.add(ProfessorLabDTO.toProfessorLabDTO(professorLabEntity));
        }
        return professorLabDTOList;
    }

    //관리자용 - 모든 연구실 목록(최적화ver)
    @Transactional
    public List<ProfessorLabDTO> findAllWithProfessorAndMember() {
        List<ProfessorLabEntity> all = professorLabRepository.findAllWithProfessorAndMember();
        return all.stream()
                .map(ProfessorLabDTO::toProfessorLabDTO)
                .collect(Collectors.toList());
    }

    //관리자용 - 교수 삭제
    @Transactional
    public void deleteProfessorById(Long id) {
        professorRepository.deleteById(id);

    }

    //관리자용 - 교수 프로필 수정
    @Transactional
    public void updateProfessor(Long id, ProfessorDTO dto) {
        Optional<ProfessorEntity> professor = professorRepository.findById(id);

        professor.get().setProfessorName(dto.getProfessorName());
        professor.get().setProfessorEmail(dto.getProfessorEmail());
        professor.get().setMajor(dto.getMajor());
        professor.get().setLikesCount(dto.getLikesCount());

        // 기존의 경력, 학력, 논문은 지우고 새로 세팅 (단순한 처리 방식)
        professor.get().getCareers().clear();
        for (ProfessorCareersDTO careersDTO : dto.getCareers()) {
            ProfessorCareersEntity career = ProfessorCareersEntity.builder()
                    .description(careersDTO.getDescription())
                    .professor(professor.get())
                    .build();
                    professor.get().getCareers().add(career);
        }
        professor.get().getEducations().clear();
        for (ProfessorEducationDTO edu : dto.getEducations()) {
           ProfessorEducationEntity eduEntity = ProfessorEducationEntity.builder()
                   .education(edu.getEducation())
                   .professor(professor.get())
                   .build();
           professor.get().getEducations().add(eduEntity);
        }
        professor.get().getPublications().clear();
        for (ProfessorPublicationsDTO pub : dto.getPublications()) {
            ProfessorPublicationsEntity pubEntity = ProfessorPublicationsEntity.builder()
                    .title(pub.getTitle())
                    .contents(pub.getContents())
                    .professor(professor.get())
                    .build();
            professor.get().getPublications().add(pubEntity);
        }
        professorRepository.save(professor.get());
    }

    //관리자용 - 연구실 id로 정보 조회
    @Transactional
    public ProfessorLabDTO findLabById(Long labId) {
        ProfessorLabEntity entity = professorLabRepository.findById(labId)
                .orElseThrow(() -> new IllegalArgumentException("해당 연구실 존재x"));
        return ProfessorLabDTO.toProfessorLabDTO(entity);
    }
    //연구실 정보 수정
    @Transactional
    public void updateLab(Long labId, ProfessorLabDTO dto) {
        ProfessorLabEntity lab = professorLabRepository.findById(labId)
                .orElseThrow(() -> new IllegalArgumentException("연구실을 찾을 수 없음"));

        lab.setLabIntro(dto.getLabIntro());
        lab.setLabKeyword(dto.getLabKeyword());
        lab.setLabSupport(dto.getLabSupport());
        lab.setLabRequirements(dto.getLabRequirements());
        lab.setLabTreatment(dto.getLabTreatment());
        professorLabRepository.save(lab);
    }
    //연구실 삭제
    @Transactional
    public void deleteLabById(Long labId) {
        professorLabRepository.deleteById(labId);
    }
}
