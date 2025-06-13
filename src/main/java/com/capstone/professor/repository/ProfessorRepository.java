package com.capstone.professor.repository;

import com.capstone.professor.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
    //특정 id를 가진 교수님을 조회하는 쿼리
    List<ProfessorEntity> findByIdIn(List<Long> ids);

    //추천 수를 기준으로 정렬하여 모든 교수 목록 가져오기
    List<ProfessorEntity> findAllByOrderByLikesCountDesc();

    //전공별 교수님 목록 가져옴
    List<ProfessorEntity> findByMajor(String major);

    //모든 교수님 목록 조회 - 최적화용
    @Query("select distinct p from ProfessorEntity p " +
            "left join fetch p.careers " +
            "left join fetch p.educations " +
            "left join fetch p.publications " +
            "left join fetch p.labs ")
    List<ProfessorEntity> findAllWithDetails();
}
