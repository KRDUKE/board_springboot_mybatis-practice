package com.dukefirstboard.board.board.board.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;//글번호

    private String boardWiter;//작성자

    private String boardPass;//게시글 비밀번호

    private String boardTitle;//글제목

    private String boardContents;//내용

    private int boardHits;//조회수

    private String createdAt;//작성시간


}
