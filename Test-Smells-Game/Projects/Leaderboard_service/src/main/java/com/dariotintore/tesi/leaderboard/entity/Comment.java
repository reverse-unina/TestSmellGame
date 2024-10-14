package com.dariotintore.tesi.leaderboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
  @GenericGenerator(name = "seq", strategy="increment")
  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "author")
  private String commentAuthor;

  @Column(name = "text")
  private String commentText;
  @Column(name = "solution_id")
  private Long solutionId;

  @Column(name = "creation_timestamp")
  @CreationTimestamp
  private Date creationTimestamp;
}
