package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IBorrowService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.BookRepo;
import dev.patika.ecommerce.dao.BorrowRepo;
import dev.patika.ecommerce.entities.Book;
import dev.patika.ecommerce.entities.Borrow;
import org.springframework.stereotype.Service;

@Service
public class BorrowManager implements IBorrowService {
    private final BorrowRepo borrowRepo;
    private final BookRepo bookRepo;

    public BorrowManager(BorrowRepo borrowRepo, BookRepo bookRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public Borrow save(Borrow bookBorrowing) {
        Book book = bookRepo.findById(bookBorrowing.getBook().getId())
                .orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
        if (book.getStock() <= 0) {
            throw new RuntimeException("Kitap stokta yok.");
        }
        book.setStock(book.getStock() - 1);
        bookRepo.save(book);
        return this.borrowRepo.save(bookBorrowing);
    }

    @Override
    public Borrow get(int id) {
        return this.borrowRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Borrow update(Borrow bookBorrowing) {
        return this.borrowRepo.save(bookBorrowing);
    }

    @Override
    public boolean delete(int id) {
        Borrow bookBorrowing = this.get(id);
        borrowRepo.delete(bookBorrowing);
        return true;
    }
}
