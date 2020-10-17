package com.rchauhan.amdb.services;

import com.rchauhan.amdb.repositories.SearchableRepository;
import com.rchauhan.amdb.utils.LuceneQueryUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SearchableServiceTest {

    @Mock
    SearchableRepository searchableRepository;

    @Mock
    LuceneQueryUtil luceneQueryUtil;

    @InjectMocks
    private SearchableService searchableService = new SearchableService();

    @Test
    public void getSearchableByName() {
        when(luceneQueryUtil.createFuzzyQuery("John Malkovich")).thenReturn("John Malkovich~");
        searchableService.getSearchableByName("John Malkovich");
        verify(searchableRepository).findByName("John Malkovich~");
    }

}
