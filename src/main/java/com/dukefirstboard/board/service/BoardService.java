package com.dukefirstboard.board.service;

import com.dukefirstboard.board.dto.*;
import com.dukefirstboard.board.repository.BoardRepository;
import com.dukefirstboard.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(BoardDTO boardDTO) throws IOException {
        UserDTO user = userRepository.findByEmail(boardDTO.getBoardWriter());
        boardDTO.setUserId(user.getId());
        if (boardDTO.getBoardFile() == null || boardDTO.getBoardFile().isEmpty()) {
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            boardDTO.setFileAttached(1);
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                String savePath = "C:/Users/DUKE/Downloads/testsave/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }

    public PageDTO findAll(int page, Long categoryId, String keyword) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        params.put("categoryId", categoryId);
        params.put("keyword", keyword != null ? "%" + keyword + "%" : null);
        List<BoardDTO> boardList = boardRepository.findAllWithPaging(params);
        int totalElements = boardRepository.count(params);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setBoardList(boardList);
        pageDTO.setCurrentPage(page);
        pageDTO.setTotalPages(totalPages);
        pageDTO.setTotalElements(totalElements);
        return pageDTO;
    }

    /*public void saveUser(UserDTO userDTO) {
        // 비밀번호 암호화 (Spring Security 사용 시)
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO);
    }*/

    public void saveUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.findByNickname(userDTO.getNickname()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO);
    }

    public UserDTO findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }




}