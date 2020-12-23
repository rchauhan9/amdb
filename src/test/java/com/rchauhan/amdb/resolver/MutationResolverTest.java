package com.rchauhan.amdb.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import com.rchauhan.amdb.utils.DateUtil;
import com.rchauhan.amdb.utils.URLGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@GraphQLTest
public class MutationResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    URLGenerator urlGenerator;

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

    private String mockUrlID = "4bCd3F6h1Jk";

    /* PERSON VARS */
    private String nameBale = "Christian Bale";
    private Date dOBBale = DateUtil.createDate("dd-MMM-yyyy", "30-Jan-1974");
    private String bioBale = "Most famous for his role as Bruce Wayne in the Dark Knight trilogy...";
    private Person christianBale = new Person(nameBale, dOBBale, bioBale, mockUrlID);

    private String nameNolan = "Christopher Nolan";
    private Date dOBNolan = DateUtil.createDate("dd-MMM-yyyy", "30-Jul-1970");
    private String bioNolan = "One of the most acclaimed directors of our time, Nolan is responsible for...";
    private Person chrisNolan = new Person(nameNolan, dOBNolan, bioNolan, mockUrlID);

    /* TITLE VARS  */
    private String titleName = "The Dark Knight";
    private String titleSummary = "Batman fights Joker.";
    private Integer titleReleased = 2008;
    private String titleCertRating = "12A";
    private Integer titleLength = 154;
    private String titleStoryline = "The second title in Nolan's epic Dark Knight trilogy.";
    private String titleTagline = "Why so serious?";
    private Title darkKnight = new Title(titleName, titleSummary, titleReleased, titleCertRating, titleLength, titleStoryline, titleTagline, mockUrlID);

    /* ACTEDINRELATION VARS */
    private List<String> characters = Arrays.asList("Bruce Wayne", "Batman");
    private Integer billing = 0;
    private ActedInRelation actedInRelation = new ActedInRelation(characters, billing, christianBale, darkKnight);

    /* AWARD VARS */
    private String awardOrganisation = "Academy Awards";

    private String awardLeadingRole = "Best Performance by an Actor in a Leading Role";
    private Award awardLead = new Award(awardLeadingRole, awardOrganisation, mockUrlID);

    private String awardSupportingRole = "Best Performance by an Actor in a Supporting Role";
    private Award awardSupp = new Award(awardSupportingRole, awardOrganisation, mockUrlID);

    /* DIRECTED RELATION VARS */
    private DirectedRelation directedRelation = new DirectedRelation(chrisNolan, darkKnight);

    /* GENRE VARS */
    private String genreName = "Thriller";
    private Genre genre = new Genre(genreName, mockUrlID);

    /* GENRE RELATION VARS */
    private GenreRelation genreRelation = new GenreRelation(darkKnight, genre);

    /* NOMINATED RELATION VARS */
    private String titleNameNom = "Vice";
    private Integer titleReleasedNom = 2018;
    private Integer nominationYear = 2019;
    private NominatedRelation nominatedRelation = new NominatedRelation(christianBale, awardLead, nominationYear, titleNameNom, titleReleasedNom);

    /* PRODUCED RELATION VARS */
    private List<String> producedItems = Arrays.asList("executive producer");
    private ProducedRelation producedRelation = new ProducedRelation(chrisNolan, darkKnight, producedItems);

    /* WON RELATION VARS */
    private String titleNameWon = "The Fighter";
    private Integer titleReleasedWon = 2010;
    private Integer wonYear = 2011;
    private WonRelation wonRelation = new WonRelation(christianBale, awardSupp, wonYear, titleNameWon, titleReleasedWon);

    /* WROTE RELATION VARS */
    private List<String> wroteItems = Arrays.asList("Screenplay", "Story");
    private WroteRelation wroteRelation = new WroteRelation(chrisNolan, darkKnight, wroteItems);

    @Test
    public void createActedInRelationTest() throws IOException {
        when(actedInRelationService.createActedInRelation(nameBale, dOBBale, titleName, titleReleased, characters, billing))
                .thenReturn(actedInRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createActedInRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(actedInRelation.getPerson().getName(), response.get("$.data.createActedInRelation.person.name", String.class));
        assertEquals(actedInRelation.getTitle().getName(), response.get("$.data.createActedInRelation.title.name", String.class));
        assertEquals(actedInRelation.getCharacters(), response.get("$.data.createActedInRelation.characters", ArrayList.class));
        assertEquals(actedInRelation.getBilling(), response.get("$.data.createActedInRelation.billing", Integer.class));
    }

    @Test
    public void createAwardTest() throws IOException {
        when(awardService.createAward(awardLeadingRole, awardOrganisation)).thenReturn(awardLead);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createAward.graphql");
        assertTrue(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.createAward.name", String.class));
        assertEquals(awardLead.getOrganisation(), response.get("$.data.createAward.organisation", String.class));
    }

    @Test
    public void createDirectedRelationTest() throws IOException {
        when(directedRelationService.createDirectedRelation(eq(nameNolan), any(Date.class), eq(titleName), eq(titleReleased)))
                .thenReturn(directedRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createDirectedRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(directedRelation.getPerson().getName(), response.get("$.data.createDirectedRelation.person.name", String.class));
        assertEquals(directedRelation.getPerson().getDateOfBirth().toString(), response.get("$.data.createDirectedRelation.person.dateOfBirth", String.class));
        assertEquals(directedRelation.getTitle().getName(), response.get("$.data.createDirectedRelation.title.name", String.class));
        assertEquals(directedRelation.getTitle().getReleased(), response.get("$.data.createDirectedRelation.title.released", Integer.class));
        assertEquals(directedRelation.getTitle().getCertificateRating(), response.get("$.data.createDirectedRelation.title.certificateRating", String.class));
    }

    @Test
    public void createGenreTest() throws IOException {
        when(genreService.createGenre(genreName)).thenReturn(genre);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createGenre.graphql");
        assertTrue(response.isOk());
        assertEquals(genre.getName(), response.get("$.data.createGenre.name", String.class));
    }

    @Test
    public void createGenreRelationTest() throws IOException {
        when(genreRelationService.createGenreRelation(titleName, titleReleased, genreName)).thenReturn(genreRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createGenreRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(genreRelation.getTitle().getName(), response.get("$.data.createGenreRelation.title.name", String.class));
        assertEquals(genreRelation.getTitle().getReleased(), response.get("$.data.createGenreRelation.title.released", Integer.class));
        assertEquals(genreRelation.getTitle().getTitleLengthInMins(), response.get("$.data.createGenreRelation.title.titleLengthInMins", Integer.class));
        assertEquals(genreRelation.getGenre().getName(), response.get("$.data.createGenreRelation.genre.name", String.class));
    }

    @Test
    public void createNominatedRelationTest() throws IOException {
        when(nominatedRelationService.createNominatedRelation(nameBale, dOBBale, awardLeadingRole, awardOrganisation, nominationYear, titleNameNom, titleReleasedNom))
                .thenReturn(nominatedRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createNominatedRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(nominatedRelation.getPerson().getName(), response.get("$.data.createNominatedRelation.person.name", String.class));
        assertEquals(nominatedRelation.getAward().getName(), response.get("$.data.createNominatedRelation.award.name", String.class));
        assertEquals(nominatedRelation.getYear(), response.get("$.data.createNominatedRelation.year", Integer.class));
        assertEquals(nominatedRelation.getTitleName(), response.get("$.data.createNominatedRelation.titleName", String.class));
        assertEquals(nominatedRelation.getTitleReleased(), response.get("$.data.createNominatedRelation.titleReleased", Integer.class));
    }

    @Test
    public void createPersonTest() throws IOException {
        when(personService.createPerson(nameBale, christianBale.getDateOfBirth(), bioBale)).thenReturn(christianBale);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createPerson.graphql");
        assertTrue(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.createPerson.name"));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.createPerson.dateOfBirth"));
        assertEquals(christianBale.getBio(), response.get("$.data.createPerson.bio"));
    }

    @Test
    public void createProducedRelationTest() throws IOException {
        when(producedRelationService.createProducedRelation(eq(nameNolan), any(Date.class), eq(titleName), eq(titleReleased), eq(producedItems)))
                .thenReturn(producedRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createProducedRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(producedRelation.getPerson().getName(), response.get("$.data.createProducedRelation.person.name", String.class));
        assertEquals(producedRelation.getTitle().getName(), response.get("$.data.createProducedRelation.title.name", String.class));
        assertEquals(producedRelation.getTitle().getReleased(), response.get("$.data.createProducedRelation.title.released", Integer.class));
        assertEquals(producedRelation.getTitle().getTitleLengthInMins(), response.get("$.data.createProducedRelation.title.titleLengthInMins", Integer.class));
        assertEquals(producedRelation.getItems(), response.get("$.data.createProducedRelation.items", ArrayList.class));
    }

    @Test
    public void createTitleTest() throws IOException {
        when(titleService.createTitle(titleName, titleSummary, titleReleased, titleCertRating, titleLength, titleStoryline, titleTagline))
                .thenReturn(darkKnight);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createTitle.graphql");
        assertTrue(response.isOk());
        assertEquals(darkKnight.getName(), response.get("$.data.createTitle.name", String.class));
        assertEquals(darkKnight.getSummary(), response.get("$.data.createTitle.summary", String.class));
        assertEquals(darkKnight.getReleased(), response.get("$.data.createTitle.released", Integer.class));
        assertEquals(darkKnight.getCertificateRating(), response.get("$.data.createTitle.certificateRating", String.class));
        assertEquals(darkKnight.getTitleLengthInMins(), response.get("$.data.createTitle.titleLengthInMins", Integer.class));
        assertEquals(darkKnight.getStoryline(), response.get("$.data.createTitle.storyline", String.class));
        assertEquals(darkKnight.getTagline(), response.get("$.data.createTitle.tagline", String.class));
    }

    @Test
    public void createWonRelationTest() throws IOException {
        when(wonRelationService.createWonRelation(nameBale, dOBBale, awardSupportingRole, awardOrganisation, wonYear, titleNameWon, titleReleasedWon))
                .thenReturn(wonRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createWonRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(wonRelation.getPerson().getName(), response.get("$.data.createWonRelation.person.name", String.class));
        assertEquals(wonRelation.getAward().getName(), response.get("$.data.createWonRelation.award.name", String.class));
        assertEquals(wonRelation.getYear(), response.get("$.data.createWonRelation.year", Integer.class));
        assertEquals(wonRelation.getTitleName(), response.get("$.data.createWonRelation.titleName", String.class));
        assertEquals(wonRelation.getTitleReleased(), response.get("$.data.createWonRelation.titleReleased", Integer.class));
    }

    @Test
    public void createWroteRelationTest() throws IOException {
        when(wroteRelationService.createWroteRelation(eq(nameNolan), any(Date.class), eq(titleName), eq(titleReleased), eq(wroteItems)))
                .thenReturn(wroteRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createWroteRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(wroteRelation.getPerson().getName(), response.get("$.data.createWroteRelation.person.name", String.class));
        assertEquals(wroteRelation.getTitle().getName(), response.get("$.data.createWroteRelation.title.name", String.class));
        assertEquals(wroteRelation.getItems(), response.get("$.data.createWroteRelation.items", ArrayList.class));
    }

}
