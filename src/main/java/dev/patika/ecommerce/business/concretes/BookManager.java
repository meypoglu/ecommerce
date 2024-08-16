package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IBookService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.BookRepo;
import dev.patika.ecommerce.dao.CategoryRepo;
import dev.patika.ecommerce.entities.Book;
import dev.patika.ecommerce.entities.Category;
import org.springframework.stereotype.Service;

@Service
public class BookManager implements IBookService {
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    public BookManager(BookRepo bookRepo, CategoryRepo categoryRepo) {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Book save(Book book) {
        Book savedBook = this.bookRepo.save(book);
        for (Category category : book.getCategoryList()) {
            category.getBookList().add(savedBook);
            categoryRepo.save(category);
        }
        return savedBook;
    }

    @Override
    public Book get(int id) {
        return this.bookRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Book update(Book book) {
        Book existingBook = this.bookRepo.findById(book.getId())
                .orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));

        if (book.getBorrowList() == null) {
            book.setBorrowList(existingBook.getBorrowList());
        }

        return this.bookRepo.save(book);
    }


    @Override
    public boolean delete(int id) {
        Book book = this.get(id);
        bookRepo.delete(book);
        return true;
    }
