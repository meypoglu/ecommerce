package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AnimalRepo;
import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;

    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(long id) {
        return this.animalRepo.findById((int) id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(long id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public List<Animal> findByName(String name) {
        List<Animal> animals = animalRepo.findByNameContainingIgnoreCase(name);
        if (animals.isEmpty()) {
            throw new NotFoundException(Message.NOT_FOUND);
        }
        return animals;
    }
}
