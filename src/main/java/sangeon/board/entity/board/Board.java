package sangeon.board.entity.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sangeon.board.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer hits = 0;

    @Enumerated(EnumType.STRING)
    private DeleteStatus status = DeleteStatus.N;

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime modifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    protected Board() {

    }

    @Builder
    public Board (String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

}
