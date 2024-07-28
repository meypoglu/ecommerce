package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
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
import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        try {
            this.availableDateService.save(saveAvailableDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        AvailableDateResponse availableDateResponse = this.modelMapper.forRequest().map(saveAvailableDate, AvailableDateResponse.class);
        return ResultHelper.created(availableDateResponse);
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
                .map(availableDate -> this.modelMapper.forRequest().map(availableDate, AvailableDateResponse.class))
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