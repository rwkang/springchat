package shop.onekorea.springchat.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import shop.onekorea.springchat.entity.ChatEntity;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<ChatEntity, String> { // JPA가 아니라서, 꼭 extends를 해 주어야 한다.

    @Tailable // Cursor.커서를 안 닫고 계속 유지하고 있다가, 또 추가되면, 계속 데이터를 흘려 보내준다. 단, 프로토콜을 'http'가 아니고, 'sse'를 사용해야 한다.
    @Query("{sender: ?0, receiver: ?1}") // MongoDB에서 검색하는 쿼리 구문
    Flux<ChatEntity> mFindBySender(String sender, String receiver);
    // 2023.07.23 Conclusion. Flux: 흐름,response를 유지하면서, 데이터를 계속 흘려 보내기
    // 예) naver.com에 로그인 되어 있는 상태에서, 아무것도 하지 않고 있는 상태에서,
    //    그 때 [신규 메일]을 받았다면, 메일 숫자가 자동으로 증가되게 하는 것.

}