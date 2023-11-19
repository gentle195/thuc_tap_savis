package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.ChatLieu;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu,Long> {
	@Query(value = "SELECT * FROM chat_lieu c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<ChatLieu> selectAllChatLieuExist(Pageable pageable);

	@Query(value = "SELECT * FROM chat_lieu c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<ChatLieu> selectAllChatLieuExist();
	
	@Query(value = "SELECT * FROM chat_lieu c WHERE c.da_xoa = false AND c.ten_chat_lieu like %:tenChatLieu% ORDER BY c.id DESC", nativeQuery = true)
	Page<ChatLieu> getChatLieuExistByName(@Param("tenChatLieu") String tenChatLieu, Pageable pageable);
}
