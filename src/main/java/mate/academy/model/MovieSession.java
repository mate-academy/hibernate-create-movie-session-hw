package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    @JoinTable(name = "movie_sessions_and_movie",
            joinColumns = @JoinColumn(name = "movie_sessions_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Movie movie;
    @ManyToOne
    @JoinTable(name = "movies_sessions_cinemahall",
            joinColumns = @JoinColumn(name = "movie_sessions_id"),
            inverseJoinColumns = @JoinColumn(name = "cinemahall_id"))
    private CinemaHall cinemaHall;
    private LocalDateTime showtime;

    public MovieSession() {
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall, LocalDateTime showtime) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.showtime = showtime;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", movie=" + movie
                + ", cinemaHall=" + cinemaHall
                + ", showtime=" + showtime + '}';
    }
}
