package com.k3.discForum.repositories;

import com.k3.discForum.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByName(String name);
}
