package site.yejin.sbb.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Slf4j
public class QuestionCreateForm {

    @Size(max=30, message = "제목은 30자 이하로 입력해 주세요.")
    @NotBlank(message = "제목을 입력해 주세요.")
    private String subject;

    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;
}
