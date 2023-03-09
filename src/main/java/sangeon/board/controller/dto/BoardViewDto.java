package sangeon.board.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sangeon.board.entity.board.Board;


import java.time.LocalDateTime;

@Setter
@Getter
public class BoardViewDto {
    private Long id;
    private String title;
    private String email;
    private String content;
    private LocalDateTime createDate;
    private int hits;


   public static BoardViewDto of(Board board){
       return BoardViewDto.builder()
               .id(board.getId())
               .title(board.getTitle())
               .email(board.getMember().getEmail())
               .createDate(board.getCreateDate())
               .hits(board.getHits())
               .content(board.getContent())
               .build();
   }

   @Builder
    public BoardViewDto(Long id, String title, String email, LocalDateTime createDate, int hits, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.email = email;
        this.createDate = createDate;
        this.hits = hits;
    }
}
