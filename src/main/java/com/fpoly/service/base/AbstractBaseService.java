package com.fpoly.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class AbstractBaseService<T extends Serializable,ID> implements BaseService<T,ID> {
    @Autowired
    JpaRepository<T,ID> repository;

    public AbstractBaseService() {
        super();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(ID id ,T entity) {
        T dbObject = repository.findById(id).orElseThrow(
                () -> new RuntimeException("ID not found" + id)
        );
        dbObject = entity;
        return repository.save(dbObject);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("ID not found" + id)
        );
    }

    @Override
    public Page<T> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page,size));
    }
}
