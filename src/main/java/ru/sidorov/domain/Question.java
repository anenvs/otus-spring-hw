package ru.sidorov.domain;

import java.util.List;

public record Question(String question, List<Answer> answer) {
}
