package com.capstone.member.service;

import com.capstone.board.entity.BoardEntity;
import com.capstone.board.entity.CommentEntity;
import com.capstone.board.repository.BoardRepository;
import com.capstone.board.repository.CommentRepository;
import com.capstone.member.dto.MemberDTO;
import com.capstone.member.entity.MemberEntity;
import com.capstone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입 서비스 메서드
    public void save(MemberDTO memberDTO) {

        //db에 동일한 회원이 있는지 검증
        boolean isMember = memberRepository.existsByMemberEmail(memberDTO.getMemberEmail());

        if (isMember) {
            System.out.println("이미 존재하는 회원 :" + memberDTO.getMemberEmail());
            return;
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        //encode 메서드 실행하면 비번을 암호화한 후 저장
        memberEntity.setMemberPassword(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        memberEntity.setMemberName(memberDTO.getMemberName());

        memberRepository.save(memberEntity);
    }

    //로그인 기능
    /*1. 회원이 입력한 이메일로 DB에서 조회를 함
     * 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
     * */
    @Transactional
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            //조회결과 있음 -> 해당 이메일을 가진 회원정보 있음
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //비밀번호 검증 -> 일치하는 경우
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity); //entity -> dto 변환 후 반환
                return dto;
            }
            else {
                return null;
            }
        } else {
            //조회 결과 없음 -> 해당 이메일 가진 회원 없음
            return null;
        }
    }

    //MemberService에 이메일로 회원 정보 조회하는 메서드
    public MemberDTO findByEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        // 조회된 회원 엔티티가 존재하는지 확인
        if (optionalMemberEntity.isPresent()) {
            // 존재한다면, 조회된 MemberEntity를 MemberDTO로 변환하여 반환
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); //entity -> dto 변환 후 반환
        } else {
            return null;
        }
    }

    //내 정보 수정하는 폼 받아오는 서비스 메서드
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            System.out.println("회원정보 수정하는 폼 요청하는 service 메서드");
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());//optional안에 있는 실제 MemberEntity객체를 반환해줌
        }else {
            return null;
        }
    }
    //내 정보 수정하는 서비스 메서드
    public void update(MemberDTO memberDTO) {
        Optional<MemberEntity> existingMemberOptional = memberRepository.findById(memberDTO.getId());

        if(existingMemberOptional.isPresent()) {
            MemberEntity existingMember = existingMemberOptional.get();

            //일반 폼 로그인 사용자(provider == null)
            if(existingMember.getProvider() == null){
                System.out.println("일반 로그인 회원 정보 수정");

                existingMember.setMemberEmail(memberDTO.getMemberEmail());
                existingMember.setMemberPassword(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
                existingMember.setMemberName(memberDTO.getMemberName());

                memberRepository.save(existingMember);

                // 사용자가 작성한 모든 게시글의 작성자 이름 변경
                List<BoardEntity> boardList = boardRepository.findByMemberEntity(existingMember);
                for (BoardEntity board : boardList) {
                    board.setBoardWriter(existingMember.getMemberName());
                    boardRepository.save(board);
                }

                System.out.println("일반 로그인 회원 정보 수정 완료");
            }
            //소셜 로그인 사용자(provider 정보가 존재)
            else {
                System.out.println("소셜 로그인 회원 정보 수정");

                //이메일, 비밀번호x 이름만 변경
                existingMember.setMemberName(memberDTO.getMemberName());

                memberRepository.save(existingMember);

                // 사용자가 작성한 모든 게시글의 작성자 이름 변경
                List<BoardEntity> boardList = boardRepository.findByMemberEntity(existingMember);
                for (BoardEntity board : boardList) {
                    board.setBoardWriter(existingMember.getMemberName());
                    boardRepository.save(board);
                }
                System.out.println("소셜 로그인 회원 정보 수정 완료");
            }
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    //이메일 중복 체크 서비스 메서드
    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            System.out.println("이미 사용중인 이메일");
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    //회원탈퇴 서비스 메서드
    @Transactional
    public void deleteMember(Long memberId) {
        //사용자 조회
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없음")); //만약 사용자 없을때를 대비 익셉션터지게끔 설정
        //작성자가 작성한 게시물 조회
        List<BoardEntity> boardEntityList = memberEntity.getBoardEntityList();

        //각 게시물에 대한 사용자의 댓글 삭제
        for (BoardEntity board : boardEntityList) {
            List<CommentEntity> memberComment = board.getCommentEntityList().stream()
                    .filter(commentEntity -> {
                        //memberEntity가 null이 아닌 경우에만 id를 비교
                        return commentEntity.getMemberEntity() != null && commentEntity.getMemberEntity().getId().equals(memberId);
                    })
                    .collect(Collectors.toList());
            commentRepository.deleteAll(memberComment);
        }
        memberRepository.delete(memberEntity);
    }

    //관리자 전용 - 모든 회원 정보 가져오기
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    /**
     * 사용안하는 메서드들
     */
   /* public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }

    }

    //회원삭제 서비스 메서드 - 사용x
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    */

}













