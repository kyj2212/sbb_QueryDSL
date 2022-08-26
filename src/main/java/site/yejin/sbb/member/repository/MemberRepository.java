package site.yejin.sbb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.base.RepositoryUtil;
import site.yejin.sbb.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , RepositoryUtil {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    @Transactional
    @Modifying
    // @Query(value="truncate question", nativeQuery = true)
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void truncateTable();

}