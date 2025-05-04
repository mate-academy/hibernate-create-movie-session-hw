package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie_sessions")
@Getter
@Setter
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;

    private LocalDateTime showTime;

    public MovieSession() {
    }

    public MovieSession(LocalDateTime showTime) {
        this.showTime = showTime;
    }
}
