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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class ClassRelocationSource extends AbstractRelocationSource {
    private final File classFile;
    private final JarEntry artificialJarEntry;

    public ClassRelocationSource(File classFile) {
        super();
        this.classFile = classFile;
        artificialJarEntry = new JarEntry(classFile.getName());
    }

    @Override
    public Enumeration<JarEntry> entries() {
        return Collections.enumeration(Arrays.asList(artificialJarEntry));
    }

    @Override
    public InputStream getInputStream(JarEntry entry) throws IOException {
        if (entry != artificialJarEntry) {
            throw new RuntimeException("Unexpected entry parameter, class relocation source was initialized with " +
                    classFile + " : " + artificialJarEntry + ", but got input stream request for " + entry);
        }
        return new FileInputStream(classFile);
    }

    @Override
    public void close() throws IOException {
        // there is no jar -- do nothing
    }
}
