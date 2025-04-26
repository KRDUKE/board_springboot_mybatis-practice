package com.dukefirstboard.board.service;

import com.dukefirstboard.board.dto.CategoryDTO;
import com.dukefirstboard.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll();
    }
}