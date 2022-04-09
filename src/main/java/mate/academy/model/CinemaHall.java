package mate.academy.model;

public class CinemaHall {
    private Long id;
    private Integer capacity;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                '}';
    }
}
