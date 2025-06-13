package com.capstone.board.service;

import com.capstone.board.dto.CommentDTO;
import com.capstone.board.entity.BoardEntity;
import com.capstone.board.entity.CommentEntity;
import com.capstone.board.repository.BoardRepository;
import com.capstone.board.repository.CommentRepository;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            //MemberEntitu 조회
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getMemberId());
            if (optionalBoardEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                //성공 시 댓글 저장 실행
                CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity,memberEntity);
                return commentRepository.save(commentEntity).getId();
            }else {
                //작성자 조회 실패
                return null;
            }
        } else { //조회 실패
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId, Long memberId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId, memberId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}
