package com.dariotintore.tesi.exerciseservice.LevelConfig;

import java.util.*;

public class LevelConfig {
	
	private List<Integer> expValues;
	private Map<Integer, String> badgeValues;
	private int answerPercentage;
	
	public List<Integer> getExpValues() {
		return expValues;
	}
	public void setExpValues(List<Integer> expValues) {
		this.expValues = expValues;
	}
	public Map<Integer, String> getBadgeValues() {
		return badgeValues;
	}
	public void setBadgeValues(Map<Integer, String> badgeValues) {
		this.badgeValues = badgeValues;
	}
	public int getAnswerPercentage() {
		return answerPercentage;
	}
	public void setAnswerPercentage(int answerPercentage) {
		this.answerPercentage = answerPercentage;
	}

}
