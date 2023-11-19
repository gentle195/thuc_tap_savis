package com.fpoly.service;

import com.fpoly.config.BcryptedPasswordEncoderConfig;
import com.fpoly.convertor.NguoiDungConvertor;
import com.fpoly.convertor.NguoiDungVaiTroConvertor;
import com.fpoly.dto.NguoiDungDTO;
import com.fpoly.dto.NguoiDungVaiTroDTO;
import com.fpoly.dto.TaiKhoanDTO;
import com.fpoly.entity.AuthenticationProvider;
import com.fpoly.entity.NguoiDung;
import com.fpoly.entity.NguoiDungVaiTro;
import com.fpoly.entity.VaiTro;
import com.fpoly.repository.INguoiDungPaginRespository;
import com.fpoly.repository.NguoiDungRepository;
import com.fpoly.repository.NguoiDungVaiTroRepository;
import com.fpoly.repository.VaiTroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NguoiDungService {
    private static NguoiDungRepository nguoiDungRepository;
    private static Map<Integer, NguoiDung> nguoiDungMap;
    private final INguoiDungPaginRespository iNguoiDungPaginRespository;

    @Autowired
    private NguoiDungConvertor nguoiDungConvertor;

    @Autowired
    private NguoiDungVaiTroConvertor nguoiDungVaiTroConvertor;

    @Autowired
    private BcryptedPasswordEncoderConfig passwordEncoder;

    @Autowired
    private VaiTroRepository VaiTroRepository;

    @Autowired
    private NguoiDungVaiTroRepository nguoiDungVaiTroRepository;

    @Autowired
    public NguoiDungService(NguoiDungRepository nguoiDungRepository, INguoiDungPaginRespository iNguoiDungPaginRespository) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.iNguoiDungPaginRespository = iNguoiDungPaginRespository;
    }

    public List<NguoiDung> getAllUsers() {
        return nguoiDungRepository.findAll();
    }

    public NguoiDung getEdit(Long id) {
        return nguoiDungRepository.findById(id).get();
    }

    public void edit(Integer id, NguoiDung productsEntity) {
        nguoiDungMap.put(id, productsEntity);
    }

    public void save(NguoiDung nguoiDung) {
        nguoiDungRepository.save(nguoiDung);
    }

    public Page<NguoiDung> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iNguoiDungPaginRespository.findAll(pageable);
    }


    public Page<NguoiDung> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iNguoiDungPaginRespository.GetAll(pageable);
    }

    public void saveNguoiDung(NguoiDung nguoiDung) {
        nguoiDungRepository.save(nguoiDung);
    }

    public NguoiDung getNguoiDungById(Long id) {
        if (id != null) {
            Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findById(id);
            if (optionalNguoiDung.isPresent()) {
                return optionalNguoiDung.get();
            }
        }
        return null;
    }

    public void updateUserStatus(Long id, int trangThai) {
        NguoiDung user = nguoiDungRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        user.setTrangThai(trangThai);
        nguoiDungRepository.save(user);
    }

    public NguoiDungDTO findByEmailAndTrangThaiAndDaXoa(String email) {
        NguoiDungDTO nguoiDungDTO = null;
        try {
            List<NguoiDungVaiTroDTO> listNguoiDungVaiTroDTO = new ArrayList<NguoiDungVaiTroDTO>();
            if (email != null) {
                nguoiDungDTO = new NguoiDungDTO();
                NguoiDung nguoiDung = nguoiDungRepository.findByEmailAndTrangThaiAndDaXoa(email);
                for (NguoiDungVaiTro nguoiDungVaiTro : nguoiDung.getListNguoiDungVaiTro()) {
                    NguoiDungVaiTroDTO nguoiDungVaiTroDTO = new NguoiDungVaiTroDTO();
                    nguoiDungVaiTroDTO = nguoiDungVaiTroConvertor.toDTO(nguoiDungVaiTro);
                    listNguoiDungVaiTroDTO.add(nguoiDungVaiTroDTO);
                }
                nguoiDungDTO = nguoiDungConvertor.toDTO(nguoiDung);
                nguoiDungDTO.setListNguoiDungVaiTroDTO(listNguoiDungVaiTroDTO);
                return nguoiDungDTO;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void capNhatMatKhau(TaiKhoanDTO taiKhoanDTO) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(taiKhoanDTO.getEmail());
        if (nguoiDung != null) {
            nguoiDung.setMatKhau(passwordEncoder.encode(taiKhoanDTO.getPassword()));
        }
        nguoiDungRepository.save(nguoiDung);
    }

    public NguoiDung findByEmail(String email) {
        if (email != null) {
            return nguoiDungRepository.findByEmail(email);
        } else {
            return null;
        }
    }

    public void taoNguoiDungSauKhiDangNhapVoiMangXaHoiThanhCong(String email, String fullname,
                                                                AuthenticationProvider google) {
        NguoiDung entity = new NguoiDung();
        entity.setEmail(email);
        entity.setTenNguoiDung(fullname);
        entity.setAuthProvider(google);
        entity.setDaXoa(false);
        entity.setTrangThai(0);
        entity = nguoiDungRepository.save(entity);
        NguoiDungVaiTro nguoiDungVaiTro = new NguoiDungVaiTro();
        nguoiDungVaiTro.setNguoiDung(entity);
        nguoiDungVaiTro.setVaiTro(VaiTroRepository.findByTenVaiTro("CUSTOMER"));
        nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
    }

    public void capNhatNguoiDungSauKhiDangNhapVoiMangXaHoiThanhCong(String email, String fullname,
                                                                    AuthenticationProvider google) {
        NguoiDung entity = findByEmail(email);
        entity.setEmail(email);
        entity.setTenNguoiDung(fullname);
        entity.setAuthProvider(google);
        entity.setDaXoa(false);
        entity.setTrangThai(0);
        entity = nguoiDungRepository.save(entity);
    }


}
