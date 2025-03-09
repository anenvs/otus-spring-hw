package ru.sidorov.service;

import lombok.RequiredArgsConstructor;
import ru.sidorov.dao.QuestionDao;
import ru.sidorov.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();
        for (Question question : questions) {
            ioService.printLine(question.question());
            for (int i = 0; i < question.answer().size(); i++) {
                ioService.printFormattedLine("%d. %s", i + 1, question.answer().get(i).answer());
            }
            ioService.printLine("");
        }
    }
}
