package sam.song.api.service;

import org.springframework.stereotype.Service;
import sam.song.api.domain.Ticket;
import sam.song.api.producer.TicketCreateProducer;
import sam.song.api.repository.AppliedUserRepository;
import sam.song.api.repository.TicketCountRepository;
import sam.song.api.repository.TicketRepository;

@Service
public class ApplyService {

    private final TicketRepository ticketRepository;
    private final TicketCountRepository ticketCountRepository;
    private final TicketCreateProducer ticketCreateProducer;

    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(final TicketRepository ticketRepository, TicketCountRepository ticketCountRepository,
                        TicketCreateProducer ticketCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketCountRepository = ticketCountRepository;
        this.ticketCreateProducer = ticketCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(final Long userId) {
        // 이미 신청한 유저인지 확인
        Long apply = appliedUserRepository.add(userId);

        if (apply != 1) {
            return;
        }

        long count = ticketCountRepository.increase();

        if (count > 100) {
            return;
        }
        ticketCreateProducer.create(userId);
    }

}
