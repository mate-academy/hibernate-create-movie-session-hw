package mate.academy.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movieSession")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime showTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private CinemaHall cinemaHall;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfSession() {
        return showTime;
    }

    public void setDateOfSession(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public Movie getMovieOfSession() {
        return movie;
    }

    public void setMovieOfSession(Movie movie) {
        this.movie = movie;
    }

    public CinemaHall getHallOfSession() {
        return cinemaHall;
    }

    public void setHallOfSession(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", dateOfSession=" + showTime
                + ", movieOfSession=" + movie
                + ", hallOfSession=" + cinemaHall
                + '}';
    }
}
