package dev.patika.ecommerce.dto.request.borrow;

import java.time.LocalDate;

public class BorrowUpdateRequest {
    private int id;
    private String name;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private int bookId;
}
