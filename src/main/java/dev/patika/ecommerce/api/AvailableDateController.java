package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.business.concretes.AvailableDateManager;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.ecommerce.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.ecommerce.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.ecommerce.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.ecommerce.dto.request.customer.CustomerSaveRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.appointment.AppointmentResponse;
import dev.patika.ecommerce.dto.response.availableDateResponse.AvailableDateResponse;
import dev.patika.ecommerce.dto.response.customer.CustomerResponse;
import dev.patika.ecommerce.entities.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper, IDoctorService doctorService) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        AvailableDate saveAvailableDate = new AvailableDate();
        saveAvailableDate.setAvailableDate(availableDateSaveRequest.getDate());

        List<Doctor> doctors = availableDateSaveRequest.getDoctorIds().stream()
                .map(doctorService::get)
                .collect(Collectors.toList());
        saveAvailableDate.setDoctorList(doctors);
        try {
            this.availableDateService.save(saveAvailableDate);
            AvailableDate savedDate = availableDateService.getAvailableDateById(saveAvailableDate.getId());
            AvailableDateResponse response = new AvailableDateResponse();
            response.setId(savedDate.getId());
            response.setDate(savedDate.getAvailableDate());
            response.setDoctorIds(savedDate.getDoctorList().stream()
                    .map(Doctor::getId)
                    .collect(Collectors.toList()));
            return ResultHelper.created(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest, AvailableDate.class);
        try {
            this.availableDateService.update(updateAvailableDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        AvailableDateResponse availableDateResponse = this.modelMapper.forRequest().map(updateAvailableDate, AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping
    public ResultData<List<AvailableDateResponse>> getAllAvailableDates() {
        List<AvailableDate> availableDates = this.availableDateService.getAllAvailableDates();
        List<AvailableDateResponse> response = availableDates.stream()
                .map(availableDate -> {
                    AvailableDateResponse availableDateResponse = new AvailableDateResponse();
                    availableDateResponse.setId(availableDate.getId());
                    availableDateResponse.setDate(availableDate.getAvailableDate());
                    availableDateResponse.setDoctorIds(availableDate.getDoctorList().stream()
                            .map(Doctor::getId)
                            .collect(Collectors.toList()));
                    return availableDateResponse;
                })
                .collect(Collectors.toList());
        return ResultHelper.success(response);
    }

    @GetMapping("/{id}")
    public ResultData<AvailableDateResponse> getAvailableDateById(@PathVariable Long id) {
        AvailableDate availableDate = this.availableDateService.getAvailableDateById(id);
        AvailableDateResponse response = this.modelMapper.forRequest().map(availableDate, AvailableDateResponse.class);
        return ResultHelper.success(response);
    }
}