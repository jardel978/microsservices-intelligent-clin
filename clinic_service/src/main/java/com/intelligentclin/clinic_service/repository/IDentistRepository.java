package com.intelligentclin.clinic_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.intelligentclin.clinic_service.model.entity.Dentist;
import com.intelligentclin.common_models.models.enums.Specialty;

import java.util.Optional;

@Repository
public interface IDentistRepository extends MongoRepository<Dentist, String> {

    Optional<Dentist> findByRegistrationNumberIgnoreCaseContaining(String matricula);

    Page<Dentist> findBySpecialtiesContaining(Specialty specialty, Pageable pageable);

    @Query("{ '$or': [ " +
           "  { 'uid': ?0 }, " +
           "  { 'firstName': ?1 }, " +
           "  { 'lastName': ?2 }, " +
           "  { 'cpf': ?3 } " +
           "] }")
    Page<Dentist> findByIdOrFirstNameOrLastNameOrCpf(
        String id,
        String firstName,
        String lastName,
        String cpf,
        Pageable pageable
    );
}