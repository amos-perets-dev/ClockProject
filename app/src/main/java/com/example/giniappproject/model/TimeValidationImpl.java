package com.example.giniappproject.model;

public class TimeValidationImpl implements ITimeValidation {
    @Override
    public boolean isTimeValid(String time) {
        return time.contains("1");
    }
}
