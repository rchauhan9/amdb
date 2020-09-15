package com.rchauhan.amdb.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@GraphQLTest
public class QueryResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    ActedInRelationService actedInRelationService;

    @MockBean
    AwardService awardService;

    @MockBean
    AwardOrganisationService awardOrganisationService;

    @MockBean
    AwardsRelationService awardsRelationService;

    @MockBean
    DirectedRelationService directedRelationService;

    @MockBean
    GenreService genreService;

    @MockBean
    GenreRelationService genreRelationService;

    @MockBean
    NominatedRelationService nominatedRelationService;

    @MockBean
    PersonService personService;

    @MockBean
    ProducedRelationService producedRelationService;

    @MockBean
    TitleService titleService;

    @MockBean
    WonRelationService wonRelationService;

    @MockBean
    WroteRelationService wroteRelationService;

    private UUID mockUUID = UUID.fromString("d9436d98-4585-4661-9cf8-a4857332043a");

    /* AWARD VARS */
    private String awardLeadingRole = "Best Performance by an Actor in a Leading Role";
    private Award awardLead = new Award(awardLeadingRole);

    /* AWARD ORGANISATION VARS */
    private Integer yearEstablished = 1929;
    private String awardOrganisationName = "Academy Awards";
    private AwardOrganisation awardOrganisation = new AwardOrganisation(awardOrganisationName, yearEstablished);

    /* GENRE VARS */
    private String genreName = "Thriller";
    private Genre genre = new Genre(genreName);

    /* PERSON VARS */
    private String nameBale = "Christian Bale";
    private String dOBBale = "30-Jan-1974";
    private Person christianBale = Person.createPerson(nameBale, dOBBale);

    /* TITLE VARS  */
    private String titleName = "The Dark Knight";
    private String titleSummary = "Batman fights Joker.";
    private Integer titleReleased = 2008;
    private String titleCertRating = "12A";
    private Integer titleLength = 154;
    private String titleStoryline = "The second title in Nolan's epic Dark Knight trilogy.";
    private String titleTagline = "Why so serious?";
    private Title darkKnight = new Title(titleName, titleSummary, titleReleased, titleCertRating, titleLength, titleStoryline, titleTagline);

    @Test
    public void getAwardByIDTest() throws IOException {
        when(awardService.getAward(mockUUID)).thenReturn(Optional.of(awardLead));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAward.graphql");
        assert(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.award.name", String.class));
    }

    @Test
    public void getAwardByNameTest() throws IOException {
        when(awardService.getAwardByName(awardLeadingRole)).thenReturn(Optional.of(awardLead));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAwardByName.graphql");
        assert(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.awardByName.name", String.class));
    }

    @Test
    public void getAwardOrganisationByIDTest() throws IOException {
        when(awardOrganisationService.getAwardOrganisation(mockUUID)).thenReturn(Optional.of(awardOrganisation));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAwardOrganisation.graphql");
        assert(response.isOk());
        assertEquals(awardOrganisation.getName(), response.get("$.data.awardOrganisation.name", String.class));
        assertEquals(awardOrganisation.getYearEstablishedIn(), response.get("$.data.awardOrganisation.yearEstablishedIn", Integer.class));
    }

    @Test
    public void getAwardOrganisationByNameTest() throws IOException {
        when(awardOrganisationService.getAwardOrganisationByName(awardOrganisationName)).thenReturn(Optional.of(awardOrganisation));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAwardOrganisationByName.graphql");
        assert(response.isOk());
        assertEquals(awardOrganisation.getName(), response.get("$.data.awardOrganisationByName.name", String.class));
        assertEquals(awardOrganisation.getYearEstablishedIn(), response.get("$.data.awardOrganisationByName.yearEstablishedIn", Integer.class));
    }

    @Test
    public void getGenreByID() throws IOException {
        when(genreService.getGenre(mockUUID)).thenReturn(Optional.of(genre));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getGenre.graphql");
        assert(response.isOk());
        assertEquals(genre.getName(), response.get("$.data.genre.name", String.class));
    }

    @Test
    public void getGenreByName() throws IOException {
        when(genreService.getGenreByName(genreName)).thenReturn(Optional.of(genre));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getGenreByName.graphql");
        assert(response.isOk());
        assertEquals(genre.getName(), response.get("$.data.genreByName.name", String.class));
    }

    @Test
    public void getPersonByIDTest() throws IOException {
        when(personService.getPerson(mockUUID)).thenReturn(Optional.of(christianBale));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getPerson.graphql");
        assert(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.person.name", String.class));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.person.dateOfBirth", String.class));
    }

    @Test
    public void getPersonByNameAndDateOfBirthTest() throws IOException {
        when(personService.getPersonByNameAndDateOfBirth(nameBale, dOBBale)).thenReturn(Optional.of(christianBale));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getPersonByNameAndDateOfBirth.graphql");
        assert(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.personByNameAndDateOfBirth.name", String.class));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.personByNameAndDateOfBirth.dateOfBirth", String.class));
    }

    @Test
    public void getTitleByIDTest() throws IOException {
        when(titleService.getTitle(mockUUID)).thenReturn(Optional.of(darkKnight));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getTitle.graphql");
        assert(response.isOk());
        assertEquals(darkKnight.getName(), response.get("$.data.title.name", String.class));
        assertEquals(darkKnight.getSummary(), response.get("$.data.title.summary", String.class));
        assertEquals(darkKnight.getReleased(), response.get("$.data.title.released", Integer.class));
        assertEquals(darkKnight.getCertificateRating(), response.get("$.data.title.certificateRating", String.class));
        assertEquals(darkKnight.getTitleLengthInMins(), response.get("$.data.title.titleLengthInMins", Integer.class));
        assertEquals(darkKnight.getStoryline(), response.get("$.data.title.storyline", String.class));
        assertEquals(darkKnight.getTagline(), response.get("$.data.title.tagline", String.class));
    }

    @Test
    public void getTitleByNameAndReleasedTest() throws IOException {
        when(titleService.getTitleByNameAndReleased(titleName, titleReleased)).thenReturn(Optional.of(darkKnight));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getTitleByNameAndReleased.graphql");
        assert(response.isOk());
        assertEquals(darkKnight.getName(), response.get("$.data.titleByNameAndReleased.name", String.class));
        assertEquals(darkKnight.getSummary(), response.get("$.data.titleByNameAndReleased.summary", String.class));
        assertEquals(darkKnight.getReleased(), response.get("$.data.titleByNameAndReleased.released", Integer.class));
        assertEquals(darkKnight.getCertificateRating(), response.get("$.data.titleByNameAndReleased.certificateRating", String.class));
        assertEquals(darkKnight.getTitleLengthInMins(), response.get("$.data.titleByNameAndReleased.titleLengthInMins", Integer.class));
        assertEquals(darkKnight.getStoryline(), response.get("$.data.titleByNameAndReleased.storyline", String.class));
        assertEquals(darkKnight.getTagline(), response.get("$.data.titleByNameAndReleased.tagline", String.class));
    }

}
