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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;

public class DirectoryRelocationTarget extends AbstractRelocationTargetOutputStream {
    private final File directory;
    private OutputStream currentOutputStream;

    public DirectoryRelocationTarget(File directory) {
        super();
        this.directory = directory;
    }

    @Override
    public void putNextEntry(JarEntry jarEntry) throws IOException {
        if (jarEntry.getName().endsWith("/")) {
            return;
        }
        String targetFile = directory + File.separator + jarEntry.getName();
        new File(targetFile).getParentFile().mkdirs();
        currentOutputStream = new FileOutputStream(targetFile);
    }

    @Override
    public void write(int b) throws IOException {
        currentOutputStream.write(b);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        currentOutputStream.write(bytes);
    }
}
