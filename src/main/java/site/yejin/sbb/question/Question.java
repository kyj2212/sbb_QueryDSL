package site.yejin.sbb.question;

import lombok.Getter;
import lombok.Setter;
import site.yejin.sbb.answer.Answer;
import site.yejin.sbb.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question {


    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200) // varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Member author;

    public void initAnswerList() {
        if (answerList == null) {
            this.answerList = new ArrayList<>();
            System.out.println("init list :"+answerList);
        }
    }
    public void addAnswer(Answer answer){
        if (answerList == null) {
            this.answerList = new ArrayList<>();
        }
        answerList.add(answer);
    }
}