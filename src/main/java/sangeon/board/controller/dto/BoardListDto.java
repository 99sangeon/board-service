package sangeon.board.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sangeon.board.entity.board.Board;


import java.time.LocalDateTime;

@Setter
@Getter
public class BoardListDto {
    private Long id;
    private String title;
    private String email;
    private LocalDateTime createDate;
    private int hits;


   public static BoardListDto of(Board board){
       return BoardListDto.builder()
               .id(board.getId())
               .title(board.getTitle())
               .email(board.getMember().getEmail())
               .createDate(board.getCreateDate())
               .hits(board.getHits())
               .build();
   }

   @Builder
    public BoardListDto(Long id, String title, String email, LocalDateTime createDate, int hits) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.createDate = createDate;
        this.hits = hits;
    }
}
