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

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.orange.links.client.DiagramController;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a graphical representation of the element's branch.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class BranchViewImpl extends AbstractView<BranchView.ActionDelegate> implements BranchView {

    @Singleton
    interface BranchViewImplUiBinder extends UiBinder<Widget, BranchViewImpl> {
    }

    @UiField
    Label           title;
    @UiField
    FlowPanel       body;
    @UiField
    DockLayoutPanel focusPanel;
    @UiField
    FlowPanel       titlePanel;

    private       DiagramController      controller;
    private       PickupDragController   dragController;
    private final EditorResources        resources;
    private final List<ElementPresenter> elements;

    private int height;
    private int width;

    @Inject
    public BranchViewImpl(BranchViewImplUiBinder ourUiBinder, EditorResources resources) {
        this.resources = resources;
        this.elements = new ArrayList<>();

        this.height = DEFAULT_HEIGHT;
        this.width = DEFAULT_WIDTH;

        initWidget(ourUiBinder.createAndBindUi(this));

        createCanvas();

        bind();
    }

    private void createCanvas() {
        body.clear();

        if (controller != null && dragController != null) {
            controller.clearDiagram();
            controller.removeDragController();

            for (ElementPresenter element : elements) {
                dragController.makeNotDraggable(element.getView().asWidget());
            }
        }

        controller = new DiagramController(width + TITLE_WIDTH, height);

        body.add(controller.getView());

        dragController = new PickupDragController(controller.getView(), true);

        controller.registerDragController(dragController);

        for (ElementPresenter element : elements) {
            addElementOnView(element.getX(), element.getY(), element);
        }
    }

    private void bind() {
        focusPanel.addDomHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                if (NativeEvent.BUTTON_LEFT == event.getNativeButton()) {
                    delegate.onMouseLeftButtonClicked(event.getRelativeX(body.getElement()), event.getRelativeY(body.getElement()));
                }

                event.stopPropagation();
            }
        }, MouseDownEvent.getType());

        focusPanel.addDomHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                event.stopPropagation();
            }
        }, MouseUpEvent.getType());

        focusPanel.addDomHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                delegate.onMouseMoved(event.getRelativeX(body.getElement()), event.getRelativeY(body.getElement()));

                event.stopPropagation();
            }
        }, MouseMoveEvent.getType());

        focusPanel.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                delegate.onMouseOut();

                event.stopPropagation();
            }
        }, MouseOutEvent.getType());

        focusPanel.addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE) {
                    delegate.onDeleteButtonPressed();
                }

                event.stopPropagation();
            }
        }, KeyDownEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nullable String title) {
        this.title.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void addElement(int x, int y, @Nonnull ElementPresenter elementPresenter) {
        addElementOnView(x, y, elementPresenter);

        elements.add(elementPresenter);
    }

    private void addElementOnView(int x, int y, @Nonnull ElementPresenter elementPresenter) {
        Widget elementView = elementPresenter.getView().asWidget();

        controller.addWidget(elementView, x, y);
        dragController.makeDraggable(elementView);
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        body.clear();

        controller.clearDiagram();

        for (ElementPresenter element : elements) {
            dragController.makeNotDraggable(element.getView().asWidget());
        }

        elements.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultCursor() {
        focusPanel.removeStyleName(resources.editorCSS().applyCursor());
        focusPanel.removeStyleName(resources.editorCSS().errorCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void setApplyCursor() {
        focusPanel.removeStyleName(resources.editorCSS().errorCursor());
        focusPanel.addStyleName(resources.editorCSS().applyCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void setErrorCursor() {
        focusPanel.removeStyleName(resources.editorCSS().applyCursor());
        focusPanel.addStyleName(resources.editorCSS().errorCursor());
    }

    /** {@inheritDoc} */
    @Override
    public int getHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public void setHeight(@Nonnegative int height) {
        this.height = height;

        setHeight(height + "px");

        createCanvas();
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return width;
    }

    /** {@inheritDoc} */
    @Override
    public void setWidth(@Nonnegative int width) {
        this.width = width;

        setWidth(width + "px");

        createCanvas();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleTitle(boolean visible) {
        focusPanel.setWidgetHidden(titlePanel, !visible);
        focusPanel.onResize();
    }

}