package dev.patika.ecommerce.api;

import dev.patika.ecommerce.business.abstracts.IDoctorService;
import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.config.modelMapper.IModelMapperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
}
