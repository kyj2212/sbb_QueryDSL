package site.yejin.sbb.answer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yejin.sbb.answer.Answer;
import site.yejin.sbb.answer.AnswerRepository;
import site.yejin.sbb.global.exception.DataNotFoundException;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.question.Question;

import java.time.LocalDateTime;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public void create(Question question, String content, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setModifyDate(LocalDateTime.now());
        question.addAnswer(answer);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public Answer findById(Integer id) {
        return answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("%d answer not found".formatted(id)));
    }

    public void update(Answer answer, String content) {
        if(!answer.getContent().equals(content)){
            answer.setContent(content);
        }
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
}
