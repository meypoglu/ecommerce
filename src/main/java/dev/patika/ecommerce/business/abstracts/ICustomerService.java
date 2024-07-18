package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer get(int id);
    Customer update(Customer customer);
    boolean delete(int id);
    Page<Customer> cursor(int page, int pageSize);
}
