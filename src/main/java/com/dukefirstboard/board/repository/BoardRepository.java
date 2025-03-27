package com.dukefirstboard.board.repository; // 패키지 선언: 이 클래스가 속한 경로 (리포지토리 패키지)

import com.dukefirstboard.board.dto.BoardDTO; // 게시글 데이터를 담는 DTO 클래스 임포트
import lombok.RequiredArgsConstructor; // Lombok: final 필드에 대한 생성자를 자동 생성하기 위한 애너테이션 임포트
import org.apache.ibatis.session.SqlSession; // MyBatis의 SqlSession 인터페이스 임포트 (사용되지 않음, 불필요할 수 있음)
import org.mybatis.spring.SqlSessionTemplate; // Spring에서 MyBatis를 사용할 때 제공하는 SqlSession 구현체 임포트
import org.springframework.stereotype.Repository; // Spring의 리포지토리 컴포넌트임을 선언하는 애너테이션 임포트

import java.util.List; // List 컬렉션 사용을 위한 임포트

@Repository // 이 클래스가 Spring의 데이터 접근 계층(리포지토리) 컴포넌트임을 지정
@RequiredArgsConstructor // final 필드(sql)에 대한 생성자를 자동 생성
public class BoardRepository { // 게시글 데이터를 데이터베이스와 상호작용하는 리포지토리 클래스

    private final SqlSessionTemplate sql; // MyBatis의 SQL 실행을 위한 템플릿 객체 (Spring에서 의존성 주입)

    // 새로운 게시글을 데이터베이스에 저장하는 메서드
    public void save(BoardDTO boardDTO) { // BoardDTO 객체를 매개변수로 받아 저장
        sql.insert("Board.save", boardDTO); // MyBatis 매퍼의 "Board.save" 쿼리를 실행하여 데이터 삽입
    }

    // 모든 게시글을 데이터베이스에서 조회하여 리스트로 반환하는 메서드
    public List<BoardDTO> findAll() { // 반환 타입은 BoardDTO 객체의 리스트
        return sql.selectList("Board.findAll"); // MyBatis 매퍼의 "Board.findAll" 쿼리를 실행하여 전체 게시글 조회
    }

    // 특정 게시글의 조회수를 증가시키는 메서드
    public void updateHits(Long id) { // 게시글 ID를 매개변수로 받아 조회수 업데이트
        sql.update("Board.updateHits", id); // MyBatis 매퍼의 "Board.updateHits" 쿼리를 실행하여 조회수 증가
    }

    // 특정 게시글을 ID로 조회하여 반환하는 메서드
    public BoardDTO findById(Long id) { // 게시글 ID를 매개변수로 받아 단일 게시글 조회
        return sql.selectOne("Board.findById", id); // MyBatis 매퍼의 "Board.findById" 쿼리를 실행하여 결과 반환
    }

    // 기존 게시글을 수정하는 메서드
    public void update(BoardDTO boardDTO) { // 수정된 BoardDTO 객체를 매개변수로 받아 업데이트
        sql.update("Board.update", boardDTO); // MyBatis 매퍼의 "Board.update" 쿼리를 실행하여 데이터 수정
    }

    // 특정 게시글을 삭제하는 메서드
    public void delete(Long id) { // 게시글 ID를 매개변수로 받아 삭제
        sql.delete("Board.delete", id); // MyBatis 매퍼의 "Board.delete" 쿼리를 실행하여 데이터 삭제
    }
}