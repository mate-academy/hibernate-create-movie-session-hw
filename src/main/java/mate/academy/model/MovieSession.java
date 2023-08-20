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
@Table(name = "sessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateOfSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movieOfSession;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private CinemaHall hallOfSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfSession() {
        return dateOfSession;
    }

    public void setDateOfSession(LocalDateTime dateOfSession) {
        this.dateOfSession = dateOfSession;
    }

    public Movie getMovieOfSession() {
        return movieOfSession;
    }

    public void setMovieOfSession(Movie movieOfSession) {
        this.movieOfSession = movieOfSession;
    }

    public CinemaHall getHallOfSession() {
        return hallOfSession;
    }

    public void setHallOfSession(CinemaHall hallOfSession) {
        this.hallOfSession = hallOfSession;
    }

    @Override
    public String toString() {
        return "MovieSession{"
                + "id=" + id
                + ", dateOfSession=" + dateOfSession
                + ", movieOfSession=" + movieOfSession
                + ", hallOfSession=" + hallOfSession
                + '}';
    }
}
