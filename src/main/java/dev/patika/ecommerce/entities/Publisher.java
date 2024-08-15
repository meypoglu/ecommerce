package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "publishers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private int id;

    @Column(name = "publisher_name")
    private String name;

    @Column(name = "publisher_date")
    private String date;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> bookList;
}
