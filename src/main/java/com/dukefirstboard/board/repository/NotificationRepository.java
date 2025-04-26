package com.dukefirstboard.board.repository;

import com.dukefirstboard.board.dto.NotificationDTO;
import com.dukefirstboard.board.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final SqlSessionTemplate sql;

    public void save(NotificationDTO notificationDTO) {
        sql.insert("Notification.save", notificationDTO);
    }

    public List<NotificationDTO> findByUserId(Long userId) {
        return sql.selectList("Notification.findByUserId", userId);
    }

    public void markAsRead(Long id) {
        sql.update("Notification.markAsRead", id);
    }

    public UserDTO findUserByNickname(String nickname) {
        return sql.selectOne("Notification.findUserByNickname", nickname);
    }

    // [NEW] 알림 단건 조회
    public NotificationDTO findById(Long id) {
        return sql.selectOne("Notification.findById", id);
    }
}