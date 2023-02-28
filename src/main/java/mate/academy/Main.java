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

public class Main {
        private static final Injector injector = Injector.getInstance("mate");
        private static MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        private static MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        private static CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        public static void main(String[] args) {
            Movie fastAndFurious = new Movie("Fast and Furious");
            fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

            movieService.add(fastAndFurious);
            System.out.println(movieService.get(fastAndFurious.getId()));
            movieService.getAll().forEach(System.out::println);

            CinemaHall largeHall = new CinemaHall();
            largeHall.setCapacity(250);
            largeHall.setDescription("The large hall for 250 visitors");

            CinemaHall smallHall = new CinemaHall();
            smallHall.setCapacity(50);
            smallHall.setDescription("The small hall for 50 visitors");

            cinemaHallService.add(largeHall);
            cinemaHallService.add(smallHall);
            System.out.println(cinemaHallService.get(largeHall.getId()));
            cinemaHallService.getAll().forEach(System.out::println);

            MovieSession fastAndFuriousSession = new MovieSession();
            fastAndFuriousSession.setCinemaHall(largeHall);
            fastAndFuriousSession.setMovie(fastAndFurious);

            MovieSession fastAndFuriousSecondSession = new MovieSession();
            fastAndFuriousSecondSession.setCinemaHall(smallHall);
            fastAndFuriousSecondSession.setMovie(fastAndFurious);

            MovieSession fastAndFuriousThirdSession = new MovieSession();
            fastAndFuriousThirdSession.setCinemaHall(largeHall);
            fastAndFuriousThirdSession.setMovie(fastAndFurious);

            LocalDate date = LocalDate.of(2016, 10, 13);
            LocalTime time = LocalTime.of(14, 15);
            fastAndFuriousSession.setShowTime(LocalDateTime.of(date, time));

            LocalDate secondDate = LocalDate.of(2016, 10, 13);
            LocalTime secondTime = LocalTime.of(15, 15);
            fastAndFuriousSecondSession.setShowTime(LocalDateTime.of(secondDate, secondTime));

            LocalDate thirdDate = LocalDate.of(2016, 10, 14);
            LocalTime thirdTime = LocalTime.of(12, 15);
            fastAndFuriousThirdSession.setShowTime(LocalDateTime.of(thirdDate, thirdTime));

            movieSessionService.add(fastAndFuriousSession);
            movieSessionService.add(fastAndFuriousSecondSession);
            movieSessionService.add(fastAndFuriousThirdSession);
            System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));

            movieSessionService.findAvailableSessions(fastAndFurious.getId(), date)
                    .forEach(System.out::println);

        }
    }
