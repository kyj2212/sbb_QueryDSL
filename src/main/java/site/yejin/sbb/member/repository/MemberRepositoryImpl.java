package site.yejin.sbb.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.entity.QMember;

import java.util.List;

import static site.yejin.sbb.interestkeyword.QInterestKeyword.interestKeyword;
import static site.yejin.sbb.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Member getQslMember(Long id) {

        return jpaQueryFactory.selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<String> getQslInterestKeywordsByFollowingsOf(Member m) {
        return jpaQueryFactory.select(interestKeyword.content)
                .from(member)
                .innerJoin(interestKeyword)
                .on(member.followings.any().eq(interestKeyword.member))
                .where(member.eq(m))
                .fetch();
    }

        @Override
        public List<String> getQslDistinctInterestKeywordsByFollowingsOf(Member m){
            return jpaQueryFactory.selectDistinct(interestKeyword.content)
                    .from(member)
                    .innerJoin(interestKeyword)
                    .on(member.followings.any().eq(interestKeyword.member))
                    .where(member.eq(m))
                    .fetch();
        }
/*
    @Override
    public List<String> getQslDistinctInterestKeywordsByFollowingsOf(Member m){
        QMember member2 = new QMember("member2");
        return jpaQueryFactory.selectDistinct(interestKeyword.content)
                .from(interestKeyword)
                .innerJoin(interestKeyword.member, member) // site_user
                .innerJoin(member.followers, member2)
                .where(member2.id.eq(m.getId()))
                .fetch();
    }
*/
/*    @Override
    public List<String> getQslDistinctInterestKeywordsByFollowingsOf(Member m) {

        return jpaQueryFactory.select(interestKeyword.content).distinct()
                .from(interestKeyword)
                .where(interestKeyword.member.in(m.getFollowings()))
                .fetch();

    }*/
    /*
    @Override
    public List<String> getQslDistinctInterestKeywordsByFollowingsOf(Member m) {
        QMember member2 = new QMember("M2");

        List<Long> ids = jpaQueryFactory.select(member.id)
                .from(member)
                .innerJoin(member.followers, member2)
                .where(member2.id.eq(m.getId()))
                .fetch();

        return jpaQueryFactory.select(interestKeyword.content).distinct()
                .from(interestKeyword)
                .where(interestKeyword.member.id.in(ids))
                .fetch();
    }*/
}
