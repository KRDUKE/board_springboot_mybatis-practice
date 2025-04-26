package com.dukefirstboard.board.controller;

import com.dukefirstboard.board.dto.*;
import com.dukefirstboard.board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final RecommendService recommendService;
    private final NotificationService notificationService;

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Authentication authentication) throws IOException {
        String nickname = authentication.getName();
        boardDTO.setBoardWriter(nickname);
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "categoryId", required = false) Long categoryId,
                          @RequestParam(value = "keyword", required = false) String keyword) {
        PageDTO pageDTO = boardService.findAll(page, categoryId, keyword);
        model.addAttribute("boardList", pageDTO.getBoardList());
        model.addAttribute("page", pageDTO);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("keyword", keyword);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model, Authentication authentication) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }
        List<CommentDTO> comments = commentService.findByBoardId(id);
        model.addAttribute("comments", comments);
        String userNickname = authentication != null ? authentication.getName() : null;
        if (userNickname != null) {
            UserDTO user = boardService.findUserByNickname(userNickname);
            RecommendDTO recommend = recommendService.getRecommendation(id, user.getId());
            model.addAttribute("recommend", recommend);
        }
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("categories", categoryService.findAll());
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        // [NEW] 서버 측 비밀번호 검증
        BoardDTO existingBoard = boardService.findById(boardDTO.getId());
        if (!existingBoard.getBoardPass().equals(boardDTO.getBoardPass())) {
            model.addAttribute("error", "비밀번호가 틀립니다!");
            model.addAttribute("board", existingBoard);
            return "update";
        }
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }

    @PostMapping("/comment")
    public String saveComment(@ModelAttribute CommentDTO commentDTO, Authentication authentication) {
        String nickname = authentication.getName();
        UserDTO user = boardService.findUserByNickname(nickname);
        commentDTO.setUserId(user.getId());
        commentDTO.setNickname(nickname);
        commentService.save(commentDTO);
        BoardDTO board = boardService.findById(commentDTO.getBoardId());
        if (!board.getBoardWriter().equals(nickname)) {
            NotificationDTO notification = new NotificationDTO();
            notification.setUserId(board.getUserId());
            notification.setBoardId(board.getId());
            notification.setCommentId(commentDTO.getId());
            notification.setMessage(nickname + "님이 댓글을 달았습니다.");
            notificationService.save(notification);
        }
        return "redirect:/" + commentDTO.getBoardId();
    }

    @PostMapping("/recommend")
    public String recommend(@RequestParam("boardId") Long boardId, @RequestParam("type") String type, Authentication authentication) {
        String nickname = authentication.getName();
        UserDTO user = boardService.findUserByNickname(nickname);
        RecommendDTO recommendDTO = new RecommendDTO();
        recommendDTO.setBoardId(boardId);
        recommendDTO.setUserId(user.getId());
        recommendDTO.setType(type);
        recommendService.save(recommendDTO);
        return "redirect:/" + boardId;
    }

    // [NEW] 파일 다운로드
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        String filePath = "C:/Users/DUKE/Downloads/testsave/" + fileName;
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO, Model model) {
        try {
            boardService.saveUser(userDTO); // 사용자 정보를 저장하는 서비스 메서드 호출
            return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
            return "signup"; // 실패 시 회원가입 페이지로 다시 이동
        }
    }
}