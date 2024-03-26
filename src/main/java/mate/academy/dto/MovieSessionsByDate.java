package mate.academy.dto;

import mate.academy.model.MovieSession;

public class MovieSessionsByDate {
    private MovieSession movieSession;

    public MovieSessionsByDate(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }
}
