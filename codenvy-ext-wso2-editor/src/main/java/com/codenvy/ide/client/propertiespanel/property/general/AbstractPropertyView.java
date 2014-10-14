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

import com.codenvy.ide.api.mvp.View;

import javax.annotation.Nullable;

/**
 * The abstract representation of the general property widget. It contains all important methods which are needed for all kinds of
 * panels.
 *
 * @author Dmitry Shnurenko
 */
public interface AbstractPropertyView<T> extends View<T> {
    /**
     * Changes title of property on the view.
     *
     * @param title
     *         title that needs to be changed
     */
    void setTitle(@Nullable String title);

    /**
     * Changes visible state of the top border.
     *
     * @param visible
     *         <code>true</code> the border will be shown, <code>false</code> it will not
     */
    void setBorderVisible(boolean visible);

    /**
     * Changes visible state of the widget.
     *
     * @param visible
     *         <code>true</code> the widget will be shown, <code>false</code> it will not
     */
    void setVisible(boolean visible);

}