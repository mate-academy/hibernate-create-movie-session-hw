package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie blackAdam = new Movie();
        blackAdam.setTitle("Black Adam");
        blackAdam.setDescription("Film about antihero in black suit");

        Movie piratesOfTheCaribbean = new Movie();
        piratesOfTheCaribbean.setTitle("Pirates of the Caribbean: dead men tell no tales");
        piratesOfTheCaribbean.setDescription("Johnny Depp as Jack sparrow");

        Movie casinoRoyale = new Movie();
        casinoRoyale.setTitle("007:Casino Royale");
        casinoRoyale.setDescription("First mission of James Bond");

        Movie bulletTrain = new Movie();
        bulletTrain.setTitle("Bullet Train");
        bulletTrain.setDescription(
                "At the head of the story is a professional assassin nicknamed \"Ladybug\"");

        Movie maverick = new Movie();
        maverick.setTitle("Top Gun: Maverick");
        maverick.setDescription(
                "Film about test pilot Captain Pete Mitchell nicknamed \"Maverick\"");

        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

        movieService.add(blackAdam);
        movieService.add(piratesOfTheCaribbean);
        movieService.add(casinoRoyale);
        movieService.add(bulletTrain);
        movieService.add(maverick);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(100);
        imaxHall.setDescription(
                "hall with 2 projectors which allow you to make a very high-quality picture");

        CinemaHall hallWith4D = new CinemaHall();
        hallWith4D.setCapacity(85);
        hallWith4D.setDescription(
                "hall where you can believe that you are in a movie as main hero");

        CinemaHall hallNo1 = new CinemaHall();
        hallNo1.setCapacity(120);
        hallNo1.setDescription(
                "hall where last places with more comfortable seats");

        CinemaHall hallNo2 = new CinemaHall();
        hallNo2.setCapacity(110);
        hallNo2.setDescription(
                "hall where last places with more comfortable seats");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        cinemaHallService.add(hallNo1);
        cinemaHallService.add(hallNo2);
        cinemaHallService.add(imaxHall);
        cinemaHallService.add(hallWith4D);

        MovieSession blackAdamSession = new MovieSession();
        blackAdamSession.setMovie(blackAdam);
        blackAdamSession.setCinemaHall(imaxHall);
        blackAdamSession.setLocalDateTime(LocalDateTime.of(
                2022,
                Month.NOVEMBER,
                12,
                15,
                00));

        MovieSession blackAdamSession2 = new MovieSession();
        blackAdamSession2.setMovie(blackAdam);
        blackAdamSession2.setCinemaHall(imaxHall);
        blackAdamSession2.setLocalDateTime(LocalDateTime.of(
                2022,
                Month.DECEMBER,
                3,
                15,
                00));

        MovieSession piratesOfTheCaribbeanSession = new MovieSession();
        piratesOfTheCaribbeanSession.setMovie(piratesOfTheCaribbean);
        piratesOfTheCaribbeanSession.setCinemaHall(imaxHall);
        piratesOfTheCaribbeanSession.setLocalDateTime(LocalDateTime.of(
                2022,
                Month.NOVEMBER,
                12,
                20,
                00));

        MovieSession piratesOfTheCaribbeanSession1 = new MovieSession();
        piratesOfTheCaribbeanSession1.setMovie(piratesOfTheCaribbean);
        piratesOfTheCaribbeanSession1.setCinemaHall(hallWith4D);
        piratesOfTheCaribbeanSession1.setLocalDateTime(LocalDateTime.of(
                2022,
                Month.NOVEMBER,
                3,
                10,
                00));

        MovieSession piratesOfTheCaribbeanSession2 = new MovieSession();
        piratesOfTheCaribbeanSession2.setMovie(piratesOfTheCaribbean);
        piratesOfTheCaribbeanSession2.setCinemaHall(hallNo1);
        piratesOfTheCaribbeanSession2.setLocalDateTime(LocalDateTime.of(
                2022,
                Month.NOVEMBER,
                9,
                10,
                00));

        MovieSession casinoRoyaleSession = new MovieSession();
        casinoRoyaleSession.setMovie(casinoRoyale);
        casinoRoyaleSession.setCinemaHall(hallNo1);
        casinoRoyaleSession.setLocalDateTime(LocalDateTime.of(
                2017,
                Month.JUNE,
                1,
                12,
                15));

        MovieSession casinoRoyaleSession1 = new MovieSession();
        casinoRoyaleSession1.setMovie(casinoRoyale);
        casinoRoyaleSession1.setCinemaHall(hallNo1);
        casinoRoyaleSession1.setLocalDateTime(LocalDateTime.of(
                2017,
                Month.JUNE,
                5,
                12,
                15));

        MovieSession casinoRoyaleSession2 = new MovieSession();
        casinoRoyaleSession2.setMovie(casinoRoyale);
        casinoRoyaleSession2.setCinemaHall(hallWith4D);
        casinoRoyaleSession2.setLocalDateTime(LocalDateTime.of(
                2017,
                Month.JUNE,
                5,
                17,
                15));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(blackAdamSession);
        movieSessionService.add(blackAdamSession2);
        movieSessionService.add(piratesOfTheCaribbeanSession);
        movieSessionService.add(piratesOfTheCaribbeanSession1);
        movieSessionService.add(piratesOfTheCaribbeanSession2);
        movieSessionService.add(casinoRoyaleSession);
        movieSessionService.add(casinoRoyaleSession1);
        movieSessionService.add(casinoRoyaleSession2);

        System.out.println("Cinema halls");
        System.out.println("\n" + "imax with laser hall -> "
                + cinemaHallService.get(imaxHall.getId()) + "\n");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("Movies");
        System.out.println("\n" + "Maverick -> " + movieService.get(maverick.getId()) + "\n");
        movieService.getAll().forEach(System.out::println);

        System.out.println("Movie sessions");
        LocalDate date = LocalDate.of(2017, Month.JUNE, 5);
        Long movieId = casinoRoyale.getId();
        movieSessionService.findAvailableSessions(movieId, date).forEach(System.out::println);
    }
}
