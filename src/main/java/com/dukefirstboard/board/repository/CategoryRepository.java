package com.dukefirstboard.board.repository;

import com.dukefirstboard.board.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final SqlSessionTemplate sql;

    public List<CategoryDTO> findAll() {
        return sql.selectList("Category.findAll");
    }
}