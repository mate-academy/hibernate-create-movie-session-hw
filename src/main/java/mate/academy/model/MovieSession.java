package mate.academy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movieSessions")
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemaHall_id")
    private CinemaHall cinemaHall;

    private LocalDateTime showTime;

    public MovieSession () {
    }

    public Long getId() {
        return id;
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

    public Movie getMovie() {
        return movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id=" + id +
                ", movie=" + movie +
                ", cinemaHall=" + cinemaHall +
                ", showTime=" + showTime +
                '}';
    }
}
