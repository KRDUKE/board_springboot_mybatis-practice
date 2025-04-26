package com.dukefirstboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecommendDTO {
    private Long id;
    private Long boardId;
    private Long userId;
    private String type;
    private String createdAt;
}