package com.k3.discForum.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @NonNull
    private String title;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "question_tag",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NonNull
    private Set<Tag> tags;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private Set<Answer> answers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_vote_up",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> votedUpByUsers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_vote_down",
            joinColumns = @JoinColumn(name = "question_id"),
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
