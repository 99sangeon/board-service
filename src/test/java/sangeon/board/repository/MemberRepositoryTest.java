package sangeon.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sangeon.board.entity.member.Address;
import sangeon.board.entity.member.Gender;
import sangeon.board.entity.member.Member;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save() {

       Member member = getMember();

       //회원 등록
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertEquals(member, findMember);

    }

    @Test
    public void update() {

        Member member = getMember();
        memberRepository.save(member);

        //회원 정보 수정
        Member findMember = memberRepository.findById(member.getId()).get();
        findMember.setName("곰뭉치");
        findMember.setEmail("moong123@gmail.com");
        Address address = new Address("마산시", "합성동", "와플대학");
        findMember.setAddress(address);

        assertThat("곰뭉치").isEqualTo(findMember.getName());
        assertThat("moong123@gmail.com").isEqualTo(findMember.getEmail());
        assertThat(address).isEqualTo(findMember.getAddress());
    }

    @Test
    public void delete() {

        Member member = getMember();
        memberRepository.save(member);

        //회원 삭제
        Member findMember = memberRepository.findById(member.getId()).get();
        memberRepository.delete(findMember);

        Optional<Member> deleteMember = memberRepository.findById(findMember.getId());
        assertThat(deleteMember.isEmpty()).isTrue();
    }

    private static Member getMember() {
        return Member.builder()
                .name("전상언")
                .email("tkd0204@naver.com")
                .address(new Address("경상남도", "창원시", "용호동 52-2"))
                .gender(Gender.MALE)
                .phoneNumber("010-7307-4546")
                .build();
    }


}