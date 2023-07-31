package shop.onekorea.springchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import shop.onekorea.springchat.entity.ChatEntity;
import shop.onekorea.springchat.repository.ChatRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor // 생성자
@RestController // 데이터 리턴 서버
public class ChatController {

    private final ChatRepository chatRepository;

    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // MeadiaType: org.springframework.http.MediaType
    public Flux<ChatEntity> getMessage(@PathVariable String sender, @PathVariable String receiver) {
        System.out.println("=====> ChatController.getMessage.receiver: " + receiver.toString());

        return chatRepository.mFindBySender(sender, receiver).subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @PostMapping("/chat")
    public Mono<ChatEntity> setMessage(@RequestBody ChatEntity chatEntity) { // JSON 형식으로 받는다, 'Mono'는 한번만 return 한다는 것, 'Flux'는 계속 흘려 보내는 return.
        chatEntity.setCreated(LocalDateTime.now()); // created 날짜만 자동으로 넣어준다
        System.out.println("=====> ChatController.setMessage.chatEntity: " + chatEntity.toString());

        return chatRepository.save(chatEntity);
    }
    // 위의 내용을 아래와 같이 return 없이 처리해도 된다. 다만 강제로 return 하면서 한번 보고 싶은 것이다.
//    @PostMapping("/chat")
//    public void setMessage(@RequestBody ChatEntity chatEntity) {
//        chatEntity.setCreated(LocalDateTime.now());
//    }

}
