package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.TitleExistsException;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.TitleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TitleServiceTest {

    @Mock
    TitleRepository titleRepository;

    @InjectMocks
    private TitleService titleService = new TitleService();

    private UUID id = UUID.randomUUID();
    private String name = "The Dark Knight";
    private String summary = "Batman fights Joker";
    private Integer released = 2008;
    private String certificateRating = "12A";
    private Integer titleLengthInMins = 154;
    private String storyline = "The second title in Nolan's epic Dark Knight trilogy";
    private String tagline = "Why so serious?";

    @Test
    public void getTitleByIDTest() {
        titleService.getTitle(id);
        verify(titleRepository).findById(id);
    }

    @Test
    public void getTitleByNameAndReleasedTest() {
        titleService.getTitleByNameAndReleased(name, released);
        verify(titleRepository).findByNameAndReleased(name, released);
    }

    @Test
    public void createTitleWhenTitleExistsTest() {
        when(titleRepository.findByNameAndReleased(name, released))
                .thenReturn(Optional.of(new Title(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline)));
        Exception exception = assertThrows(TitleExistsException.class, () -> {
            titleService.createTitle(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline);
        });

        String expectedMessage = "A title with name The Dark Knight and release year 2008 already exists.";
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    public void createTitleWhenDoesNotExistTest() {
        when(titleRepository.findByNameAndReleased(name, released))
                .thenReturn(Optional.empty());
        Title darkKnight = new Title(name, summary, released, certificateRating, titleLengthInMins,  storyline, tagline);
        when(titleRepository.save(darkKnight)).thenReturn(darkKnight);

        Title title = titleService.createTitle(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline);
        assertEquals(name, title.getName());
        assertEquals(summary, title.getSummary());
        assertEquals(released, title.getReleased());
        assertEquals(certificateRating, title.getCertificateRating());
        assertEquals(titleLengthInMins, title.getTitleLengthInMins());
        assertEquals(storyline, title.getStoryline());
        assertEquals(tagline, title.getTagline());
    }

    @Test
    public void titleDoesNotExistByNameAndReleased() {
        when(titleRepository.findByNameAndReleased(name, released)).thenReturn(Optional.empty());
        assertFalse(titleService.titleExists(name, released));
    }

    @Test
    public void titleExistsByNameAndReleased() {
        when(titleRepository.findByNameAndReleased(name, released))
                .thenReturn(Optional.of(new Title(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline)));
        assertTrue(titleService.titleExists(name, released));
    }

    @Test
    public void titleDoesNotExistByID() {
        when(titleRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(titleService.titleExists(id));
    }

    @Test
    public void titleExistsByID() {
        when(titleRepository.findById(id))
                .thenReturn(Optional.of(new Title(name, summary, released, certificateRating, titleLengthInMins, storyline, tagline)));
        assertTrue(titleService.titleExists(id));
    }

}
