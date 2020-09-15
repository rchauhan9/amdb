package com.rchauhan.amdb.services;

import com.rchauhan.amdb.exceptions.TitleExistsException;
import com.rchauhan.amdb.model.Title;
import com.rchauhan.amdb.repositories.TitleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TitleService {

    @Autowired
    TitleRepository titleRepository;

    public Optional<Title> getTitle(UUID id) {
        return titleRepository.findById(id);
    }

    public Optional<Title> getTitleByNameAndReleased(String name, Integer released) {
        return titleRepository.findByNameAndReleased(name, released);
    }

    public Title createTitle(String name, String description, Integer released, String audienceRating, Integer filmLengthInMins, String storyline, String tagline) {
        if (titleExists(name, released)) {
            throw new TitleExistsException(String.format("A title with name %s and release year %d already exists.", name, released));
        }
        return titleRepository.save(new Title(name, description, released, audienceRating, filmLengthInMins, storyline, tagline));
    }

    public boolean titleExists(String titleName, Integer released) {
        return getTitleByNameAndReleased(titleName, released).isPresent();
    }

    public boolean titleExists(UUID titleID) {
        return getTitle(titleID).isPresent();
    }
}
