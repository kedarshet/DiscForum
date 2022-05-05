package com.k3.discForum.domain.views;

import com.k3.discForum.domain.Answer;
import com.k3.discForum.domain.User;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnswerView {
   final private Integer id;
   final private UserView author;
   final private String creationDateTime;
   final private Integer votes;
   final private String body;
   final private boolean votedUpByActiveUser;
   final private boolean votedDownByActiveUser;

   public AnswerView(final Answer a, final User activeUser) {
      this.id = a.getId();
      this.author = new UserView(a.getAuthor());
      this.creationDateTime = formatDateTime(a.getCreationDateTime());
      this.votes = calculateVotes(a);
      this.body = convertBodyFromMarkdownToHTML(a.getBody());
      this.votedUpByActiveUser = isVotedUpByActiveUser(a, activeUser);
      this.votedDownByActiveUser = isVotedDownByActiveUser(a, activeUser);
   }

   private String formatDateTime(final Date d) {
      DateFormat fmt = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
      return fmt.format(d);
   }

   private Integer calculateVotes(final Answer a) {
      return a.getVotedUpByUsers().size() - a.getVotedDownByUsers().size();
   }

   private String convertBodyFromMarkdownToHTML(final String markdown) {
      Node document = Parser.builder().build().parse(markdown);
      HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();

      return renderer.render(document);
   }

   private boolean isVotedUpByActiveUser(final Answer a, final User u) {
      if (u == null)
         return false;

      return u.getVotedUpAnswers().contains(a);
   }

   private boolean isVotedDownByActiveUser(final Answer a, final User u) {
      if (u == null)
         return false;

      return u.getVotedDownAnswers().contains(a);
   }
}
