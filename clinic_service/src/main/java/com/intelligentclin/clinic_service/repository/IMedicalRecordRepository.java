package com.intelligentclin.clinic_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intelligentclin.clinic_service.model.entity.MedicalRecord;

@Repository
public interface IMedicalRecordRepository extends MongoRepository<MedicalRecord, String> {
    
}