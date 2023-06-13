package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.entitiy.PushTb;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 클라이언트 연결
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(-1L);
        this.emitters.put(userId, emitter);

        emitter.onCompletion(() -> this.emitters.remove(userId));
        emitter.onTimeout(() -> this.emitters.remove(userId));

        return emitter;
    }
    // 알림 이벤트 전송
    public void sendEvent(PushTb pushTb) {
        SseEmitter emitter = this.emitters.get(pushTb.getUser().getId());

        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("알림").data(pushTb));
            } catch (IOException e) {
                this.emitters.remove(pushTb.getUser().getId());
            }
        }
    }
}
