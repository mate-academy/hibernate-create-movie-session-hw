package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movie_session")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showTime;

    @ManyToOne
    private Movie movie;
    @ManyToMany
    @JoinTable(name = "movie_session_cinema_hall",
            joinColumns = @JoinColumn(name = "movie_session_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_hall_id"))
    private List<CinemaHall> cinemaHall;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<CinemaHall> getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(List<CinemaHall> cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", showTime=" + showTime
                + ", movie=" + movie
                + ", cinemaHall=" + cinemaHall + '}';
    }
}
