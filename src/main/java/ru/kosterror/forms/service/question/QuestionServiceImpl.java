package ru.kosterror.forms.service.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.NewQuestionDto;
import ru.kosterror.forms.entity.question.QuestionEntity;
import ru.kosterror.forms.exception.NotFoundException;
import ru.kosterror.forms.mapper.QuestionMapper;
import ru.kosterror.forms.repository.QuestionRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    @Override
    public QuestionDto createQuestion(NewQuestionDto question) {
        var entity = questionMapper.toEntity(question);
        entity = questionRepository.save(entity);
        return questionMapper.toDto(entity);
    }

    @Override
    public QuestionDto getQuestion(UUID id) {
        var entity = getQuestionById(id);
        return questionMapper.toDto(entity);
    }

    private QuestionEntity getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
    }


}
