package com.k3.discForum.repositories;

import com.k3.discForum.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    public List<Question> findAllByOrderByCreationDateTimeDesc();
}
