package com.fpoly.service.impl;

import com.fpoly.entity.TrangThai;
import com.fpoly.repository.TrangThaiRepository;
import com.fpoly.service.TrangThaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrangThaiServiceImpl implements TrangThaiService {
    private final TrangThaiRepository trangThaiRepository;

    @Autowired
    public TrangThaiServiceImpl(TrangThaiRepository trangThaiRepository) {
        this.trangThaiRepository = trangThaiRepository;
    }

    @Override
    public TrangThai getTrangThaiById(Long id) {
        return trangThaiRepository.findById(id).orElse(null);
    }
}