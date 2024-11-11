/*
 * Copyright Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.lucko.jarrelocator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

public class DirectoryRelocationSource extends AbstractRelocationSource {
    private final File directory;
    private final Collection<JarEntry> entries;

    public DirectoryRelocationSource(File directory) throws IOException {
        super();
        this.directory = directory;
        entries = Files.find(directory.toPath(), /* todo: better const... */ 1024,
                (filePath, fileAttr) ->
                        fileAttr.isRegularFile()).map((entry) ->
                            new JarEntry(getEntryName(directory, entry.toFile())))
                .collect(Collectors.toList());
    }

    @Override
    public Enumeration<JarEntry> entries() {
        return Collections.enumeration(entries);
    }

    @Override
    public InputStream getInputStream(JarEntry entry) throws IOException {
        if (!entries.contains(entry)) {
            throw new RuntimeException("Unexpected entry parameter, directory relocation source was initialized with " +
                    directory + ", and does not contain nested file " + entry);
        }
        return new FileInputStream(directory + File.separator + entry.getName());
    }

    @Override
    public void close() throws IOException {
        // this is not jar -- do nothing
    }

    private static String getEntryName(File root, File entry) {
        return removeStart(entry.getAbsolutePath(), root.getAbsolutePath());
    }

    private static String removeStart(String s, String prefix) {
        if (s == null) {
            return null;
        }
        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        }
        return s;
    }
}
