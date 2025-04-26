package com.dukefirstboard.board.service;

import com.dukefirstboard.board.dto.NotificationDTO;
import com.dukefirstboard.board.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void save(NotificationDTO notificationDTO) {
        notificationRepository.save(notificationDTO);
    }

    public List<NotificationDTO> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void markAsRead(Long id) {
        notificationRepository.markAsRead(id);
    }

    public Long findUserIdByNickname(String nickname) {
        return notificationRepository.findUserByNickname(nickname).getId();
    }

    // [NEW] 알림 단건 조회
    public NotificationDTO findById(Long id) {
        return notificationRepository.findById(id);
    }
}