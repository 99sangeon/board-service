package sangeon.board.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.member.Address;
import sangeon.board.entity.member.Gender;
import sangeon.board.entity.member.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void save() {
        Member member = createMember();
        Board board = new Board("제목", "안녕하세용~~ 히히", member);

        //게시판 등록
        boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getId()).get();
        assertEquals(board, findBoard);
    }

    @Test
    public void update() {
        Member member = createMember();
        Board board = new Board("제목", "안녕하세용~~ 히히", member);
        boardRepository.save(board);

        //게시판 수정
        Board findBoard = boardRepository.findById(board.getId()).get();
        findBoard.setTitle("hi");

    }



    private Member createMember() {
        Member member = getMember();

        //회원 등록
        memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();
        assertEquals(member, findMember);

        return findMember;
    }

    private static Member getMember() {
        return new Member("전상언",
                "tkd0204@naver.com",
                "010-7307-4546",
                new Address("창원시", "용호동 52-2", "303호"),
                Gender.MALE);
    }

}