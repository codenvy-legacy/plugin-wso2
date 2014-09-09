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
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.inject.EditorFactory;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.widgets.element.ElementView.BRANCHES_PADDING;
import static com.codenvy.ide.client.elements.widgets.element.ElementView.DEFAULT_HEIGHT;
import static com.codenvy.ide.client.elements.widgets.element.ElementView.DEFAULT_WIDTH;

/**
 * The class that provides business logic of the diagram element widget.
 *
 * @author Andrey Plotnikov
 */
public class ElementPresenter extends AbstractPresenter<ElementView> implements ElementView.ActionDelegate,
                                                                                SelectionManager.SelectionStateListener,
                                                                                ElementChangedListener {
    private final SelectionManager        selectionManager;
    private final EditorFactory           editorFactory;
    private final EditorState<State>      editorState;
    private final MediatorCreatorsManager mediatorCreatorsManager;
    private final Element                 element;
    private final InnerElementsValidator  innerElementsValidator;

    private final List<ElementDeleteListener>  elementDeleteListeners;
    private final List<ElementMoveListener>    elementMoveListeners;
    private final List<ElementChangedListener> elementChangedListeners;
    private final List<BranchPresenter>        branches;

    private int x;
    private int y;

    @Inject
    public ElementPresenter(EditorFactory editorFactory,
                            SelectionManager selectionManager,
                            MediatorCreatorsManager mediatorCreatorsManager,
                            InnerElementsValidator innerElementsValidator,
                            @Assisted EditorState<State> editorState,
                            @Assisted Element element) {
        super(editorFactory.createElementView(element.isPossibleToAddBranches()));

        this.view.setVisibleTitleAndIcon(element.needsToShowIconAndTitle());

        this.selectionManager = selectionManager;
        this.editorFactory = editorFactory;
        this.editorState = editorState;
        this.element = element;
        this.mediatorCreatorsManager = mediatorCreatorsManager;
        this.innerElementsValidator = innerElementsValidator;

        this.elementMoveListeners = new ArrayList<>();
        this.elementDeleteListeners = new ArrayList<>();
        this.elementChangedListeners = new ArrayList<>();

        this.branches = new ArrayList<>();

        this.selectionManager.addListener(this);

        view.setTitle(element.getTitle());
        view.setIcon(element.getIcon());

        redrawBranches();
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public ElementView getView() {
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
        for (BranchPresenter branch : branches) {
            branch.removeElementChangedListener(this);
        }

        branches.clear();
        view.removeBranches();

        int maxWidth = 0;

        for (Branch branch : element.getBranches()) {
            BranchPresenter branchPresenter = editorFactory.createContainer(editorState, selectionManager, branch);
            branchPresenter.addElementChangedListener(this);

            this.branches.add(branchPresenter);
            view.addBranch(branchPresenter);

            int branchWidth = branchPresenter.getWidth();

            if (branchWidth > maxWidth) {
                maxWidth = branchWidth;
            }
        }
    }

    private void detectWidgetSize() {
        int width;
        int height;
        List<Branch> branches = element.getBranches();

        if (branches.isEmpty()) {
            width = 0;
            height = DEFAULT_HEIGHT;
        } else {
            height = 0;
            int maxWidth = this.branches.get(0).getWidth();

            for (BranchPresenter branch : this.branches) {
                height += branch.getHeight();
                int branchWidth = branch.getWidth();

                if (branchWidth > maxWidth) {
                    maxWidth = branchWidth;
                }
            }

            for (BranchPresenter branch : this.branches) {
                branch.setWidth(maxWidth);
            }

            width = maxWidth + 2 * BRANCHES_PADDING;
            height += BRANCHES_PADDING * (branches.size() + 1);
        }

        width += element.needsToShowIconAndTitle() ? DEFAULT_WIDTH : 0;

        view.setHeight(height);
        view.setWidth(width);
    }

    /** {@inheritDoc} */
    @Override
    public void onStateChanged(@Nullable Element element) {
        Element selectedElement = selectionManager.getElement();

        view.unselectBelowCursor();

        if (selectedElement == null) {
            return;
        }

        if (selectedElement.equals(element)) {
            view.select();
        } else {
            view.unselect();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseLeftButtonClicked() {
        editorState.setState(State.CREATING_NOTHING);
        selectionManager.setElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseRightButtonClicked(int x, int y) {
        onMouseLeftButtonClicked();

        view.showContextMenu(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void onMoved(int x, int y) {
        this.x = x;
        this.y = y;

        notifyElementMoveListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOver() {
        String elementName = mediatorCreatorsManager.getElementNameByState(editorState.getState());

        if (elementName == null) {
            return;
        }

        view.selectBelowCursor(!innerElementsValidator.canInsertElement(element.getElementName(), elementName));
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
    public void onRemovedWidget() {
        selectionManager.removeListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        redrawBranches();

        notifyElementChangedListeners();
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

    /**
     * Removes listener that listens to moving of the current element.
     *
     * @param listener
     *         listener that needs to be removed
     */
    public void removeElementMoveListener(@Nonnull ElementMoveListener listener) {
        elementMoveListeners.remove(listener);
    }

    /** Notify all listeners about moving element. */
    public void notifyElementMoveListeners() {
        for (ElementMoveListener listener : elementMoveListeners) {
            listener.onElementMoved(element, x, y);
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

    /**
     * Removes listener that listens to deleting of the current element.
     *
     * @param listener
     *         listener that needs to be removed
     */
    public void removeElementDeleteListener(@Nonnull ElementDeleteListener listener) {
        elementDeleteListeners.remove(listener);
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

    public interface ElementMoveListener {
        void onElementMoved(@Nonnull Element element, int x, int y);
    }

    public interface ElementDeleteListener {
        void onElementDeleted(@Nonnull Element element);
    }

}