package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IVaccineService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.VaccineRepo;
import dev.patika.ecommerce.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManagement implements IVaccineService {
    private final VaccineRepo vaccineRepo;

    public VaccineManagement(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(long id) {
        return this.vaccineRepo.findById((int) id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(long id) {
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
        List<Vaccine> vaccines = vaccineRepo.findByProtectionEndDateBetween(protectionStartDate, protectionEndDate);
        if (vaccines.isEmpty()) {
            throw new NotFoundException(Message.NOT_FOUND);
        }
        return vaccines;
    }
}
