package com.intelligentclin.clinic_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intelligentclin.clinic_service.model.entity.Consultation;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface IConsultationRepository extends MongoRepository<Consultation, String> {

    Consultation findByConsultationDateAndConsultationTime(LocalDate consultationDate, LocalTime consultationTime);
}
