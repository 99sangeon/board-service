package sangeon.board.entity.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.board.DeleteStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private LocalDate birth;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    protected Member() {

    }

    @Builder
    public Member(String name, String email,String phoneNumber,Address address,Gender gender, LocalDate birth){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
    }

}
