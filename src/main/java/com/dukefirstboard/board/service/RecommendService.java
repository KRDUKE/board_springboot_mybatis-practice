package com.dukefirstboard.board.service;

import com.dukefirstboard.board.dto.RecommendDTO;
import com.dukefirstboard.board.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    public void save(RecommendDTO recommendDTO) {
        RecommendDTO existing = recommendRepository.findByBoardIdAndUserId(recommendDTO.getBoardId(), recommendDTO.getUserId());
        if (existing != null) {
            recommendDTO.setId(existing.getId());
            recommendRepository.save(recommendDTO);
        } else {
            recommendRepository.save(recommendDTO);
        }
    }

    public RecommendDTO getRecommendation(Long boardId, Long userId) {
        return recommendRepository.findByBoardIdAndUserId(boardId, userId);
    }
}