package dev.patika.ecommerce.dto.request.borrow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowSaveRequest {
    private String name;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private int bookId;
}
