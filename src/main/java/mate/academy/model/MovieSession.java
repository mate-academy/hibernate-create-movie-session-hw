package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_session")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id",
            referencedColumnName = "id")
    private Movie movies;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id",
            referencedColumnName = "id")
    private CinemaHall cinemaHall;

    private LocalDateTime sessionTime;

    public MovieSession() {
    }

    public MovieSession(Movie movies, CinemaHall cinemaHall, LocalDateTime sessionTime) {
        this.movies = movies;
        this.cinemaHall = cinemaHall;
        this.sessionTime = sessionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovies() {
        return movies;
    }

    public void setMovies(Movie movies) {
        this.movies = movies;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public LocalDateTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalDateTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id=" + id +
                ", movies=" + movies +
                ", cinemaHall=" + cinemaHall +
                ", sessionTime=" + sessionTime +
                '}';
    }
}
