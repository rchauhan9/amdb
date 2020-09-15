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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@GraphQLTest
public class MutationResolverTest {

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

    /* PERSON VARS */
    private String nameBale = "Christian Bale";
    private String dOBBale = "30-Jan-1974";
    private Person christianBale = Person.createPerson(nameBale, dOBBale);

    private String nameNolan = "Christopher Nolan";
    private String dOBNolan = "30-Jul-1970";
    private Person chrisNolan = Person.createPerson(nameNolan, dOBNolan);

    /* TITLE VARS  */
    private String titleName = "The Dark Knight";
    private String titleSummary = "Batman fights Joker.";
    private Integer titleReleased = 2008;
    private String titleCertRating = "12A";
    private Integer titleLength = 154;
    private String titleStoryline = "The second title in Nolan's epic Dark Knight trilogy.";
    private String titleTagline = "Why so serious?";
    private Title darkKnight = new Title(titleName, titleSummary, titleReleased, titleCertRating, titleLength, titleStoryline, titleTagline);

    /* ACTEDINRELATION VARS */
    private List<String> characters = Arrays.asList("Bruce Wayne", "Batman");
    private Integer billing = 0;
    private ActedInRelation actedInRelation = new ActedInRelation(characters, billing, christianBale, darkKnight);

    /* AWARD VARS */
    private String awardLeadingRole = "Best Performance by an Actor in a Leading Role";
    private Award awardLead = new Award(awardLeadingRole);

    private String awardSupportingRole = "Best Performance by an Actor in a Supporting Role";
    private Award awardSupp = new Award(awardSupportingRole);

    /* AWARD ORGANISATION VARS */
    private Integer yearEstablished = 1929;
    private String awardOrganisationName = "Academy Awards";
    private AwardOrganisation awardOrganisation = new AwardOrganisation(awardOrganisationName, yearEstablished);

    /* AWARDS RELATION VARS */
    private AwardsRelation awardsRelation = new AwardsRelation(awardOrganisation, awardLead);

    /* DIRECTED RELATION VARS */
    private DirectedRelation directedRelation = new DirectedRelation(chrisNolan, darkKnight);

    /* GENRE VARS */
    private String genreName = "Thriller";
    private Genre genre = new Genre(genreName);

    /* GENRE RELATION VARS */
    private GenreRelation genreRelation = new GenreRelation(darkKnight, genre);

    /* NOMINATED RELATION VARS */
    private String titleIDNom = "485b62e8-8ab2-42ed-b6d1-59582915b5b5"; // a made up titleID e.g. for the film Vice (2018).
    private Integer nominationYear = 2019;
    private NominatedRelation nominatedRelation = new NominatedRelation(nominationYear, titleIDNom, christianBale, awardLead);

    /* PRODUCED RELATION VARS */
    private ProducedRelation producedRelation = new ProducedRelation(chrisNolan, darkKnight);

    /* WON RELATION VARS */
    private String titleIDWon = "5516e648-ecb5-4b08-97e0-1615ce4e111a"; // a made up titleID e.g. for the film The Fighter (2011).
    private Integer wonYear = 2011;
    private WonRelation wonRelation = new WonRelation(wonYear, titleIDWon, christianBale, awardSupp);

    /* WROTE RELATION VARS */
    private List<String> items = Arrays.asList("Screenplay", "Story");
    private WroteRelation wroteRelation = new WroteRelation(items, chrisNolan, darkKnight);

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
        when(awardService.createAward(awardLeadingRole)).thenReturn(awardLead);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createAward.graphql");
        assertTrue(response.isOk());
        assertEquals(awardLead.getName(), response.get("$.data.createAward.name", String.class));
    }

    @Test
    public void createAwardOrganisationTest() throws IOException {
        when(awardOrganisationService.createAwardOrganisation(awardOrganisationName, yearEstablished)).thenReturn(awardOrganisation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createAwardOrganisation.graphql");
        assertTrue(response.isOk());
        assertEquals(awardOrganisation.getName(), response.get("$.data.createAwardOrganisation.name", String.class));
        assertEquals(awardOrganisation.getYearEstablishedIn(), response.get("$.data.createAwardOrganisation.yearEstablishedIn", Integer.class));
    }

    @Test
    public void createAwardsRelationTest() throws IOException {
        when(awardsRelationService.createAwardsRelation(awardOrganisationName, awardLeadingRole)).thenReturn(awardsRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createAwardsRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(awardsRelation.getAward().getName(), response.get("$.data.createAwardsRelation.award.name", String.class));
        assertEquals(awardsRelation.getAwardOrganisation().getName(), response.get("$.data.createAwardsRelation.awardOrganisation.name", String.class));
        assertEquals(awardsRelation.getAwardOrganisation().getYearEstablishedIn(),
                response.get("$.data.createAwardsRelation.awardOrganisation.yearEstablishedIn", Integer.class));
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
        when(nominatedRelationService.createNominatedRelation(nameBale, dOBBale, awardLeadingRole, titleIDNom, nominationYear))
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
        when(producedRelationService.createProducedRelation(nameNolan, dOBNolan, titleName, titleReleased))
                .thenReturn(producedRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createProducedRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(producedRelation.getPerson().getName(), response.get("$.data.createProducedRelation.person.name", String.class));
        assertEquals(producedRelation.getTitle().getName(), response.get("$.data.createProducedRelation.title.name", String.class));
        assertEquals(producedRelation.getTitle().getReleased(), response.get("$.data.createProducedRelation.title.released", Integer.class));
        assertEquals(producedRelation.getTitle().getTitleLengthInMins(), response.get("$.data.createProducedRelation.title.titleLengthInMins", Integer.class));
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
        when(wonRelationService.createWonRelation(nameBale, dOBBale, awardSupportingRole, titleIDWon, wonYear))
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
        when(wroteRelationService.createWroteRelation(nameNolan, dOBNolan, titleName, titleReleased, items))
                .thenReturn(wroteRelation);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/createWroteRelation.graphql");
        assertTrue(response.isOk());
        assertEquals(wroteRelation.getPerson().getName(), response.get("$.data.createWroteRelation.person.name", String.class));
        assertEquals(wroteRelation.getTitle().getName(), response.get("$.data.createWroteRelation.title.name", String.class));
        assertEquals(wroteRelation.getItems(), response.get("$.data.createWroteRelation.items", ArrayList.class));
    }

//    private Person createPerson(String name, String dOB) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//        Date dateOfBirth = new Date();
//        try {
//            dateOfBirth = sdf.parse(dOB);
//        } catch (ParseException e) {
//            System.out.println("Could not parse date of birth");
//        }
//        return new Person(name, dateOfBirth);
//    }

}
