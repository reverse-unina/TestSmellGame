package com.dariotintore.tesi.leaderboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSubmitHistoryDTO {
    private Long userId;
    private Integer exerciseScore;
    private String exerciseType;
    private String exerciseName;
}
