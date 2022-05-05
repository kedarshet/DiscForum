package com.k3.discForum.domain.views;

import com.k3.discForum.domain.Answer;
import com.k3.discForum.domain.Question;
import com.k3.discForum.domain.Tag;
import com.k3.discForum.domain.User;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class QuestionView {
    final private Integer id;
    final private String title;
    final private UserView author;
    final private String creationDateTime;
    final private Integer votes;
    final private Integer answersCount;
    final private String body;
    final private boolean votedUpByActiveUser;
    final private boolean votedDownByActiveUser;
    final private Set<AnswerView> answers;
    final private Set<TagView> tags;

    public QuestionView(final Question q, final User activeUser) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.author = new UserView(q.getAuthor());
        this.creationDateTime = formatDateTime(q.getCreationDateTime());
        this.answersCount = calculateAnswersCount(q);
        this.votes = calculateVotes(q);
        this.body = convertBodyFromMarkdownToHTML(q.getBody());
        this.votedUpByActiveUser = isVotedUpByActiveUser(q, activeUser);
        this.votedDownByActiveUser = isVotedDownByActiveUser(q, activeUser);
        this.answers = getAnswersViews(q, activeUser);
        this.tags = getTagsViews(q);
    }

    private String formatDateTime(final Date d) {
        DateFormat fmt = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
        return fmt.format(d);
    }

    private Integer calculateAnswersCount(final Question q) {
        return q.getAnswers().size();
    }

    private Integer calculateVotes(final Question q) {
        return q.getVotedUpByUsers().size() - q.getVotedDownByUsers().size();
    }

    private String convertBodyFromMarkdownToHTML(final String markdown) {
        Node document = Parser.builder().build().parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();

        return renderer.render(document);
    }

    private boolean isVotedUpByActiveUser(final Question q, final User u) {
        if (u == null)
            return false;

        return u.getVotedUpQuestions().contains(q);
    }

    private boolean isVotedDownByActiveUser(final Question q, final User u) {
        if (u == null)
            return false;

        return u.getVotedDownQuestions().contains(q);
    }

    private Set<AnswerView> getAnswersViews(Question q, User activeUser) {
        Set<AnswerView> answersViews = new HashSet<>();

        for (Answer a : q.getAnswers())
            answersViews.add(new AnswerView(a, activeUser));

        return answersViews;
    }

    private Set<TagView> getTagsViews(Question q) {
        Set<TagView> tagsViews = new HashSet<>();

        for (Tag t : q.getTags())
            tagsViews.add(new TagView(t));

        return tagsViews;
    }
}
