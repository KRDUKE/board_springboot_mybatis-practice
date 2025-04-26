package com.dukefirstboard.board.service;

import com.dukefirstboard.board.dto.CommentDTO;
import com.dukefirstboard.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO) {
        commentRepository.save(commentDTO);
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
}