package com.dukefirstboard.board.service; // 패키지 선언: 이 클래스가 속한 경로 (서비스 패키지)

import com.dukefirstboard.board.dto.BoardDTO; // 게시글 데이터를 담는 DTO 클래스 임포트
import com.dukefirstboard.board.repository.BoardRepository; // 데이터베이스 작업을 수행하는 리포지토리 클래스 임포트
import lombok.RequiredArgsConstructor; // Lombok: final 필드에 대한 생성자를 자동 생성하기 위한 애너테이션 임포트
import org.springframework.stereotype.Service; // Spring의 서비스 컴포넌트임을 선언하는 애너테이션 임포트

import java.util.List; // List 컬렉션 사용을 위한 임포트

@Service // 이 클래스가 Spring의 서비스 계층 컴포넌트임을 지정
@RequiredArgsConstructor // final 필드(boardRepository)에 대한 생성자를 자동 생성
public class BoardService { // 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스
    private final BoardRepository boardRepository; // 데이터베이스 작업을 수행하는 리포지토리 객체 (의존성 주입)

    // 새로운 게시글을 저장하는 메서드
    public void save(BoardDTO boardDTO) { // BoardDTO 객체를 매개변수로 받아 저장 요청
        boardRepository.save(boardDTO); // 리포지토리 계층에 저장 작업 위임
    }

    // 모든 게시글을 조회하여 리스트로 반환하는 메서드
    public List<BoardDTO> findAll() { // 반환 타입은 BoardDTO 객체의 리스트
        return boardRepository.findAll(); // 리포지토리 계층에서 모든 게시글 조회 후 반환
    }

    // 특정 게시글의 조회수를 증가시키는 메서드
    public void updateHits(Long id) { // 게시글 ID를 매개변수로 받아 조회수 업데이트 요청
        boardRepository.updateHits(id); // 리포지토리 계층에 조회수 증가 작업 위임
    }

    // 특정 게시글을 ID로 조회하여 반환하는 메서드
    public BoardDTO findById(Long id) { // 게시글 ID를 매개변수로 받아 단일 게시글 조회
        return boardRepository.findById(id); // 리포지토리 계층에서 특정 게시글 조회 후 반환
    }

    // 기존 게시글을 수정하는 메서드
    public void update(BoardDTO boardDTO) { // 수정된 BoardDTO 객체를 매개변수로 받아 업데이트 요청
        boardRepository.update(boardDTO); // 리포지토리 계층에 수정 작업 위임
    }

    // 특정 게시글을 삭제하는 메서드
    public void delete(Long id) { // 게시글 ID를 매개변수로 받아 삭제 요청
        boardRepository.delete(id); // 리포지토리 계층에 삭제 작업 위임
    }
}