package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinemaHalls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfHall;
    @OneToOne
    @JoinTable(
            name = "cinemaHall_movie",
            joinColumns = @JoinColumn(name = "cinemaHall_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Movie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfHall() {
        return numberOfHall;
    }

    public void setNumberOfHall(int numberOfHall) {
        this.numberOfHall = numberOfHall;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovies(Movie movie) {
        this.movie = movie;
    }
}
