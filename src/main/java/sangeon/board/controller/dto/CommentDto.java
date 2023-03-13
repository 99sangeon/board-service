package sangeon.board.controller.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sangeon.board.entity.board.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private String email;
    private Long boardId;
    private String content;
    private Long parentId;
    private LocalDateTime createDate;


    @Builder
    public CommentDto(String email, Long boardId, String content, LocalDateTime createDate, Long parentId){
        this.email = email;
        this.boardId = boardId;
        this.content = content;
        this.parentId = parentId;
        this.createDate = createDate;
    }

    public static CommentDto of(Comment comment) {
        return CommentDto
                .builder()
                .email(comment.getMember().getEmail())
                .boardId(comment.getBoard().getId())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .build();
    }

}
