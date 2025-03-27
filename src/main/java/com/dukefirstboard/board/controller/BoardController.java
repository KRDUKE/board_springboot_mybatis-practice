package com.dukefirstboard.board.controller; // 패키지 선언: 이 클래스가 속한 경로

import com.dukefirstboard.board.dto.BoardDTO; // 게시글 데이터를 담는 DTO 클래스 임포트
import com.dukefirstboard.board.service.BoardService; // 게시글 비즈니스 로직을 처리하는 서비스 클래스 임포트
import lombok.RequiredArgsConstructor; // Lombok: final 필드에 대한 생성자를 자동 생성
import org.springframework.stereotype.Controller; // Spring MVC 컨트롤러임을 선언
import org.springframework.ui.Model; // 뷰에 데이터를 전달하기 위한 Model 객체 임포트
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 매핑하기 위한 애너테이션
import org.springframework.web.bind.annotation.PathVariable; // URL 경로 변수 처리를 위한 애너테이션
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 매핑하기 위한 애너테이션

import java.util.List; // List 컬렉션 사용을 위한 임포트

@Controller // 이 클래스가 Spring MVC의 컨트롤러로 동작하도록 지정
@RequiredArgsConstructor // final 필드(boardService)에 대한 생성자를 자동 생성
public class BoardController {
    private final BoardService boardService; // 게시글 관련 비즈니스 로직을 처리하는 서비스 객체 (의존성 주입)

    // 게시글 작성 페이지로 이동하는 GET 요청 처리
    @GetMapping("/save")
    public String save() {
        return "save"; // "save"라는 이름의 뷰(HTML 템플릿)를 반환하여 작성 폼을 표시
    }

    // 게시글 작성 데이터를 받아 저장하고 목록 페이지로 리다이렉트하는 POST 요청 처리
    @PostMapping("/save")
    public String save(BoardDTO boardDTO) { // BoardDTO 객체로 폼 데이터를 자동 바인딩
        System.out.println("boardDTO = " + boardDTO); // 콘솔에 받은 데이터 출력 (디버깅용)
        boardService.save(boardDTO); // 서비스 계층을 통해 게시글 데이터를 저장
        return "redirect:/list"; // 저장 후 "/list"로 리다이렉트하여 게시글 목록 페이지로 이동
    }

    // 모든 게시글을 조회하여 목록 페이지로 전달하는 GET 요청 처리
    @GetMapping("/list")
    public String findAll(Model model) { // Model 객체로 뷰에 데이터 전달
        List<BoardDTO> boardDTOList = boardService.findAll(); // 서비스 계층에서 모든 게시글 목록 조회
        model.addAttribute("boardList", boardDTOList); // "boardList"라는 이름으로 뷰에 데이터 전달
        System.out.println("boardDTOList" + boardDTOList); // 콘솔에 조회된 목록 출력 (디버깅용)
        return "list"; // "list"라는 이름의 뷰(목록 페이지)를 반환
    }

    // 특정 게시글의 상세 내용을 조회하여 상세 페이지로 전달하는 GET 요청 처리
    @GetMapping("/{id}") // URL에서 {id}를 경로 변수로 받음
    public String findById(@PathVariable("id") Long id, Model model) { // 경로 변수 id와 Model 객체 주입
        // 조회수 처리
        boardService.updateHits(id); // 서비스 계층에서 해당 게시글의 조회수 증가

        // 상세 내용 가져오기
        BoardDTO boardDTO = boardService.findById(id); // 서비스 계층에서 특정 게시글 조회
        model.addAttribute("board", boardDTO); // "board"라는 이름으로 뷰에 데이터 전달
        System.out.println("boardDTO = " + boardDTO); // 콘솔에 조회된 게시글 출력 (디버깅용)
        return "detail"; // "detail"라는 이름의 뷰(상세 페이지)를 반환
    }

    // 게시글 수정 페이지로 이동하는 GET 요청 처리
    @GetMapping("/update/{id}") // URL에서 {id}를 경로 변수로 받음
    public String update(@PathVariable("id") Long id, Model model) { // 경로 변수 id와 Model 객체 주입
        BoardDTO boardDTO = boardService.findById(id); // 서비스 계층에서 수정할 게시글 조회
        model.addAttribute("board", boardDTO); // "board"라는 이름으로 뷰에 데이터 전달
        return "update"; // "update"라는 이름의 뷰(수정 폼 페이지)를 반환
    }

    // 수정된 게시글 데이터를 저장하고 상세 페이지로 이동하는 POST 요청 처리
    @PostMapping("/update/{id}") // URL에서 {id}를 경로 변수로 받음
    public String update(BoardDTO boardDTO, Model model) { // 수정된 BoardDTO와 Model 객체 주입
        boardService.update(boardDTO); // 서비스 계층에서 게시글 업데이트
        BoardDTO dto = boardService.findById(boardDTO.getId()); // 업데이트된 게시글 다시 조회
        model.addAttribute("board", dto); // "board"라는 이름으로 뷰에 데이터 전달
        return "detail"; // "detail" 뷰로 이동하여 수정된 상세 내용 표시
    }

    // 특정 게시글을 삭제하고 목록 페이지로 리다이렉트하는 GET 요청 처리
    @GetMapping("/delete/{id}") // URL에서 {id}를 경로 변수로 받음
    public String delete(@PathVariable("id") Long id) { // 경로 변수 id 주입
        boardService.delete(id); // 서비스 계층에서 해당 게시글 삭제
        return "redirect:/list"; // 삭제 후 "/list"로 리다이렉트하여 목록 페이지로 이동
    }
}