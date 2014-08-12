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
package com.codenvy.ide.client.elements.shape;

import com.codenvy.ide.client.elements.shape.branch.BranchPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.resources.client.ImageResource;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 */
public abstract class ShapeView extends AbstractView<ShapeView.ActionDelegate> {

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

    public abstract void addBranch(@Nonnull BranchPresenter branchPresenter);

    public abstract void removeBranches();

    public abstract void select();

    public abstract void unselect();

    /**
     * Select widget when mouse cursor is over.
     *
     * @param isError
     *         <code>true</code> if it is possible to select element as error element (which has some problems. For example: it is
     *         impossible to create a connection or etc)
     *         <code>false</code> if it isn't.
     */
    public abstract void selectBelowCursor(boolean isError);

    /** Unselect widget when mouse cursor is moved out. */
    public abstract void unselectBelowCursor();

    public abstract void showContextMenu(int x, int y);

    public abstract void hideContextMenu();

    public abstract void setVisibleTitleAndIcon(boolean visible);

    @Nonnegative
    public abstract int getWidth();

    public abstract void setWidth(@Nonnegative int width);

    @Nonnegative
    public abstract int getHeight();

    public abstract void setHeight(@Nonnegative int height);

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

        void onDeleteActionClicked();

        void onChangeNumberBranchesActionClicked();

        void onRemovedWidget();

    }

}