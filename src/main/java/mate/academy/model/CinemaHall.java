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

    public CinemaHall(int capacity, String description) {
        this.capacity = capacity;
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "id=" + id
                + ", capacity=" + capacity
                + ", description='" + description + '\''
                + '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
