package mate.academy.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;
    @Column(name = "time")
    private LocalDateTime dateTime;

    public MovieSession() {
    }

    public MovieSession(Movie movie, CinemaHall cinemaHall, LocalDateTime dateTime) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovieSession.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("movie=" + movie)
                .add("cinemaHall=" + cinemaHall)
                .add("dateTime=" + dateTime)
                .toString();
    }
}
