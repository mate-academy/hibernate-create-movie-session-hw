package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Movie;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie findingNemo = new Movie("Finding Nemo");
        findingNemo.setDescription("Sweet father-son tale has some very scary moments.");
        movieService.add(findingNemo);
        System.out.println(movieService.get(findingNemo.getId()));
        Movie despicableMe = new Movie("Despicable Me");
        despicableMe.setDescription("Clever, funny, and sweet villain-with-a-heart-of-gold tale.");
        movieService.add(despicableMe);
        movieService.getAll().forEach(System.out::println);


    }
}
