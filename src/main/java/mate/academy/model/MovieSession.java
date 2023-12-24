package mate.academy.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movieSessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private CinemaHall cinemaHall;
    private LocalDate localDate;

    public MovieSession() {
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall, LocalDate localDate) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.localDate = localDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                +
                "id=" + id
                +
                ", movie=" + movie
                +
                ", cinemaHall=" + cinemaHall
                +
                ", dateTime=" + localDate
                +
                '}';
    }
}
