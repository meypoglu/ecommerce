package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal get(int id);
    Animal update(Animal animal);
    boolean delete(int id);
    Page<Animal> cursor(int page, int pageSize);
    List<Animal> findByName(String name);

}
