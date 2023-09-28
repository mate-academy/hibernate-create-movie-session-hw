package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "movie_id")
    private Movie movies;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHalls;
    private LocalDateTime showTime;

    private MovieSession() {
    }

    public MovieSession(Movie movies, CinemaHall cinemaHalls, LocalDateTime showTime) {
        this.movies = movies;
        this.cinemaHalls = cinemaHalls;
        this.showTime = showTime;
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

    public CinemaHall getCinemaHalls() {
        return cinemaHalls;
    }

    public void setCinemaHalls(CinemaHall cinemaHalls) {
        this.cinemaHalls = cinemaHalls;
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
                + ", movies=" + movies
                + ", cinemaHalls=" + cinemaHalls
                + ", showTime=" + showTime
                + '}';
    }
}
