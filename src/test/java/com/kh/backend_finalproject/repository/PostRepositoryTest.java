package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("게시글 저장 테스트")
    public void createPostTest() {
        for (int i = 1; i <= 10; i++) {
            PostTb post = new PostTb();
            post.setTitle("분위기 좋은 재즈바에서 즐기는 와인 데이트" + i);
            post.setRegion(RegionStatus.SEOUL);
            post.setCourse("당일 치기");
            post.setTheme("힐링 코스");
            post.setDistrict("서울시 마포구");
            post.setComment(Arrays.asList("예약 필수", "대중교통이 편해요", "안주 맛있어요"));
            post.setPlaceTag(Arrays.asList("도성 외곽길", "세컨드플로어", "와인 전문점"));
            post.setImgUrl(Arrays.asList("dfjsfljsidlfj"));
            post.setViewCount(243);
            post.setWriteDate(LocalDateTime.now());
            post.setContent("서울 마포구에 위치한 재즈바에서 편안하게 와인을 즐기는 방법을 알려드릴게요." +
                    "시작은 도성 외곽길에서 편안한 산책으로 시작해요. 도심 속에서도 힐링을 느낄 수 있는 곳이죠. " +
                    "여기에서는 크고 아름다운 나무들과 꽃들을 즐길 수 있고, 계절에 따라 다양한 풍경을 즐길 수 있어요. " +
                    "이후엔 두 번째 장소인 세컨드플로어로 이동해볼게요. 분위기 있는 음악과 맛있는 음식을 즐길 수 있는 곳이죠. " +
                    "특히 와인 선택이 다양해서 취향에 맞는 와인을 선택하실 수 있어요. 맛있는 안주와 함께 늦은 밤까지 편악하게 즐기면 좋아요. " +
                    "이 외에도 마포구에는 많은 와인 전문점들이 있으니, 시간이 되시면 여러 곳을 방문해보시는 것도 추천드려요. ");
            postRepository.save(post);
        }
    }
}