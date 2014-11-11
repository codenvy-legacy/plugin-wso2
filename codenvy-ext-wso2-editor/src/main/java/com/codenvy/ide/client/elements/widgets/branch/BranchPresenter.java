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
import com.codenvy.ide.client.elements.widgets.branch.arrow.ArrowPresenter;
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
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.HORIZONTAL_ELEMENT_ARROW_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.TITLE_WIDTH;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.VERTICAL_ELEMENT_ARROW_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.arrow.ArrowPresenter.ARROW_HORIZONTAL_SIZE;
import static com.codenvy.ide.client.elements.widgets.branch.arrow.ArrowPresenter.ARROW_VERTICAL_SIZE;

/**
 * The class that provides business logic of the element's branch widget.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class BranchPresenter extends AbstractPresenter<BranchView> implements BranchView.ActionDelegate,
                                                                              ElementPresenter.ElementDeleteListener,
                                                                              ElementPresenter.ElementMoveListener,
                                                                              ElementChangedListener {

    private final ConnectionsValidator     connectionsValidator;
    private final SelectionManager         selectionManager;
    private final Provider<ArrowPresenter> arrowProvider;
    private final InnerElementsValidator   innerElementsValidator;
    private final ElementWidgetFactory     elementWidgetFactory;
    private final ElementCreatorsManager   elementCreatorsManager;
    private final EditorState              editorState;

    private final Branch branch;

    private final List<ElementChangedListener> elementChangedListeners;

    private final Map<String, ElementPresenter> widgetElements;
    private final List<ArrowPresenter>          arrows;

    private boolean isBorderTopVisible;
    private boolean isBorderLeftVisible;

    @Inject
    public BranchPresenter(ConnectionsValidator connectionsValidator,
                           InnerElementsValidator innerElementsValidator,
                           ElementWidgetFactory elementWidgetFactory,
                           ElementCreatorsManager elementCreatorsManager,
                           EditorState editorState,
                           SelectionManager selectionManager,
                           Provider<ArrowPresenter> arrowProvider,
                           @Assisted Branch branch) {
        super(elementWidgetFactory.createContainerView(branch));

        this.connectionsValidator = connectionsValidator;
        this.innerElementsValidator = innerElementsValidator;
        this.elementWidgetFactory = elementWidgetFactory;
        this.elementCreatorsManager = elementCreatorsManager;
        this.editorState = editorState;
        this.selectionManager = selectionManager;
        this.arrowProvider = arrowProvider;

        this.elementChangedListeners = new ArrayList<>();
        this.widgetElements = new LinkedHashMap<>();
        this.arrows = new ArrayList<>();

        this.branch = branch;

        this.view.setTitle(branch.getTitle());

        this.isBorderTopVisible = false;
        this.isBorderLeftVisible = false;

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
        return view.getWidth() + (isBorderLeftVisible ? BORDER_SIZE : 0);
    }

    /**
     * Changes the width of the widget.
     *
     * @param width
     *         new width of the widget
     */
    public void setWidth(@Nonnegative int width) {
        view.setWidth(width);

        alignElements();
    }

    /** @return the height of the widget */
    @Nonnegative
    public int getHeight() {
        return view.getHeight() + (isBorderTopVisible ? BORDER_SIZE : 0);
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

    /** @return x-position of the widget */
    @Nonnegative
    public int getX() {
        return view.getAbsoluteLeft();
    }

    /** @return y-position of the widget */
    @Nonnegative
    public int getY() {
        return view.getAbsoluteTop();
    }

    /**
     * Change visible state of border at the top of element.
     *
     * @param visible
     *         visible state of border
     */
    public void setVisibleTopBorder(boolean visible) {
        view.setVisibleTopBorder(visible);
        isBorderTopVisible = visible;
    }

    /**
     * Change visible state of border at the left of element.
     *
     * @param visible
     *         visible state of border
     */
    public void setVisibleLeftBorder(boolean visible) {
        view.setVisibleLeftBorder(visible);
        isBorderLeftVisible = visible;
    }

    private boolean needsToShowArrows() {
        Element parent = branch.getParent();
        return parent.isRoot() || branch.getElements().size() > 1;
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseLeftButtonClicked(@Nonnegative int x, @Nonnegative int y) {
        Element newElement = getCreatingElement();
        Element branchParent = branch.getParent();

        editorState.resetState();
        view.setDefaultCursor();

        if (newElement == null
            || !connectionsValidator.canInsertElement(branch, newElement.getElementName(), x, y)
            || !innerElementsValidator.canInsertElement(branchParent.getElementName(), newElement.getElementName())) {

            selectionManager.setElement(branchParent);
            return;
        }

        newElement.setX(x);
        newElement.setY(y);
        newElement.setParent(branchParent);
        newElement.setHorizontalOrientation(branchParent.isHorizontalOrientation());

        branch.addElement(newElement);
        branch.sortElements();

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
    public void onMouseMoved(@Nonnegative int x, @Nonnegative int y) {
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

            branch.sortElements();

            onElementChanged();
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

    public void redrawElements() {
        displayElements();

        resizeView();

        alignElements();
    }

    private void displayElements() {
        view.clear();

        showTitleOrNot();

        boolean isFirst = true;
        boolean needsToShowArrows = needsToShowArrows();
        boolean isHorizontal = branch.getParent().isHorizontalOrientation();

        int x = isHorizontal ? ARROW_PADDING : 0;
        int y = isHorizontal ? 0 : ARROW_PADDING;
        int arrowIndex = 0;

        for (Element element : branch.getElements()) {
            if (needsToShowArrows && isFirst) {
                addArrow(x, y, arrowIndex++);
                if (isHorizontal) {
                    x += ARROW_HORIZONTAL_SIZE;
                } else {
                    y += ARROW_VERTICAL_SIZE;
                }

                isFirst = false;
            }

            element.setX(x);
            element.setY(y);

            String elementId = element.getId();
            ElementPresenter elementPresenter = widgetElements.get(elementId);

            if (elementPresenter == null) {
                elementPresenter = createElementPresenter(element);
                widgetElements.put(elementId, elementPresenter);
            } else {
                elementPresenter.updateView();
            }

            elementPresenter.setHorizontalHeaderPanelOrientation(isHorizontal);

            view.addElement(elementPresenter, x, y);

            if (isHorizontal) {
                x += elementPresenter.getWidth() + HORIZONTAL_ELEMENT_ARROW_PADDING;
            } else {
                y += elementPresenter.getHeight() + VERTICAL_ELEMENT_ARROW_PADDING;
            }

            if (needsToShowArrows) {
                addArrow(x, y, arrowIndex++);

                if (isHorizontal) {
                    x += ARROW_HORIZONTAL_SIZE;
                } else {
                    y += ARROW_VERTICAL_SIZE;
                }
            }
        }
    }

    private void addArrow(@Nonnegative int x, @Nonnegative int y, @Nonnegative int index) {
        ArrowPresenter arrow;

        if (index + 1 > arrows.size()) {
            arrow = arrowProvider.get();
            arrows.add(arrow);
        } else {
            arrow = arrows.get(index);
        }

        arrow.setX(x);
        arrow.setY(y);

        rotateArrow(arrow);

        view.addArrow(arrow, x, y);
    }

    private void showTitleOrNot() {
        Element parent = branch.getParent();
        view.setVisibleTitle(!parent.isRoot());
    }

    @Nonnull
    private ElementPresenter createElementPresenter(@Nonnull Element element) {
        ElementPresenter elementPresenter = elementWidgetFactory.createElementPresenter(element);

        elementPresenter.setParent(this);

        elementPresenter.addElementChangedListener(this);
        elementPresenter.addElementMoveListener(this);
        elementPresenter.addElementDeleteListener(this);

        return elementPresenter;
    }

    /** Sets alignment for arrows if vertical orientation of the diagram is activated */
    public void applyVerticalAlign() {
        view.applyVerticalAlign();
    }

    /** Sets alignment for arrows if horizontal orientation of the diagram is activated */
    public void applyHorizontalAlign() {
        view.applyHorizontalAlign();
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

        if (parent.isRoot()) {
            return 0;
        }

        return TITLE_WIDTH;
    }

    private void detectElementSizeAndResizeView() {
        List<ElementPresenter> elements = new ArrayList<>(widgetElements.values());
        boolean isHorizontal = branch.getParent().isHorizontalOrientation();
        boolean isShowArrow = needsToShowArrows();

        int widthElementAndArrow = (elements.size() + 1) * ARROW_HORIZONTAL_SIZE;
        int widthTitleWithArrowPadding = getTitleWidth() + 2 * ARROW_PADDING;
        int firstElementWidth = elements.get(0).getWidth();

        int width = isHorizontal ? (isShowArrow ? widthElementAndArrow : 0) + widthTitleWithArrowPadding : firstElementWidth;
        int height = isHorizontal ? firstElementWidth : (isShowArrow ? widthElementAndArrow : 0) + widthTitleWithArrowPadding;

        for (ElementPresenter element : elements) {
            if (isHorizontal) {
                width += element.getWidth() + HORIZONTAL_ELEMENT_ARROW_PADDING;

                int elementHeight = element.getHeight();
                if (height < elementHeight) {
                    height = elementHeight;
                }
            } else {
                height += element.getHeight() + VERTICAL_ELEMENT_ARROW_PADDING;

                int elementWidth = element.getWidth();
                if (width < elementWidth) {
                    width = elementWidth;
                }
            }
        }

        if (isHorizontal) {
            height += ELEMENTS_PADDING;
        } else {
            width += ELEMENTS_PADDING;
        }

        view.setHeight(height);
        view.setWidth(width);
    }

    private void alignElements() {
        int top = view.getHeight() / 2;
        int left = view.getWidth() / 2;

        for (ElementPresenter element : widgetElements.values()) {
            if (branch.getParent().isHorizontalOrientation()) {
                element.setY(top - element.getHeight() / 2);
            } else {
                element.setX(left - element.getWidth() / 2);
            }
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

    /**
     * Changes visible state of horizontal title panel of the view.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public void setVisibleHorizontalTitlePanel(boolean visible) {
        view.setVisibleHorizontalTitlePanel(visible);
    }

    private void rotateArrow(@Nonnull ArrowPresenter arrow) {
        if (branch.getParent().isHorizontalOrientation()) {
            arrow.applyVerticalAlign();
        } else {
            arrow.applyHorizontalAlign();
        }
    }

}