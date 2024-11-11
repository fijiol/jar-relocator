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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarRelocationSource extends AbstractRelocationSource {
    private final JarFile jarFile;

    public JarRelocationSource(File inputFile) throws IOException {
        super();
        this.jarFile = new JarFile(inputFile);
    }

    @Override
    public Enumeration<JarEntry> entries() {
        return jarFile.entries();
    }

    @Override
    public InputStream getInputStream(JarEntry entry) throws IOException {
        return jarFile.getInputStream(entry);
    }

    @Override
    public void close() throws IOException {
        jarFile.close();
    }
}
