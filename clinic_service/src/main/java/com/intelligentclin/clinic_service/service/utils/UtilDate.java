package com.intelligentclin.clinic_service.service.utils;

import com.intelligentclin.clinic_service.entity.Age;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Component
@NoArgsConstructor
public class UtilDate implements Serializable {

    private static final long serialVersionUID = 1L;

    public Age generate(LocalDate initialDate, LocalDate finalDate) {

        Period period = Period.between(initialDate, finalDate);

        return Age.builder()
                .days(period.getDays())
                .months(period.getMonths())
                .years(period.getYears()).build();
    }

    public boolean checkMedicalRecordValidity(LocalDate creationDate) {
        Period period = Period.between(creationDate, LocalDate.now());
        return period.getYears() >= 10;
    }

    public Boolean checkIfPreviousDate(LocalDateTime dataTime) {
        return dataTime.isBefore(LocalDateTime.now());
    }

}
