import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sidorov.config.TestFileNameProvider;
import ru.sidorov.dao.CsvQuestionDao;
import ru.sidorov.domain.Question;
import ru.sidorov.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {

    private CsvQuestionDao csvQuestionDao;
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        fileNameProvider = mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    void findAll_fileExists() {

        boolean isCorrectTrue = true;
        boolean isCorrectFalse = false;

        when(fileNameProvider.getTestFileName()).thenReturn("test-questions.csv");
        List<Question> questions = csvQuestionDao.findAll();
        assertNotNull(questions, "Questions should not be null");

        Question sampleQuestion = questions.getFirst();
        assertEquals("Is there life on Mars?", sampleQuestion.question());
        assertEquals(3, sampleQuestion.answer().size());
        assertEquals("Science doesn't know this yet", sampleQuestion.answer().get(0).answer());
        assertEquals(isCorrectTrue, sampleQuestion.answer().get(0).isCorrect());
        assertEquals(isCorrectFalse, sampleQuestion.answer().get(1).isCorrect());

    }

    @Test
    void findAll_fileDoesNotExist() {
        when(fileNameProvider.getTestFileName()).thenReturn("zzz-test-questions.csv");

        QuestionReadException exception = assertThrows(
                QuestionReadException.class,
                () -> csvQuestionDao.findAll(),
                "QuestionReadException expected"
        );
        assertTrue(exception.getMessage().contains("Failed to read questions from csv file"), "Exception text matches");
    }
}
