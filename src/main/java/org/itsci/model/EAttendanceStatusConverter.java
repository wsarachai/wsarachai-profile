package org.itsci.model;

import javax.persistence.AttributeConverter;

public class EAttendanceStatusConverter  implements AttributeConverter<EAttendanceStatus, String> {
    @Override
    public String convertToDatabaseColumn(EAttendanceStatus attribute) {
        return attribute.toString();
    }

    @Override
    public EAttendanceStatus convertToEntityAttribute(String dbData) {
        return stringToEAttendanceStatusConverter(dbData);
    }

    private EAttendanceStatus stringToEAttendanceStatusConverter(String value) {
        if (value != null) {
            if (value.equals(EAttendanceStatus.NA.toString())) {
                return EAttendanceStatus.NA;
            } else if (value.equals(EAttendanceStatus.ABSENT.toString())) {
                return EAttendanceStatus.ABSENT;
            } else if (value.equals(EAttendanceStatus.LATE.toString())) {
                return EAttendanceStatus.LATE;
            } else if (value.equals(EAttendanceStatus.LETTERS.toString())) {
                return EAttendanceStatus.LETTERS;
            } else if (value.equals(EAttendanceStatus.ATTENDED.toString())) {
                return EAttendanceStatus.ATTENDED;
            }
            return EAttendanceStatus.NA;
        }
        return EAttendanceStatus.NA;
    }
}
