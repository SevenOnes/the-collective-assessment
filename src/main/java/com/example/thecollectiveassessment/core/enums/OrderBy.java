package com.example.thecollectiveassessment.core.enums;

import javax.management.InvalidAttributeValueException;

public enum OrderBy {
    top,
    bottom;

    public OrderBy fromString(String str) throws InvalidAttributeValueException {
        if (str.equalsIgnoreCase("top") || str.equalsIgnoreCase("desc"))
            return top;
        else if (str.equalsIgnoreCase("bottom") || str.equalsIgnoreCase("asc"))
            return bottom;

        throw new InvalidAttributeValueException(str + " does not match any enum");
    }
}
