package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardExistsException;
import com.rchauhan.amdb.exceptions.AwardOrganisationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.AwardOrganisation;
import com.rchauhan.amdb.repositories.AwardOrganisationRepository;
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
public class AwardOrganisationServiceTest {

    @Mock
    AwardOrganisationRepository awardOrganisationRepository;

    @InjectMocks
    private AwardOrganisationService awardOrganisationService = new AwardOrganisationService();

    private UUID awardOrganisationID = UUID.randomUUID();
    private String awardOrganisationName = "Academy Awards";
    private Integer awardOrganisationYearEstablished = 1929;

    @Test
    public void getAwardOrganisationByIDTest() {
        awardOrganisationService.getAwardOrganisation(awardOrganisationID);
        verify(awardOrganisationRepository).findById(awardOrganisationID);
    }

    @Test
    public void getAwardOrganisationByNameTest() {
        awardOrganisationService.getAwardOrganisationByName(awardOrganisationName);
        verify(awardOrganisationRepository).findByName(awardOrganisationName);
    }

    @Test
    public void createAwardOrganisationWhenAwardOrganisationExistsTest() {
        AwardOrganisation awardOrganisation = new AwardOrganisation(awardOrganisationName, awardOrganisationYearEstablished);
        when(awardOrganisationRepository.findByName(awardOrganisationName)).thenReturn(Optional.of(awardOrganisation));

        Exception exception = assertThrows(AwardOrganisationExistsException.class, () -> {
            awardOrganisationService.createAwardOrganisation(awardOrganisationName, awardOrganisationYearEstablished);
        });

        String expectedMessage = "Award Organisation with name Academy Awards established in 1929 already exists.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createAwardOrganisationWhenDoesNotExist() {
        AwardOrganisation academyAwards = new AwardOrganisation(awardOrganisationName, awardOrganisationYearEstablished);
        when(awardOrganisationRepository.findByName(awardOrganisationName)).thenReturn(Optional.empty());
        when(awardOrganisationRepository.save(academyAwards)).thenReturn(academyAwards);
        AwardOrganisation awardOrganisation = awardOrganisationService.createAwardOrganisation(awardOrganisationName, awardOrganisationYearEstablished);
        assertEquals(awardOrganisationName, awardOrganisation.getName());
        assertEquals(awardOrganisationYearEstablished, awardOrganisation.getYearEstablishedIn());
    }

}
