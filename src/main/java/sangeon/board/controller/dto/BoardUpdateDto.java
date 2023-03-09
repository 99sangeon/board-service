package sangeon.board.controller.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardUpdateDto {

    @NotEmpty(message = "제목을 작성해주세요.")
    @Length(max = 30, message = "제목은 30글자 이내로 작성가능합니다.")
    private String title;
    private String content;

}
