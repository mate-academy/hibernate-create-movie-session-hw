package mate.academy.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movieSessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CinemaHall cinemaHall;
    @ManyToOne
    private Movie movie;
    private LocalDateTime showTime;

    public MovieSession(CinemaHall cinemaHall, Movie movie, LocalDateTime showTime) {
        this.cinemaHall = cinemaHall;
        this.movie = movie;
        this.showTime = showTime;
    }

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
