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
package com.codenvy.ide.client.elements.widgets.element;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.orange.links.client.menu.ContextMenu;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides a graphical representation of the diagram's element.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ElementViewImpl extends AbstractView<ElementView.ActionDelegate>
        implements ElementView, HasAllMouseHandlers, HasClickHandlers {

    @Singleton
    interface ElementViewImplUiBinder extends UiBinder<Widget, ElementViewImpl> {
    }

    @UiField
    Label           title;
    @UiField
    Image           icon;
    @UiField
    FlowPanel       rightPanel;
    @UiField
    DockLayoutPanel mainPanel;
    @UiField
    FlowPanel       leftPanel;

    private final EditorResources resources;
    private final ContextMenu     contextMenu;

    private int     height;
    private int     width;
    private boolean isBranchAdded;

    @Inject
    public ElementViewImpl(ElementViewImplUiBinder ourUiBinder, EditorResources resources, @Assisted boolean isPossibleChangeCases) {
        this.resources = resources;
        this.contextMenu = new ContextMenu();

        this.height = DEFAULT_HEIGHT;
        this.width = DEFAULT_WIDTH;
        this.isBranchAdded = false;

        this.contextMenu.addItem("Delete", new Command() {
            @Override
            public void execute() {
                delegate.onDeleteActionClicked();
            }
        });

        if (isPossibleChangeCases) {
            this.contextMenu.addItem("Number of branches", new Command() {
                @Override
                public void execute() {
                    delegate.onChangeNumberBranchesActionClicked();
                }
            });
        }

        initWidget(ourUiBinder.createAndBindUi(this));

        bind();
    }

    private void bind() {
        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                int nativeButton = event.getNativeButton();

                if (NativeEvent.BUTTON_RIGHT == nativeButton) {
                    delegate.onMouseRightButtonClicked(event.getClientX(), event.getClientY());
                }

                if (NativeEvent.BUTTON_LEFT == nativeButton) {
                    delegate.onMouseLeftButtonClicked();
                }

                event.stopPropagation();
            }
        });

        addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                delegate.onMoved(event.getRelativeX(getParent().getElement()), event.getRelativeY(getParent().getElement()));

                event.stopPropagation();
            }
        });

        addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                delegate.onMouseOut();

                event.stopPropagation();
            }
        });

        addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                delegate.onMouseOver();

                event.stopPropagation();
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
        this.title.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setIcon(@Nullable ImageResource icon) {
        if (icon == null) {
            return;
        }

        this.icon.setResource(icon);
    }

    /** {@inheritDoc} */
    @Override
    public void addBranch(@Nonnull BranchPresenter branchPresenter) {
        IsWidget widget = branchPresenter.getView();

        if (isBranchAdded) {
            widget.asWidget().addStyleName(resources.editorCSS().topBorder());
        } else {
            isBranchAdded = true;
        }

        rightPanel.add(widget);
    }

    /** {@inheritDoc} */
    @Override
    public void removeBranches() {
        this.isBranchAdded = false;
        rightPanel.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void select() {
        mainPanel.addStyleName(resources.editorCSS().selectedElement());
    }

    /** {@inheritDoc} */
    @Override
    public void unselect() {
        mainPanel.removeStyleName(resources.editorCSS().selectedElement());
    }

    /** {@inheritDoc} */
    @Override
    public void selectBelowCursor(boolean isError) {
        EditorResources.EditorCSS css = resources.editorCSS();

        mainPanel.addStyleName(isError ?
                               css.selectErrorElementBelowCursor() :
                               css.selectElementBelowCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void unselectBelowCursor() {
        EditorResources.EditorCSS css = resources.editorCSS();

        mainPanel.removeStyleName(css.selectElementBelowCursor());
        mainPanel.removeStyleName(css.selectErrorElementBelowCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void showContextMenu(int x, int y) {
        contextMenu.setPopupPosition(x, y);
        contextMenu.show();
    }

    /** {@inheritDoc} */
    @Override
    public void hideContextMenu() {
        contextMenu.hide();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleTitleAndIcon(boolean visible) {
        mainPanel.setWidgetHidden(leftPanel, !visible);

        mainPanel.onResize();
    }

    /** {@inheritDoc} */
    @Override
    protected void onDetach() {
        delegate.onRemovedWidget();

        super.onDetach();
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
    }

    /** {@inheritDoc} */
    @Override
    public void setY(@Nonnegative int y) {
        getElement().getStyle().setProperty("top", y + "px");
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
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
        return addDomHandler(handler, MouseMoveEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
        return addDomHandler(handler, MouseOutEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
        return addDomHandler(handler, MouseOverEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
        return addDomHandler(handler, MouseUpEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
        return addDomHandler(handler, MouseWheelEvent.getType());
    }

}