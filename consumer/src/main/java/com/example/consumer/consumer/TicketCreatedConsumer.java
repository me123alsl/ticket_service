package com.example.consumer.consumer;

import com.example.consumer.domain.FailedEvent;
import com.example.consumer.domain.Ticket;
import com.example.consumer.repository.FailedEventRepository;
import com.example.consumer.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class TicketCreatedConsumer {

    private final TicketRepository ticketRepository;
    private final FailedEventRepository failedEventRepository;
    private final Logger logger = LoggerFactory.getLogger(TicketCreatedConsumer.class);

    public TicketCreatedConsumer(final TicketRepository ticketRepository, final FailedEventRepository failedEventRepository) {
        this.ticketRepository = ticketRepository;
        this.failedEventRepository = failedEventRepository;
    }

    @KafkaListener(topics = "ticketing-create", groupId = "ticketing-consumer-1")
    public void listener(Long userId) {
        try {
            ticketRepository.save(new Ticket(userId));
        } catch (Exception e) {
            logger.error("failed to save ticket", e);
            failedEventRepository.save(new FailedEvent(userId));
        }
    }
}
