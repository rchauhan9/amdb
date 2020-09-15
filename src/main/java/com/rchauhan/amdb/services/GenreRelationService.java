package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.GenreDoesNotExistException;
import com.rchauhan.amdb.exceptions.GenreRelationExistsException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.model.GenreRelation;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.GenreRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreRelationService {

    @Autowired
    GenreRelationRepository genreRelationRepository;

    @Autowired
    GenreService genreService;

    @Autowired
    TitleService titleService;

    public List<GenreRelation> getTitlesByGenre(String name) {
        return genreRelationRepository.getTitlesByGenre(name);
    }

    public GenreRelation createGenreRelation(String titleName, Integer titleReleased, String genreName) {

        Optional<Title> title = titleService.getTitleByNameAndReleased(titleName, titleReleased);
        if (title.isEmpty()) {
            throw new TitleDoesNotExistException(String.format("Cannot create GENRE relation between title %s and genre %s. Title does not exist.", titleName, genreName));
        }

        Optional<Genre> genre = genreService.getGenreByName(genreName);
        if (genre.isEmpty()) {
            throw new GenreDoesNotExistException(String.format("Cannot create GENRE relation between title %s and genre %s. Genre does not exist.", titleName, genreName));
        }

        if (genreRelationExists(title.get().getId(), genre.get().getId())) {
            throw new GenreRelationExistsException(String.format("GENRE relation between title: %s and genre: %s already exists.", titleName, genreName));
        }

        return genreRelationRepository.createGenreRelation(title.get().getId(), genre.get().getId());
    }

    private boolean genreRelationExists(UUID titleID, UUID genreID) {
        return genreRelationRepository.getGenreRelation(titleID, genreID).isPresent();
    }
}
