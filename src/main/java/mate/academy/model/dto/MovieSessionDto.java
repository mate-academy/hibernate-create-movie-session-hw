package mate.academy.model.dto;

import java.time.LocalDateTime;

public class MovieSessionDto {
    private String title;
    private String cinemaHallDescription;
    private LocalDateTime showTime;

    public MovieSessionDto(String title, String cinemaHallDescription, LocalDateTime showTime) {
        this.title = title;
        this.cinemaHallDescription = cinemaHallDescription;
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "MovieSessionDto{"
                + "title='" + title + '\''
                + ", cinemaHallDescription='" + cinemaHallDescription + '\''
                + ", showTime=" + showTime
                + '}';
    }
}
