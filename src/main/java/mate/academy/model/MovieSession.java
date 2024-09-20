package mate.academy.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private CinemaHall cinemaHall;

    private LocalDateTime showTime;

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;

    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
