package sangeon.board.OAuth;

import lombok.Getter;
import sangeon.board.entity.member.Member;

@Getter
public class SessionMember {

    private String name;
    private String email;
    private String profile_yn;

    public SessionMember(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
    }

    public static SessionMember of(Member member) {
        return new SessionMember(member);
    }

}
