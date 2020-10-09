package com.rchauhan.amdb.services;

import com.rchauhan.amdb.model.Searchable;
import com.rchauhan.amdb.repositories.SearchableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchableService {

    @Autowired
    SearchableRepository searchableRepository;

    public List<Searchable> getSearchableByName(String name) {
        return searchableRepository.findByName(name);
    }
}
