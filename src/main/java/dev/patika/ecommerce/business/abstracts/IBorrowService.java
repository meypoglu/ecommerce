package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Borrow;

public interface IBorrowService {
    Borrow save(Borrow borrow);
    Borrow get(int id);
    Borrow update(Borrow borrow);
    boolean delete(int id);
}
