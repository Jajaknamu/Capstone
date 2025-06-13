package com.capstone.board;


import com.capstone.board.dto.BoardDTO;
import com.capstone.board.entity.BoardEntity;
import com.capstone.board.repository.BoardFileRepository;
import com.capstone.board.repository.BoardRepository;
import com.capstone.board.service.BoardService;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.service.MemberService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private BoardFileRepository boardFileRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private MultipartFile mockFile;

    private BoardDTO boardDTO;

    @Before
    public void setUp() {
        //기본 테스트용 BoardDto 객체 생성
        boardDTO = new BoardDTO();
        boardDTO.setBoardWriter("tester");
        boardDTO.setBoardPassword("1234");
        boardDTO.setBoardTitle("Test Title");
        boardDTO.setBoardContents("Test Contents");

        //파일이 첨부되지 않은 경우를 기본 설정
        when(mockFile.isEmpty()).thenReturn(true);
        boardDTO.setBoardFile(mockFile);

        //MemberDTO 및 MemberEntity 모킹 설정
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test@email.com");
        boardDTO.setMemberDTO(memberDTO);

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail("test@email.com");
        when(memberService.findByEmail("tester")).thenReturn(memberDTO);
        when(memberService.findByEmail("test@email.com")).thenReturn(memberDTO);
    }

    @Test
    public void boardsaved() throws Exception {
        //given
        BoardEntity savedEntity = new BoardEntity();
        savedEntity.setId(1L);
        when(boardRepository.save(Mockito.any(BoardEntity.class))).thenReturn(savedEntity);

        //when
        boardService.save(boardDTO);

        //then
        ArgumentCaptor<BoardEntity> boardEntityCaptor = ArgumentCaptor.forClass(BoardEntity.class);
        verify(boardRepository, times(1)).save(boardEntityCaptor.capture());

        BoardEntity capturedBoardEntity = boardEntityCaptor.getValue();

        Assert.assertNotNull(capturedBoardEntity);
        assertEquals("tester", capturedBoardEntity.getBoardWriter());
        assertEquals("1234", capturedBoardEntity.getBoardPassword());
        assertEquals("Test Title", capturedBoardEntity.getBoardTitle());
        assertEquals("Test Contents", capturedBoardEntity.getBoardContents());
    }
}
