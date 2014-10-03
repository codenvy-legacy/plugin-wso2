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

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.validators.ConnectionsValidator;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.widgets.branch.BranchView.ARROW_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.BORDER_SIZE;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.DEFAULT_HEIGHT;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.DEFAULT_WIDTH;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.ELEMENTS_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.TITLE_WIDTH;

/**
 * The class that provides business logic of the element's branch widget.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class BranchPresenter extends AbstractPresenter<BranchView> implements BranchView.ActionDelegate,
                                                                              ElementPresenter.ElementDeleteListener,
                                                                              ElementPresenter.ElementMoveListener,
                                                                              ElementChangedListener {

    private final ConnectionsValidator   connectionsValidator;
    private final SelectionManager       selectionManager;
    private final InnerElementsValidator innerElementsValidator;
    private final ElementWidgetFactory   elementWidgetFactory;
    private final ElementCreatorsManager elementCreatorsManager;
    private final EditorState            editorState;

    private final Branch branch;

    private final List<ElementChangedListener> elementChangedListeners;

    private final Map<String, ElementPresenter> widgetElements;

    private boolean isBorderVisible;

    @Inject
    public BranchPresenter(BranchView view,
                           ConnectionsValidator connectionsValidator,
                           InnerElementsValidator innerElementsValidator,
                           ElementWidgetFactory elementWidgetFactory,
                           ElementCreatorsManager elementCreatorsManager,
                           EditorState editorState,
                           SelectionManager selectionManager,
                           @Assisted Branch branch) {
        super(view);

        this.connectionsValidator = connectionsValidator;
        this.innerElementsValidator = innerElementsValidator;
        this.elementWidgetFactory = elementWidgetFactory;
        this.elementCreatorsManager = elementCreatorsManager;
        this.editorState = editorState;
        this.selectionManager = selectionManager;

        this.elementChangedListeners = new ArrayList<>();
        this.widgetElements = new LinkedHashMap<>();

        this.branch = branch;

        this.view.setTitle(branch.getTitle());

        this.isBorderVisible = false;

        redrawElements();
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public IsWidget getView() {
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
        return view.getHeight() + (isBorderVisible ? BORDER_SIZE : 0);
    }

    /**
     * Changes the height of the widget.
     *
     * @param height
     *         new height of the widget
     */
    public void setHeight(@Nonnegative int height) {
        view.setHeight(height);

        alignElements();
    }

    /**
     * Change visible state of border at the top of element.
     *
     * @param visible
     *         visible state of border
     */
    public void setVisibleTopBorder(boolean visible) {
        view.setVisibleTopBorder(visible);
        isBorderVisible = visible;
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseLeftButtonClicked(int x, int y) {
        Element newElement = getCreatingElement();
        Element branchParent = branch.getParent();

        editorState.resetState();
        view.setDefaultCursor();

        if (newElement == null || !connectionsValidator.canInsertElement(branch, newElement.getElementName(), x, y)
            || (branchParent != null &&
                !innerElementsValidator.canInsertElement(branchParent.getElementName(), newElement.getElementName()))) {
            selectionManager.setElement(branchParent);
            return;
        }

        newElement.setX(x);
        newElement.setY(y);

        branch.addElement(newElement);

        onElementChanged();

        selectionManager.setElement(newElement);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseRightButtonClicked() {
        selectionManager.setElement(branch.getParent());
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {
        String elementName = elementCreatorsManager.getElementNameByState(editorState.getState());

        if (elementName == null) {
            return;
        }

        if (connectionsValidator.canInsertElement(branch, elementName, x, y)) {
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
        Element element = selectionManager.getElement();

        if (element == null) {
            return;
        }

        removeElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementDeleted(@Nonnull Element element) {
        removeElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementMoved(@Nonnull Element element, int x, int y) {
        if (connectionsValidator.canRemoveElement(branch, element.getId()) &&
            connectionsValidator.canInsertElement(branch, element.getElementName(), x, y)) {
            element.setX(x);
            element.setY(y);

            onElementChanged();
        } else {
            redrawElements();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        redrawElements();

        notifyElementChangedListeners();
    }

    /** @return an instance of creating element if new element is creating or <code>null<code/> if it isn't */
    @Nullable
    private Element getCreatingElement() {
        Provider<? extends Element> provider = elementCreatorsManager.getProviderByState(editorState.getState());
        return provider == null ? null : provider.get();
    }

    private void removeElement(@Nonnull Element element) {
        if (!connectionsValidator.canRemoveElement(branch, element.getId())) {
            return;
        }

        branch.removeElement(element);

        ElementPresenter elementPresenter = widgetElements.remove(element.getId());
        elementPresenter.unsubscribeWidget();

        selectionManager.setElement(null);

        onElementChanged();
    }

    private void redrawElements() {
        displayElements();

        resizeView();

        alignElements();
    }

    private void displayElements() {
        view.clear();

        showTitleOrNot();

        int x = ARROW_PADDING;
        int y = 0;

        for (Element element : branch.getElements()) {
            element.setX(x);
            element.setY(y);

            String elementId = element.getId();
            ElementPresenter elementPresenter = widgetElements.get(elementId);

            if (elementPresenter == null) {
                elementPresenter = createElementPresenter(element);
                widgetElements.put(elementId, elementPresenter);
            }

            view.addElement(x, y, elementPresenter);

            x += ARROW_PADDING + elementPresenter.getWidth();
        }
    }

    private void showTitleOrNot() {
        Element parent = branch.getParent();
        view.setVisibleTitle(parent != null && parent.needsToShowIconAndTitle());
    }

    @Nonnull
    private ElementPresenter createElementPresenter(@Nonnull Element element) {
        ElementPresenter elementPresenter = elementWidgetFactory.createElementPresenter(element);

        elementPresenter.addElementChangedListener(this);
        elementPresenter.addElementMoveListener(this);
        elementPresenter.addElementDeleteListener(this);

        return elementPresenter;
    }

    public void resizeView() {
        if (branch.hasElements()) {
            detectElementSizeAndResizeView();
        } else {
            view.setHeight(DEFAULT_HEIGHT);
            view.setWidth(DEFAULT_WIDTH + getTitleWidth());
        }
    }

    private int getTitleWidth() {
        Element parent = branch.getParent();

        if (parent == null || !parent.needsToShowIconAndTitle()) {
            return 0;
        }

        return TITLE_WIDTH;
    }

    private void detectElementSizeAndResizeView() {
        List<ElementPresenter> elements = new ArrayList<>(widgetElements.values());

        int width = (elements.size() + 1) * ARROW_PADDING + getTitleWidth();
        int height = elements.get(0).getHeight();

        for (ElementPresenter element : elements) {
            width += element.getWidth();

            int elementHeight = element.getHeight();
            if (height < elementHeight) {
                height = elementHeight;
            }
        }

        height += ELEMENTS_PADDING;

        view.setHeight(height);
        view.setWidth(width);
    }

    private void alignElements() {
        int top = view.getHeight() / 2;

        for (ElementPresenter element : widgetElements.values()) {
            element.setY(top - element.getHeight() / 2);
        }
    }

    /** Performs actions which change state of widget to default. */
    public void resetToDefaultState() {
        view.setDefaultCursor();
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

    /** Notify all listeners about changing element. */
    public void notifyElementChangedListeners() {
        for (ElementChangedListener listener : elementChangedListeners) {
            listener.onElementChanged();
        }
    }

}