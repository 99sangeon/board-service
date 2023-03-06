package sangeon.board.Service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import sangeon.board.entity.board.Board;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@RequiredArgsConstructor
public class BoardFormDto {

    private String email;
    @NotEmpty(message = "제목을 작성해주세요.")
    @Length(max = 30, message = "제목은 30글자 이내로 작성가능합니다.")
    private String title;
    private String content;


    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

}
