package com.fpoly.service.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface BaseService<T extends Serializable,ID> {
    T save(T entity);

    T update(ID id,T entity);

    void delete(ID id);

    T findById(ID id);

    Page<T> findAll(int page, int size);
}
