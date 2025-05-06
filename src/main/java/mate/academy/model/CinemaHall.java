package mate.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "cinemaHall")
    private Set<MovieSession> movieSessions;

    public CinemaHall() {
    }

    public CinemaHall(Long id, String name, int capacity, Set<MovieSession> movieSessions) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.movieSessions = movieSessions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<MovieSession> getMovieSessions() {
        return movieSessions;
    }

    public void setMovieSessions(Set<MovieSession> movieSessions) {
        this.movieSessions = movieSessions;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", capacity=" + capacity
                + ", movieSessions=" + (movieSessions != null ? movieSessions.stream()
                .map(ms -> "MovieSession[id=" + ms.getId() + "]")
                .collect(Collectors.toList()) : "null")
                + '}';
    }
}
