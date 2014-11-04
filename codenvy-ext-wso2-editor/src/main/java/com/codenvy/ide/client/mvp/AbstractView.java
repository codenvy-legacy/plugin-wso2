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
package com.codenvy.ide.client.mvp;

import com.codenvy.ide.api.mvp.View;
import com.google.gwt.user.client.ui.Composite;

import javax.annotation.Nonnull;

/**
 * The abstract implementation of view. It contains the implementation of general methods which might not be changed.
 *
 * @param <T>
 *         type of action delegate
 * @author Andrey Plotnikov
 */
public abstract class AbstractView<T> extends Composite implements View<T> {

    protected T delegate;

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull T delegate) {
        this.delegate = delegate;
    }

}