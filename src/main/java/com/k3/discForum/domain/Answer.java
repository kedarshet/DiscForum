package com.k3.discForum.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "LONGTEXT")
    @NonNull
    private String body;

    @Column(name = "creationDateTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date creationDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    @NonNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "question", nullable = false)
    @NonNull
    private Question question;

    @ManyToMany
    @JoinTable(
            name = "answer_vote_up",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> votedUpByUsers;

    @ManyToMany
    @JoinTable(
            name = "answer_vote_down",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> votedDownByUsers;

    public void voteByUser(final User u, final Vote v) {
        if (v == Vote.UP) {
            votedDownByUsers.remove(u);
            votedUpByUsers.add(u);
        } else if (v == Vote.DOWN) {
            votedDownByUsers.add(u);
            votedUpByUsers.remove(u);
        } else if (v == Vote.NONE) {
            votedDownByUsers.remove(u);
            votedUpByUsers.remove(u);
        }
    }
}
