package mate.academy.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movieSession")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Movie movie;
    @OneToOne
    private CinemaHall cinemaHall;
    private LocalDateTime localDateTime;

    public MovieSession() {
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall, LocalDateTime localDateTime) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id=" + id +
                ", movie=" + movie +
                ", cinemaHall=" + cinemaHall +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
