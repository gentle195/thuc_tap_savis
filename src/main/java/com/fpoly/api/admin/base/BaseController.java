package com.fpoly.api.admin.base;

import com.fpoly.service.base.AbstractBaseService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public class BaseController<T extends Serializable, ID> extends AbstractBaseService<T, ID> {
    @Override
    @PostMapping("/save")
    public T save(@RequestBody T entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("/update/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) {
        return super.update(id, entity);
    }

    @Override
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable ID id) {
        super.delete(id);
    }

    @Override
    @PostMapping("/find/{id}")
    public T findById(@PathVariable ID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/findAll/{page}/{size}")
    public Page<T> findAll(@PathVariable int page, @PathVariable int size) {
        return super.findAll(page, size);
    }
}
