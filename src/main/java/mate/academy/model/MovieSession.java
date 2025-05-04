package mate.academy.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime showTime;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private CinemaHall cinemaHall;

    public MovieSession() {
    }

    public MovieSession(LocalDateTime showTime, Movie movie, CinemaHall cinemaHall) {
        this.showTime = showTime;
        this.movie = movie;
        this.cinemaHall = cinemaHall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
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

    @Override
    public String toString() {
        return "MovieSession{ id=" + id + ", showTime=" + showTime
                + ", movie=" + movie + ", cinemaHall=" + cinemaHall + '}';
    }
}
