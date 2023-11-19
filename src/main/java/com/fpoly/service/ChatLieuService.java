package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.ChatLieu;


public interface ChatLieuService {

	Page<ChatLieu> selectAllChatLieuExist(Pageable pageable);

	Optional<ChatLieu> findById(Long id);

	<S extends ChatLieu> S save(S entity);

	List<ChatLieu> selectAllChatLieuExist();

	Page<ChatLieu> getChatLieuExistByName(String tenChatLieu, Pageable pageable);

	void delete(ChatLieu entity);

}
