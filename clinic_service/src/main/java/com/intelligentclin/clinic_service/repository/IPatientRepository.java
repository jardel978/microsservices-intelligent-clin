package com.intelligentclin.clinic_service.repository;

import com.intelligentclin.clinic_service.entity.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends MongoRepository<Patient, String> {

    Page<Patient> findByFirstNameContainingIgnoreCase(Pageable pageable, String firstName);
    
    Page<Patient> findByLastNameContainingIgnoreCase(String lastName);

    @Query("{ '$or': [ " +
           "  { 'uid': ?0 }, " +
           "  { 'firstName': ?1 }, " +
           "  { 'lastName': ?2 }, " +
           "  { 'cpf': ?3 } " +
           "] }")
    Page<Patient> findByIdOrFirstNameOrLastNameOrCpf(
        String id,
        String firstName,
        String lastName,
        String cpf
    );
}