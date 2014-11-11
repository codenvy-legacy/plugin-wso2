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

import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * The class that provides business logic of the arrow widget for the editor. Also it contains position parameters which inform about
 * position a current arrow in the editor.
 *
 * @author Andrey Plotnikov
 */
public class ArrowPresenter extends AbstractPresenter<ArrowView> implements ArrowView.ActionDelegate {

    public static final int ARROW_VERTICAL_SIZE   = 67;
    public static final int ARROW_HORIZONTAL_SIZE = 67;

    private int x;
    private int y;

    @Inject
    public ArrowPresenter(ArrowView view) {
        super(view);
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
        return view;
    }

    /** @return x-position of the widget */
    @Nonnegative
    public int getX() {
        return x;
    }

    /**
     * Changes x-position of the widget.
     *
     * @param x
     *         new x-position of the widget
     */
    public void setX(@Nonnegative int x) {
        this.x = x;
    }

    /** @return y-position of the widget */
    @Nonnegative
    public int getY() {
        return y;
    }

    /**
     * Changes y-position of the widget.
     *
     * @param y
     *         new y-position of the widget
     */
    public void setY(@Nonnegative int y) {
        this.y = y;
    }

    /** Sets alignment for arrows if horizontal orientation of the diagram is activated */
    public void applyVerticalAlign() {
        view.applyVerticalAlign();
    }

    /** Sets alignment for arrows if vertical orientation of the diagram is activated */
    public void applyHorizontalAlign() {
        view.applyHorizontalAlign();
    }

}