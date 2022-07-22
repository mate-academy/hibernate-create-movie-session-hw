package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        Movie misterBean = new Movie("Mister Bean");
        misterBean.setDescription("A comedy film about funny guy.");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.add(misterBean);
        System.out.println(movieService.get(misterBean.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall big = new CinemaHall();
        big.setCapacity(100);
        big.setDescription("Big hall for hundred people");
        CinemaHall small = new CinemaHall();
        small.setCapacity(20);
        small.setDescription("Small hall for twenty people");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(big);
        System.out.println(cinemaHallService.get(big.getId()));
        cinemaHallService.add(small);
        System.out.println(cinemaHallService.get(small.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        //correct movie (F&F) and correct date (2022-07-22)
        MovieSession fastAndFuriousSessionBigHall = new MovieSession();
        fastAndFuriousSessionBigHall.setShowTime(LocalDateTime.of(2022, 7, 22, 18, 40));
        fastAndFuriousSessionBigHall.setMovie(fastAndFurious);
        fastAndFuriousSessionBigHall.setCinemaHall(big);
        //correct movie (F&F) and incorrect date (2022-08-22)
        MovieSession fastAndFuriousSessionSmallHall = new MovieSession();
        fastAndFuriousSessionSmallHall.setShowTime(LocalDateTime.of(2022, 8, 22, 16, 20));
        fastAndFuriousSessionSmallHall.setMovie(fastAndFurious);
        fastAndFuriousSessionSmallHall.setCinemaHall(small);
        //incorrect movie (Mr. Bean) and correct date (2022-07-22)
        MovieSession misterBeanSessionSmallHall = new MovieSession();
        misterBeanSessionSmallHall.setShowTime(LocalDateTime.of(2022, 7, 22, 12, 30));
        misterBeanSessionSmallHall.setMovie(misterBean);
        misterBeanSessionSmallHall.setCinemaHall(small);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSessionBigHall);
        System.out.println(movieSessionService.get(fastAndFuriousSessionBigHall.getId()));
        movieSessionService.add(fastAndFuriousSessionSmallHall);
        System.out.println(movieSessionService.get(fastAndFuriousSessionSmallHall.getId()));
        movieSessionService.add(misterBeanSessionSmallHall);
        System.out.println(movieSessionService.get(misterBeanSessionSmallHall.getId()));

        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2022, 7, 22));
        availableSessions.forEach(System.out::println);
    }
}
