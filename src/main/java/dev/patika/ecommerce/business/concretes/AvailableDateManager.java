package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AvailableDateRepo;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(long id) {
        return this.availableDateRepo.findById((int) id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        this.get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public AvailableDate getAvailableDateById(Long id) {
        return availableDateRepo.findById(Math.toIntExact(id)).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public List<AvailableDate> findByDoctorIdsAndDate(Long doctorId, LocalDate date) {
        return availableDateRepo.findByDoctors_IdAndAvailableDate(doctorId, date);
    }

    @Override
    public List<AvailableDate> findByDoctorIds(Long doctorId) {
        return availableDateRepo.findByDoctors_Id(doctorId);
    }

    @Override
    public List<AvailableDate> getAllAvailableDates() {
        return availableDateRepo.findAll();
    }

}
