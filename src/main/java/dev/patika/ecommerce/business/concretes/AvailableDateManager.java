package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAvailableDateService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AppointmentRepo;
import dev.patika.ecommerce.dao.AvailableDateRepo;
import dev.patika.ecommerce.dao.DoctorRepo;
import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private static final Logger logger = LoggerFactory.getLogger(AvailableDateManager.class);

    public AvailableDateManager(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo, AppointmentRepo appointmentRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public AvailableDate save(AvailableDate availableDate) {
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        this.get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(Long id) {
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
        return availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public List<AvailableDate> findByDoctorIdsAndDate(Long doctorId, LocalDate date) {
        return availableDateRepo.findByDoctorList_IdAndAvailableDate(doctorId, date);
    }

    @Override
    public List<AvailableDate> findByDoctorIds(Long doctorId) {
        return availableDateRepo.findByDoctorList_Id(doctorId);
    }

    @Override
    public List<AvailableDate> getAllAvailableDates() {
        return availableDateRepo.findAll();
    }
}
