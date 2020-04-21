package com.example.giniappproject.model;

public interface ITimeValidation {

    /**
     * Check if the time is valid(contains the 1 digit)
     *
     * @param time need to check
     * @return  {@link Boolean#TRUE}  - time is valid
     *          {@link Boolean#FALSE} - time isn't valid
     */
    public boolean isTimeValid(String time);

}