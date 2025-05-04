package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showTime;
    @OneToOne
    private Movie movie;
    @OneToOne
    private CinemaHall cinemaHall;

    public MovieSession() {
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", showTime=" + showTime
                + ", movie=" + movie
                + ", cinemaHall=" + cinemaHall
                + '}';
    }

    public Long getId() {
        return id;
    }

    public MovieSession setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public MovieSession setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieSession setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public MovieSession setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
        return this;
    }
}
