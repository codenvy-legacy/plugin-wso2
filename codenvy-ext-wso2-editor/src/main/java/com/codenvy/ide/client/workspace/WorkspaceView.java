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
package com.codenvy.ide.client.workspace;

import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.PayloadFactory;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch_mediator;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@ImplementedBy(WorkspaceViewImpl.class)
public abstract class WorkspaceView extends AbstractView<WorkspaceView.ActionDelegate> {

    /** Clear diagram content. */
    public abstract void clearDiagram();

    /**
     * Remove a diagram element by id.
     *
     * @param elementId
     *         id of diagram element which needs to be removed
     */
    public abstract void removeElement(@Nonnull String elementId);

    /**
     * Change the enable state of the Zoom In button.
     *
     * @param enable
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    public abstract void setZoomInButtonEnable(boolean enable);

    /**
     * Change the enable state of the Zoom Out button.
     *
     * @param enable
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    public abstract void setZoomOutButtonEnable(boolean enable);

    /**
     * Changes the auto-alignment state of the editor.
     *
     * @param isAutoAligned
     *         <code>true</code> to enable auto-alignment state of diagram elements, <code>false</code> to disable it
     */
    public abstract void setAutoAlignmentParam(boolean isAutoAligned);

    /** @return <code>true</code> if the auto-alignment param is selected, <code>false</code> it isn't */
    public abstract boolean isAutoAligned();

    /**
     * Select a diagram element.
     *
     * @param elementId
     *         id of element which needs to be selected
     */
    public abstract void selectElement(@Nullable String elementId);

    /**
     * Select a diagram element.
     *
     * @param elementId
     *         id of element which needs to be selected
     * @param isError
     *         <code>true</code> to select element as error element (Which has some problems. For example: it is impossible to create a
     *         connection or etc).
     */
    public abstract void selectElementBelowCursor(@Nullable String elementId, boolean isError);

    /**
     * Unselect a diagram element.
     *
     * @param elementId
     *         id of element which needs to be selected
     * @param isError
     *         <code>true</code> to unselect element as error element (Which has some problems. For example: it is impossible to create a
     *         connection or etc).
     */
    public abstract void unselectElementBelowCursor(@Nullable String elementId, boolean isError);

    public abstract void setDefaultCursor();

    public abstract void setApplyCursor();

    public abstract void setErrorCursor();

    public abstract void addLog(int x, int y, @Nonnull Log element);

    public abstract void addProperty(int x, int y, @Nonnull Property element);

    public abstract void addPayloadFactory(int x, int y, @Nonnull PayloadFactory element);

    public abstract void addSend(int x, int y, @Nonnull Send element);

    public abstract void addHeader(int x, int y, @Nonnull Header element);

    public abstract void addRespond(int x, int y, @Nonnull Respond element);

    public abstract void addFilter(int x, int y, @Nonnull Filter element);

    public abstract void addSwitch_mediator(int x, int y, @Nonnull Switch_mediator element);

    public abstract void addSequence(int x, int y, @Nonnull Sequence element);

    public abstract void addEnrich(int x, int y, @Nonnull Enrich element);

    public abstract void addLoopBack(int x, int y, @Nonnull LoopBack element);

    public abstract void addCallTemplate(int x, int y, @Nonnull CallTemplate element);

    public abstract void addCall(int x, int y, @Nonnull Call element);

    public abstract void addConnection(@Nonnull String sourceElementID, @Nonnull String targetElementID);

    /** Required for delegating functions in the view. */
    public interface ActionDelegate extends AbstractView.ActionDelegate {
        /* Workspace actions */

        /**
         * Performs some actions in response to a user's doing right mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onRightMouseButtonClicked(int x, int y);

        /**
         * Performs some actions in response to a user's doing left mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onLeftMouseButtonClicked(int x, int y);

        /**
         * Performs some actions in response to a user's moving mouse.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseMoved(int x, int y);

        /** Performs any actions appropriate in response to the user having pressed the Zoom In button. */
        void onZoomInButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Zoom Out button. */
        void onZoomOutButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Delete button on keyboard. */
        void onDeleteButtonPressed();

        /** Performs any actions appropriate in response to the user having changed the auto-alignment state. */
        void onAutoAlignmentParamChanged();

        /* Elements actions */

        /**
         * Performs some actions in response to a user's clicking on diagram element.
         *
         * @param elementId
         *         the identifier of clicked diagram element
         */
        void onDiagramElementClicked(@Nonnull String elementId);

        /**
         * Performs some actions in response to a user's right mouse button clicking on diagram element.
         *
         * @param elementId
         *         the identifier of clicked diagram element
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onDiagramElementRightClicked(@Nonnull String elementId, int x, int y);

        /**
         * Performs some actions in response to a user's moving on diagram element.
         *
         * @param elementId
         *         the identifier of moved diagram element
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onDiagramElementMoved(@Nonnull String elementId, int x, int y);

        /**
         * Performs some actions in response to a user's moving mouse over diagram element.
         *
         * @param elementId
         *         the identifier of diagram element
         */
        void onMouseOverDiagramElement(@Nonnull String elementId);

        /**
         * Performs some actions in response to a user's moving mouse out diagram element.
         *
         * @param elementId
         *         the identifier of diagram element
         */
        void onMouseOutDiagramElement(@Nonnull String elementId);

    }

}