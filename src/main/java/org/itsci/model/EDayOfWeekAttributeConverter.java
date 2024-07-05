package org.itsci.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EDayOfWeekAttributeConverter implements AttributeConverter<EDayOfWeek, String> {
    @Override
    public String convertToDatabaseColumn(EDayOfWeek attribute) {
        return attribute.toString();
    }

    @Override
    public EDayOfWeek convertToEntityAttribute(String dbData) {
        return stringToEDayOfWeekConverter(dbData);
    }

    private EDayOfWeek stringToEDayOfWeekConverter(String value) {
        if (value != null) {
            if (value.equals(EDayOfWeek.MONDAY.toString())) {
                return EDayOfWeek.MONDAY;
            } else if (value.equals(EDayOfWeek.TUESDAY.toString())) {
                return EDayOfWeek.TUESDAY;
            } else if (value.equals(EDayOfWeek.WEDNESDAY.toString())) {
                return EDayOfWeek.WEDNESDAY;
            } else if (value.equals(EDayOfWeek.THURSDAY.toString())) {
                return EDayOfWeek.THURSDAY;
            } else if (value.equals(EDayOfWeek.FRIDAY.toString())) {
                return EDayOfWeek.FRIDAY;
            } else if (value.equals(EDayOfWeek.SATURDAY.toString())) {
                return EDayOfWeek.SATURDAY;
            }
            return EDayOfWeek.SUNDAY;
        }
        return EDayOfWeek.SUNDAY;
    }

}
