package com.rchauhan.amdb.utils;

import org.springframework.stereotype.Component;

@Component
public class LuceneQueryUtil {

    public String createFuzzyQuery(String query) {
        return query + "~";
    }
}
