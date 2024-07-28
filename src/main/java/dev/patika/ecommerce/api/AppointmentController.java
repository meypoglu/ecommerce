package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IAnimalService animalService, IDoctorService doctorService, IAvailableDateService availableDateService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
        this.availableDateService = availableDateService;
    }

    @PostMapping()
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
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") int id) {
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
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/search")
    public ResultData<List<AppointmentResponse>> searchAppointmentsByDoctorName(@RequestParam(name = "doctorName") String doctorName) {
        List<Appointment> appointments = this.appointmentService.findByDoctorName(doctorName);
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }
}
