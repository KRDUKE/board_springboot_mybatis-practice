package com.dukefirstboard.board.repository;

import com.dukefirstboard.board.dto.RecommendDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendRepository {
    private final SqlSessionTemplate sql;

    public void save(RecommendDTO recommendDTO) {
        sql.insert("Recommend.save", recommendDTO);
    }

    public RecommendDTO findByBoardIdAndUserId(Long boardId, Long userId) {
        RecommendDTO recommendDTO = new RecommendDTO();
        recommendDTO.setBoardId(boardId);
        recommendDTO.setUserId(userId);
        return sql.selectOne("Recommend.findByBoardIdAndUserId", recommendDTO);
    }
}