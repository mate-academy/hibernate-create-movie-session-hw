package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieServiceImpl) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie knockinOnHeavensDoor = new Movie();
        knockinOnHeavensDoor.setTitle("Knockin' On Heaven's Door");
        knockinOnHeavensDoor.setDescription(
                "\"Knockin' on Heaven's Door\" is a comedy and drama two in one.");
        movieService.add(knockinOnHeavensDoor);

        Movie lordOfRingsFirstPart = new Movie();
        lordOfRingsFirstPart.setTitle("The Lord of the Rings: The Fellowship of the Ring");
        lordOfRingsFirstPart.setDescription("First part of the trilogy");
        movieService.add(lordOfRingsFirstPart);

        Movie lordOfRingsSecondPart = new Movie();
        lordOfRingsSecondPart.setTitle("The Lord of the Rings: The Two Towers");
        lordOfRingsSecondPart.setDescription("Second part of the trilogy");
        movieService.add(lordOfRingsSecondPart);

        Movie lordOfRingsThirdPart = new Movie();
        lordOfRingsThirdPart.setTitle("The Lord of the Rings: The Return of the King");
        lordOfRingsThirdPart.setDescription("Third part of the trilogy");
        movieService.add(lordOfRingsThirdPart);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallServiceImpl) injector
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setCapacity(80);
        cinemaHallRed.setDescription("The hall is of medium size. With red interior");
        cinemaHallService.add(cinemaHallRed);

        CinemaHall cinemaHallBlue = new CinemaHall();
        cinemaHallBlue.setCapacity(24);
        cinemaHallBlue.setDescription("The hall is of small size. With blue interior");
        cinemaHallService.add(cinemaHallBlue);

        CinemaHall cinemaHallBlack = new CinemaHall();
        cinemaHallBlack.setCapacity(300);
        cinemaHallBlack.setDescription("The hall is of huge size. With black interior");
        cinemaHallService.add(cinemaHallBlack);

        CinemaHall cinemaHallVip = new CinemaHall();
        cinemaHallVip.setCapacity(60);
        cinemaHallVip.setDescription("The hall is of medium size. With armchairs and sofas");
        cinemaHallService.add(cinemaHallVip);

        System.out.println(cinemaHallService.get(cinemaHallVip.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalTime time12 = LocalTime.of(12, 0, 0, 0);
        MovieSession movieSessionTomorrow12 = new MovieSession();
        movieSessionTomorrow12.setMovie(knockinOnHeavensDoor);
        movieSessionTomorrow12.setCinemaHall(cinemaHallRed);
        movieSessionTomorrow12.setShowTime(LocalDateTime.of(LocalDate.now().plusDays(1), time12));

        MovieSessionService movieSessionService = (MovieSessionServiceImpl) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(movieSessionTomorrow12);

        LocalTime time15 = LocalTime.of(15, 0, 0, 0);
        MovieSession movieSessionTomorrow15 = new MovieSession();
        movieSessionTomorrow15.setMovie(knockinOnHeavensDoor);
        movieSessionTomorrow15.setCinemaHall(cinemaHallRed);
        movieSessionTomorrow15.setShowTime(LocalDateTime.of(LocalDate.now().plusDays(1), time15));
        movieSessionService.add(movieSessionTomorrow15);

        LocalTime time18 = LocalTime.of(18, 0, 0, 0);
        MovieSession movieSessionTomorrow18 = new MovieSession();
        movieSessionTomorrow18.setMovie(lordOfRingsFirstPart);
        movieSessionTomorrow18.setCinemaHall(cinemaHallVip);
        movieSessionTomorrow18.setShowTime(LocalDateTime.of(LocalDate.now().plusDays(1), time18));
        movieSessionService.add(movieSessionTomorrow18);

        MovieSession movieSessionDayAfterTomorrow12 = new MovieSession();
        movieSessionDayAfterTomorrow12.setMovie(lordOfRingsFirstPart);
        movieSessionDayAfterTomorrow12.setCinemaHall(cinemaHallRed);
        movieSessionDayAfterTomorrow12.setShowTime(
                LocalDateTime.of(LocalDate.now().plusDays(2), time12));
        movieSessionService.add(movieSessionDayAfterTomorrow12);

        MovieSession movieSessionDayAfterTomorrow15 = new MovieSession();
        movieSessionDayAfterTomorrow15.setMovie(knockinOnHeavensDoor);
        movieSessionDayAfterTomorrow15.setCinemaHall(cinemaHallRed);
        movieSessionDayAfterTomorrow15.setShowTime(
                LocalDateTime.of(LocalDate.now().plusDays(2), time15));
        movieSessionService.add(movieSessionDayAfterTomorrow15);

        MovieSession movieSessionDayAfterTomorrow18 = new MovieSession();
        movieSessionDayAfterTomorrow18.setMovie(knockinOnHeavensDoor);
        movieSessionDayAfterTomorrow18.setCinemaHall(cinemaHallVip);
        movieSessionDayAfterTomorrow18.setShowTime(
                LocalDateTime.of(LocalDate.now().plusDays(2), time18));
        movieSessionService.add(movieSessionDayAfterTomorrow18);

        System.out.println(movieSessionService.get(movieSessionTomorrow15.getId()));
        System.out.println(movieSessionService.get(movieSessionDayAfterTomorrow18.getId()));
        System.out.println(
                movieSessionService.findAvailableSessions(
                    lordOfRingsFirstPart.getId(),
                    LocalDate.now().plusDays(1)
                    ));
    }
}
