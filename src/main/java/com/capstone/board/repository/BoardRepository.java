package com.capstone.board.repository;

import com.capstone.board.entity.BoardEntity;
import com.capstone.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    // 특정 회원이 작성한 모든 게시글 조회
    List<BoardEntity> findByMemberEntity(MemberEntity memberEntity);

    //주어진 멤버ID에 해당하는 게시글을 조회
    List<BoardEntity> findAllByMemberEntityId(Long memberId);

    //주어진 교수ID에 해당하는 모든 게시글 조회
    List<BoardEntity> findAllByProfessorEntityId(Long professorId);

    //jpql 사용해서 커스텀 쿼리 만들기
    //Fetch Join을 사용하여 게시글과 작성(MemberEntity)를 한번에 가져오기
    @Query("select b from BoardEntity  b join fetch b.memberEntity where b.id = :id")
    Optional<BoardEntity> findByIdWithMember(@Param("id") Long id);

    // 게시글 제목이나 내용으로 검색
    @Query("select b from BoardEntity b where b.boardTitle like %:keyword% or b.boardContents like %:keyword%")
    List<BoardEntity> searchByKeyword(@Param("keyword") String keyword);

    //교수 id로 평균 점수 조회
    @Query("select avg (b.totalScore) from BoardEntity b where b.professorEntity.id = :professorId")
    Double findAverageByProfessorId(@Param("professorId")Long professorId);
}














