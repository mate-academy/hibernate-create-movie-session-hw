package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import mate.academy.lib.Inject;

@Entity
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Inject
    @OneToOne
    private Movie movie;
    @Inject
    @OneToOne
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;
}
