package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAuthorService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AuthorRepo;
import dev.patika.ecommerce.entities.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorManager implements IAuthorService {

    private final AuthorRepo authorRepo;

    public AuthorManager(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public Author save(Author author) {
        return this.authorRepo.save(author);
    }

    @Override
    public Author get(int id) {
        return this.authorRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Author update(Author author) {
        return this.authorRepo.save(author);
    }

    @Override
    public boolean delete(int id) {
        Author author = this.get(id);
        this.authorRepo.delete(author);
        return true;
    }
}