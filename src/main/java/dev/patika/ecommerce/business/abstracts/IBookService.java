package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Book;

public interface IBookService {
    Book save(Book book);
    Book get(int id);
    Book update(Book book);
    boolean delete(int id);
}
