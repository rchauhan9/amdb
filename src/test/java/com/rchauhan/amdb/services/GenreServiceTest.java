package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.GenreExistsException;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.repositories.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService = new GenreService();

    private UUID genreID = UUID.randomUUID();
    private String genreName = "Thriller";

    @Test
    public void getGenreByIDTest() {
        genreService.getGenre(genreID);
        verify(genreRepository).findById(genreID);
    }

    @Test
    public void getGenreByNameTest() {
        genreService.getGenreByName(genreName);
        verify(genreRepository).findByName(genreName);
    }

    @Test
    public void createGenreWhenGenreExistsTest() {
        Genre genre = new Genre(genreName);
        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre));

        Exception exception = assertThrows(GenreExistsException.class, () -> {
            genreService.createGenre(genreName);
        });

        String expectedMessage = "A genre with the name Thriller exists already";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createGenreWhenDoesNotExist() {
        Genre thriller = new Genre(genreName);
        when(genreRepository.findByName(genreName)).thenReturn(Optional.empty());
        when(genreRepository.save(thriller)).thenReturn(thriller);
        Genre genre = genreService.createGenre(genreName);
        assertEquals(genreName, genre.getName());
    }
}
