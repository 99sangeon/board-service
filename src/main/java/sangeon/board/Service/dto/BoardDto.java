package sangeon.board.Service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import sangeon.board.controller.dto.BoardViewDto;
import sangeon.board.entity.board.Board;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@Setter
@RequiredArgsConstructor
public class BoardDto {

    private Long id;
    private String email;
    @NotEmpty(message = "제목을 작성해주세요.")
    @Length(max = 30, message = "제목은 30글자 이내로 작성가능합니다.")
    private String title;
    private String content;
    private LocalDateTime createDate;
    private int hits;


    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static BoardDto of(Board board){
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .email(board.getMember().getEmail())
                .createDate(board.getCreateDate())
                .hits(board.getHits())
                .content(board.getContent())
                .build();
    }

    @Builder
    public BoardDto(Long id, String title, String email, LocalDateTime createDate, int hits, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.email = email;
        this.createDate = createDate;
        this.hits = hits;
    }

}
