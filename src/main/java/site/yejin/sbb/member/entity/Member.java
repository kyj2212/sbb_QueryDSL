package site.yejin.sbb.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.yejin.sbb.interestkeyword.InterestKeyword;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자 제한으로 누락 방지

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // entity crud 전, 후 이벤트 처리
@Table(name = "member_tbl")
public class Member {

    @Id
    @Column(name="member_uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name="member_pwd", nullable = false)
    private String password;

    @Column(name="member_name", nullable = false, length = 20)
    private String name;

    @Column(name="member_email", unique = true)
    private String email;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Set<InterestKeyword> interestKeywords = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Member> followers = new HashSet<>();

//    @Builder.Default
//    @ManyToMany(cascade = CascadeType.ALL)
//    private Set<Member> followings = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_tbl_followings",                              // 연결테이블 이름
            joinColumns = @JoinColumn(name = "member_id"),        // 회원과 매핑할 조인 컬럼 정보를 지정
            inverseJoinColumns = @JoinColumn(name = "following_id") // 팔로잉과 매핑할 조인 컬럼 정보를 지정
    )
    private Set<Member> followings = new HashSet<>();


    public void addInterestKeywordContent(String keywordContent) {
        interestKeywords.add(new InterestKeyword(this, keywordContent));
    }

    public void removeInterestKeyword(InterestKeyword interestKeyword){
        interestKeywords.remove(interestKeyword);
    }

    public void follow(Member following) {
        if (this == following) return;
        if (following == null) return;
        if (this.getId() == following.getId()) return;

        // 유튜버(following)이 나(follower)를 구독자로 등록
        following.getFollowers().add(this);

        // 내(follower)가 유튜버(following)를 구독한다.
        getFollowings().add(following);
    }

    @Builder
    public Member(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void updateUsername(String username){
        this.username = username;
    }
    public void updateName(String name){
        this.name = name;
    }
    public void updateEmail(String email){
        this.email = email;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    public void setRole(MemberRole role) {this.role=role;}
}