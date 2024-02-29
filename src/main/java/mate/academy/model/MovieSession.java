package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movie_sessions")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieSession extends AbstractEntity {

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public MovieSession(Movie movie, CinemaHall cinemaHall) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
    }
}
