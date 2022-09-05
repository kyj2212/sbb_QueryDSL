package site.yejin.sbb.member.repository;

import site.yejin.sbb.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    Member getQslMember(Long id);

    List<String> getQslInterestKeywordsByFollowingsOf(Member member);

    List<String> getQslDistinctInterestKeywordsByFollowingsOf(Member member);
}
