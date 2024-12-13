package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinema_hall")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public CinemaHall setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CinemaHall setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CinemaHall setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "capacity=" + capacity
                + ", id=" + id
                + ", description='" + description + '\''
                + '}';
    }
}
