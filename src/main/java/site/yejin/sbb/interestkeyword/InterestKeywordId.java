package site.yejin.sbb.interestkeyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.yejin.sbb.member.entity.Member;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestKeywordId implements Serializable {
    private Member member;
    private String content;
}