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

import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupView;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating widgets of the properties panel.
 *
 * @author Andrey Plotnikov
 */
public interface PropertiesGroupFactory {

    /**
     * Create an instance of {@link PropertyGroupView} with a given title.
     *
     * @param title
     *         title needs to be shown
     * @return an instance of {@link PropertyGroupView}
     */
    @Nonnull
    PropertyGroupView createPropertyGroupView(@Nonnull String title);

    /**
     * Create an instance of {@link PropertyGroupPresenter} with a given title.
     *
     * @param title
     *         title needs to be shown
     * @return an instance of {@link PropertyGroupPresenter}
     */
    @Nonnull
    PropertyGroupPresenter createPropertyGroupPresenter(@Nonnull String title);

}