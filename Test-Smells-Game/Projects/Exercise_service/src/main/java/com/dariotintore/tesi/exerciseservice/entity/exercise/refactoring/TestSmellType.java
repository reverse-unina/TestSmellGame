package com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TestSmellType {
    ASSERTION_ROULETTE("Assertion Roulette"),
    CONDITIONAL_TEST_LOGIC("Conditional Test Logic"),
    CONSTRUCTOR_INITIALIZATION("Constructor Initialization"),
    DEFAULT_TEST("Default Test"),
    DUPLICATE_ASSERT("Duplicate Assert"),
    EAGER_TEST("Eager Test"),
    EMPTY_TEST("Empty Test"),
    EXCEPTION_HANDLING("Exception Handling"),
    GENERAL_FIXTURE("General Fixture"),
    IGNORED_TEST("Ignored Test"),
    LAZY_TEST("Lazy Test"),
    MAGIC_NUMBER_TEST("Magic Number Test"),
    MYSTERY_GUEST("Mystery Guest"),
    REDUNDANT_PRINT("Redundant Print"),
    REDUNDANT_ASSERTION("Redundant Assertion"),
    RESOURCE_OPTIMISM("Resource Optimism"),
    SENSITIVE_EQUALITY("Sensitive Equality"),
    SLEEPY_TEST("Sleepy Test"),
    UNKNOWN_TEST("Unknown Test");

    private final String displayName;

    TestSmellType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getValue() {
        return displayName;
    }

    @JsonCreator
    public static TestSmellType fromValue(String value) {
        for (TestSmellType testSmell : TestSmellType.values()) {
            if (testSmell.displayName.equalsIgnoreCase(value)) {
                return testSmell;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
