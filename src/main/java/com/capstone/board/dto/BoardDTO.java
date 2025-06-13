package com.capstone.board.dto;

import com.capstone.board.entity.BoardEntity;
import com.capstone.member.dto.MemberDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean,         Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {

    private Long id; // 데이터베이스에 저장된 인덱스 값
    private String boardWriter; //사용자 이름(닉네임)
    private String boardPassword; //사용자 비밀번호
    private String boardTitle; //게시글 제목
    private String boardContents; //게시글 내용
    private int boardHits; //조회수
    private MemberDTO memberDTO;
    private LocalDateTime boardCreatedTime; //게시글 작성 시간
    private LocalDateTime boardUpdatedTime; //게시글 작성 수정 시간

    private Long professorId; // 교수Id
    private String professorName; //교수 이름
    private Integer totalScore; //총 점수
    private Integer creditScore; //성적 점수
    private Integer comfortableScore; // 출석 평가 점수
    private Integer lectureScore; // 강의 평가 점수

    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)


    //entity -> dto 변환
    public static BoardDTO toBoardDTO(BoardEntity boardEntity, Long professorId) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPassword(boardEntity.getBoardPassword());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        //entity -> dto 변환 시 작성자 정보도 함께 설정
        boardDTO.setMemberDTO(MemberDTO.toMemberDTO(boardEntity.getMemberEntity()));

        //점수 관련
        boardDTO.setComfortableScore(boardEntity.getComfortableScore());
        boardDTO.setCreditScore(boardEntity.getCreditScore());
        boardDTO.setLectureScore(boardEntity.getLectureScore());
        boardDTO.setTotalScore(boardEntity.getTotalScore());
        boardDTO.setProfessorName(boardEntity.getProfessorName());

        //professorEntity가 존재하는 경우에만 id 설정
        if (boardEntity.getProfessorEntity() != null) {
            boardDTO.setProfessorId(boardEntity.getProfessorEntity().getId());
            boardDTO.setProfessorName(boardEntity.getProfessorEntity().getProfessorName());
        }

        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            // orginalFileName, storedFileName : board_file_table(BoardFileEntity)
            // join
            // select * from board_table b, board_file_table bf where b.id=bf.board_id
            // and where b.id=?
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }

        return boardDTO;
    }

    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, Integer totalScore, String boardContents) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardContents = boardContents;

        //추가한것
        this.professorName = professorName;
        this.totalScore = totalScore;
        this.creditScore = creditScore;
        this.comfortableScore = comfortableScore;
        this.lectureScore = lectureScore;
    }


    //검색할때 사용할 dto
    public static BoardDTO toBoardSearchDTO(BoardEntity boardEntity) {
        BoardDTO dto = new BoardDTO();
        //필드 매핑
        dto.setId(boardEntity.getId());
        dto.setBoardTitle(boardEntity.getBoardTitle());
        dto.setBoardContents(boardEntity.getBoardContents());
        dto.setBoardWriter(boardEntity.getBoardWriter());
        dto.setBoardCreatedTime(boardEntity.getCreatedTime());
        dto.setBoardHits(boardEntity.getBoardHits());
        return dto;
    }

    /*public static BoardDTO toBoardDTOs(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPassword(boardEntity.getBoardPassword());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());

        //추가한 부분
        boardDTO.setComfortableScore(boardEntity.getComfortableScore());
        boardDTO.setProfessorName(boardEntity.getProfessorName());
        boardDTO.setCreditScore(boardEntity.getCreditScore());
        boardDTO.setLectureScore(boardEntity.getLectureScore());
        boardDTO.setTotalScore(boardEntity.getTotalScore());

        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            // orginalFileName, storedFileName : board_file_table(BoardFileEntity)
            // join
            // select * from board_table b, board_file_table bf where b.id=bf.board_id
            // and where b.id=?
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }

        return boardDTO;
    }*/
}
