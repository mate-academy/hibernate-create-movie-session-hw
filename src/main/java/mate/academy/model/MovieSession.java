package mate.academy.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinTable(name = "movie_sessions_movies",
            joinColumns = @JoinColumn(name = "movie_session_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Movie movie;
    @ManyToOne
    @JoinTable(name = "movie_sessions_cinema_halls",
            joinColumns = @JoinColumn(name = "movie_session_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_hall_id"))
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
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
