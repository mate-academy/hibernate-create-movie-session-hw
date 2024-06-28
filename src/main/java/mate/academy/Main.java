package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall imax = new CinemaHall(120,"Imax experience");
        CinemaHall dbox = new CinemaHall(100, "D-Box experience");
        cinemaHallService.add(imax);
        cinemaHallService.add(dbox);
        System.out.println(cinemaHallService.get(imax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        LocalDateTime firstSession = LocalDateTime.of(2023, 6,26,10,20);
        LocalDateTime secondSession = LocalDateTime.of(2023,6,26,19,35);
        LocalDateTime thirdSession = LocalDateTime.of(2023,6,27,10,20);
        MovieSession firstImaxSession = new MovieSession(fastAndFurious,imax,firstSession);
        MovieSession firstDboxSession = new MovieSession(fastAndFurious, dbox, firstSession);
        MovieSession secondImaxSession = new MovieSession(fastAndFurious, imax, secondSession);
        MovieSession thirdDboxSession = new MovieSession(fastAndFurious,dbox,thirdSession);

        movieSessionService.add(firstImaxSession);
        movieSessionService.add(firstDboxSession);
        movieSessionService.add(secondImaxSession);
        movieSessionService.add(thirdDboxSession);

        System.out.println(movieSessionService.get(firstImaxSession.getId()));

        LocalDate currentDate = LocalDate.of(2023,6,26);
        int size = movieSessionService.findAvailableSession(fastAndFurious.getId(), currentDate)
                .size();
        System.out.println(size);
    }
}
