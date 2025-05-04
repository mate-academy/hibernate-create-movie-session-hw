package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capicacity;
    private String description;

    public CinemaHall(int capicacity, String description) {
        this.capicacity = capicacity;
        this.description = description;
    }

    public CinemaHall() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapicacity() {
        return capicacity;
    }

    public void setCapicacity(int capicacity) {
        this.capicacity = capicacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "id=" + id
                + ", capicacity=" + capicacity
                + ", description='" + description + '\'' + '}';
    }
}
