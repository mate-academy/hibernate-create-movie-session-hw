package mate.academy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cinemaHalls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public CinemaHall() {
    }

    public CinemaHall(String title, String description) {
        this.name = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                +
                "id=" + id
                +
                ", title='" + name + '\''
                +
                ", description='" + description + '\''
                +
                '}';
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

    public void setName(String title) {
        this.name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
