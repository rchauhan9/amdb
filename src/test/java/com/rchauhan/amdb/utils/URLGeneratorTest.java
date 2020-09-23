package com.rchauhan.amdb.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class URLGeneratorTest {

    private URLGenerator urlGenerator = new URLGenerator();

    @Test
    public void createURLStringTest() {
        HashSet<String> urls = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            urls.add(urlGenerator.createURLString());
        }
        assertEquals(100, urls.size());

        Iterator url = urls.iterator();
        while (url.hasNext()) {
            assertEquals(11, ((String) url.next()).length());
        }
    }
}
