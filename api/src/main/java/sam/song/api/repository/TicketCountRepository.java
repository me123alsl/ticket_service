package sam.song.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TicketCountRepository {

    private final RedisTemplate<String ,String> redisTemplate;

    public TicketCountRepository(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long increase() {
        return redisTemplate
                .opsForValue()
                .increment("ticketCount");
    }
}
