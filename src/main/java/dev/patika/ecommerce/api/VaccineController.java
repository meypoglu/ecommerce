package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.ecommerce.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.vaccine.VaccineResponse;
import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapper) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        Vaccine vaccine = modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        Vaccine savedVaccine = vaccineService.save(vaccine);

        VaccineResponse response = new VaccineResponse();
        response.setId(savedVaccine.getId());
        response.setName(savedVaccine.getName());
        response.setCode(savedVaccine.getCode());
        response.setProtectionStartDate(savedVaccine.getProtectionStartDate());
        response.setProtectionEndDate(savedVaccine.getProtectionEndDate());
        response.setAnimalId(savedVaccine.getAnimal().getId());

        return ResultHelper.created(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @GetMapping
    public ResultData<List<VaccineResponse>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.getAllVaccines();
        List<VaccineResponse> response = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(response);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        this.vaccineService.update(updateVaccine);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/search")
    public ResultData<List<VaccineResponse>> searchVaccinesByDate(@RequestParam(name = "protectionStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionStartDate,
                                                                  @RequestParam(name = "protectionEndDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate protectionEndDate) {
        List<Vaccine> vaccines = this.vaccineService.findByDateRange(protectionStartDate, protectionEndDate);
        List<VaccineResponse> vaccineResponses = new ArrayList<>();
        for (Vaccine vaccine : vaccines) {
            VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
            vaccineResponses.add(vaccineResponse);
        }
        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/animal/{animalId}")
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable Long animalId) {
        List<Vaccine> vaccines = this.vaccineService.findByAnimalId(animalId);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }


}