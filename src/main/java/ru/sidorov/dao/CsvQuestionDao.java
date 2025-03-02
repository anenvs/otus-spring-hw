package ru.sidorov.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.sidorov.config.TestFileNameProvider;
import ru.sidorov.dao.dto.QuestionDto;
import ru.sidorov.domain.Question;
import ru.sidorov.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (
                InputStream inputStream = getClass().getClassLoader()
                        .getResourceAsStream(fileNameProvider.getTestFileName());
                InputStreamReader reader = new InputStreamReader(inputStream)) {
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build();
            List<QuestionDto> questionDtos = csvToBean.parse();
            return questionDtos.stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new QuestionReadException("Failed to read questions from csv file", e);
        }
    }
}
