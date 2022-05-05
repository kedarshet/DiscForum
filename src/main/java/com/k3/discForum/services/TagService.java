package com.k3.discForum.services;

import com.k3.discForum.domain.Tag;
import com.k3.discForum.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    final private TagRepository tagRepository;

    public Tag getTagCreateIfNotExists(String name) {
        Tag tag = tagRepository.findByName(name);

        if (tag == null)
            tag = new Tag(name);

        tagRepository.save(tag);

        return tag;
    }
}
