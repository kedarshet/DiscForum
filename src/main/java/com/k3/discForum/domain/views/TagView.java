package com.k3.discForum.domain.views;

import com.k3.discForum.domain.Tag;

public class TagView {
    final private String name;

    public TagView(final Tag t) {
        this.name = t.getName();
    }
}
