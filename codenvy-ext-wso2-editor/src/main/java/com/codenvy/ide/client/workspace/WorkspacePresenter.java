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

package com.codenvy.ide.client.workspace;

import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.workspace.WorkspaceView.SIZE_OF_TWO_BORDER_LINES;

/**
 * The class that provides business logic of the workspace element of the editor. The workspace is the center part of UI. It has an ability
 * to show root element of diagram. The main function of workspace is detect position and size of root element and change it.
 *
 * @author Andrey Plotnikov
 */
public class WorkspacePresenter extends AbstractPresenter<WorkspaceView> implements WorkspaceView.ActionDelegate, ElementChangedListener {

    private final RootElement                  element;
    private final ElementPresenter             elementWidget;
    private       List<ElementChangedListener> listeners;

    @Inject
    public WorkspacePresenter(WorkspaceView view, ElementWidgetFactory elementWidgetFactory, RootElement element) {
        super(view);

        this.element = element;

        this.elementWidget = elementWidgetFactory.createElementPresenter(element);
        this.elementWidget.addElementChangedListener(this);

        this.view.setElement(elementWidget);

        this.listeners = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        resize();

        notifyElementChangedListeners();
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return element.serialize();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        element.deserialize(content);

        resize();
    }

    /**
     * Resize workspace. The size depends on the size of parent widget. Needs to adopt size of workspace to parent widget. It is impossible
     * to with CSS so this method will be created.
     */
    public void resize() {
        elementWidget.updateView();

        int parentHeight = view.getParentHeight();

        if (parentHeight > elementWidget.getHeight()) {
            elementWidget.setHeight(parentHeight - SIZE_OF_TWO_BORDER_LINES);
        }

        int parentWidth = view.getParentWidth();

        if (parentWidth > elementWidget.getWidth()) {
            elementWidget.setWidth(parentWidth - SIZE_OF_TWO_BORDER_LINES);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        resize();
    }

    /**
     * Adds listener that listens to changing of the current element.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addElementChangedListener(@Nonnull ElementChangedListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners about changing element. */
    public void notifyElementChangedListeners() {
        for (ElementChangedListener listener : listeners) {
            listener.onElementChanged();
        }
    }

}