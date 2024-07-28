package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.exception.CustomException;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.ecommerce.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.ecommerce.dto.request.customer.CustomerSaveRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.appointment.AppointmentResponse;
import dev.patika.ecommerce.dto.response.customer.CustomerResponse;
import dev.patika.ecommerce.entities.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;
    private final IAvailableDateService availableDateService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IAnimalService animalService, IDoctorService doctorService, IAvailableDateService availableDateService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
        this.availableDateService = availableDateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);
        try {
            Animal animal = this.animalService.get(appointmentSaveRequest.getAnimalId());
            saveAppointment.setAnimal(animal);

            Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());
            saveAppointment.setDoctorAppointment(doctor);

            AvailableDate availableDate = this.availableDateService.get(appointmentSaveRequest.getAvailableDateId());
            saveAppointment.setAvailableDate(availableDate);

            this.appointmentService.save(saveAppointment);
        } catch (NotFoundException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Veri bulunamadı: " + e.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        Appointment appointment = this.appointmentService.get(id);
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize)
    {
        Page<Appointment> appointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage.map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
        return ResultHelper.cursor(appointmentResponsePage);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class);
        try {
            this.appointmentService.update(updateAppointment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(updateAppointment, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            availableDateService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> searchAppointmentsByDateRangeAndAnimal(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "animalId") Long animalId) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Appointment> appointments = this.appointmentService.findByAnimalIdAndAppointmentDateBetween(animalId, startDateTime, endDateTime);
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }

    @GetMapping("/search/by-doctor")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> searchAppointmentsByDateRangeAndDoctor(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "doctorId") Long doctorId) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Appointment> appointments = this.appointmentService.findByDoctorIdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(appointmentResponses);
    }
}
