package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.repositories.AwardRepository;
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
public class AwardServiceTest {

    @Mock
    AwardRepository awardRepository;

    @InjectMocks
    private AwardService awardService = new AwardService();

    private UUID awardID = UUID.randomUUID();
    private String awardName = "Best Performance by an Actor in a Leading Role";

    @Test
    public void getAwardByIDTest() {
        awardService.getAward(awardID);
        verify(awardRepository).findById(awardID);
    }

    @Test
    public void getAwardByNameTest() {
        awardService.getAwardByName(awardName);
        verify(awardRepository).findByName(awardName);
    }

    @Test
    public void createAwardWhenAwardExistsTest() {
        Award award = new Award(awardName);
        when(awardRepository.findByName(awardName)).thenReturn(Optional.of(award));

        Exception exception = assertThrows(AwardExistsException.class, () -> {
            awardService.createAward(awardName);
        });

        String expectedMessage = "Award with name Best Performance by an Actor in a Leading Role already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createAwardWhenDoesNotExist() {
        Award bestPerformance = new Award(awardName);
        when(awardRepository.findByName(awardName)).thenReturn(Optional.empty());
        when(awardRepository.save(bestPerformance)).thenReturn(bestPerformance);
        Award award = awardService.createAward(awardName);
        assertEquals(awardName, award.getName());
    }
}
