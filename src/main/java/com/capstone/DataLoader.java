/*
package com.capstone;

import com.capstone.professor.entity.ProfessorCareersEntity;
import com.capstone.professor.entity.ProfessorEducationEntity;
import com.capstone.professor.entity.ProfessorEntity;
import com.capstone.professor.entity.ProfessorPublicationsEntity;
import com.capstone.professor.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProfessorRepository professorRepository;


    */
/*public DataLoader(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository; -> @AllArgsConstructor 어노테이션 사용하면 생성자 자동 생성됨
    }*//*

    @Override
    public void run(String...args) throws Exception{
        // 데이터베이스에 데이터가 없으면 저장
        if (professorRepository.count() == 0) {

            // Professor 1 (1 교수) 추가
            ProfessorEntity professor1 = ProfessorEntity.builder()
                    .professorName("서 교수")
                    .professorEmail("seo@email.com")
                    .major("공과대학 화학공학과")
                    .likesCount(0) // 추천 수 0
                    .build();

            // Professor 1 학력 추가
            ProfessorEducationEntity education1 = ProfessorEducationEntity.builder()
                    .professor(professor1)
                    .education("ㅇㅇ대학교 응용화학부(공학박사)")
                    .build();

            //학력 추가 2
            ProfessorEducationEntity education1_1 = ProfessorEducationEntity.builder()
                    .professor(professor1)
                    .education("ㅇㅇ대학교 화학공학과(공학석사)")
                    .build();
            */
