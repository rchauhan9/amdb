package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.AwardDoesNotExistException;
import com.rchauhan.amdb.exceptions.AwardOrganisationDoesNotExistException;
import com.rchauhan.amdb.exceptions.AwardsRelationExistsException;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.AwardOrganisation;
import com.rchauhan.amdb.model.AwardsRelation;
import com.rchauhan.amdb.repositories.AwardsRelationRepository;
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
public class AwardsRelationServiceTest {

    @Mock
    AwardsRelationRepository awardsRelationRepository;

    @Mock
    AwardOrganisationService awardOrganisationService;

    @Mock
    AwardService awardService;

    @InjectMocks
    private AwardsRelationService awardsRelationService = new AwardsRelationService();

    private UUID awardOrganisationID = UUID.randomUUID();
    private UUID awardID = UUID.randomUUID();
    private String awardOrganisationName = "Academy Awards";
    private Integer awardOrganisationEstablishedYear = 1929;
    private String awardName = "Best Performance by an Actor in a Leading Role";

    @Test
    public void createAwardsRelationWhenAwardOrganisationDoesNotExistTest() {
        when(awardOrganisationService.getAwardOrganisationByName(awardOrganisationName)).thenReturn(Optional.empty());
        Exception exception = assertThrows(AwardOrganisationDoesNotExistException.class, () -> {
            awardsRelationService.createAwardsRelation(awardOrganisationName, awardName);
        });

        String expectedMessage = "Cannot create AWARDS relation between Award Organisation Academy Awards and " +
                "Award Best Performance by an Actor in a Leading Role. Award Organisation does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createAwardsRelationWhenAwardDoesNotExistTest() {
        when(awardOrganisationService.getAwardOrganisationByName(awardOrganisationName))
                .thenReturn(Optional.of(new AwardOrganisation(awardOrganisationName, awardOrganisationEstablishedYear)));
        when(awardService.getAwardByName(awardName))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(AwardDoesNotExistException.class, () -> {
            awardsRelationService.createAwardsRelation(awardOrganisationName, awardName);
        });

        String expectedMessage = "Cannot create AWARDS relation between Award Organisation Academy Awards and " +
                "Award Best Performance by an Actor in a Leading Role. Award does not exist.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createAwardsRelationExistsAlreadyTest() {
        AwardOrganisation awardOrganisation = new AwardOrganisation(awardOrganisationID);
        when(awardOrganisationService.getAwardOrganisationByName(awardOrganisationName))
                .thenReturn(Optional.of(awardOrganisation));
        Award award = new Award(awardID);
        when(awardService.getAwardByName(awardName)).thenReturn(Optional.of(award));
        when(awardsRelationRepository.getAwardsRelation(awardOrganisationID, awardID))
                .thenReturn(Optional.of(new AwardsRelation()));

        Exception exception = assertThrows(AwardsRelationExistsException.class, () -> {
            awardsRelationService.createAwardsRelation(awardOrganisationName, awardName);
        });

        String expectedMessage = "AWARDS relation between Award Organisation Academy Awards and Award Best " +
                "Performance by an Actor in a Leading Role exists already.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void createAwardsRelationTest() {
        AwardOrganisation awardOrganisation = new AwardOrganisation(awardOrganisationID);
        when(awardOrganisationService.getAwardOrganisationByName(awardOrganisationName))
                .thenReturn(Optional.of(awardOrganisation));
        Award award = new Award(awardID);
        when(awardService.getAwardByName(awardName)).thenReturn(Optional.of(award));
        when(awardsRelationRepository.getAwardsRelation(awardOrganisationID, awardID))
                .thenReturn(Optional.empty());
        AwardsRelation ar = new AwardsRelation(awardOrganisation, award);
        when(awardsRelationRepository.createAwardsRelation(awardOrganisationID, awardID)).thenReturn(ar);
        AwardsRelation awardsRelation = awardsRelationService.createAwardsRelation(awardOrganisationName, awardName);

        assertEquals(award, awardsRelation.getAward());
        assertEquals(awardOrganisation, awardsRelation.getAwardOrganisation());
    }

}