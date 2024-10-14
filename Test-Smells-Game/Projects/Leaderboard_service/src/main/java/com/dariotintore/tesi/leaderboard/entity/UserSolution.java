package com.dariotintore.tesi.leaderboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_solution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserVotes.class)
public class UserSolution implements Serializable {
    @Id
    @Column(name = "solutionId")
    private Long solutionId;

    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "vote")
    private VoteType voteType;
    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private Date updateTimestamp;
}
