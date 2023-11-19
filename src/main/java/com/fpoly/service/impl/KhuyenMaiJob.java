package com.fpoly.service.impl;

import com.fpoly.entity.BaseEntity;
import com.fpoly.entity.KhuyenMai;
import com.fpoly.repository.KhuyenMaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhuyenMaiJob {
    private final KhuyenMaiRepository khuyenMaiRepository;

//    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Ho_Chi_Minh") 1 minute 1 time
    @Scheduled(cron = "0/2 * * * * *", zone = "Asia/Ho_Chi_Minh") //2 second 1 time
    @Transactional
    public void updateStatusVoucher() {
        List<KhuyenMai> list = khuyenMaiRepository.findAll();
        Date currentDate = new Date();

        List<Long> disable = list.stream()
                .filter(item -> currentDate.before(item.getNgayBatDau()) || currentDate.after(item.getNgayKetThuc()))
                .filter(KhuyenMai::isTrangThai)
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        khuyenMaiRepository.updateStatusByDate(disable, false);

        List<Long> enable = list.stream()
                .filter(item -> currentDate.before(item.getNgayKetThuc()) && currentDate.after(item.getNgayBatDau()))
                .filter(item -> !item.isTrangThai())
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        khuyenMaiRepository.updateStatusByDate(enable, true);
    }
}
