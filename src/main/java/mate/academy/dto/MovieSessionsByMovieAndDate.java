package mate.academy.dto;

import mate.academy.model.MovieSession;

public class MovieSessionsByMovieAndDate {
    private MovieSession movieSession;

    public MovieSessionsByMovieAndDate(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }
}
