package com.dariotintore.tesi.leaderboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVotes implements Serializable {
    private Long solutionId;
    private Long userId;
}
