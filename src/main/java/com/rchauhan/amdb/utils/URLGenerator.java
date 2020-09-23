package com.rchauhan.amdb.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class URLGenerator {

    private Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    public String createURLString() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[8];
        random.nextBytes(bytes);
        String url = encoder.encodeToString(bytes);
        return url;
    }
}
