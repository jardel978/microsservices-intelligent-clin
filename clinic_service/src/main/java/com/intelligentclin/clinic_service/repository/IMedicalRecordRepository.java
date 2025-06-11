package com.intelligentclin.clinic_service.repository;

import com.intelligentclin.clinic_service.entity.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicalRecordRepository extends MongoRepository<MedicalRecord, String> {
    
}