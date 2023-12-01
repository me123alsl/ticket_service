package sam.song.api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sam.song.api.repository.TicketRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void 티켓팅1회() {
        applyService.apply(1L);

        long count = ticketRepository.count();

        assertThat(count).isEqualTo(1);

    }

    
    @Test
    public void 티켓팅여러번() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 다른 쓰레드 작업을 기다리는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(5000);
        long count = ticketRepository.count();

        assertThat(count).isEqualTo(100);

    }

    @Test
    public void 티켓팅여러번_유저당1개() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 다른 쓰레드 작업을 기다리는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(5000);
        long count = ticketRepository.count();

        assertThat(count).isEqualTo(1);

    }
}