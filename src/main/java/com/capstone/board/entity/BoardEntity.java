package com.capstone.board.entity;

import com.capstone.board.dto.BoardDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.professor.entity.ProfessorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPassword;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private Integer totalScore;
    @Column
    private Integer creditScore;
    @Column
    private Integer comfortableScore;
    @Column
    private Integer lectureScore;
    @Column(name = "professor_name")
    private String professorName;

    //교수 평가서 작성을 위한 연관관계
    /*professor:board = 1:n -> 한 개의 professor에 여러개의 board가 있을 수 있음*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professorEntity;

    //작성자(MemberEntity와의 다대일 관계
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @Column
    private int fileAttached; // 1 or 0

    //첨부파일 테이블
    @OneToMany(mappedBy = "boardEntity",
                cascade = CascadeType.REMOVE,
                orphanRemoval = true,
                fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    //댓글 테이블
    @OneToMany(mappedBy = "boardEntity",
                cascade = CascadeType.REMOVE,
                orphanRemoval = true,
                fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    // DTO를 entity 변환하기 위한 메서드들
    public static BoardEntity toBoardEntity(BoardDTO boardDTO, ProfessorEntity professorEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);

        boardEntity.setTotalScore(boardDTO.getTotalScore());
        boardEntity.setCreditScore(boardDTO.getCreditScore());
        boardEntity.setComfortableScore(boardDTO.getComfortableScore());
        boardEntity.setLectureScore(boardDTO.getLectureScore());

        //member
        boardEntity.setMemberEntity(MemberEntity.toMemberEntity(boardDTO.getMemberDTO()));
        //professor
        boardEntity.setProfessorEntity(professorEntity);
        if (professorEntity != null) {
            boardEntity.setProfessorName(professorEntity.getProfessorName());
        }

        boardEntity.setFileAttached(0); // 파일 없음.
        return boardEntity;
    }
    //dto -> entity 변환
    public static BoardEntity toUpdateEntity(BoardDTO boardDTO, ProfessorEntity professorEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());//id가 있어야 update로 인식, 없으면 -> save로 인식
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits()); //db에 저장된 조회수 값 사용

        boardEntity.setTotalScore(boardDTO.getTotalScore());
        boardEntity.setCreditScore(boardDTO.getCreditScore());
        boardEntity.setComfortableScore(boardDTO.getComfortableScore());
        boardEntity.setLectureScore(boardDTO.getLectureScore());

        // memberDTO가 존재하는지 확인 후 변환
        if (boardDTO.getMemberDTO() != null) {
            boardEntity.setMemberEntity(MemberEntity.toMemberEntity(boardDTO.getMemberDTO()));
        }

        //professorEntity가 null이 아닌 경우에만 설정
        if (professorEntity != null) {
            boardEntity.setProfessorEntity(professorEntity);
            boardEntity.setProfessorName(professorEntity.getProfessorName());
        }

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.
        return boardEntity;
    }
}











