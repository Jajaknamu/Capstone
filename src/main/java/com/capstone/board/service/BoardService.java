package com.capstone.board.service;

import com.capstone.board.dto.BoardDTO;
import com.capstone.board.entity.BoardEntity;
import com.capstone.board.entity.BoardFileEntity;
import com.capstone.board.repository.BoardFileRepository;
import com.capstone.board.repository.BoardRepository;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.service.MemberService;
import com.capstone.professor.entity.ProfessorEntity;
import com.capstone.professor.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberService memberService;
    private final ProfessorRepository professorRepository;

    //게시글 작성 서비스 메서드
    @Transactional
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            ProfessorEntity professorEntity = professorRepository.findById(boardDTO.getProfessorId())
                    .orElseThrow(() -> new IllegalArgumentException("교수님id: " + boardDTO.getProfessorId()));
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO,professorEntity);
            boardRepository.save(boardEntity);
            System.out.println("저장된 게시글의 교수님: " + boardEntity.getProfessorEntity().getProfessorName()); //저장 후 확인

        } else {
            // 첨부 파일 있음.
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
            boardFile.transferTo(new File(savePath)); // 5.
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findByIdWithMember(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }
    }

    //모든 게시글 확인 메서드
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            Long professorId = boardEntity.getProfessorEntity() != null ? boardEntity.getProfessorEntity().getId() : null;
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity,professorId));
        }
        return boardDTOList;
    }

    //조회수 처리 메서드
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    //해당 게시글 디테일 보기 메서드
    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            //게시글에 연결된 교수 정보가 있는 경우, 교수 id 가져옴 or 없다면 null 설정
            Long professorId = boardEntity.getProfessorEntity() != null ? boardEntity.getProfessorEntity().getId() : null;
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity,professorId);
            return boardDTO;
        } else {
            return null;
        }
    }
    //특정 회원이 쓴글 불러오는 메서드
    @Transactional
    public List<BoardDTO> findPostsByMemberID(Long memberId) {
        List<BoardEntity> boardEntities = boardRepository.findAllByMemberEntityId(memberId);
        List<BoardDTO> boardDTOs = boardEntities.stream()
                .map(boardEntity -> {
                    Long professorId = boardEntity.getProfessorEntity() != null ? boardEntity.getProfessorEntity().getId() : null;
                    return BoardDTO.toBoardDTO(boardEntity, professorId);
                })
                .collect(Collectors.toList());
        return boardDTOs;
    }
    //게시글 수정 메서드
    @Transactional
    public BoardDTO update(BoardDTO boardDTO) {
        //memberDTO가 null이 아닌지 확인하고 처리
        if (boardDTO.getMemberDTO() == null){
            MemberDTO memberDTO = memberService.findByEmail(boardDTO.getBoardWriter());
            boardDTO.setMemberDTO(memberDTO);
        }
        //교수 id로 교수 엔티티 찾아서 설정
        ProfessorEntity professorEntity = null; // 교수 엔티티 초기화

        if (boardDTO.getProfessorId() != null) {
            professorEntity = professorRepository.findById(boardDTO.getProfessorId())
                    .orElseThrow(() -> new IllegalArgumentException("id: " + boardDTO.getProfessorId()));
        }
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO,professorEntity);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }
    //게시글 삭제하기
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    //페이징 서비스 메서드
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime(), board.getTotalScore(), board.getBoardContents()));
        return boardDTOS;
    }

    //게시글 검색 서비스 메서드
    public List<BoardDTO> searchBoard(String keyword) {
        List<BoardEntity> boards = boardRepository.searchByKeyword(keyword);
        return boards.stream()
                .map(BoardDTO::toBoardSearchDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    //교수별 작성된 글 조회하는 메서드
    public List<BoardDTO> professorByBoard(Long professorId) {
        List<BoardEntity> professorEntity = boardRepository.findAllByProfessorEntityId(professorId);
        //professorEntity이거 엔티티로 받아왔으니까 dto로 변환해줘야됨.
        return professorEntity.stream()
                .map(entity -> BoardDTO.toBoardDTO(entity, professorId))
                .collect(Collectors.toList());
    }
}














