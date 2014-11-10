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

import com.googlecode.gwt.test.GwtRunner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Gwt runner that can setup correctly class path during code coverage test on sonar
 * @author Sergii Kabashniuk
 */
public class SonarAwareGwtRunner extends GwtRunner {

    static {
        URLClassLoader classLoader = (URLClassLoader)SonarAwareGwtRunner.class.getClassLoader();

        try {
            URL[] urls = getClassPath();
            for (URL url : urls) {
                if (url.toString().contains("sonar")) {
                    ClassLoader cl = URLClassLoader.newInstance(urls, classLoader);
                    Thread.currentThread().setContextClassLoader(cl);
                    break;
                }
            }

        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

    }

    public SonarAwareGwtRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    private static URL[] getClassPath() throws MalformedURLException {
        String classPath = System.getProperty("java.class.path");
        String pathSeparator = System.getProperty("path.separator");
        String[] array = classPath.split(pathSeparator);

        List<URL> files = new ArrayList<>();
        for (String a : array) {
            files.add(new File(a).toURI().toURL());
        }
        return files.toArray(new URL[files.size()]);
    }

}