package com.dukefirstboard.board.repository;

import com.dukefirstboard.board.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final SqlSessionTemplate sql;

    public void save(CommentDTO commentDTO) {
        sql.insert("Comment.save", commentDTO);
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        return sql.selectList("Comment.findByBoardId", boardId);
    }
}