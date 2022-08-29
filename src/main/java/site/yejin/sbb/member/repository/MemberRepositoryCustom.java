package site.yejin.sbb.member.repository;

import site.yejin.sbb.member.entity.Member;

public interface MemberRepositoryCustom {
    Member getQslMember(Long id);
}
