package com.intelligentclin.clinic_service.repository;

import com.intelligentclin.clinic_service.entity.Dentist;
import com.intelligentclin.clinic_service.entity.enums.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDentistRepository extends MongoRepository<Dentist, String> {

    Optional<Dentist> findByRegistrationNumberIgnoreCaseContaining(String matricula);

    Page<Dentist> findBySpecialtiesContaining(Pageable pageable, Specialty specialty);

    @Query("{ '$or': [ " +
           "  { 'uid': ?0 }, " +
           "  { 'firstName': ?1 }, " +
           "  { 'lastName': ?2 }, " +
           "  { 'cpf': ?3 } " +
           "] }")
    List<Dentist> findByIdOrFirstNameOrLastNameOrCpf(
        String id,
        String firstName,
        String lastName,
        String cpf
    );
}