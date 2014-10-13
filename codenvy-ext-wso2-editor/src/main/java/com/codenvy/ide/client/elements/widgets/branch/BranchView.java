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

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.elements.widgets.branch.arrow.ArrowPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
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
public interface BranchView extends View<BranchView.ActionDelegate> {

    int ELEMENTS_PADDING      = 25;
    int ARROW_PADDING         = 15;
    int DEFAULT_HEIGHT        = 100;
    int DEFAULT_WIDTH         = 100;
    int TITLE_WIDTH           = 40;
    int BORDER_SIZE           = 1;
    int ELEMENT_ARROW_PADDING = 2;

    /**
     * Changes title on the view.
     *
     * @param title
     *         title that needs to be set
     */
    void setTitle(@Nullable String title);

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
    void addElement(@Nonnegative int x, @Nonnegative int y, @Nonnull ElementPresenter element);

    /**
     * Adds an arrow in the container.The position where this element have to be located must be defined into a give element.
     *
     * @param arrow
     *         arrow that needs to be added
     */
    void addArrow(@Nonnull ArrowPresenter arrow);

    /** Clear container's content. Removes all inner diagram elements. */
    void clear();

    /** Set default cursor for branch. */
    void setDefaultCursor();

    /** Set apply cursor for branch. */
    void setApplyCursor();

    /** Set error cursor for branch. */
    void setErrorCursor();

    /** @return the width of the view */
    @Nonnegative
    int getWidth();

    /**
     * Changes width of the view.
     *
     * @param width
     *         new width of the view
     */
    void setWidth(@Nonnegative int width);

    /** @return the height of the view */
    @Nonnegative
    int getHeight();

    /**
     * Changes height of the view.
     *
     * @param height
     *         new height of the view
     */
    void setHeight(@Nonnegative int height);

    /**
     * Changes visible state of title panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    void setVisibleTitle(boolean visible);

    /**
     * Changes visible state of top border of the view.
     *
     * @param visible
     *         <code>true</code> the border will be shown, <code>false</code> it will not
     */
    void setVisibleTopBorder(boolean visible);

    public interface ActionDelegate {

        /**
         * Performs some actions in response to a user's doing left mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseLeftButtonClicked(int x, int y);

        /** Performs some actions in response to a user's right mouse button clicking on diagram element. */
        void onMouseRightButtonClicked();

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