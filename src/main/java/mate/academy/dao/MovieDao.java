package mate.academy.dao;

import mate.academy.model.Movie;

public interface MovieDao extends DataAccess<Movie>,
        GetAllItems<Movie> {
}
