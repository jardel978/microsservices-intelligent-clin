package com.intelligentclin.users_service.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.intelligentclin.common_models.models.dtos.attendat.AttendantReq;
import com.intelligentclin.common_models.models.dtos.dentist.DentistReq;

@FeignClient(name = "clinic-service", path = "/")
public interface IClinicFeignClient {

    @PostMapping("/dentist/save")
    void createDentist(@RequestBody DentistReq dentist);

    @PostMapping("/attendant/save")
    void createAttendant(@RequestBody AttendantReq attendant);

}