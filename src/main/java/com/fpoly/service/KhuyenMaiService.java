package com.fpoly.service;

import com.fpoly.dto.KhuyenMaiDTO;
import org.springframework.data.domain.Page;

public interface KhuyenMaiService {
    Page<KhuyenMaiDTO> getListKhuyenMai(int page,
                                        int size,
                                        String keyword,
                                        String status,
                                        Integer startStr,
                                        Integer endStr,
                                        String dateFrom,
                                        String dateTo);

    KhuyenMaiDTO createVoucher(KhuyenMaiDTO khuyenMaiDTO);

    KhuyenMaiDTO editVoucher(Long id, KhuyenMaiDTO khuyenMaiDTO);

    KhuyenMaiDTO getVoucher(Long id);

    boolean deleteVoucher(Long id);

    boolean toggleDisableVoucher(Long id);

    KhuyenMaiDTO timKhuyenMaiTheoTenKhuyenMai(String maGiamGia);

    boolean checkExistVoucher(KhuyenMaiDTO khuyenMaiDTO);
}
