package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IPublisherService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.PublisherRepo;
import dev.patika.ecommerce.entities.Publisher;
import org.springframework.stereotype.Service;

@Service
public class PublisherManager implements IPublisherService {
    private final PublisherRepo publisherRepo;

    public PublisherManager(PublisherRepo publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return this.publisherRepo.save(publisher);
    }

    @Override
    public Publisher get(int id) {
        return this.publisherRepo.findById(id).orElseThrow(()-> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Publisher update(Publisher publisher) {
        Publisher existingPublisher = this.get(publisher.getId());
        existingPublisher.setName(publisher.getDate());
        existingPublisher.setDate(publisher.getDate());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setBookList(publisher.getBookList());
        return this.publisherRepo.save(existingPublisher);
    }

    @Override
    public boolean delete(int id) {
        Publisher publisher = this.get(id);
        this.publisherRepo.delete(publisher);
        return true;
    }
