package com.rchauhan.amdb.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.rchauhan.amdb.model.*;
import com.rchauhan.amdb.services.*;
import com.rchauhan.amdb.utils.URLGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
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
    TitleService titleService;

    @MockBean
    WonRelationService wonRelationService;

    @MockBean
    WroteRelationService wroteRelationService;

    private String mockUrlID = "4bCd3F6h1Jk";

    /* PERSON VARS */
    private String nameBale = "Christian Bale";
    private String dOBBale = "30-Jan-1974";
    private Person christianBale = Person.createPerson(nameBale, dOBBale, mockUrlID);

    private String nameNolan = "Christopher Nolan";
    private String dOBNolan = "30-Jul-1970";
    private Person chrisNolan = Person.createPerson(nameNolan, dOBNolan, mockUrlID);

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
    private String titleIDNom = "485b62e8-8ab2-42ed-b6d1-59582915b5b5"; // a made up titleID e.g. for the film Vice (2018).
    private Integer nominationYear = 2019;
    private NominatedRelation nominatedRelation = new NominatedRelation(nominationYear, titleIDNom, christianBale, awardLead);

    /* PRODUCED RELATION VARS */
    private List<String> producedItems = Arrays.asList("executive producer");
    private ProducedRelation producedRelation = new ProducedRelation(chrisNolan, darkKnight, producedItems);

    /* WON RELATION VARS */
    private String titleIDWon = "5516e648-ecb5-4b08-97e0-1615ce4e111a"; // a made up titleID e.g. for the film The Fighter (2011).
    private Integer wonYear = 2011;
    private WonRelation wonRelation = new WonRelation(wonYear, titleIDWon, christianBale, awardSupp);

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
        when(directedRelationService.createDirectedRelation(nameNolan, dOBNolan, titleName, titleReleased))
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
        when(nominatedRelationService.createNominatedRelation(nameBale, dOBBale, awardLeadingRole, awardOrganisation, titleIDNom, nominationYear))
                .thenReturn(nominatedRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createNominatedRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(nominatedRelation.getPerson().getName(), response.get("$.data.createNominatedRelation.person.name", String.class));
        assertEquals(nominatedRelation.getAward().getName(), response.get("$.data.createNominatedRelation.award.name", String.class));
        assertEquals(nominatedRelation.getTitleID(), response.get("$.data.createNominatedRelation.titleID", String.class));
        assertEquals(nominatedRelation.getYear(), response.get("$.data.createNominatedRelation.year", Integer.class));
    }

    @Test
    public void createPersonTest() throws IOException {
        when(personService.createPerson(nameBale, christianBale.getDateOfBirth())).thenReturn(christianBale);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createPerson.graphql");
        assertTrue(response.isOk());
        assertEquals(christianBale.getName(), response.get("$.data.createPerson.name"));
        assertEquals(christianBale.getDateOfBirth().toString(), response.get("$.data.createPerson.dateOfBirth"));
    }

    @Test
    public void createProducedRelationTest() throws IOException {
        when(producedRelationService.createProducedRelation(nameNolan, dOBNolan, titleName, titleReleased, producedItems))
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
        when(wonRelationService.createWonRelation(nameBale, dOBBale, awardSupportingRole, awardOrganisation, titleIDWon, wonYear))
                .thenReturn(wonRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createWonRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(wonRelation.getPerson().getName(), response.get("$.data.createWonRelation.person.name", String.class));
        assertEquals(wonRelation.getAward().getName(), response.get("$.data.createWonRelation.award.name", String.class));
        assertEquals(wonRelation.getTitleID(), response.get("$.data.createWonRelation.titleID", String.class));
        assertEquals(wonRelation.getYear(), response.get("$.data.createWonRelation.year", Integer.class));
    }

    @Test
    public void createWroteRelationTest() throws IOException {
        when(wroteRelationService.createWroteRelation(nameNolan, dOBNolan, titleName, titleReleased, wroteItems))
                .thenReturn(wroteRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createWroteRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(wroteRelation.getPerson().getName(), response.get("$.data.createWroteRelation.person.name", String.class));
        assertEquals(wroteRelation.getTitle().getName(), response.get("$.data.createWroteRelation.title.name", String.class));
        assertEquals(wroteRelation.getItems(), response.get("$.data.createWroteRelation.items", ArrayList.class));
    }

}
