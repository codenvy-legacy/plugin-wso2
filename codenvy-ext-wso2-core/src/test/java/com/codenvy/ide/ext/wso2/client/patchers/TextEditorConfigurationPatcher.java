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
package com.codenvy.ide.ext.wso2.client.patchers;

import com.codenvy.ide.texteditor.api.TextEditorConfiguration;
import com.codenvy.ide.texteditor.api.parser.CmParser;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

import static org.mockito.Mockito.mock;

/**
 * The patcher for {@link TextEditorConfiguration}. Replace native method in it.
 *
 * @author Andrey Plotnikov
 */
@PatchClass(TextEditorConfiguration.class)
public class TextEditorConfigurationPatcher {

    @PatchMethod(override = true)
    public static CmParser getParserForMime(String mime) {
        return mock(CmParser.class);
    }
}