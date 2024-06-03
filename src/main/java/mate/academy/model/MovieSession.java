package mate.academy.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private Movie movie;
    @ManyToMany
    private List<CinemaHall> cinemaHallList;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<CinemaHall> getCinemaHallList() {
        return cinemaHallList;
    }

    public void setCinemaHallList(List<CinemaHall> cinemaHallList) {
        this.cinemaHallList = cinemaHallList;
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public void setLocalDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", movie=" + movie
                + ", cinemaHallList=" + cinemaHallList
                + ", date=" + date
                + '}';
    }
}
