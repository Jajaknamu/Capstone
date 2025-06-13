package com.capstone.professor.repository;

import com.capstone.professor.entity.ProfessorLabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorLabRepository extends JpaRepository<ProfessorLabEntity, Long> {

    //교수 id로 연구실 정보를 조회
    Optional<ProfessorLabEntity> findByProfessorId(Long professorId);

    //교수id로 해당 교수와 관련된 연구실 관심 수 조회
    @Query("select p.labLikes from ProfessorLabEntity p where p.professor.id = :professorId")
    Optional<Integer> findLabLikesByProfessorId(@Param("professorId")Long professorId);

    //연구실 목록 모두 불러오기 - 최적화
    @Query("select l from ProfessorLabEntity l " +
            "join fetch l.professor p " +
            "join fetch l.member m " +
            "left join fetch p.careers " +
            "left join fetch p.educations " +
            "left join fetch p.publications")
    List<ProfessorLabEntity> findAllWithProfessorAndMember();
}