/*//*
/학력 추가 3
            ProfessorEducationEntity education1_2 = ProfessorEducationEntity.builder()
                    .professor(professor1)
                    .education("ㅇㅇ대학교 경영학과(경영학사)")
                    .build();*//*



            // Professor 1 경력 추가
            ProfessorCareersEntity career1 = ProfessorCareersEntity.builder()
                    .professor(professor1)
                    .description("A전자 메모리사업부 공정개발팀")
                    .build();
            // Professor 1 경력 추가 2
            ProfessorCareersEntity career1_1 = ProfessorCareersEntity.builder()
                    .professor(professor1)
                    .description("A전자 광에너지사업팀")
                    .build();

            // Professor 1 논문 추가
            ProfessorPublicationsEntity publication1 = ProfessorPublicationsEntity.builder()
                    .professor(professor1)
                    .title("POLYMER-KOREA 논문")
                    .contents("Field performance analysis of solar cell designs")
                    .build();

            // Professor 1 논문 추가2
            ProfessorPublicationsEntity publication1_1 = ProfessorPublicationsEntity.builder()
                    .professor(professor1)
                    .title("POLYMER-KOREA  논문")
                    .contents("Laponite-Mediated Copper Metallization by Palladium Intercalation")
                    .build();

            // Professor 2 (2 교수) 추가 =======================
            ProfessorEntity professor2 = ProfessorEntity.builder()
                    .professorName("한 교수")
                    .professorEmail("hahah@university.email")
                    .major("공과대학 화학공학과")
                    .likesCount(0) // 추천 수 0
                    .build();

            // Professor 2 학력 추가
            ProfessorEducationEntity education2 = ProfessorEducationEntity.builder()
                    .professor(professor2)
                    .education("ㄱㄱ대 화학공학(공학박사)")
                    .build();
            // Professor 2 학력 추가
            ProfessorEducationEntity education2_1 = ProfessorEducationEntity.builder()
                    .professor(professor2)
                    .education("ㄱㄱ대 화학공학(공학석사)")
                    .build();
            // Professor 2 학력 추가
            ProfessorEducationEntity education2_2 = ProfessorEducationEntity.builder()
                    .professor(professor2)
                    .education("ㅁㅁ대 화학공학(공학사)")
                    .build();

            // Professor 2 경력 추가
            ProfessorCareersEntity career2 = ProfessorCareersEntity.builder()
                    .professor(professor2)
                    .description("시스템연구단")
                    .build();
            // Professor 2 경력 추가2
            ProfessorCareersEntity career2_1 = ProfessorCareersEntity.builder()
                    .professor(professor2)
                    .description("화학시스템연구단")
                    .build();

            // Professor 2 논문 추가
            ProfessorPublicationsEntity publication2 = ProfessorPublicationsEntity.builder()
                    .professor(professor2)
                    .title("공업화학 논문")
                    .contents("제올라이트 쉬트 담지 백금촉매의 제조 및 톨루엔 연소 특성")
                    .build();

            // Professor 3(3 교수) 추가========================
            ProfessorEntity professor3 = ProfessorEntity.builder()
                    .professorName("모 교수")
                    .professorEmail("mo@university.email")
                    .major("공과대학 화학공학과")
                    .likesCount(0) // 추천 수 0
                    .build();

            // Professor 3 학력 추가
            ProfessorEducationEntity education3 = ProfessorEducationEntity.builder()
                    .professor(professor3)
                    .education("ㄷㄷ대학교 석유화학과(공학박사)")
                    .build();
            // Professor 3 학력 추가
            ProfessorEducationEntity education3_1 = ProfessorEducationEntity.builder()
                    .professor(professor3)
                    .education("ㄷㄷ대학교 석유화학과(이학석사)")
                    .build();


            // Professor 3 경력 추가
            ProfessorCareersEntity career3 = ProfessorCareersEntity.builder()
                    .professor(professor3)
                    .description("A사 화학연구팀")
                    .build();

            // Professor 3 논문 추가
            ProfessorPublicationsEntity publication3 = ProfessorPublicationsEntity.builder()
                    .professor(professor3)
                    .title("공업화학 논문")
                    .contents("천연 수활석의 이산화황 흡수 성능에 대한 수열처리의 효과")
                    .build();
            // Professor 3 논문 추가
            ProfessorPublicationsEntity publication3_1 = ProfessorPublicationsEntity.builder()
                    .professor(professor3)
                    .title("공업화학 논문")
                    .contents("DUP가 있는 위그선의 공력학 특성 및 고도 안정성")
                    .build();

            // Professor 4 (4 교수) 추가 ======================================
            ProfessorEntity professor4 = ProfessorEntity.builder()
                    .professorName("팜 교수")
                    .professorEmail("pam@university.email")
                    .major("공과대학 화학공학과")
                    .likesCount(0) // 추천 수 0
                    .build();

            // Professor 4 학력 추가
            ProfessorEducationEntity education4 = ProfessorEducationEntity.builder()
                    .professor(professor4)
                    .education("ㅁㅁ대 화학공학(공학박사)")
                    .build();

            // Professor 4 경력 추가
            ProfessorCareersEntity career4 = ProfessorCareersEntity.builder()
                    .professor(professor4)
                    .description("한국공업화학회 ACE 편집이사")
                    .build();
            // Professor 4 경력 추가2
            ProfessorCareersEntity career4_1 = ProfessorCareersEntity.builder()
                    .professor(professor4)
                    .description("한국과학기술연구원(KIST) 공정개발연구실")
                    .build();

            // Professor 4 논문 추가
            ProfessorPublicationsEntity publication4 = ProfessorPublicationsEntity.builder()
                    .professor(professor4)
                    .title("공업화학 논문")
                    .contents("열플라즈마를 이용한 나노분말 제조")
                    .build();

            // Professor 5 (5 교수) 추가 ====================================
            ProfessorEntity professor5 = ProfessorEntity.builder()
                    .professorName("이 교수")
                    .professorEmail("eiei@university.email")
                    .major("공과대학 화학공학과")
                    .likesCount(0) // 추천 수 0
                    .build();

            // Professor 5 학력 추가
            ProfessorEducationEntity education5 = ProfessorEducationEntity.builder()
                    .professor(professor5)
                    .education("ㅁㅁ대 화학공학(공학박사)")
                    .build();

            // Professor 5 경력 추가
            ProfessorCareersEntity career5 = ProfessorCareersEntity.builder()
                    .professor(professor5)
                    .description("화학공학개론 집필")
                    .build();

            // Professor 5 논문 추가
            ProfessorPublicationsEntity publication5 = ProfessorPublicationsEntity.builder()
                    .professor(professor5)
                    .title("공업화학  논문")
                    .contents("자동차용 팰트로부터 방출되는 휘발성 유기화합물의 저감 연구")
                    .build();

            // List에 학력, 경력, 논문 추가 (양방향 연관 관계 설정)
            professor1.setEducations(Arrays.asList(education1, education1_1));
            professor1.setCareers(Arrays.asList(career1,career1_1));
            professor1.setPublications(Arrays.asList(publication1, publication1_1));

            professor2.setEducations(Arrays.asList(education2, education2_1, education2_2));
            professor2.setCareers(Arrays.asList(career2, career2_1));
            professor2.setPublications(Arrays.asList(publication2));

            professor3.setEducations(Arrays.asList(education3, education3_1));
            professor3.setCareers(Arrays.asList(career3));
            professor3.setPublications(Arrays.asList(publication3,publication3_1));

            professor4.setEducations(Arrays.asList(education4));
            professor4.setCareers(Arrays.asList(career4,career4_1));
            professor4.setPublications(Arrays.asList(publication4));

            professor5.setEducations(Arrays.asList(education5));
            professor5.setCareers(Arrays.asList(career4,career5));
            professor5.setPublications(Arrays.asList(publication5));


            // 데이터베이스에 저장
            professorRepository.save(professor1);
            professorRepository.save(professor2);
            professorRepository.save(professor3);
            professorRepository.save(professor4);
            professorRepository.save(professor5);

            System.out.println("교수님 데이터가 저장되었습니다.");
        } else {
            System.out.println("이미 교수님 데이터가 존재합니다.");
        }
        */
/*//*
/데이터 있을 경우
        Optional<ProfessorEntity> professorOptional1 = professorRepository.findById(1L);
        if (professorOptional1.isPresent()) {
            ProfessorEntity professor1 = professorOptional1.get();
            // 새로운 학력 추가
            ProfessorEducationEntity newEducation = ProfessorEducationEntity.builder()
                    .professor(professor1)
                    .education("서울대 컴퓨터공학 학사")
                    .build();

            //기존 학력에 리스트 추가
            professor1.getEducations().add(newEducation);

            //데이터베이스 업데이트 저장
            professorRepository.save(professor1);
            System.out.println("김교수 학력 추가");

        }*//*


    }
}
*/
