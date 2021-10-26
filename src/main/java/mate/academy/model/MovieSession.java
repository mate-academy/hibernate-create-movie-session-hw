package mate.academy.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movieSessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }
}
