package ru.sidorov.dao;

import ru.sidorov.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
