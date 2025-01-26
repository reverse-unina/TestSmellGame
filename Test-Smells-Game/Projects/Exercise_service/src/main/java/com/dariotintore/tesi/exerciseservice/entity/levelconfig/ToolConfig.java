package com.dariotintore.tesi.exerciseservice.entity.levelconfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class ToolConfig {
	
	private List<Integer> expValues;
	private List<BadgeValue> badgeValues;
	private int answerPercentage;
	private boolean logTries;

	@JsonCreator
	public ToolConfig(
			@JsonProperty(value = "expValues", required = true) @NonNull List<Integer> expValues,
			@JsonProperty(value = "badgeValues", required = true) @NonNull List<BadgeValue> badgeValues,
			@JsonProperty(value = "answerPercentage", required = true) @NonNull int answerPercentage,
			@JsonProperty(value = "logTries", required = true) @NonNull boolean logTries)
	{
		this.expValues = expValues;
		this.badgeValues = badgeValues;
		this.answerPercentage = answerPercentage;
		this.logTries = logTries;
	}
}
