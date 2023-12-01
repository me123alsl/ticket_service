package sam.song.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sam.song.api.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
