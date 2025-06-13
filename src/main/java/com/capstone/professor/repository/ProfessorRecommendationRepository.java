package com.capstone.professor.repository;

import com.capstone.professor.entity.ProfessorRecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//교수님 추천 기능
@Repository
public interface ProfessorRecommendationRepository extends JpaRepository<ProfessorRecommendationEntity, Long> {
    boolean existsByProfessorIdAndMemberId(Long professorId, Long memberId);

    void deleteByProfessorIdAndMemberId(Long professorId, Long memberId);
}
