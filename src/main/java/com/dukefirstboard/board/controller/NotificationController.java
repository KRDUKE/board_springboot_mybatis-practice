package com.dukefirstboard.board.controller;

import com.dukefirstboard.board.dto.NotificationDTO;
import com.dukefirstboard.board.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public String getNotifications(Model model, Authentication authentication) {
        String nickname = authentication.getName();
        Long userId = notificationService.findUserIdByNickname(nickname);
        model.addAttribute("notifications", notificationService.findByUserId(userId));
        return "notifications";
    }

    @GetMapping("/notification/read/{id}")
    public String readNotification(@PathVariable("id") Long id) {
        NotificationDTO notification = notificationService.findById(id);
        notificationService.markAsRead(id);
        return "redirect:/" + notification.getBoardId();
    }
}