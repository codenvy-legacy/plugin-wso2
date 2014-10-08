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

package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.SonarAwareGwtRunner;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.AbstractEntityElement.Key;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrey Plotnikov
 */
@GwtModule("com.codenvy.ide.WSO2Editor")
@RunWith(SonarAwareGwtRunner.class)
public abstract class AbstractEntityTest<T extends AbstractEntityElement> extends GwtTestWithMockito {

    private static final Key<Boolean>      BOOLEAN_PROPERTY = new Key<>("BooleanProperty");
    private static final Key<String>       STRING_PROPERTY  = new Key<>("StringProperty");
    private static final Key<Integer>      INTEGER_PROPERTY = new Key<>("IntegerProperty");
    private static final Key<List<String>> LIST_PROPERTY    = new Key<>("ListProperty");

    protected T entity;

    protected void assertContentAndValue(@Nonnull String path, @Nonnull String content) throws IOException {
        assertEquals(getContent(path), content);
    }

    @SuppressWarnings("NonJREEmulationClassesInClientCode")
    @Nonnull
    protected String getContent(@Nonnull String path) throws IOException {
        String file = AbstractEntityTest.class.getResource(path).getFile();
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    public void booleanPropertyShouldBeAdded() throws Exception {
        entity.putProperty(BOOLEAN_PROPERTY, true);
        assertTrue(entity.getProperty(BOOLEAN_PROPERTY));

        entity.putProperty(BOOLEAN_PROPERTY, false);
        assertFalse(entity.getProperty(BOOLEAN_PROPERTY));

        entity.putProperty(BOOLEAN_PROPERTY, null);
        assertNull(entity.getProperty(BOOLEAN_PROPERTY));
    }

    @Test
    public void booleanPropertyShouldBeAbsentWhenNoOneAddedIt() throws Exception {
        assertNull(entity.getProperty(BOOLEAN_PROPERTY));
    }

    @Test
    public void stringPropertyShouldBeAdded() throws Exception {
        String value1 = "value1";
        entity.putProperty(STRING_PROPERTY, value1);
        assertEquals(value1, entity.getProperty(STRING_PROPERTY));

        String value2 = "value2";
        entity.putProperty(STRING_PROPERTY, value2);
        assertEquals(value2, entity.getProperty(STRING_PROPERTY));

        entity.putProperty(STRING_PROPERTY, null);
        assertNull(entity.getProperty(STRING_PROPERTY));
    }

    @Test
    public void stringPropertyShouldBeAbsentWhenNoOneAddedIt() throws Exception {
        assertNull(entity.getProperty(STRING_PROPERTY));
    }

    @Test
    public void integerPropertyShouldBeAdded() throws Exception {
        Integer value1 = 1;
        entity.putProperty(INTEGER_PROPERTY, value1);
        assertEquals(value1, entity.getProperty(INTEGER_PROPERTY));

        Integer value2 = 2;
        entity.putProperty(INTEGER_PROPERTY, value2);
        assertEquals(value2, entity.getProperty(INTEGER_PROPERTY));

        entity.putProperty(INTEGER_PROPERTY, null);
        assertNull(entity.getProperty(INTEGER_PROPERTY));
    }

    @Test
    public void integerPropertyShouldBeAbsentWhenNoOneAddedIt() throws Exception {
        assertNull(entity.getProperty(INTEGER_PROPERTY));
    }

    @Test
    public void listPropertyShouldBeAdded() throws Exception {
        List<String> list1 = Arrays.asList("value");
        entity.putProperty(LIST_PROPERTY, list1);
        assertEquals(list1, entity.getProperty(LIST_PROPERTY));

        List<String> list2 = Collections.emptyList();
        entity.putProperty(LIST_PROPERTY, list2);
        assertEquals(list2, entity.getProperty(LIST_PROPERTY));

        entity.putProperty(LIST_PROPERTY, null);
        assertNull(entity.getProperty(LIST_PROPERTY));
    }

    @Test
    public void listPropertyShouldBeAbsentWhenNoOneAddedIt() throws Exception {
        assertNull(entity.getProperty(LIST_PROPERTY));
    }

}