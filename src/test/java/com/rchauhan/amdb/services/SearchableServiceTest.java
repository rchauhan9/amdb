package com.rchauhan.amdb.services;

import com.rchauhan.amdb.model.Person;
import com.rchauhan.amdb.model.Searchable;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.SearchableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SearchableServiceTest {

    @Mock
    SearchableRepository searchableRepository;

    @InjectMocks
    private SearchableService searchableService = new SearchableService();



    @Test
    public void getSearchableByName() {
        searchableService.getSearchableByName("John Malkovich");
        verify(searchableRepository).findByName("John Malkovich");
    }

}
