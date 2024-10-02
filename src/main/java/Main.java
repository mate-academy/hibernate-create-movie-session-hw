import mate.academy.dao.MovieSessionDao;
import mate.academy.dao.impl.MovieSessionDaoImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        MovieSessionDao movieSessionDao = new MovieSessionDaoImpl();
        movieSessionDao.findAvailableSessions(2L, LocalDate.now().plusDays(1)).forEach(System.out::println);
    }
}
