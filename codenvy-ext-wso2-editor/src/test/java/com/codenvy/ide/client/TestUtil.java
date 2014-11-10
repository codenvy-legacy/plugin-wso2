/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The class contains business logic which is general for all tests classes.
 *
 * @author Dmitry Shnurenko
 */
public class TestUtil {

    /**
     * Returns string content representation by following path.
     *
     * @param clazz
     *         class which uses this method
     * @param path
     *         path to content which need read
     * @return string representation of content which located by current path
     */
    @Nonnull
    public static String getContentByPath(@Nonnull Class clazz, @Nonnull String path) throws IOException {
        String file = clazz.getResource(path).getFile();
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}