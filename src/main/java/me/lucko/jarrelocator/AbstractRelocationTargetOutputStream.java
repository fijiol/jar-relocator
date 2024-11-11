package me.lucko.jarrelocator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarEntry;

public abstract class AbstractRelocationTargetOutputStream extends OutputStream {
    public static AbstractRelocationTargetOutputStream getRelocationTargetOutputStream(File output) throws IOException {
        if (output.getName().endsWith(".class")) {
            return new ClassRelocationTarget(output);
        }
        if (output.getName().endsWith(".jar")) {
            return new JarRelocationTarget(output);
        }
        assert (output.getName().endsWith("/"));
        // target directory can be not created yet.
        return new DirectoryRelocationTarget(output);
    }

    public abstract void putNextEntry(JarEntry jarEntry) throws IOException;

    @Override
    public abstract void write(int b) throws IOException;

    public abstract void write(byte[] bytes) throws IOException;
}
