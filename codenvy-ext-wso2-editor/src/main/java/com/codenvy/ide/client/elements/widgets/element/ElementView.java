/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements.widgets.element;

import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.resources.client.ImageResource;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract view that represents the diagram element visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
public abstract class ElementView extends AbstractView<ElementView.ActionDelegate> {

    public static final int BRANCHES_PADDING = 12;
    public static final int DEFAULT_HEIGHT   = 80;
    public static final int DEFAULT_WIDTH    = 70;

    /**
     * Change content of title visual element.
     *
     * @param title
     *         content that need to be applied
     */
    public abstract void setTitle(@Nonnull String title);

    /**
     * Change icon of element.
     *
     * @param icon
     *         icon that need to be applied
     */
    public abstract void setIcon(@Nullable ImageResource icon);

    /**
     * Adds a new branch on the view.
     *
     * @param branchPresenter
     *         branch that needs to be added
     */
    public abstract void addBranch(@Nonnull BranchPresenter branchPresenter);

    /**
     * Removes all branches for the view.
     */
    public abstract void removeBranches();

    /** Changes the visual style of element that makes understandable that the element is selected. */
    public abstract void select();

    /** Changes the visual style of element that makes understandable that the element is unselected. */
    public abstract void unselect();

    /**
     * Changes the visual style of element that makes understandable that the element is selected by the mouse cursor over.
     *
     * @param isError
     *         <code>true</code> if it is possible to select element as error element (which has some problems. For example: it is
     *         impossible to create a connection or etc)
     *         <code>false</code> if it isn't.
     */
    public abstract void selectBelowCursor(boolean isError);

    /** Unselect widget when mouse cursor is moved out. */
    public abstract void unselectBelowCursor();

    /**
     * Shows context menu that is needed for the current element.
     *
     * @param x
     *         the x-position where the menu should be opened
     * @param y
     *         the y-position where the menu should be opened
     */
    public abstract void showContextMenu(@Nonnegative int x, @Nonnegative int y);

    /** Hides context menu. */
    public abstract void hideContextMenu();

    /**
     * Changes visible state of title and icon panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleTitleAndIcon(boolean visible);

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

    /**
     * Changes y-position of the view on the parent container.
     *
     * @param y
     *         new y-position of the view
     */
    public abstract void setY(@Nonnegative int y);

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to a user's clicking on diagram element. */
        void onMouseLeftButtonClicked();

        /**
         * Performs some actions in response to a user's right mouse button clicking on diagram element.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseRightButtonClicked(int x, int y);

        /**
         * Performs some actions in response to a user's moving the widget.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMoved(int x, int y);

        /** Performs some actions in response to a user's moving mouse over diagram element. */
        void onMouseOver();

        /** Performs some actions in response to a user's moving mouse out diagram element. */
        void onMouseOut();

        /** Performs some actions in response to a user's clicking on 'Delete' action in the context menu. */
        void onDeleteActionClicked();

        /** Performs some actions in response to a user's clicking on 'Number of branches' action in the context menu. */
        void onChangeNumberBranchesActionClicked();

        /** Performs some actions in response to a widget is being removed from DOM. */
        void onRemovedWidget();

    }

}