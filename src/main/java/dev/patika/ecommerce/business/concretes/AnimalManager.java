package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAnimalService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AnimalRepo;
import dev.patika.ecommerce.entities.Animal;
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
        List<Animal> existingAnimals = animalRepo.findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirth(
                animal.getName(), animal.getSpecies(), animal.getBreed(), animal.getGender(), animal.getColour(), animal.getDateOfBirth()
        );

        if (!existingAnimals.isEmpty()) {
            throw new RuntimeException("Animal with the same details already exists.");
        }

        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(Long id) {
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

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepo.findAll();
    }

    @Override
    public List<Animal> findByCustomerId(Long customerId) {
        return animalRepo.findByCustomerId(customerId);
    }
}

