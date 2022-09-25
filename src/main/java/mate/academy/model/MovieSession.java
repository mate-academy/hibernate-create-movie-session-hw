package mate.academy.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private long id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall hall;
    @Column(name = "show_time")
    private LocalDateTime showTime;

    public MovieSession() {

    }

    public MovieSession(Movie movie, CinemaHall hall, LocalDateTime showTime) {
        this.movie = movie;
        this.hall = hall;
        this.showTime = showTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public CinemaHall getCinemaHall() {
        return hall;
    }

    public void setCinemaHall(CinemaHall hall) {
        this.hall = hall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "MovieSession{" + "id=" + id
                + ", movie=" + movie
                + ", hall=" + hall
                + ", showTime=" + showTime + '}';
    }
}
