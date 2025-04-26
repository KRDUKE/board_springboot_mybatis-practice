package com.dukefirstboard.board.repository;

import com.dukefirstboard.board.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SqlSessionTemplate sql;


    public void save(UserDTO userDTO) {
        sql.insert("User.save", userDTO);
    }

    public UserDTO findByEmail(String email) {
        return sql.selectOne("User.findByEmail", email);
    }

    public UserDTO findByNickname(String nickname) {
        return sql.selectOne("User.findByNickname", nickname);
    }
}