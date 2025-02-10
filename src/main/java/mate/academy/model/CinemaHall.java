package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private String description;

    public CinemaHall() {
    }

    @Override
    public String toString() {
        return "CinemaHall{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public CinemaHall setId(Long id) {
        this.id = id;
        return this;
    }

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
}
