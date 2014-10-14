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
package com.codenvy.ide.client.propertiespanel.property.general;

import com.codenvy.ide.client.mvp.AbstractView;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.EditorResources.EditorCSS;

/**
 * Implementation of methods which are general for all widget panels.
 *
 * @author Dmitry Shnurenko
 */
public abstract class AbstractPropertyViewImpl<T> extends AbstractView<T> implements AbstractPropertyView<T> {

    private EditorCSS styles;

    public AbstractPropertyViewImpl(@Nonnull EditorCSS styles) {
        this.styles = styles;
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderVisible(boolean visible) {
        String style = styles.propertyBorderTop();

        if (visible) {
            addStyleName(style);
        } else {
            removeStyleName(style);
        }
    }

}