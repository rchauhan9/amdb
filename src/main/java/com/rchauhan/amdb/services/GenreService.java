package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.GenreExistsException;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Optional<Genre> getGenre(UUID id) {
        return genreRepository.findById(id);
    }

    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    public Genre createGenre(String name) {
        if (genreExists(name)) {
            throw new GenreExistsException(String.format("A genre with the name %s exists already", name));
        }
        return genreRepository.save(new Genre(name));
    }

    private boolean genreExists(String name) {
        return genreRepository.findByName(name).isPresent();
    }
}
