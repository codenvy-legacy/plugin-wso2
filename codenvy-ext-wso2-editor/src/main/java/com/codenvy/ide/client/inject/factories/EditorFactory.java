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
package com.codenvy.ide.client.inject.factories;

import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating elements of WSO2 editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@Singleton
public interface EditorFactory {
    /**
     * Create an instance of {@link PropertiesPanelManager} with a given widget.
     *
     * @param container
     *         widget that need to be used
     * @return an instance of {@link PropertiesPanelManager}
     */
    @Nonnull
    PropertiesPanelManager createPropertiesPanelManager(@Nonnull AcceptsOneWidget container);
}