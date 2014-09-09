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
package com.codenvy.ide.client.elements.widgets.branch;

import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract view that represents the branch of a diagram element visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(BranchViewImpl.class)
public abstract class BranchView extends AbstractView<BranchView.ActionDelegate> {

    public static final int ELEMENTS_PADDING = 20;
    public static final int ARROW_PADDING    = 20;
    public static final int DEFAULT_HEIGHT   = 100;
    public static final int DEFAULT_WIDTH    = 90;
    public static final int TITLE_HEIGHT     = 20;

    /**
     * Changes title on the view.
     *
     * @param title
     *         title that needs to be set
     */
    public abstract void setTitle(@Nullable String title);

    /**
     * Adds a diagram element in the container in a given place.
     *
     * @param x
     *         x-position where a new diagram element needs to be added
     * @param y
     *         y-position where a new diagram element needs to be added
     * @param element
     *         the diagram element that needs to be added in container
     */
    public abstract void addElement(int x, int y, @Nonnull ElementPresenter element);

    /** Clear container's content. Removes all inner diagram elements. */
    public abstract void clear();

    /** Set default cursor for branch. */
    public abstract void setDefaultCursor();

    /** Set apply cursor for branch. */
    public abstract void setApplyCursor();

    /** Set error cursor for branch. */
    public abstract void setErrorCursor();

    /** @return the width of the view */
    @Nonnegative
    public abstract int getWidth();

    /**
     * Changes width of the view.
     *
     * @param width
     *         new width of the view
     */
    public abstract void setWidth(@Nonnegative int width);

    /** @return the height of the view */
    @Nonnegative
    public abstract int getHeight();

    /**
     * Changes height of the view.
     *
     * @param height
     *         new height of the view
     */
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

        /**
         * Performs some actions in response to a user's moving mouse .
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseMoved(int x, int y);

        /** Performs some actions in response to a user's moving mouse out. */
        void onMouseOut();

        /** Performs some actions in response to a user's typing 'Delete' button on the keyboard. */
        void onDeleteButtonPressed();

    }

}