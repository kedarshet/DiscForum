package com.k3.discForum.domain.views;

import com.k3.discForum.domain.User;

public class UserView {
    final private Integer id;
    final private String username;

    public UserView(final User u) {
        this.id = u.getId();
        this.username = u.getUsername();
    }
}
