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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.PropertyChangedListener;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.codenvy.ide.client.workspace.WorkspacePresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The presenter that provides a business logic of WSO2Editor. It provides ability to configure the editor.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class WSO2Editor extends AbstractPresenter<WSO2EditorView> implements PropertyChangedListener,
                                                                             WSO2EditorView.ActionDelegate,
                                                                             ElementChangedListener {

    private final ToolbarPresenter           toolbar;
    private final WorkspacePresenter         workspace;
    private final List<EditorChangeListener> listeners;

    private boolean isVisibleToolbarPanel;

    private boolean isVisiblePropertyPanel;

    @Inject
    public WSO2Editor(WSO2EditorView view,
                      WorkspacePresenter workspace,
                      ToolbarPresenter toolbar,
                      PropertiesPanelManager propertiesPanelManager,
                      Set<Initializer> initializers) {
        super(view);

        isVisiblePropertyPanel = false;
        changePropertyPanelVisibility();
        
        isVisibleToolbarPanel = false;
        changeToolbarPanelVisibility();

        onShowPropertyButtonClicked();

        this.listeners = new ArrayList<>();

        this.workspace = workspace;
        this.workspace.addElementChangedListener(this);

        this.toolbar = toolbar;

        propertiesPanelManager.addPropertyChangedListener(this);
        propertiesPanelManager.setContainer(view.getPropertiesPanel());

        for (Initializer initializer : initializers) {
            initializer.initialize();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(view.getToolbarPanel());
        workspace.go(view.getWorkspacePanel());

        super.go(container);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyListeners();
    }

    /**
     * Adds a new listener for detecting the moment when diagram is changed.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addListener(@Nonnull EditorChangeListener listener) {
        listeners.add(listener);
    }

    /** Notify all listener about changing on the diagram. */
    public void notifyListeners() {
        for (EditorChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return workspace.serialize();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        workspace.deserialize(content);
    }

    /** {@inheritDoc} */
    @Override
    public void onCloseToolbarButtonClicked() {
        isVisibleToolbarPanel = false;
        view.hideToolbarPanel();
    }

    /** {@inheritDoc} */
    @Override
    public void onHidePanelButtonClicked() {
        view.setVisiblePropertyPanel(isVisiblePropertyPanel = false);
    }

    /** {@inheritDoc} */
    @Override
    public void onShowPropertyButtonClicked() {
        view.setVisiblePropertyPanel(true);
    }

    /** Performs some actions in response to user's clicking on palette panel button. */
    public void changeToolbarPanelVisibility() {
        view.setToolbarPanelVisibility(isVisibleToolbarPanel = !isVisibleToolbarPanel);
    /** Performs some actions in response to user's clicking on hide panel button. */
    public void changePropertyPanelVisibility() {
        view.setVisiblePropertyPanel(isVisiblePropertyPanel = !isVisiblePropertyPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditorDOMChanged() {
        workspace.resize();
    }

    public interface EditorChangeListener {
        /** Performs some actions when editor was changed. */
        void onChanged();
    }

}