package site.yejin.sbb.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.yejin.sbb.member.entity.Member;
import static site.yejin.sbb.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Member getQslMember(Long id) {

        return jpaQueryFactory.selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }
}
