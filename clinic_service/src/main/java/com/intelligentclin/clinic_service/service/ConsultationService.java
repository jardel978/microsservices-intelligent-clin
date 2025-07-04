package com.intelligentclin.clinic_service.service;

import com.intelligentclin.common_models.models.dtos.consultation.ConsultationReq;
import com.intelligentclin.common_models.models.dtos.consultation.ConsultationResp;
import com.intelligentclin.clinic_service.model.dtos.converters.ConsultationModelMapperConverter;
import com.intelligentclin.clinic_service.model.entity.Attendant;
import com.intelligentclin.clinic_service.model.entity.Consultation;
import com.intelligentclin.clinic_service.model.entity.Dentist;
import com.intelligentclin.clinic_service.model.entity.Patient;
import com.intelligentclin.common_models.models.enums.ConsultationStatus;
import com.intelligentclin.clinic_service.repository.IAttendantRepository;
import com.intelligentclin.clinic_service.repository.IConsultationRepository;
import com.intelligentclin.clinic_service.repository.IDentistRepository;
import com.intelligentclin.clinic_service.repository.IPatientRepository;
import com.intelligentclin.clinic_service.service.exception.ConsultationStatusException;
import com.intelligentclin.clinic_service.service.exception.DataNotFoundException;
import com.intelligentclin.clinic_service.service.utils.UtilDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ConsultationService {

        @Autowired
        private IConsultationRepository consultationRepository;

        @Autowired
        private IPatientRepository patientRepository;

        @Autowired
        private IDentistRepository dentistRepository;

        @Autowired
        private ConsultationModelMapperConverter consultationConverter;

        @Autowired
        private IAttendantRepository attendantRepository;

        @Autowired
        private UtilDate utilDate;

        public void save(ConsultationReq consultationReq) {
                LocalDate consultationDate = consultationReq.getConsultationDate();
                LocalTime consultationTime = consultationReq.getConsultationTime();
                Consultation existingConsultation = consultationRepository.findByConsultationDateAndConsultationTime(
                                consultationDate,
                                consultationTime);

                LocalDateTime dateTime = LocalDateTime.of(
                                consultationDate.getYear(),
                                consultationDate.getMonth(),
                                consultationDate.getDayOfMonth(),
                                consultationTime.getHour(),
                                consultationTime.getMinute(),
                                consultationTime.getSecond());

                if (existingConsultation == null
                                || existingConsultation.getStatus() == ConsultationStatus.CANCELED) {
                        if (Boolean.FALSE.equals(utilDate.checkIfPreviousDate(dateTime))) {

                                String notFoundMessage = "not found in the database.";
                                Patient patient = patientRepository.findById(consultationReq.getUid()).orElseThrow(() -> new DataNotFoundException("Patient " + notFoundMessage));
                                Dentist dentist = dentistRepository.findById(consultationReq.getUid()).orElseThrow(() -> new DataNotFoundException("Dentist " + notFoundMessage));
                                Attendant attendat = attendantRepository.findById(consultationReq.getUid()).orElseThrow(() -> new DataNotFoundException("Attendant " + notFoundMessage));

                               
                                var consultationAux = new ConsultationReq(null, patient.getUid(), dentist.getUid(), attendat.getUid(), consultationReq.getConsultationDate(), consultationReq.getConsultationTime(), consultationReq.getNotes(), consultationReq.getAmount(), ConsultationStatus.PENDING);
                                Consultation consultation = consultationConverter.mapModelReqToEntity(
                                                consultationAux,
                                                Consultation.class);
                                consultationRepository.save(consultation);
                        } else
                                throw new ConsultationStatusException(
                                                "The chosen date/time for scheduling the consultation is earlier than today. Please provide a date after the current date.");
                } else {
                        if (existingConsultation.getStatus() == ConsultationStatus.PENDING)
                                throw new ConsultationStatusException(
                                                "There is already a scheduled consultation for the date: " +
                                                                consultationDate + " and time: " + consultationTime
                                                                + ". However, it has not been confirmed by the patient. Please verify patient confirmation to know if the time slot is available.");
                        if (existingConsultation.getStatus() == ConsultationStatus.CONFIRMED)
                                throw new ConsultationStatusException(
                                                "There is already a scheduled and confirmed consultation for the date: "
                                                                +
                                                                consultationDate + " and time: " + consultationTime + ".");
                        if (existingConsultation.getStatus() == ConsultationStatus.COMPLETED)
                                throw new ConsultationStatusException(
                                                "There is already a scheduled and confirmed consultation for the date: "
                                                                +
                                                                consultationDate + " and time: " + consultationTime + ".");
                        
                }
        }

        public ConsultationResp findById(String uid) throws DataNotFoundException {
                Consultation consultation = consultationRepository.findById(uid)
                                .orElseThrow(() -> new DataNotFoundException(
                                                "The specified consultation could not be found in the database."));
                consultation.setStatus(checkIfConsultationCompleted(consultation));
                return consultationConverter.mapEntityToModelResp(consultation, ConsultationResp.class);
        }

        public Page<ConsultationResp> findAll(Pageable pageable) {
                Page<Consultation> consultationsPage = consultationRepository.findAll(pageable);
                return consultationsPage.map(consultation -> {
                        consultation.setStatus(checkIfConsultationCompleted(consultation));
                        return consultationConverter.mapEntityToModelResp(consultation,
                                        ConsultationResp.class);
                });
        }

        public void deleteById(String id) throws DataNotFoundException {
                Consultation consultation = consultationRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException(
                                                "The specified consultation could not be found in the database."));

                consultationRepository.deleteById(consultation.getUid());
        }

        public void update(String id, ConsultationReq consultationReq) {
                Consultation consultationFromDB = consultationRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException(
                                                "The specified consultation could not be found in the database."));

                LocalDate consultationDate = consultationReq.getConsultationDate();
                LocalTime consultationTime = consultationReq.getConsultationTime();
                Consultation existingConsultation = consultationRepository.findByConsultationDateAndConsultationTime(
                                consultationDate,
                                consultationTime);

                LocalDateTime dateTime = LocalDateTime.of(
                                consultationDate.getYear(),
                                consultationDate.getMonth(),
                                consultationDate.getDayOfMonth(),
                                consultationTime.getHour(),
                                consultationTime.getMinute(),
                                consultationTime.getSecond());

                boolean isDateValid = Boolean.FALSE.equals(utilDate.checkIfPreviousDate(dateTime));

                boolean canUpdateConsultation = true;
                // If there is an existing consultation -> only allow update if it's the same
                // consultation or if status is CANCELLED
                if (existingConsultation != null) {
                        canUpdateConsultation = consultationFromDB.getUid().equals(existingConsultation.getUid())
                                        || existingConsultation.getStatus() == ConsultationStatus.CANCELED;
                }

                if (isDateValid) {
                        if (canUpdateConsultation) {
                                String notFoundMessage = "not found in the database.";
                                if (consultationReq.getDentistUid() != null){
                                Dentist dentist = dentistRepository.findById(consultationReq.getDentistUid())
                                                .orElseThrow(() -> new DataNotFoundException(
                                                                "Dentist " + notFoundMessage));
                                        consultationFromDB.setDentistUid(dentist.getUid());

                                }
                                Attendant attendant = attendantRepository.findById(consultationReq.getAttendantUid())
                                                .orElseThrow(() -> new DataNotFoundException(
                                                                "Attendant " + notFoundMessage));

                                consultationFromDB.setAttendantUid(attendant.getUid());
                                consultationFromDB.setConsultationDate(consultationReq.getConsultationDate());
                                consultationFromDB.setConsultationTime(consultationReq.getConsultationTime());
                                consultationFromDB.setNotes(consultationReq.getNotes());
                                consultationFromDB.setAmount(consultationReq.getAmount());
                                if (consultationFromDB.getStatus() != consultationReq.getStatus()) {
                                        consultationFromDB.setStatus(consultationReq.getStatus());
                                        consultationFromDB.setStatusUpdatedAt(LocalDateTime.now());
                                }
                                consultationRepository.save(consultationFromDB);
                        } else
                                throw new ConsultationStatusException(
                                                "There is already a scheduled and confirmed consultation for the date: "
                                                                +
                                                                consultationDate + " and time: " + consultationTime + ".");
                } else
                        throw new ConsultationStatusException(
                                        "The chosen date/time for scheduling the consultation is earlier than today. Please provide a date after the current date.");
        }

        public ConsultationStatus checkIfConsultationCompleted(Consultation consultation) {
                // If consultation is CONFIRMED and date/time is in the past -> return REALIZED
                // status. Otherwise return current status.
                LocalDateTime dateTime = LocalDateTime.of(
                                consultation.getConsultationDate().getYear(),
                                consultation.getConsultationDate().getMonth(),
                                consultation.getConsultationDate().getDayOfMonth(),
                                consultation.getConsultationTime().getHour(),
                                consultation.getConsultationTime().getMinute(),
                                consultation.getConsultationTime().getSecond());
                if (consultation.getStatus() == ConsultationStatus.CONFIRMED
                                && Boolean.TRUE.equals(utilDate.checkIfPreviousDate(dateTime)))
                        return ConsultationStatus.COMPLETED;
                else
                        return consultation.getStatus();
        }
}
