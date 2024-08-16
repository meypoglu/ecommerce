package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Author;

public interface IAuthorService {
    Author save(Author author);
    Author get(int id);
    Author update(Author author);
    boolean delete(int id);
}
