package site.yejin.sbb.interestkeyword;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yejin.sbb.member.entity.Member;

public interface InterestKeywordRepository extends JpaRepository<InterestKeyword, InterestKeywordId> {
}
