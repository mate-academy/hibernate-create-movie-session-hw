package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "movies_session")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "cinemaHall_id")
    private CinemaHall cinemaHall;
    private LocalDate localDate;

    public MovieSession() {
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDateTime(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "MovieSession{"
            + "id=" + id
            + ", movie=" + movie
            + ", cinemaHall=" + cinemaHall
            + ", localDateTime=" + localDate
            + '}';
    }
}
