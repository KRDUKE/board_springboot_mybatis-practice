package com.dukefirstboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String createdAt;
}