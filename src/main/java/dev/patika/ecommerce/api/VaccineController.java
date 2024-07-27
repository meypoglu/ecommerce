package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.doctor.DoctorSaveRequest;
import dev.patika.ecommerce.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.ecommerce.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.ecommerce.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.doctor.DoctorResponse;
import dev.patika.ecommerce.dto.response.vaccine.VaccineResponse;
import dev.patika.ecommerce.entities.Doctor;
import dev.patika.ecommerce.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;

    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapper) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        this.vaccineService.save(saveVaccine);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class);
        return ResultHelper.created(vaccineResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") int id) {
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                             @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize)
    {
        Page<Vaccine> vaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage.map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
        return ResultHelper.cursor(vaccineResponsePage);
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
    public Result delete(@PathVariable("id") int id) {
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
}
