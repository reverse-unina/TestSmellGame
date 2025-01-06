package com.dariotintore.tesi.exerciseservice.entity.levelConfig;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class LevelConfig {
	
	private List<Integer> expValues;
	private List<BadgeValue> badgeValues;
	private int answerPercentage;
}
