package mate.academy;

import mate.academy.dao.MovieDao;
import mate.academy.dao.impl.MovieDaoImpl;
import mate.academy.model.Movie;
import mate.academy.service.MovieService;
import mate.academy.service.impl.MovieServiceImpl;

public class Main {
    public static void main(String[] args) {
        MovieDao movieDao = new MovieDaoImpl();
        MovieService movieService = new MovieServiceImpl(movieDao);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
