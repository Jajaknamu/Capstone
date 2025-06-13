package com.capstone.professor.repository;


import com.capstone.professor.entity.ProfessorInterestLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorInterestLabRepository extends JpaRepository<ProfessorInterestLab, Long> {

    //주어진 교수id와 회원id로 추천이 존재하는지 확인하는 메서드
    boolean existsByProfessorLabIdAndMemberId(Long professorLabId, Long memberId);

    //연구실 추천 버튼 한 번 더 누르면 취소하기 위한 메서드
    void deleteByProfessorLabIdAndMemberId(Long professorLabId, Long memberId);
}
