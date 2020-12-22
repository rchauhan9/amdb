package com.rchauhan.amdb.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.rchauhan.amdb.model.Award;
import com.rchauhan.amdb.model.Genre;
import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.services.*;
import com.rchauhan.amdb.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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
    SearchableService searchableService;

    @MockBean
    TitleService titleService;

    @MockBean
    WonRelationService wonRelationService;

    @MockBean
    WroteRelationService wroteRelationService;

    private UUID mockUUID = UUID.fromString("d9436d98-4585-4661-9cf8-a4857332043a");
    private String mockUrlID = "4bCd3F6h1Jk";

    /* AWARD VARS */
    private String awardLeadingRole = "Best Performance by an Actor in a Leading Role";
    private String awardOrganisation = "Academy Awards";
    private Award awardLead = new Award(awardLeadingRole, awardOrganisation, mockUrlID);

    /* GENRE VARS */
    private String genreName = "Thriller";
    private Genre genre = new Genre(genreName, mockUrlID);

    /* PERSON VARS */
    private String nameBale = "Christian Bale";
    private Date dOBBale = DateUtil.createDate("dd-MMM-yyyy", "30-Jan-1974");
    private String bioBale = "Most famous for his role as Bruce Wayne in the Dark Knight trilogy...";
    private Person christianBale = new Person(nameBale, dOBBale, bioBale, mockUrlID);

    /* TITLE VARS  */
    private String titleName = "The Dark Knight";
    private String titleSummary = "Batman fights Joker.";
    private Integer titleReleased = 2008;
    private String titleCertRating = "12A";
    private Integer titleLength = 154;
    private String titleStoryline = "The second title in Nolan's epic Dark Knight trilogy.";
    private String titleTagline = "Why so serious?";
    private Title darkKnight = new Title(titleName, titleSummary, titleReleased, titleCertRating, titleLength, titleStoryline, titleTagline, mockUrlID);

    /* SEARCHABLE VARS */
    private String bjmName = "Being John Malkovich";
    private String bjmSummary = "A puppeteer discovers a portal that leads literally into the head of movie star John Malkovich";
    private Integer bjmReleased = 1999;
    private String bjmCertificateRating = "15";
    private Integer bjmTitleLengthInMins = 113;
    private String bjmStoryline = "Puppeteer Craig Schwartz finds a portal the leads into the head of John Malkovich";
    private String bjmTagline = "Be All That Someone Else Can Be";
    private String bjmUrlID = "6SNW-eo67E8";

    private Title beingJM = new Title(bjmName, bjmSummary, bjmReleased, bjmCertificateRating, bjmTitleLengthInMins, bjmStoryline, bjmTagline, bjmUrlID);
    private Person johnMalkovich = new Person("John Malkovich", DateUtil.createDate("dd-MMM-yyyy", "09-Sep-1953"), "Famous for playing himself in the title role...", "1a2B3c4D5e-");

    @Test
    public void getAwardByIDTest() throws IOException {
        when(awardService.getAward(mockUUID)).thenReturn(Optional.of(awardLead));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAward.graphql");
        assert(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.award.name", String.class));
    }

    @Test
    public void getAwardByNameAndOrganisationTest() throws IOException {
        when(awardService.getAwardByNameAndOrganisation(awardLeadingRole, awardOrganisation)).thenReturn(Optional.of(awardLead));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getAwardByNameAndOrganisation.graphql");
        assert(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.awardByNameAndOrganisation.name", String.class));
        assertEquals(awardLead.getOrganisation(), response.get("$.data.awardByNameAndOrganisation.organisation", String.class));
    }

    @Test
    public void getGenreByID() throws IOException {
        when(genreService.getGenre(mockUUID)).thenReturn(Optional.of(genre));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getGenre.graphql");
        assert(response.isOk());
        assertEquals(genre.getName(), response.get("$.data.genre.name", String.class));
    }

    @Test
    public void getGenreByUrlID() throws IOException {
        when(genreService.getGenreByUrlID(mockUrlID)).thenReturn(Optional.of(genre));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getGenreByUrlID.graphql");
        assert(response.isOk());
        assertEquals(genre.getName(), response.get("$.data.genreByUrlID.name", String.class));
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
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.person.dateOfBirth", String.class));
        assertEquals(christianBale.getBio(), response.get("$.data.person.bio", String.class));
    }

    @Test
    public void getPersonByUrlIDTest() throws IOException {
        when(personService.getPersonByUrlID(mockUrlID)).thenReturn(Optional.of(christianBale));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getPersonByUrlID.graphql");
        assert(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.personByUrlID.name", String.class));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.personByUrlID.dateOfBirth", String.class));
        assertEquals(christianBale.getBio(), response.get("$.data.personByUrlID.bio", String.class));
    }

    @Test
    public void getPersonByNameAndDateOfBirthTest() throws IOException {
        when(personService.getPersonByNameAndDateOfBirth(nameBale, dOBBale)).thenReturn(Optional.of(christianBale));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getPersonByNameAndDateOfBirth.graphql");
        assert(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.personByNameAndDateOfBirth.name", String.class));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.personByNameAndDateOfBirth.dateOfBirth", String.class));
        assertEquals(christianBale.getBio(), response.get("$.data.personByNameAndDateOfBirth.bio", String.class));
    }

    @Test
    public void getSearchableByName() throws IOException {
        when(searchableService.getSearchableByName("John Malkovich~")).thenReturn(Arrays.asList(beingJM, johnMalkovich));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getSearchableByName.graphql");
        assert(response.isOk());
        assertEquals(beingJM.getName(), response.get("$.data.searchableByName[0].name", String.class));
        assertEquals(beingJM.getReleased(), response.get("$.data.searchableByName[0].released", Integer.class));
        assertEquals(johnMalkovich.getName(), response.get("$.data.searchableByName[1].name", String.class));
        assertEquals(johnMalkovich.getDateOfBirth().toString(), response.get("$.data.searchableByName[1].dateOfBirth", String.class));
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
    public void getTitleByUrlID() throws IOException {
        when(titleService.getTitleByUrlID(mockUrlID)).thenReturn(Optional.of(darkKnight));
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/getTitleByUrlID.graphql");
        assert(response.isOk());
        assertEquals(darkKnight.getName(), response.get("$.data.titleByUrlID.name", String.class));
        assertEquals(darkKnight.getSummary(), response.get("$.data.titleByUrlID.summary", String.class));
        assertEquals(darkKnight.getReleased(), response.get("$.data.titleByUrlID.released", Integer.class));
        assertEquals(darkKnight.getCertificateRating(), response.get("$.data.titleByUrlID.certificateRating", String.class));
        assertEquals(darkKnight.getTitleLengthInMins(), response.get("$.data.titleByUrlID.titleLengthInMins", Integer.class));
        assertEquals(darkKnight.getStoryline(), response.get("$.data.titleByUrlID.storyline", String.class));
        assertEquals(darkKnight.getTagline(), response.get("$.data.titleByUrlID.tagline", String.class));
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
