package dev.patika.ecommerce.dto.request.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    private int id;
    private String name;
    private String publicationYear;
    private int stock;
    private int authorId;
    private int publisherId;
    private List<Integer> categoryIds;
}