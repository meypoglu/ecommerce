package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;
import dev.patika.ecommerce.core.utilities.ResultHelper;
import dev.patika.ecommerce.dto.request.customer.CustomerUpdateRequest;
import dev.patika.ecommerce.dto.request.doctor.DoctorSaveRequest;
import dev.patika.ecommerce.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.ecommerce.dto.response.CursorResponse;
import dev.patika.ecommerce.dto.response.customer.CustomerResponse;
import dev.patika.ecommerce.dto.response.doctor.DoctorResponse;
import dev.patika.ecommerce.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {
        Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
        this.doctorService.save(saveDoctor);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(saveDoctor, DoctorResponse.class);
        return ResultHelper.created(doctorResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") int id) {
        Doctor doctor = this.doctorService.get(id);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(doctor, DoctorResponse.class);
        return ResultHelper.success(doctorResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize)
    {
        Page<Doctor> doctorPage = this.doctorService.cursor(page, pageSize);
        Page<DoctorResponse> doctorResponsePage = doctorPage.map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class));
        return ResultHelper.cursor(doctorResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        Doctor updateDoctor = this.modelMapper.forRequest().map(doctorUpdateRequest, Doctor.class);
        this.doctorService.update(updateDoctor);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(updateDoctor, DoctorResponse.class);
        return ResultHelper.success(doctorResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.doctorService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/search")
    public ResultData<List<DoctorResponse>> searchDoctorsByName(@RequestParam(name = "name") String name) {
        List<Doctor> doctors = this.doctorService.findByName(name);
        List<DoctorResponse> doctorRespons = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorResponse doctorResponse = this.modelMapper.forResponse().map(doctor, DoctorResponse.class);
            doctorRespons.add(doctorResponse);
        }
        return ResultHelper.success(doctorRespons);
    }
}
