package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.GenreExistsException;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.repositories.GenreRepository;
import com.rchauhan.amdb.utils.URLGenerator;
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
    URLGenerator urlGenerator;

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService = new GenreService();

    private UUID id = UUID.randomUUID();
    private String name = "Thriller";
    private String urlID = "4bCd3F6h1Jk";


    @Test
    public void getGenreByIDTest() {
        genreService.getGenre(id);
        verify(genreRepository).findById(id);
    }

    @Test
    public void getGenreByUrlIDTest() {
        genreService.getGenreByUrlID(urlID);
        verify(genreRepository).findByUrlID(urlID);
    }

    @Test
    public void getGenreByNameTest() {
        genreService.getGenreByName(name);
        verify(genreRepository).findByName(name);
    }

    @Test
    public void createGenreWhenGenreExistsTest() {
        Genre genre = new Genre(name, urlID);
        when(genreRepository.findByName(name)).thenReturn(Optional.of(genre));

        Exception exception = assertThrows(GenreExistsException.class, () -> {
            genreService.createGenre(name);
        });

        String expectedMessage = "A genre with the name Thriller exists already";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createGenreWhenDoesNotExist() {
        Genre thriller = new Genre(name, urlID);
        when(genreRepository.findByName(name)).thenReturn(Optional.empty());
        when(urlGenerator.createURLString()).thenReturn(urlID);
        when(genreRepository.save(thriller)).thenReturn(thriller);
        Genre genre = genreService.createGenre(name);
        assertEquals(name, genre.getName());
        assertEquals(urlID, genre.getUrlID());
    }
}
