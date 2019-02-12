package org.openttt;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Strings {
    private Strings() {

    }

    public static String get(String resourcePath) {
        try {
            InputStream stream = Strings.class.getResourceAsStream("/" + resourcePath);
            return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Could not load file " + resourcePath, e);
        }
    }
}
