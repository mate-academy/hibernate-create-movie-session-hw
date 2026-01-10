package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public MovieSession() {
    }

    public MovieSession(Movie movie) {
        this.movie = movie;
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall, LocalDateTime showTime) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.showTime = showTime;
    }

    public MovieSession(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public MovieSession(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public MovieSession(CinemaHall cinemaHall, LocalDateTime showTime) {
        this.cinemaHall = cinemaHall;
        this.showTime = showTime;
    }

    public MovieSession(Movie movie, LocalDateTime showTime) {
        this.movie = movie;
        this.showTime = showTime;
    }

    public Long getId() {
        return id;
    }

    public MovieSession setId(Long id) {
        this.id = id;
        return this;
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

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", movie=" + movie
                + ", cinemaHall=" + cinemaHall
                + ", showTime=" + showTime
                + '}';
    }
}
