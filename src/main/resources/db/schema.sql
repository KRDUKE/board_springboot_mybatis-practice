-- 사용자 테이블: 로그인 및 알림 수신용
CREATE TABLE user_table (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            email VARCHAR(100) UNIQUE NOT NULL,
                            password VARCHAR(100) NOT NULL,
                            nickname VARCHAR(50) NOT NULL,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 카테고리 테이블: 게시글 분류용
CREATE TABLE category_table (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(50) UNIQUE NOT NULL
);

-- 게시글 테이블: 기존 기능 + 카테고리, 사용자, Spotify 연동
CREATE TABLE board_table (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             board_title VARCHAR(255) NOT NULL,
                             board_writer VARCHAR(50) NOT NULL,
                             board_pass VARCHAR(100),
                             board_contents TEXT,
                             board_hits INT DEFAULT 0,
                             file_attached TINYINT DEFAULT 0,
                             category_id BIGINT, -- [NEW] 카테고리 참조
                             user_id BIGINT, -- [NEW] 작성자 참조
                             spotify_track_id VARCHAR(100), -- [NEW] Spotify 트랙 ID
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (category_id) REFERENCES category_table(id),
                             FOREIGN KEY (user_id) REFERENCES user_table(id)
);

-- 파일 테이블: 첨부 파일 저장
CREATE TABLE board_file_table (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  original_file_name VARCHAR(255),
                                  stored_file_name VARCHAR(255),
                                  board_id BIGINT,
                                  FOREIGN KEY (board_id) REFERENCES board_table(id) ON DELETE CASCADE
);

-- 추천 테이블: 게시글 추천/비추천
CREATE TABLE recommend_table (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 board_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 type ENUM('LIKE', 'DISLIKE') NOT NULL,
                                 created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 UNIQUE(board_id, user_id),
                                 FOREIGN KEY (board_id) REFERENCES board_table(id) ON DELETE CASCADE,
                                 FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE
);

-- 댓글 테이블: 댓글 및 대댓글
CREATE TABLE comment_table (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               board_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               parent_id BIGINT,
                               content TEXT NOT NULL,
                               nickname VARCHAR(50) NOT NULL,
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (board_id) REFERENCES board_table(id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE,
                               FOREIGN KEY (parent_id) REFERENCES comment_table(id) ON DELETE CASCADE
);

-- 알림 테이블: 사용자 알림
CREATE TABLE notification_table (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    board_id BIGINT,
                                    comment_id BIGINT,
                                    message VARCHAR(255) NOT NULL,
                                    is_read TINYINT DEFAULT 0,
                                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE,
                                    FOREIGN KEY (board_id) REFERENCES board_table(id) ON DELETE CASCADE,
                                    FOREIGN KEY (comment_id) REFERENCES comment_table(id) ON DELETE CASCADE
);

-- 초기 데이터: 카테고리
INSERT INTO category_table (name) VALUES ('자유'), ('유머'), ('게임');

-- 인덱스: 조회 성능 향상
CREATE INDEX idx_category_id ON board_table(category_id);
CREATE INDEX idx_board_id ON comment_table(board_id);
CREATE INDEX idx_user_id ON notification_table(user_id);