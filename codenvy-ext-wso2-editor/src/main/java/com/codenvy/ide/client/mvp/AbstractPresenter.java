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
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;

/**
 * The abstract implementation of presenter. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractPresenter<T extends View> {

    protected final T view;

    protected AbstractPresenter(T view) {
        this.view = view;
        //noinspection unchecked
        this.view.setDelegate(this);
    }

    /**
     * Show view on a given container.
     *
     * @param container
     *         container where view must be contained
     */
    public void go(@Nonnull AcceptsOneWidget container) {
        container.setWidget(view);
    }

}