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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie avatar = new Movie("Avatar");
        avatar.setDescription("American science fiction film"
                + " written and directed by James Cameron.");
        Movie titanic = new Movie("Titanic");
        titanic.setDescription("1997 disaster feature film directed, written,"
                + " co-produced and co-edited by James Cameron.");
        Movie split = new Movie("Split");
        split.setDescription("an American thriller directed by"
                + " M. Night Shyamalan, producer and screenwriter.");
        Movie pianist = new Movie("Pianist");
        pianist.setDescription("a 2002 film directed by Roman Polyansky. Based on real events.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(avatar);
        movieService.add(titanic);
        movieService.add(split);
        movieService.add(pianist);
        System.out.println(movieService.getAll());

        CinemaHall hugeHall = new CinemaHall(120, "IMAX");
        CinemaHall largeHall = new CinemaHall(90, "CINETECH+");
        CinemaHall middleHall = new CinemaHall(60, "4DX");
        CinemaHall miniHall = new CinemaHall(20, "RE'LUX");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hugeHall);
        cinemaHallService.add(largeHall);
        cinemaHallService.add(middleHall);
        cinemaHallService.add(miniHall);
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSessionEvening = new MovieSession();
        movieSessionEvening.setMovie(fastAndFurious);
        movieSessionEvening.setCinemaHall(hugeHall);
        movieSessionEvening.setShowTime(LocalDateTime.of(2022, 7, 23, 14, 0, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionEvening);
        MovieSession movieSessionMorning = new MovieSession();
        movieSessionMorning.setMovie(avatar);
        movieSessionMorning.setCinemaHall(middleHall);
        movieSessionMorning.setShowTime(LocalDateTime.of(2021, 9, 25, 10, 0, 0));
        movieSessionService.add(movieSessionMorning);
        System.out.println(movieSessionService.findAvailableSessions(avatar.getId(),
                LocalDate.of(2021, 9, 25)));
    }
}
