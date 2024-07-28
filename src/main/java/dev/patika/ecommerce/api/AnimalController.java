package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.*;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.animal.AnimalSaveRequest;
import dev.patika.ecommerce.dto.request.animal.AnimalUpdateRequest;
import dev.patika.ecommerce.dto.request.customer.CustomerUpdateRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.animal.AnimalResponse;
import dev.patika.ecommerce.dto.response.customer.CustomerResponse;
import dev.patika.ecommerce.entities.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService, IDoctorService doctorService, IAppointmentService appointmentService, IVaccineService vaccineService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.vaccineService = vaccineService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal animal = modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = customerService.get(animalSaveRequest.getCustomerId());
        animal.setCustomer(customer);

        List<Vaccine> vaccines = animalSaveRequest.getVaccineIds().stream()
                .map(vaccineService::get)
                .collect(Collectors.toList());
        animal.setVaccineList(vaccines);

        Animal savedAnimal = animalService.save(animal);

        // AnimalResponse oluşturulurken Customer ve Vaccine ID'leri ekleniyor
        AnimalResponse response = new AnimalResponse();
        response.setId(savedAnimal.getId());
        response.setName(savedAnimal.getName());
        response.setSpecies(savedAnimal.getSpecies());
        response.setBreed(savedAnimal.getBreed());
        response.setGender(savedAnimal.getGender());
        response.setColour(savedAnimal.getColour());
        response.setDateOfBirth(savedAnimal.getDateOfBirth());
        response.setCustomerId(savedAnimal.getCustomer().getId());
        response.setVaccineIds(savedAnimal.getVaccineList().stream()
                .map(Vaccine::getId)
                .collect(Collectors.toList()));

        return ResultHelper.created(response);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        Customer customer = this.customerService.get(id);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer, CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }

    @GetMapping
    public ResultData<List<AnimalResponse>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        List<AnimalResponse> response = animals.stream()
                .map(animal -> {
                    AnimalResponse animalResponse = new AnimalResponse();
                    animalResponse.setId(animal.getId());
                    animalResponse.setName(animal.getName());
                    animalResponse.setSpecies(animal.getSpecies());
                    animalResponse.setBreed(animal.getBreed());
                    animalResponse.setGender(animal.getGender());
                    animalResponse.setColour(animal.getColour());
                    animalResponse.setDateOfBirth(animal.getDateOfBirth());
                    animalResponse.setCustomerId(animal.getCustomer().getId());
                    animalResponse.setVaccineIds(animal.getVaccineList().stream()
                            .map(Vaccine::getId)
                            .collect(Collectors.toList()));
                    return animalResponse;
                })
                .collect(Collectors.toList());
        return ResultHelper.success(response);
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class);
        this.animalService.update(updateAnimal);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(updateAnimal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/search")
    public ResultData<List<AnimalResponse>> searchAnimalsByName(@RequestParam(name = "name") String name) {
        List<Animal> animals = this.animalService.findByName(name);
        List<AnimalResponse> animalResponses = new ArrayList<>();
        for (Animal animal : animals) {
            AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);
            animalResponses.add(animalResponse);
        }
        return ResultHelper.success(animalResponses);
    }
}
