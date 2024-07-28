package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.ICustomerService;
import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.animal.AnimalSaveRequest;
import dev.patika.ecommerce.dto.request.animal.AnimalUpdateRequest;
import dev.patika.ecommerce.dto.response.animal.AnimalResponse;
import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
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

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService, IDoctorService doctorService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal animal = modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = customerService.get(animalSaveRequest.getCustomerId());
        animal.setCustomer(customer);

        if (animalSaveRequest.getDoctorId() != null) {
            Doctor doctor = doctorService.get(animalSaveRequest.getDoctorId());
            animal.setDoctor(doctor);
        }

        Animal savedAnimal = animalService.save(animal);

        AnimalResponse response = modelMapper.forResponse().map(savedAnimal, AnimalResponse.class);
        return ResultHelper.created(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    @GetMapping
    public ResultData<List<AnimalResponse>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        List<AnimalResponse> response = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
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

    @GetMapping("/customer/{customerId}")
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable Long customerId) {
        List<Animal> animals = this.animalService.findByCustomerId(customerId);
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(animalResponses);
    }
}