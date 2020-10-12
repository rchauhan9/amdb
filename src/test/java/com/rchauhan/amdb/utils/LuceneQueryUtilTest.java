package com.rchauhan.amdb.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LuceneQueryUtilTest {

    private LuceneQueryUtil luceneQueryUtil = new LuceneQueryUtil();

    @Test
    public void createFuzzyQueryTest() {
        String query = "example query";
        String fuzziedQuery = luceneQueryUtil.createFuzzyQuery(query);
        assertEquals("example query~", fuzziedQuery);
    }
}
