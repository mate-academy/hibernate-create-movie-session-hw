package mate.academy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacity;
    private String descriprion;

    public CinemaHall() {
    }

    public CinemaHall(Integer capacity, String descriprion) {
        this.capacity = capacity;
        this.descriprion = descriprion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescriprion() {
        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    @Override
    public String toString() {
        return "CinemaHall{" + "id=" + id
                + ", capacity=" + capacity
                + ", descriprion='" + descriprion + '\'' + '}';
    }
}
