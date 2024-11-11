package me.lucko.jarrelocator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public abstract class AbstractRelocationSource implements Closeable {
    public static AbstractRelocationSource getSource(File input) throws IOException {
        if (input.isDirectory()) {
            return new DirectoryRelocationSource(input);
        }
        if (input.exists() && input.getName().endsWith(".class")) {
            return new ClassRelocationSource(input);
        }
        if (input.exists() && input.getName().endsWith(".jar")) {
            return new JarRelocationSource(input);
        }
        throw new RuntimeException("Unknown type of source '" + input + "': not a directory or .class/.jar file.");
    }

    public abstract Enumeration<JarEntry> entries();
    public abstract InputStream getInputStream(JarEntry entry) throws IOException;
}
