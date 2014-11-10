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
package com.codenvy.ide.client.initializers.propertytype;

import com.codenvy.ide.client.managers.PropertyTypeManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer.BOOLEAN_TYPE_NAME;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonPropertyTypeInitializerTest {

    @Mock
    private PropertyTypeManager           manager;
    @InjectMocks
    private CommonPropertyTypeInitializer initializer;

    @Test
    public void booleanPropertyTypeShouldBeInitialized() throws Exception {
        initializer.initialize();

        verify(manager).register(BOOLEAN_TYPE_NAME, Arrays.asList(Boolean.FALSE.toString(), Boolean.TRUE.toString()));
    }
}