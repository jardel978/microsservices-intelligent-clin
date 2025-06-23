package com.intelligentclin.clinic_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.intelligentclin.clinic_service.model.entity.Attendant;

@Repository
public interface IAttendantRepository extends MongoRepository<Attendant, String> {
    @Query("{ '$or': [ " +
           "  { 'uid': ?0 }, " +
           "  { 'firstName': ?1 }, " +
           "  { 'lastName': ?2 }, " +
           "  { 'cpf': ?3 } " +
           "] }")
    Page<Attendant> findByIdOrFirstNameOrLastNameOrCpf(
        String id,
        String firstName,
        String lastName,
        String cpf,
        Pageable pageable
    );
}
