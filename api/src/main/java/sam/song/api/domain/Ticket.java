package sam.song.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    public Ticket() {
    }

    public Ticket(final Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }
}
