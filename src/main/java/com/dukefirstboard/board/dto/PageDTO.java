package com.dukefirstboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageDTO {
    private List<BoardDTO> boardList;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    // [NEW] 페이지 네비게이션용 메서드
    public boolean hasPrevious() {
        return currentPage > 1;
    }

    public boolean hasNext() {
        return currentPage < totalPages;
    }

    public int getStartPage() {
        return Math.max(1, currentPage - 2);
    }

    public int getEndPage() {
        return Math.min(totalPages, currentPage + 2);
    }
}