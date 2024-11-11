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
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class ClassRelocationTarget extends AbstractRelocationTargetOutputStream {
    private final OutputStream outputStream;
    private final File outputFile;

    public ClassRelocationTarget(File outputFile) throws FileNotFoundException {
        super();
        this.outputFile = outputFile;
        this.outputStream = new FileOutputStream(outputFile);
    }

    @Override
    public void putNextEntry(JarEntry jarEntry) throws IOException {
        outputFile.getParentFile().mkdirs();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }
}
