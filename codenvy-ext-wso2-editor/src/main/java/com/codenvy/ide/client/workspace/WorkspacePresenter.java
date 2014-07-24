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

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.HasState;
import com.codenvy.ide.client.SelectionManager;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.common.ContentFormatter;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Connection;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.Link;
import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.PayloadFactory;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Shape;
import com.codenvy.ide.client.elements.Switch_mediator;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.orange.links.client.menu.ContextMenu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.State.CREATING_CONNECTION_TARGET;
import static com.codenvy.ide.client.State.CREATING_NOTHING;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class WorkspacePresenter extends AbstractPresenter implements WorkspaceView.ActionDelegate, HasState<State> {

    private final Shape                           mainElement;
    private final Map<String, Element>            elements;
    private final List<DiagramChangeListener>     diagramChangeListeners;
    private final List<MainElementChangeListener> mainElementChangeListeners;
    private final ContextMenu                     contextMenu;
    private       EditorState<State>              state;
    private       String                          selectedElementId;
    private       Shape                           nodeElement;
    private       SelectionManager                selectionManager;
    private       boolean                         isErrorElement;

    @Inject
    public WorkspacePresenter(WorkspaceView view, @Assisted EditorState<State> state, @Assisted SelectionManager selectionManager) {
        super(view);

        this.state = state;
        this.selectionManager = selectionManager;
        this.mainElement = new RootElement();
        this.nodeElement = mainElement;
        this.elements = new HashMap<>();
        this.diagramChangeListeners = new ArrayList<>();
        this.mainElementChangeListeners = new ArrayList<>();

        this.selectionManager.setElement(mainElement);
        this.selectedElementId = mainElement.getId();

        this.elements.put(selectedElementId, mainElement);

        this.contextMenu = new ContextMenu();
        this.contextMenu.addItem("Delete", new Command() {
            @Override
            public void execute() {
                deleteSelectedElement();
                contextMenu.hide();
            }
        });

        isErrorElement = false;
    }

    /** {@inheritDoc} */
    @Override
    public void onLeftMouseButtonClicked(int x, int y) {
        selectElement(null);

        switch (getState()) {
            case CREATING_LOG:
                Log log = new Log();

                ((WorkspaceView)view).addLog(x, y, log);
                addShape(log, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_PROPERTY:
                Property property = new Property();

                ((WorkspaceView)view).addProperty(x, y, property);
                addShape(property, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_PAYLOADFACTORY:
                PayloadFactory payloadFactory = new PayloadFactory();

                ((WorkspaceView)view).addPayloadFactory(x, y, payloadFactory);
                addShape(payloadFactory, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_SEND:
                Send send = new Send();

                ((WorkspaceView)view).addSend(x, y, send);
                addShape(send, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_HEADER:
                Header header = new Header();

                ((WorkspaceView)view).addHeader(x, y, header);
                addShape(header, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_RESPOND:
                Respond respond = new Respond();

                ((WorkspaceView)view).addRespond(x, y, respond);
                addShape(respond, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_FILTER:
                Filter filter = new Filter();

                ((WorkspaceView)view).addFilter(x, y, filter);
                addShape(filter, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_SWITCH_MEDIATOR:
                Switch_mediator switch_mediator = new Switch_mediator();

                ((WorkspaceView)view).addSwitch_mediator(x, y, switch_mediator);
                addShape(switch_mediator, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_SEQUENCE:
                Sequence sequence = new Sequence();

                ((WorkspaceView)view).addSequence(x, y, sequence);
                addShape(sequence, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_ENRICH:
                Enrich enrich = new Enrich();

                ((WorkspaceView)view).addEnrich(x, y, enrich);
                addShape(enrich, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_LOOPBACK:
                LoopBack loopBack = new LoopBack();

                ((WorkspaceView)view).addLoopBack(x, y, loopBack);
                addShape(loopBack, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_CALLTEMPLATE:
                CallTemplate callTemplate = new CallTemplate();

                ((WorkspaceView)view).addCallTemplate(x, y, callTemplate);
                addShape(callTemplate, x, y);

                setState(CREATING_NOTHING);
                break;
            case CREATING_CALL:
                Call call = new Call();

                ((WorkspaceView)view).addCall(x, y, call);
                addShape(call, x, y);

                setState(CREATING_NOTHING);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementClicked(@Nonnull String elementId) {
        String prevSelectedElement = selectedElementId;
        selectElement(elementId);

        Shape selectedElement = (Shape)elements.get(prevSelectedElement);
        Shape element = (Shape)elements.get(elementId);

        ((WorkspaceView)view).unselectElementBelowCursor(elementId, isErrorElement);

        Shape parent;

        switch (getState()) {
            case CREATING_CONNECTION_SOURCE:
                setState(CREATING_CONNECTION_TARGET);
                break;
            case CREATING_CONNECTION_TARGET:
                if (selectedElement.canCreateConnection("Connection", element.getElementName())) {
                    ((WorkspaceView)view).addConnection(prevSelectedElement, selectedElementId);
                    Connection connection = new Connection(selectedElement.getId(), element.getId());
                    elements.put(element.getId(), element);

                    parent = selectedElement.getParent();
                    if (parent != null) {
                        parent.addLink(connection);
                    }

                    notifyDiagramChangeListeners();
                }

                setState(CREATING_NOTHING);
                selectElement(elementId);

                break;
            default:
                setState(CREATING_NOTHING);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOverDiagramElement(@Nonnull String elementId) {
        Element element = elements.get(elementId);
        if (element == null) {
            return;
        }

        switch (getState()) {
            case CREATING_CONNECTION_TARGET:
                Shape selectedElement = (Shape)elements.get(selectedElementId);
                if (!selectedElement.canCreateConnection("Connection", element.getElementName())) {
                    ((WorkspaceView)view).selectElementBelowCursor(elementId, isErrorElement = true);
                } else {
                    ((WorkspaceView)view).selectElementBelowCursor(elementId, isErrorElement = false);
                }
                break;
            default:
        }
    }

    protected void showElements(@Nonnull Shape element) {
        ((WorkspaceView)view).clearDiagram();

        nodeElement = element;
        String selectedElement = null;

        ((WorkspaceView)view).setZoomOutButtonEnable(nodeElement.getParent() != null);
        ((WorkspaceView)view).setAutoAlignmentParam(nodeElement.isAutoAligned());

        int defaultX = 100;
        int defaultY = 100;

        for (Shape shape : nodeElement.getShapes()) {
            int x = shape.getX();
            int y = shape.getY();

            if (x == Shape.UNDEFINED_POSITION || nodeElement.isAutoAligned()) {
                x = defaultX;
                defaultX += 100;
            }

            if (y == Shape.UNDEFINED_POSITION || nodeElement.isAutoAligned()) {
                y = defaultY;
            }

            if (shape instanceof Log) {
                ((WorkspaceView)view).addLog(x, y, (Log)shape);
            }
            if (shape instanceof Property) {
                ((WorkspaceView)view).addProperty(x, y, (Property)shape);
            }
            if (shape instanceof PayloadFactory) {
                ((WorkspaceView)view).addPayloadFactory(x, y, (PayloadFactory)shape);
            }
            if (shape instanceof Send) {
                ((WorkspaceView)view).addSend(x, y, (Send)shape);
            }
            if (shape instanceof Header) {
                ((WorkspaceView)view).addHeader(x, y, (Header)shape);
            }
            if (shape instanceof Respond) {
                ((WorkspaceView)view).addRespond(x, y, (Respond)shape);
            }
            if (shape instanceof Filter) {
                ((WorkspaceView)view).addFilter(x, y, (Filter)shape);
            }
            if (shape instanceof Switch_mediator) {
                ((WorkspaceView)view).addSwitch_mediator(x, y, (Switch_mediator)shape);
            }
            if (shape instanceof Sequence) {
                ((WorkspaceView)view).addSequence(x, y, (Sequence)shape);
            }
            if (shape instanceof Enrich) {
                ((WorkspaceView)view).addEnrich(x, y, (Enrich)shape);
            }
            if (shape instanceof LoopBack) {
                ((WorkspaceView)view).addLoopBack(x, y, (LoopBack)shape);
            }
            if (shape instanceof CallTemplate) {
                ((WorkspaceView)view).addCallTemplate(x, y, (CallTemplate)shape);
            }
            if (shape instanceof Call) {
                ((WorkspaceView)view).addCall(x, y, (Call)shape);
            }

            shape.setX(x);
            shape.setY(y);

            if (shape.getId().equals(selectedElementId)) {
                selectedElement = shape.getId();
            }

            elements.put(shape.getId(), shape);
        }

        selectElement(selectedElement);

        for (Link link : mainElement.getLinks()) {
            if (link instanceof Connection) {
                ((WorkspaceView)view).addConnection(link.getSource(), link.getTarget());
            }
        }

        notifyMainElementChangeListeners();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public State getState() {
        return state.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull State state) {
        this.state.setState(state);
    }

    public void addDiagramChangeListener(@Nonnull DiagramChangeListener listener) {
        diagramChangeListeners.add(listener);
    }

    public void removeDiagramChangeListener(@Nonnull DiagramChangeListener listener) {
        diagramChangeListeners.remove(listener);
    }

    public void notifyDiagramChangeListeners() {
        for (DiagramChangeListener listener : diagramChangeListeners) {
            listener.onChanged();
        }
    }

    public void addMainElementChangeListener(@Nonnull MainElementChangeListener listener) {
        mainElementChangeListeners.add(listener);
    }

    public void removeMainElementChangeListener(@Nonnull MainElementChangeListener listener) {
        mainElementChangeListeners.remove(listener);
    }

    public void notifyMainElementChangeListeners() {
        for (MainElementChangeListener listener : mainElementChangeListeners) {
            listener.onMainElementChanged(nodeElement);
        }
    }

    @Nonnull
    public String serialize() {
        return ContentFormatter.formatXML(ContentFormatter.trimXML(mainElement.serialize()));
    }

    @Nonnull
    public String serializeInternalFormat() {
        return ContentFormatter.trimXML(mainElement.serializeInternalFormat());
    }

    public void deserialize(@Nonnull String content) {
        mainElement.deserialize(ContentFormatter.trimXML(content));
        showElements(mainElement);
    }

    public void deserializeInternalFormat(@Nonnull String content) {
        mainElement.deserializeInternalFormat(ContentFormatter.trimXML(content));
        showElements(mainElement);
    }

    /** {@inheritDoc} */
    @Override
    public void onRightMouseButtonClicked(int x, int y) {
        selectElement(null);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {
        // TODO add implementation
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteButtonPressed() {
        deleteSelectedElement();
    }

    private void deleteSelectedElement() {
        Shape element = (Shape)elements.remove(selectedElementId);

        if (element != null) {
            nodeElement.removeShape(element);
            ((WorkspaceView)view).removeElement(selectedElementId);

            if (nodeElement.isAutoAligned()) {
                showElements(nodeElement);
            }

            notifyDiagramChangeListeners();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementRightClicked(@Nonnull String elementId, int x, int y) {
        selectElement(elementId);

        contextMenu.setPopupPosition(x, y);
        contextMenu.show();
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOutDiagramElement(@Nonnull String elementId) {
        ((WorkspaceView)view).unselectElementBelowCursor(elementId, isErrorElement);
    }

    /** {@inheritDoc} */
    @Override
    public void onZoomInButtonClicked() {
        Shape element = (Shape)elements.get(selectedElementId);

        if (element != null) {
            showElements(element);
            nodeElement = element;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onZoomOutButtonClicked() {
        Shape nodeElementParent = nodeElement.getParent();

        if (nodeElementParent != null) {
            showElements(nodeElementParent);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementMoved(@Nonnull String elementId, int x, int y) {
        Shape shape = (Shape)elements.get(elementId);
        shape.setX(x);
        shape.setY(y);

        if (nodeElement.isAutoAligned()) {
            showElements(nodeElement);
        }

        notifyDiagramChangeListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAutoAlignmentParamChanged() {
        nodeElement.setAutoAlignmentParam(((WorkspaceView)view).isAutoAligned());
        showElements(nodeElement);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        showElements(mainElement);
    }

    protected void addShape(@Nonnull Shape shape, int x, int y) {
        String shapeId = shape.getId();

        elements.put(shapeId, shape);
        nodeElement.addShape(shape);

        shape.setX(x);
        shape.setY(y);

        selectElement(shapeId);

        if (nodeElement.isAutoAligned()) {
            showElements(nodeElement);
        }

        notifyDiagramChangeListeners();
    }

    protected void selectElement(@Nullable String elementId) {
        selectedElementId = elementId;
        Shape shape = (Shape)elements.get(elementId);

        selectionManager.setElement(shape);

        ((WorkspaceView)view).selectElement(elementId);
        ((WorkspaceView)view).setZoomInButtonEnable(shape != null && shape.isContainer());
    }

    public interface DiagramChangeListener {

        void onChanged();

    }

    public interface MainElementChangeListener {

        void onMainElementChanged(@Nonnull Shape element);

    }

}