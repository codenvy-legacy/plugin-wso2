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
package com.codenvy.ide.client.elements.shape.branch;

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.HasState;
import com.codenvy.ide.client.MetaModelValidator;
import com.codenvy.ide.client.SelectionManager;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Shape;
import com.codenvy.ide.client.elements.Switch;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.client.elements.shape.ElementChangedListener;
import com.codenvy.ide.client.elements.shape.ShapePresenter;
import com.codenvy.ide.client.inject.EditorFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.State.CREATING_NOTHING;
import static com.codenvy.ide.client.elements.shape.branch.BranchView.ARROW_PADDING;
import static com.codenvy.ide.client.elements.shape.branch.BranchView.DEFAULT_HEIGHT;
import static com.codenvy.ide.client.elements.shape.branch.BranchView.DEFAULT_WIDTH;
import static com.codenvy.ide.client.elements.shape.branch.BranchView.ELEMENTS_PADDING;
import static com.codenvy.ide.client.elements.shape.branch.BranchView.TITLE_HEIGHT;

/**
 * The class that provides business logic of the element's branch widget.
 *
 * @author Andrey Plotnikov
 */
public class BranchPresenter extends AbstractPresenter<BranchView> implements BranchView.ActionDelegate,
                                                                              HasState<State>,
                                                                              ShapePresenter.ElementDeleteListener,
                                                                              ShapePresenter.ElementMoveListener,
                                                                              ElementChangedListener {

    private final MetaModelValidator metaModelValidator;
    private final SelectionManager   selectionManager;
    private final EditorFactory      editorFactory;
    private final EditorState<State> editorState;

    private final Provider<Log>            logProvider;
    private final Provider<Enrich>         enrichProvider;
    private final Provider<Filter>         filterProvider;
    private final Provider<Header>         headerProvider;
    private final Provider<Call>           callProvider;
    private final Provider<CallTemplate>   callTemplateProvider;
    private final Provider<LoopBack>       loopBackProvider;
    private final Provider<PayloadFactory> payloadFactoryProvider;
    private final Provider<Property>       propertyProvider;
    private final Provider<Respond>        respondProvider;
    private final Provider<Send>           sendProvider;
    private final Provider<Sequence>       sequenceProvider;
    private final Provider<Switch>         switchMediatorProvider;
    private final Branch                   branch;

    private final List<ElementChangedListener> elementChangedListeners;
    private final List<ShapePresenter>         elements;

    @Inject
    public BranchPresenter(BranchView view,
                           MetaModelValidator metaModelValidator,
                           EditorFactory editorFactory,
                           Provider<Log> logProvider,
                           Provider<Enrich> enrichProvider,
                           Provider<Filter> filterProvider,
                           Provider<Header> headerProvider,
                           Provider<Call> callProvider,
                           Provider<CallTemplate> callTemplateProvider,
                           Provider<LoopBack> loopBackProvider,
                           Provider<PayloadFactory> payloadFactoryProvider,
                           Provider<Property> propertyProvider,
                           Provider<Respond> respondProvider,
                           Provider<Send> sendProvider,
                           Provider<Sequence> sequenceProvider,
                           Provider<Switch> switchMediatorProvider,
                           @Assisted EditorState<State> editorState,
                           @Assisted SelectionManager selectionManager,
                           @Assisted Branch branch) {
        super(view);

        this.metaModelValidator = metaModelValidator;
        this.editorFactory = editorFactory;
        this.editorState = editorState;
        this.selectionManager = selectionManager;

        this.logProvider = logProvider;
        this.enrichProvider = enrichProvider;
        this.filterProvider = filterProvider;
        this.headerProvider = headerProvider;
        this.callProvider = callProvider;
        this.callTemplateProvider = callTemplateProvider;
        this.loopBackProvider = loopBackProvider;
        this.payloadFactoryProvider = payloadFactoryProvider;
        this.propertyProvider = propertyProvider;
        this.respondProvider = respondProvider;
        this.sendProvider = sendProvider;
        this.sequenceProvider = sequenceProvider;
        this.switchMediatorProvider = switchMediatorProvider;

        this.elementChangedListeners = new ArrayList<>();
        this.elements = new ArrayList<>();

        this.branch = branch;

        this.view.setTitle(branch.getTitle());

        redrawElements();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public State getState() {
        return editorState.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull State state) {
        editorState.setState(state);
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public BranchView getView() {
        return view;
    }

    /** @return the width of the widget */
    @Nonnegative
    public int getWidth() {
        return view.getWidth();
    }

    /**
     * Changes the width of the widget.
     *
     * @param width
     *         new width of the widget
     */
    public void setWidth(@Nonnegative int width) {
        view.setWidth(width);
    }

    /** @return the height of the widget */
    @Nonnegative
    public int getHeight() {
        return view.getHeight();
    }

    /**
     * Changes the height of the widget.
     *
     * @param height
     *         new height of the widget
     */
    public void setHeight(@Nonnegative int height) {
        view.setHeight(height);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseLeftButtonClicked(int x, int y) {
        Shape newElement = getCreatingElement();

        selectionManager.setElement(newElement);
        setState(CREATING_NOTHING);
        view.setDefaultCursor();

        if (newElement == null || !metaModelValidator.canInsertElement(branch, newElement.getElementName(), x, y)) {
            return;
        }

        newElement.setX(x);
        newElement.setY(y);

        branch.addShape(newElement);

        onElementChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {
        String elementName = getCreatingElementName();

        if (elementName == null) {
            return;
        }

        if (metaModelValidator.canInsertElement(branch, elementName, x, y)) {
            view.setApplyCursor();
        } else {
            view.setErrorCursor();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOut() {
        view.setDefaultCursor();
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteButtonPressed() {
        Shape shape = selectionManager.getElement();

        if (shape == null) {
            return;
        }

        removeElement(shape);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementDeleted(@Nonnull Shape element) {
        removeElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementMoved(@Nonnull Shape element, int x, int y) {
        if (!metaModelValidator.canRemoveElement(branch, element.getId()) ||
            !metaModelValidator.canInsertElement(branch, element.getElementName(), x, y)) {
            return;
        }

        element.setX(x);
        element.setY(y);

        onElementChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        redrawElements();

        notifyElementChangedListeners();
    }

    /** @return element name of creating element if new element is creating or <code>null<code/> if it isn't */
    @Nullable
    private String getCreatingElementName() {
        switch (editorState.getState()) {
            case CREATING_LOG:
                return Log.ELEMENT_NAME;

            case CREATING_PROPERTY:
                return Property.ELEMENT_NAME;

            case CREATING_PAYLOADFACTORY:
                return PayloadFactory.ELEMENT_NAME;

            case CREATING_SEND:
                return Send.ELEMENT_NAME;

            case CREATING_HEADER:
                return Header.ELEMENT_NAME;

            case CREATING_RESPOND:
                return Respond.ELEMENT_NAME;

            case CREATING_FILTER:
                return Filter.ELEMENT_NAME;

            case CREATING_SWITCH_MEDIATOR:
                return Switch.ELEMENT_NAME;

            case CREATING_SEQUENCE:
                return Sequence.ELEMENT_NAME;

            case CREATING_ENRICH:
                return Enrich.ELEMENT_NAME;

            case CREATING_LOOPBACK:
                return LoopBack.ELEMENT_NAME;

            case CREATING_CALLTEMPLATE:
                return CallTemplate.ELEMENT_NAME;

            case CREATING_CALL:
                return Call.ELEMENT_NAME;

            default:
                return null;
        }
    }

    /** @return an instance of creating element if new element is creating or <code>null<code/> if it isn't */
    @Nullable
    private Shape getCreatingElement() {
        switch (getState()) {
            case CREATING_LOG:
                return logProvider.get();

            case CREATING_PROPERTY:
                return propertyProvider.get();

            case CREATING_HEADER:
                return headerProvider.get();

            case CREATING_PAYLOADFACTORY:
                return payloadFactoryProvider.get();

            case CREATING_RESPOND:
                return respondProvider.get();

            case CREATING_FILTER:
                return filterProvider.get();

            case CREATING_SWITCH_MEDIATOR:
                return switchMediatorProvider.get();

            case CREATING_SEQUENCE:
                return sequenceProvider.get();

            case CREATING_ENRICH:
                return enrichProvider.get();

            case CREATING_LOOPBACK:
                return loopBackProvider.get();

            case CREATING_CALLTEMPLATE:
                return callTemplateProvider.get();

            case CREATING_SEND:
                return sendProvider.get();

            case CREATING_CALL:
                return callProvider.get();

            default:
                return null;
        }
    }

    private void removeElement(@Nonnull Shape shape) {
        if (!metaModelValidator.canRemoveElement(branch, shape.getId())) {
            return;
        }

        branch.removeShape(shape);

        onElementChanged();
    }

    private void redrawElements() {
        recreateElements();

        detectWidgetSize();

        alignElements();
    }

    private void recreateElements() {
        for (ShapePresenter element : elements) {
            element.removeElementChangedListener(this);
            element.removeElementMoveListener(this);
            element.removeElementDeleteListener(this);
        }

        view.clear();
        elements.clear();

        int x = ARROW_PADDING;
        int y = 0;

        for (Shape shape : branch.getShapes()) {
            shape.setX(x);
            shape.setY(y);

            ShapePresenter shapePresenter = editorFactory.createShapePresenter(editorState, shape);

            shapePresenter.addElementChangedListener(this);
            shapePresenter.addElementMoveListener(this);
            shapePresenter.addElementDeleteListener(this);

            elements.add(shapePresenter);
            view.addElement(x, y, shapePresenter);

            x += ARROW_PADDING + shapePresenter.getWidth();
        }
    }

    private void detectWidgetSize() {
        int height;
        int width;

        if (branch.getShapes().isEmpty()) {
            height = DEFAULT_HEIGHT;
            width = DEFAULT_WIDTH;
        } else {
            width = 0;
            height = elements.get(0).getHeight();

            for (ShapePresenter element : elements) {
                width += element.getWidth();
                int elementHeight = element.getHeight();

                if (height < elementHeight) {
                    height = elementHeight;
                }
            }

            height += ELEMENTS_PADDING;
            width += (elements.size() + 1) * ARROW_PADDING;
        }

        height += TITLE_HEIGHT;

        view.setHeight(height);
        view.setWidth(width);
    }

    private void alignElements() {
        int top = view.getAbsoluteTop() + view.getHeight() / 2 - BranchView.TITLE_HEIGHT;

        for (ShapePresenter element : elements) {
            element.setY(top - element.getHeight() / 2);
        }
    }

    /**
     * Adds listener that listens to changing of the current element.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addElementChangedListener(@Nonnull ElementChangedListener listener) {
        elementChangedListeners.add(listener);
    }

    /**
     * Removes listener that listens to changing of the current element.
     *
     * @param listener
     *         listener that needs to be removed
     */
    public void removeElementChangedListener(@Nonnull ElementChangedListener listener) {
        elementChangedListeners.remove(listener);
    }

    /** Notify all listeners about changing element. */
    public void notifyElementChangedListeners() {
        for (ElementChangedListener listener : elementChangedListeners) {
            listener.onElementChanged();
        }
    }

}