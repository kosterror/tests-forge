package ru.kosterror.forms.service.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.NewQuestionDto;
import ru.kosterror.forms.mapper.QuestionMapper;
import ru.kosterror.forms.repository.QuestionRepository;

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


}
