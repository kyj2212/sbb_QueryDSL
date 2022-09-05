package site.yejin.sbb.interestkeyword;

import lombok.*;
import site.yejin.sbb.interestkeyword.InterestKeywordId;
import site.yejin.sbb.member.entity.Member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(InterestKeywordId.class)
public class InterestKeyword {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private Member member;

    @Id
    @EqualsAndHashCode.Include
    private String content;
}