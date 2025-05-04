package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public MovieSession setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieSession setMovie(Movie movie) {
        this.movie = movie;
        return this;
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

    @Override
    public String toString() {
        return "MovieSession{"
                + "cinemaHall=" + cinemaHall
                + ", id=" + id
                + ", movie=" + movie
                + ", showTime=" + showTime
                + '}';
    }
}
