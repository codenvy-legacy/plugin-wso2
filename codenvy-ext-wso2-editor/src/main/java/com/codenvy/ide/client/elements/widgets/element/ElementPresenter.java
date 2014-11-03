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

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.widgets.element.ElementView.DEFAULT_HEIGHT;
import static com.codenvy.ide.client.elements.widgets.element.ElementView.DEFAULT_WIDTH;

/**
 * The class that provides business logic of the diagram element widget.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class ElementPresenter extends AbstractPresenter<ElementView> implements ElementView.ActionDelegate,
                                                                                SelectionManager.SelectionStateListener,
                                                                                ElementChangedListener {
    private final SelectionManager       selectionManager;
    private final ElementWidgetFactory   elementWidgetFactory;
    private final EditorState            editorState;
    private final ElementCreatorsManager elementCreatorsManager;
    private final Element                element;
    private final InnerElementsValidator innerElementsValidator;

    private final List<ElementDeleteListener>  elementDeleteListeners;
    private final List<ElementMoveListener>    elementMoveListeners;
    private final List<ElementChangedListener> elementChangedListeners;

    private final Map<String, BranchPresenter> widgetBranches;

    private BranchPresenter parent;
    private int             x;
    private int             y;
    private int             prev_x;
    private int             prev_y;

    @Inject
    public ElementPresenter(ElementWidgetFactory elementWidgetFactory,
                            SelectionManager selectionManager,
                            ElementCreatorsManager elementCreatorsManager,
                            InnerElementsValidator innerElementsValidator,
                            EditorState editorState,
                            @Assisted Element element) {
        super(elementWidgetFactory.createElementView(element));

        this.view.setVisibleTitleAndIcon(element.needsToShowIconAndTitle());

        this.selectionManager = selectionManager;
        this.elementWidgetFactory = elementWidgetFactory;
        this.editorState = editorState;
        this.element = element;
        this.elementCreatorsManager = elementCreatorsManager;
        this.innerElementsValidator = innerElementsValidator;

        this.elementMoveListeners = new ArrayList<>();
        this.elementDeleteListeners = new ArrayList<>();
        this.elementChangedListeners = new ArrayList<>();

        this.widgetBranches = new LinkedHashMap<>();

        this.selectionManager.addListener(this);

        this.view.setTitle(element.getTitle());
        this.view.setIcon(element.getIcon());

        redrawBranches();
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

        updateBranchesWidth(width);
    }

    private void updateBranchesWidth(@Nonnegative int width) {
        for (BranchPresenter branch : widgetBranches.values()) {
            branch.setWidth(width);
        }
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

        updateBranchesHeight(height);
    }

    /**
     * Set parent branch for the current element.
     *
     * @param parent
     *         parent that needs to be set
     */
    public void setParent(@Nonnull BranchPresenter parent) {
        this.parent = parent;
    }

    private void updateBranchesHeight(@Nonnegative int height) {
        Collection<BranchPresenter> branches = widgetBranches.values();

        int branchHeight = height / branches.size();

        for (BranchPresenter branch : branches) {
            branch.setHeight(branchHeight);
        }
    }

    /** @return x-position of the widget */
    @Nonnegative
    public int getX() {
        return element.getX();
    }

    /** @return y-position of the widget */
    @Nonnegative
    public int getY() {
        return element.getY();
    }

    /**
     * Changes y-position of the widget.
     *
     * @param y
     *         new y-position of the widget
     */
    public void setY(@Nonnegative int y) {
        element.setY(y);
        view.setY(y);
    }

    private void redrawBranches() {
        recreateBranches();

        detectWidgetSize();
    }

    private void recreateBranches() {
        view.removeBranches();

        chooseTitleAndShowIt();

        List<String> branchesId = showWidgetsAndReturnBranchesId();

        removeUnnecessaryWidgets(branchesId);
    }

    private void chooseTitleAndShowIt() {
        boolean isContainer = element.getBranchesAmount() > 0;

        view.setVisibleHeader(!isContainer);
        view.setVisibleTitle(isContainer);
    }

    @Nonnull
    private List<String> showWidgetsAndReturnBranchesId() {
        List<String> branchesId = new ArrayList<>();
        boolean isFirst = true;

        for (Branch branch : element.getBranches()) {
            branchesId.add(showWidgetAndReturnBranchId(branch, isFirst));

            if (isFirst) {
                isFirst = false;
            }
        }

        return branchesId;
    }

    @Nonnull
    private String showWidgetAndReturnBranchId(@Nonnull Branch branch, boolean isFirst) {
        String branchId = branch.getId();
        BranchPresenter branchPresenter = widgetBranches.get(branchId);

        if (branchPresenter == null) {
            branchPresenter = createBranchPresenter(branch);
            widgetBranches.put(branchId, branchPresenter);
        }

        if (!isFirst) {
            branchPresenter.setVisibleTopBorder(true);
        }

        view.addBranch(branchPresenter);

        return branchId;
    }

    @Nonnull
    private BranchPresenter createBranchPresenter(@Nonnull Branch branch) {
        BranchPresenter branchPresenter = elementWidgetFactory.createContainer(branch);
        branchPresenter.addElementChangedListener(this);

        return branchPresenter;
    }

    private void removeUnnecessaryWidgets(@Nonnull List<String> branchesId) {
        for (String key : widgetBranches.keySet()) {
            if (!branchesId.contains(key)) {
                widgetBranches.remove(key);
            }
        }
    }

    private void detectWidgetSize() {
        if (element.getBranches().isEmpty()) {
            view.setHeight(DEFAULT_HEIGHT);
            view.setWidth(element.needsToShowIconAndTitle() ? DEFAULT_WIDTH : 0);
        } else {
            resizeWidgetToBranchesSize();
        }
    }

    private void resizeWidgetToBranchesSize() {
        int height = 0;
        int width;

        List<BranchPresenter> branchesWidgets = new ArrayList<>(widgetBranches.values());
        int maxWidth = -1;

        for (BranchPresenter branch : branchesWidgets) {
            branch.resizeView();

            height += branch.getHeight();

            int branchWidth = branch.getWidth();

            if (branchWidth > maxWidth) {
                maxWidth = branchWidth;
            }
        }

        for (BranchPresenter branch : branchesWidgets) {
            branch.setWidth(maxWidth);
        }

        width = maxWidth + (element.needsToShowIconAndTitle() ? DEFAULT_WIDTH : 0);

        view.setHeight(height);
        view.setWidth(width);
    }

    /** {@inheritDoc} */
    @Override
    public void onStateChanged(@Nullable Element element) {
        view.unselectBelowCursor();
        resetCursors();

        if (this.element.equals(element)) {
            view.select();
        } else {
            view.unselect();
        }
    }

    private void resetCursors() {
        for (BranchPresenter branch : widgetBranches.values()) {
            branch.resetToDefaultState();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseLeftButtonClicked() {
        editorState.resetState();
        selectionManager.setElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseRightButtonClicked(@Nonnegative int x, @Nonnegative int y) {
        onMouseLeftButtonClicked();

        if (element.needsToShowIconAndTitle()) {
            view.showContextMenu(x, y);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOver() {
        String creatingElementName = elementCreatorsManager.getElementNameByState(editorState.getState());

        if (creatingElementName == null) {
            return;
        }

        view.selectBelowCursor(!innerElementsValidator.canInsertElement(element.getElementName(), creatingElementName));
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOut() {
        view.unselectBelowCursor();
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteActionClicked() {
        view.hideContextMenu();

        notifyElementDeleteListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onChangeNumberBranchesActionClicked() {
        view.hideContextMenu();

        // TODO need to change it. It is like prototype
        String amount = Window.prompt("input amount of branches", String.valueOf(element.getBranchesAmount()));

        if (amount.isEmpty()) {
            return;
        }

        element.setBranchesAmount(Integer.valueOf(amount));

        onElementChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void onElementDragged(@Nonnegative int x, @Nonnegative int y) {
        if (parent == null) {
            return;
        }

        this.prev_x = this.x;
        this.prev_y = this.y;

        this.x = x - parent.getX();
        this.y = y - parent.getY();
    }

    /** {@inheritDoc} */
    @Override
    public void onDragFinished() {
        notifyElementMoveListeners();

        prev_x = 0;
        prev_y = 0;
        x = 0;
        y = 0;
    }

    /** Unsubsribe from notifications. */
    public void unsubscribeWidget() {
        selectionManager.removeListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        updateView();

        notifyElementChangedListeners();
    }

    /** Updates the widget according to the current state of element without notifying of listeners. */
    public void updateView() {
        redrawBranches();
    }

    /**
     * Adds listener that listens to moving of the current element.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addElementMoveListener(@Nonnull ElementMoveListener listener) {
        elementMoveListeners.add(listener);
    }

    /** Notify all listeners about moving element. */
    public void notifyElementMoveListeners() {
        for (ElementMoveListener listener : elementMoveListeners) {
            listener.onElementMoved(element, prev_x, prev_y);
        }
    }

    /**
     * Adds listener that listens to deleting of the current element.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addElementDeleteListener(@Nonnull ElementDeleteListener listener) {
        elementDeleteListeners.add(listener);
    }

    /** Notify all listeners about deleting element. */
    public void notifyElementDeleteListeners() {
        for (ElementDeleteListener listener : elementDeleteListeners) {
            listener.onElementDeleted(element);
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

    /** Notify all listeners about changing element. */
    public void notifyElementChangedListeners() {
        for (ElementChangedListener listener : elementChangedListeners) {
            listener.onElementChanged();
        }
    }

    public interface ElementMoveListener {
        void onElementMoved(@Nonnull Element element, int x, int y);
    }

    public interface ElementDeleteListener {
        void onElementDeleted(@Nonnull Element element);
    }

}