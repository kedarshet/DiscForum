package com.k3.discForum.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Answer> votedUpAnswers;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
    private Set<Answer> votedDownAnswers;

    public void voteForQuestion(final Question q, final Vote v) {
        if (v == Vote.UP) {
            votedDownQuestions.remove(q);
            votedUpQuestions.add(q);
        } else if (v == Vote.DOWN) {
            votedDownQuestions.add(q);
            votedUpQuestions.remove(q);
        } else if (v == Vote.NONE) {
            votedDownQuestions.remove(q);
            votedUpQuestions.remove(q);
        }
    }

    public void voteForAnswer(final Answer a, final Vote v) {
        if (v == Vote.UP) {
            votedDownAnswers.remove(a);
            votedUpAnswers.add(a);
        } else if (v == Vote.DOWN) {
            votedDownAnswers.add(a);
            votedUpAnswers.remove(a);
        } else if (v == Vote.NONE) {
            votedDownAnswers.remove(a);
            votedUpAnswers.remove(a);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }
}
