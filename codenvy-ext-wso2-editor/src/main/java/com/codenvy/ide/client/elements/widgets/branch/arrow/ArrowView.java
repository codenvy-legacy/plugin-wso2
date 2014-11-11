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
package com.codenvy.ide.client.elements.widgets.branch.arrow;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

/**
 * The abstract view that represents the arrow of a diagram element visual part of the widget.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(ArrowViewImpl.class)
public interface ArrowView extends View<ArrowView.ActionDelegate> {

    /** Sets vertical alignment for arrows if vertical orientation of the diagram is activated */
    void applyVerticalAlign();

    /** Sets horizontal alignment for arrows if horizontal orientation of the diagram is activated */
    void applyHorizontalAlign();

    interface ActionDelegate {
    }

}