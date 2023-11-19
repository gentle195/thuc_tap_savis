package com.fpoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.VaiTro;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro,Long> {
	
	@Query(value="SELECT * FROM vai_tro WHERE code= :vaiTro", nativeQuery = true)
	VaiTro findByTenVaiTro(@Param("vaiTro")String vaiTro);

}
