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

import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.gwt.user.client.ui.IsWidget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract representation of the general property widget. It contains all important methods which are needed for all kinds of
 * properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public abstract class AbstractPropertyPresenter<T extends AbstractPropertyView> extends AbstractPresenter<T> {

    protected AbstractPropertyPresenter(T view) {
        super(view);
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
        return view;
    }

    /**
     * Changes property title on the view.
     *
     * @param title
     *         title that needs to be set
     */
    public void setTitle(@Nullable String title) {
        view.setTitle(title);
    }

    /**
     * Calls the method on view which set visibility of the widget.
     *
     * @param visible
     *         <code>true</code> the border will be shown, <code>false</code> it will not
     */
    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    /**
     * Calls the method on view which set visibility of the top border.
     *
     * @param visible
     *         <code>true</code> the border will be shown, <code>false</code> it will not
     */
    public void setTopBorderVisible(boolean visible) {
        view.setBorderVisible(visible);
    }

}