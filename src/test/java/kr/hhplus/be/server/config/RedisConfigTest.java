package kr.hhplus.be.server.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("redis 연동 테스트")
    void testRedisConnection() {
        // Given
        String key = "testKey";
        String value = "Hello, Redis!";

        // When
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value);
        Object result = valueOps.get(key);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(value);
    }
}