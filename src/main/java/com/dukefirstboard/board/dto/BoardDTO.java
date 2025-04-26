package com.dukefirstboard.board.dto; // 패키지 선언: 이 클래스가 속한 경로 (DTO 패키지)

import lombok.Getter; // Lombok: getter 메서드를 자동 생성하기 위한 애너테이션 임포트
import lombok.Setter; // Lombok: setter 메서드를 자동 생성하기 위한 애너테이션 임포트
import lombok.ToString; // Lombok: toString 메서드를 자동 생성하기 위한 애너테이션 임포트
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter // 모든 필드에 대해 getter 메서드를 자동 생성
@Setter // 모든 필드에 대해 setter 메서드를 자동 생성
@ToString // 객체의 필드 값을 문자열로 반환하는 toString 메서드를 자동 생성
public class BoardDTO { // 게시글 데이터를 담는 데이터 전송 객체(DTO) 클래스
    private Long id; // 글번호: 게시글의 고유 식별자 (데이터베이스의 기본 키로 사용될 가능성 높음)

    private String boardWriter; // 작성자: 게시글을 작성한 사용자의 이름 또는 ID

    private String boardPass; // 게시글 비밀번호: 게시글 수정/삭제 시 인증용으로 사용되는 비밀번호

    private String boardTitle; // 글제목: 게시글의 제목

    private String boardContents; // 내용: 게시글의 본문 내용

    private int boardHits; // 조회수: 게시글이 조회된 횟수 (기본값은 보통 0)

    private String createdAt; // 작성시간: 게시글이 작성된 날짜와 시간 (문자열로 저장, 예: "2023-10-01 12:00")
    private int fileAttached;
    private List<MultipartFile> boardFile;
    private Long categoryId;
    private Long userId;
    private String spotifyTrackId;

    // [NEW] Spotify URL에서 트랙 ID 추출
    public void setSpotifyTrackUrl(String spotifyTrackUrl) {
        if (spotifyTrackUrl != null && spotifyTrackUrl.contains("track/")) {
            this.spotifyTrackId = spotifyTrackUrl.substring(spotifyTrackUrl.indexOf("track/") + 6);
            if (this.spotifyTrackId.contains("?")) {
                this.spotifyTrackId = this.spotifyTrackId.substring(0, this.spotifyTrackId.indexOf("?"));
            }
        }
    }
}