--
 INSERT INTO user_tb (user_num, email, pwd, nickname, user_comment, pf_img, user_region, reg_date, is_push, is_membership, auth_key, is_active)
 VALUES (1, 'user1@naver.com', 'a1234!@#$1', '명란젓코난', '어디든 갈 준비가 되어있습니다.', '기본이미지1', 'SEOUL', '2023-06-01 12:00:00', 'PUSH', 'FREE', 'authKey1', 'ACTIVE');

 INSERT INTO user_tb (user_num, email, pwd, nickname, user_comment, pf_img, user_region, reg_date, is_push, is_membership, auth_key, is_active)
 VALUES (2, 'user2@kakao.com', 'a1234!@#$1', '꿔바로우많이두개더', '맛집 탐방 위주로 다니고 있습니다.', '기본이미지2', 'BUSAN', '2023-06-05 12:00:00', 'PUSH', 'FREE', 'authKey2', 'ACTIVE');

 INSERT INTO user_tb (user_num, email, pwd, nickname, user_comment, pf_img, user_region, reg_date, is_push, is_membership, auth_key, is_active)
 VALUES (3, 'user3@nate.com', 'a1234!@#$1', '이웃집또털어', '핫플 좋아해요.', '기본이미지3', 'SEOUL', '2023-06-07 12:00:00', 'PUSH', 'FREE', 'authKey3', 'ACTIVE');

-- POST_TB
-- 1번 게시글
INSERT INTO post_tb (post_num, title, region, course, theme, district, img_url, view_count, write_date, content, user_num)
VALUES (1, '서울의 명소: 경복궁', 'SEOUL', '당일 치기', '힐링 코스', '서울시 종로구', '이미지1', 100, '2023-06-02 00:00:00', '경복궁은 조선 시대 왕궁입니다. 임금의 거처였던 곳이며, 정부의 중심지였습니다.', 1);

INSERT INTO post_tb_comment (post_tb_post_num, comment)
VALUES (1, '대중교통이 편해요'), (1, '사진찍기 좋아요'), (1, '야경이 이뻐요');

INSERT INTO post_tb_place_tag (post_tb_post_num, place_tag)
VALUES (1, '경복궁'), (1, '창덕궁'), (1, '어라운드 시소');

INSERT INTO pin_tb (pin_num, latitude, longitude, route_num, post_num)
VALUES (1, 3.3434, 34.2323, 1, 1);

INSERT INTO push_tb (push_num, send_date, post_num_fk, user_num_fk)
VALUES (1, '2023-06-02 00:01:00', 1, 3);

INSERT INTO reply_tb (reply_num, content, write_date, post_num, user_num)
VALUES (1, '좋은 코스 공유 감사합니다.', '2023-06-03 00:00:00', 1, 3);

-- 2번 게시글
INSERT INTO post_tb (post_num, title, region, course, theme, district, view_count, write_date, content, user_num)
VALUES (2, '부산 해운대 바다 여행', 'BUSAN', '1박 2일', '맛집 탐방', '부산시 해운대구', 200, '2023-06-06 11:00:00', '코엑스는 한국 최대의 쇼핑과 엔터테인먼트 복합 시설입니다. 많은 가게와 레스토랑이 있습니다.', 2);

INSERT INTO post_tb_comment (post_tb_post_num, comment)
VALUES (2, '주차가 힘들어요'), (2, '사람이 많아요'), (2, '바가지 조심해요');

INSERT INTO post_tb_place_tag (post_tb_post_num, place_tag)
VALUES (2, '서면 회센터'), (2, '남천정'), (2, '쿠키인가곰');

-- ALTER TABLE post_tb_comment add column id int auto_increment primary key;
-- ALTER TABLE post_tb_place_tag add column id int auto_increment primary key;
-- ALTER TABLE post_tb_img_url add column id int auto_increment primary key;

-- FOLDER_TB
INSERT INTO folder_tb (folder_num, name, user_id)
VALUES (1, '북마크1', 2);
INSERT INTO folder_tb (folder_num, name, user_id)
VALUES (2, '북마크2', 3);

-- BOOKMARK_TB
INSERT INTO bookmark_tb (bookmark_num, folder_id, post_id)
VALUES (1, 1, 1);
INSERT INTO bookmark_tb (bookmark_num, folder_id, post_id)
VALUES (2, 2, 1);
INSERT INTO bookmark_tb (bookmark_num, folder_id, post_id)
VALUES (3, 2, 2);

-- AD_TB
INSERT INTO ad_tb (ad_num, img_url, name)
VALUES (1, '광고 이미지 1', '광고1');
INSERT INTO ad_tb (ad_num, img_url, name)
VALUES (2, '광고 이미지 2', '광고2');
INSERT INTO ad_tb (ad_num, img_url, name)
VALUES (3, '광고 이미지 3', '광고3');

-- CHATBOT_TB
INSERT INTO chatbot_tb (inquiry_num, inquiry_content, user_num, inquiry_date, inquiry_status, email)
VALUES (1, '문의합니다!! 안녕하세요 ~~~~ 이거 어떻게하는거죵???????? 너무어렵네요.. 관리자님.. 도와주세요 ㅜ-ㅜ', 1, '2023-06-06 11:00:00', '완료', 'user1@naver.com');

INSERT INTO chatbot_tb (inquiry_num, inquiry_content, user_num, inquiry_date, inquiry_status, email)
VALUES (2, '안녕하세요. ㅇㅇㅇㅇ에 대해 문의드립니다. 블라블라블라', 2, '2023-06-06 11:00:00', '완료', 'user4@naver.com');

INSERT INTO chatbot_tb (inquiry_num, inquiry_content, user_num, inquiry_date, inquiry_status, email)
VALUES (3, '질문합니다!! 이거 제가 이해한게 맞나요?!!!!', 3, '2023-06-06 12:00:00', '대기', 'user6@naver.com');

--INSERT INTO chatbot_tb (inquiry_num, inquiry_content, user_num, inquiry_date, inquiry_status, email)
--VALUES (4, '문의합니다!! 안녕하세요 ~~~~ 이렇게 하려면 어떻게 해야하죠? 관리자님.. 도와주세요 ㅜ-ㅜ', 4, '2023-06-06 12:30:00', '대기', 'user9@naver.com');
--
--INSERT INTO chatbot_tb (inquiry_num, inquiry_content, user_num, inquiry_date, inquiry_status, email)
--VALUES (5, '안녕하세요 ~ 문의드립니다~ ', 5, '2023-06-06 12:30:00', '대기', 'user11@naver.com');

-- BLOCK_TB
INSERT INTO block_tb (block_num,blocker_fk, blocked_fk)
VALUES (1,1,2);