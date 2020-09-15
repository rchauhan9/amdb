package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.GenreDoesNotExistException;
import com.rchauhan.amdb.exceptions.GenreRelationExistsException;
import com.rchauhan.amdb.exceptions.TitleDoesNotExistException;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.model.GenreRelation;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.GenreRelationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreRelationServiceTest {

    @Mock
    GenreRelationRepository genreRelationRepository;

    @Mock
    TitleService titleService;

    @Mock
    GenreService genreService;

    @InjectMocks
    GenreRelationService genreRelationService;

    private UUID titleID = UUID.randomUUID();
    private UUID genreID = UUID.randomUUID();
    private String titleName = "The Dark Knight";
    private Integer titleReleased = 2008;
    private String genreName = "Thriller";

    @Test
    public void createGenreRelationWhenTitleDoesNotExistTest() {
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.empty());
        Exception exception = assertThrows(TitleDoesNotExistException.class, () -> {
            genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
        });

        String expectedMessage = "Cannot create GENRE relation between title The Dark Knight and genre Thriller. Title does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createGenreRelationWhenGenreDoesNotExistTest() {
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(new Title(titleID)));
        when(genreService.getGenreByName(genreName)).thenReturn(Optional.empty());
        Exception exception = assertThrows(GenreDoesNotExistException.class, () -> {
            genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
        });

        String expectedMessage = "Cannot create GENRE relation between title The Dark Knight and genre Thriller. Genre does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createGenreRelationWhenItExistsTest() {
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(new Title(titleID)));
        when(genreService.getGenreByName(genreName)).thenReturn(Optional.of(new Genre(genreID)));
        when(genreRelationRepository.getGenreRelation(titleID, genreID)).thenReturn(Optional.of(new GenreRelation()));
        Exception exception = assertThrows(GenreRelationExistsException.class, () -> {
            genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
        });

        String expectedMessage = "GENRE relation between title: The Dark Knight and genre: Thriller already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createGenreRelationTest() {
        Title title = new Title(titleID);
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(title));
        Genre genre = new Genre(genreID);
        when(genreService.getGenreByName(genreName)).thenReturn(Optional.of(genre));
        when(genreRelationRepository.getGenreRelation(titleID, genreID)).thenReturn(Optional.empty());
        GenreRelation genreRelation = new GenreRelation(title, genre);
        when(genreRelationRepository.createGenreRelation(titleID, genreID)).thenReturn(genreRelation);

        GenreRelation gr = genreRelationService.createGenreRelation(titleName, titleReleased, genreName);
        assertEquals(titleID, gr.getTitle().getId());
        assertEquals(genreID, gr.getGenre().getId());
    }
}
