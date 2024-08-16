package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Publisher;

public interface IPublisherService {
    Publisher save(Publisher publisher);
    Publisher get(int id);
    Publisher update(Publisher publisher);
    boolean delete(int id);
}