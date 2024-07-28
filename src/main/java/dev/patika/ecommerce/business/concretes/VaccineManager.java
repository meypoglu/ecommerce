package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.exception.CustomException;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.VaccineRepo;
import dev.patika.ecommerce.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private final IAnimalService animalService;

    public VaccineManager(VaccineRepo vaccineRepo, IAnimalService animalService) {
        this.vaccineRepo = vaccineRepo;
        this.animalService = animalService;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        List<Vaccine> existingVaccines = vaccineRepo.findByNameAndCodeAndAnimal_Id(vaccine.getName(), vaccine.getCode(), vaccine.getAnimal().getId());
        for (Vaccine v : existingVaccines) {
            if (!v.getProtectionEndDate().isBefore(LocalDate.now())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Aşının koruyuculuk süresi devam ediyor.");
            }
        }
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(Long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public List<Vaccine> findByDateRange(LocalDate protectionStartDate, LocalDate protectionEndDate) {
        return vaccineRepo.findByProtectionEndDateBetween(protectionStartDate, protectionEndDate);
    }

    @Override
    public List<Vaccine> getAllVaccines() {
        return vaccineRepo.findAll();
    }

    @Override
    public List<Vaccine> findByAnimalId(Long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }
}