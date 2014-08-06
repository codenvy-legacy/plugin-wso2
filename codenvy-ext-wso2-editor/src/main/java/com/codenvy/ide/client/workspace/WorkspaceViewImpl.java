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

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Shape;
import com.codenvy.ide.client.elements.Switch_mediator;
import com.codenvy.ide.client.elements.shape.ShapeWidget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orange.links.client.DiagramController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class WorkspaceViewImpl extends WorkspaceView {

    interface WorkspaceViewImplUiBinder extends UiBinder<Widget, WorkspaceViewImpl> {
    }

    @UiField
    FlowPanel  mainPanel;
    @UiField
    Button     zoomIn;
    @UiField
    Button     zoomOut;
    @UiField
    FocusPanel focusPanel;
    @UiField
    FlowPanel  controlPanel;

    private final DiagramController     controller;
    private final PickupDragController  dragController;
    private final Provider<ShapeWidget> widgetProvider;
    private final EditorResources       resources;
    private final Map<String, Widget>   elements;
    private       Widget                selectedElement;

    @Inject
    public WorkspaceViewImpl(WorkspaceViewImplUiBinder ourUiBinder, Provider<ShapeWidget> widgetProvider, EditorResources resources) {
        this.resources = resources;
        this.widgetProvider = widgetProvider;
        this.elements = new HashMap<>();

        widget = ourUiBinder.createAndBindUi(this);

        controller = new DiagramController(1600, 1000);
        mainPanel.add(controller.getView());

        dragController = new PickupDragController(controller.getView(), true);
        controller.registerDragController(dragController);

        bind();
    }

    private void bind() {
        focusPanel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onLeftMouseButtonClicked(event.getRelativeX(mainPanel.getElement()), event.getRelativeY(mainPanel.getElement()));
            }
        });

        focusPanel.addDomHandler(new ContextMenuHandler() {
            @Override
            public void onContextMenu(ContextMenuEvent event) {
                NativeEvent nativeEvent = event.getNativeEvent();
                delegate.onRightMouseButtonClicked(nativeEvent.getClientX(), nativeEvent.getClientY());
            }
        }, ContextMenuEvent.getType());

        focusPanel.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                delegate.onMouseMoved(event.getRelativeX(mainPanel.getElement()), event.getRelativeY(mainPanel.getElement()));
            }
        });

        focusPanel.addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE) {
                    delegate.onDeleteButtonPressed();
                }
            }
        }, KeyDownEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void setZoomInButtonEnable(boolean enable) {
        if (enable) {
            controlPanel.add(zoomIn);

            if (zoomOut.isAttached()) {
                controlPanel.remove(zoomOut);
                controlPanel.add(zoomOut);
            }
        } else {
            controlPanel.remove(zoomIn);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setZoomOutButtonEnable(boolean enable) {
        if (enable) {
            controlPanel.add(zoomOut);

            if (zoomOut.isAttached()) {
                controlPanel.remove(zoomIn);
                controlPanel.add(zoomIn);
            }
        } else {
            controlPanel.remove(zoomOut);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectElement(@Nullable String elementId) {
        Widget element = elements.get(elementId);

        if (selectedElement != null) {
            selectedElement.removeStyleName(resources.editorCSS().selectedElement());
        }

        selectedElement = element;
        if (selectedElement != null) {
            selectedElement.addStyleName(resources.editorCSS().selectedElement());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectElementBelowCursor(@Nullable String elementId, boolean isError) {
        Widget element = elements.get(elementId);
        if (element != null) {
            if (isError) {
                element.addStyleName(resources.editorCSS().selectErrorElementBelowCursor());
            } else {
                element.addStyleName(resources.editorCSS().selectElementBelowCursor());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void unselectElementBelowCursor(@Nullable String elementId, boolean isError) {
        Widget element = elements.get(elementId);
        if (element != null) {
            if (isError) {
                element.removeStyleName(resources.editorCSS().selectErrorElementBelowCursor());
            } else {
                element.removeStyleName(resources.editorCSS().selectElementBelowCursor());
            }
        }
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

    @UiHandler("zoomIn")
    public void onZoomInButtonClicked(ClickEvent event) {
        delegate.onZoomInButtonClicked();
    }

    @UiHandler("zoomOut")
    public void onZoomOutButtonClicked(ClickEvent event) {
        delegate.onZoomOutButtonClicked();
    }

    /** {@inheritDoc} */
    @Override
    public void clearDiagram() {
        controller.clearDiagram();
    }

    /** {@inheritDoc} */
    @Override
    public void removeElement(@Nonnull String elementId) {
        Widget widget = elements.get(elementId);
        if (widget != null) {
            controller.deleteWidget(widget);
        }
    }

    private void addShape(int x, int y, @Nonnull final Shape shape, @Nonnull ImageResource resource) {
        final ShapeWidget elementWidget = widgetProvider.get();
        final String elementId = shape.getId();

        elementWidget.setTitle(shape.getTitle());
        elementWidget.setIcon(resource);

        elementWidget.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                switch (event.getNativeButton()) {
                    case NativeEvent.BUTTON_RIGHT:
                        delegate.onDiagramElementRightClicked(elementId, event.getClientX(), event.getClientY());
                        break;

                    case NativeEvent.BUTTON_LEFT:
                    default:
                        delegate.onDiagramElementClicked(elementId);
                }
            }
        });

        elementWidget.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                delegate.onDiagramElementMoved(elementId,
                                               elementWidget.getAbsoluteLeft() - mainPanel.getAbsoluteLeft(),
                                               elementWidget.getAbsoluteTop() - mainPanel.getAbsoluteTop());
            }
        });

        elementWidget.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                delegate.onMouseOverDiagramElement(elementId);
            }
        });

        elementWidget.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                delegate.onMouseOutDiagramElement(elementId);
            }
        });

        controller.addWidget(elementWidget, x, y);
        dragController.makeDraggable(elementWidget);

        elements.put(shape.getId(), elementWidget);
    }

    /** {@inheritDoc} */
    @Override
    public void addLog(int x, int y, @Nonnull Log element) {
        addShape(x, y, element, resources.log());
    }

    /** {@inheritDoc} */
    @Override
    public void addProperty(int x, int y, @Nonnull Property element) {
        addShape(x, y, element, resources.property());
    }

    /** {@inheritDoc} */
    @Override
    public void addPayloadFactory(int x, int y, @Nonnull PayloadFactory element) {
        addShape(x, y, element, resources.payloadFactory());
    }

    /** {@inheritDoc} */
    @Override
    public void addSend(int x, int y, @Nonnull Send element) {
        addShape(x, y, element, resources.send());
    }

    /** {@inheritDoc} */
    @Override
    public void addHeader(int x, int y, @Nonnull Header element) {
        addShape(x, y, element, resources.header());
    }

    /** {@inheritDoc} */
    @Override
    public void addRespond(int x, int y, @Nonnull Respond element) {
        addShape(x, y, element, resources.respond());
    }

    /** {@inheritDoc} */
    @Override
    public void addFilter(int x, int y, @Nonnull Filter element) {
        addShape(x, y, element, resources.filter());
    }

    /** {@inheritDoc} */
    @Override
    public void addSwitch_mediator(int x, int y, @Nonnull Switch_mediator element) {
        addShape(x, y, element, resources.switch_mediator());
    }

    /** {@inheritDoc} */
    @Override
    public void addSequence(int x, int y, @Nonnull Sequence element) {
        addShape(x, y, element, resources.sequence());
    }

    /** {@inheritDoc} */
    @Override
    public void addEnrich(int x, int y, @Nonnull Enrich element) {
        addShape(x, y, element, resources.enrich());
    }

    /** {@inheritDoc} */
    @Override
    public void addLoopBack(int x, int y, @Nonnull LoopBack element) {
        addShape(x, y, element, resources.loopBack());
    }

    /** {@inheritDoc} */
    @Override
    public void addCallTemplate(int x, int y, @Nonnull CallTemplate element) {
        addShape(x, y, element, resources.callTemplate());
    }

    /** {@inheritDoc} */
    @Override
    public void addCall(int x, int y, @Nonnull Call element) {
        addShape(x, y, element, resources.call());
    }

    /** {@inheritDoc} */
    @Override
    public void addConnection(@Nonnull String sourceElementID, @Nonnull String targetElementID) {
        Widget sourceWidget = elements.get(sourceElementID);
        Widget targetWidget = elements.get(targetElementID);
        controller.drawStraightArrowConnection(sourceWidget, targetWidget);
    }

}