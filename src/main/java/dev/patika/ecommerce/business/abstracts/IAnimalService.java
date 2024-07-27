package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal get(long id);
    Animal update(Animal animal);
    boolean delete(long id);
    Page<Animal> cursor(int page, int pageSize);
    List<Animal> findByName(String name);

}
