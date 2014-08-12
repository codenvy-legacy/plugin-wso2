/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.shape.branch;

import com.codenvy.ide.client.elements.shape.ShapePresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(BranchViewImpl.class)
public abstract class BranchView extends AbstractView<BranchView.ActionDelegate> {

    public static final int ELEMENTS_PADDING = 20;
    public static final int ARROW_PADDING    = 20;
    public static final int DEFAULT_HEIGHT   = 100;
    public static final int DEFAULT_WIDTH    = 90;
    public static final int TITLE_HEIGHT     = 20;

    public abstract void setTitle(@Nullable String title);

    /**
     * Adds a diagram element in the container in a given place.
     *
     * @param x
     *         x-position where a new diagram element needs to be added
     * @param y
     *         y-position where a new diagram element needs to be added
     * @param shape
     *         the diagram element that needs to be added in container
     */
    public abstract void addElement(int x, int y, @Nonnull ShapePresenter shape);

    /** Clear container's content. */
    public abstract void clear();

    /** Set default cursor for workspace. */
    public abstract void setDefaultCursor();

    /** Set apply cursor for workspace. */
    public abstract void setApplyCursor();

    /** Set error cursor for workspace. */
    public abstract void setErrorCursor();

    @Nonnegative
    public abstract int getWidth();

    public abstract void setWidth(@Nonnegative int width);

    @Nonnegative
    public abstract int getHeight();

    public abstract void setHeight(@Nonnegative int height);

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /**
         * Performs some actions in response to a user's doing left mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseLeftButtonClicked(int x, int y);

        void onMouseMoved(int x, int y);

        void onMouseOut();

        void onDeleteButtonPressed();

    }

}