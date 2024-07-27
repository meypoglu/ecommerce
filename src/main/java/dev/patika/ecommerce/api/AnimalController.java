package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.ICustomerService;
import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.dto.request.animal.AnimalSaveRequest;
import dev.patika.ecommerce.dto.response.animal.AnimalResponse;
import dev.patika.ecommerce.entities.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;
    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;
    private final IVaccineService vaccineService;
    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);
        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);
        Doctor doctor = this.doctorService.get(animalSaveRequest.getDoctorId());
        saveAnimal.setDoctor(doctor);

        List<Vaccine> vaccines = animalSaveRequest.getVaccineIds().stream()
                .map(vaccineService::get)
                .collect(Collectors.toSet());
        saveAnimal.setVaccineList(vaccines);

    }
}
