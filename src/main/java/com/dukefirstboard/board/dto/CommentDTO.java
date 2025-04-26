package com.dukefirstboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private String content;
    private String nickname;
    private String createdAt;
}