package site.yejin.sbb.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class AnswerCreateForm {
    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
}
