package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cinema_halls")
@Getter
@Setter
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;

    private String description;

    public CinemaHall() {
    }

    public CinemaHall(int capacity) {
        this.capacity = capacity;
    }
}
