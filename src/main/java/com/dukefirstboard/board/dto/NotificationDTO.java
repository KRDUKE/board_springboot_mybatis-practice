package com.dukefirstboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationDTO {
    private Long id;
    private Long userId;
    private Long boardId;
    private Long commentId;
    private String message;
    private int isRead;
    private String createdAt;
}