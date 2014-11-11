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
package com.codenvy.ide.client.elements.widgets;


import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementView;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.gwt.resources.client.ImageResource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.widgets.element.ElementPresenter.ElementDeleteListener;
import static com.codenvy.ide.client.elements.widgets.element.ElementPresenter.ElementMoveListener;
import static com.codenvy.ide.client.elements.widgets.element.ElementView.DEFAULT_SIZE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementPresenterTest {

    private static final String  TITLE                        = "title";
    private static final String  ELEMENT_NAME                 = "element name";
    private static final String  BRANCH1_ID                   = "branch1 id";
    private static final String  BRANCH2_ID                   = "branch2 id";
    private static final boolean NEEDS_TO_SHOW_ICON_AND_TITLE = true;
    private static final int     BRANCH_HEIGHT                = 100;
    private static final int     BRANCH_WIDTH                 = 100;
    private static final int     MAX_BRANCH_WIDTH             = 200;
    private static final int     X_POSITION                   = 100;
    private static final int     Y_POSITION                   = 100;

    @Mock
    private ImageResource          icon;
    @Mock
    private Branch                 branch1;
    @Mock
    private Branch                 branch2;
    @Mock
    private BranchPresenter        branchPresenter;
    @Mock
    private BranchPresenter        branchPresenter2;
    @Mock
    private ElementMoveListener    movingElementListener;
    @Mock
    private ElementDeleteListener  deletingElementListener;
    @Mock
    private ElementChangedListener changingElementListener;

    @Mock
    private ElementWidgetFactory   elementWidgetFactory;
    @Mock
    private ElementView            view;
    @Mock
    private SelectionManager       selectionManager;
    @Mock
    private ElementCreatorsManager elementCreatorsManager;
    @Mock
    private InnerElementsValidator innerElementsValidator;
    @Mock
    private EditorState            editorState;
    @Mock
    private Element                element;

    private ElementPresenter presenter;

    private void createPresenter() {
        presenter = new ElementPresenter(elementWidgetFactory,
                                         selectionManager,
                                         elementCreatorsManager,
                                         innerElementsValidator,
                                         editorState,
                                         element);
    }

    private void prepareStartView() {
        prepareGeneralElements();

        createPresenter();
    }

    private void prepareGeneralElements() {
        prepareComponents(NEEDS_TO_SHOW_ICON_AND_TITLE, Arrays.asList(branch1, branch2));

        prepareBranchIds();

        prepareBranchWidgetSize(branchPresenter, BRANCH_HEIGHT, BRANCH_WIDTH);
        prepareBranchWidgetSize(branchPresenter2, BRANCH_HEIGHT, BRANCH_WIDTH);

        prepareElementWidgetFactory();
    }

    private void prepareDefaultUseCaseForHorizontalOrientation() {
        when(element.isHorizontalOrientation()).thenReturn(true);
        prepareStartView();

        resetComponentsUseInPrepareCase();

        prepareGeneralElements();

        when(element.isHorizontalOrientation()).thenReturn(true);

        presenter.addElementChangedListener(changingElementListener);
    }

    private void prepareDefaultUseCaseForVerticalOrientation() {
        when(element.isHorizontalOrientation()).thenReturn(false);
        prepareStartView();

        resetComponentsUseInPrepareCase();

        prepareGeneralElements();

        when(element.isHorizontalOrientation()).thenReturn(false);

        presenter.addElementChangedListener(changingElementListener);
    }

    private void resetComponentsUseInPrepareCase() {
        reset(view);
        reset(elementWidgetFactory);
        reset(branchPresenter);
        reset(branchPresenter2);
        reset(element);
        reset(branch1);
        reset(branch2);
        reset(selectionManager);
    }

    private void prepareElementWidgetFactory() {
        when(elementWidgetFactory.createContainer(branch1)).thenReturn(branchPresenter);
        when(elementWidgetFactory.createContainer(branch2)).thenReturn(branchPresenter2);
    }

    private void prepareComponents(boolean needsToShowIconAndTitle, List<Branch> branches) {
        when(element.getTitle()).thenReturn(TITLE);
        when(element.getElementName()).thenReturn(ELEMENT_NAME);
        when(element.isRoot()).thenReturn(!needsToShowIconAndTitle);
        when(element.getIcon()).thenReturn(icon);

        when(element.getBranches()).thenReturn(branches);
        when(element.getBranchesAmount()).thenReturn(branches.size());

        when(elementWidgetFactory.createElementView(any(Element.class))).thenReturn(view);
    }

    private void prepareBranchIds() {
        when(branch1.getId()).thenReturn(BRANCH1_ID);
        when(branch2.getId()).thenReturn(BRANCH2_ID);
    }

    private void prepareBranchWidgetSize(BranchPresenter branchPresenter, int height, int width) {
        when(branchPresenter.getHeight()).thenReturn(height);
        when(branchPresenter.getWidth()).thenReturn(width);
    }

    private void verifyConstructorAction(boolean needsToShowTitleAndIcon) {
        verify(view).setDelegate(presenter);
        verify(view).setTitle(TITLE);
        verify(view).setIcon(icon);
        verify(view).setVisibleTitleAndIcon(needsToShowTitleAndIcon);

        verify(selectionManager).addListener(presenter);

        verify(element).getTitle();
        verify(element).getIcon();
    }

    private void branchPresenterShouldBeCreatedAndShown(BranchPresenter branchPresenter, int width) {
        verify(branchPresenter).addElementChangedListener(presenter);
        verify(branchPresenter).resizeView();
        verify(branchPresenter).setWidth(width);
    }

    private void branchPresenterShouldBeNotCreated(BranchPresenter branchPresenter) {
        verify(branchPresenter, never()).addElementChangedListener(presenter);
        verify(branchPresenter, never()).resizeView();
        verify(branchPresenter, never()).setWidth(anyInt());
    }

    private void elementWidgetsShouldBeCreated() {
        verify(elementWidgetFactory).createContainer(branch1);
        verify(elementWidgetFactory).createContainer(branch2);
    }

    private void branchesShouldBeAddedOnViewIfOrientationIsHorizontal() {
        verify(view).addBranch(branchPresenter);
        verify(view).addBranch(branchPresenter2);

        verify(branchPresenter, never()).setVisibleTopBorder(anyBoolean());
        verify(branchPresenter2).setVisibleTopBorder(true);
    }

    private void viewSizeShouldBeChanged(int height, int width) {
        verify(view).setHeight(height);
        verify(view).setWidth(width);
    }

    private void verifyShowTitle(boolean visible) {
        verify(view).setVisibleHeader(!visible);
        verify(view).setVisibleTitle(visible);
    }

    @Test
    public void viewShouldBePreparedIfOrientationIsHorizontal() throws Exception {
        when(element.isHorizontalOrientation()).thenReturn(true);
        prepareStartView();

        verifyConstructorAction(NEEDS_TO_SHOW_ICON_AND_TITLE);

        verify(view).removeBranches();
        verify(element, times(5)).isRoot();

        verifyShowTitle(true);

        elementWidgetsShouldBeCreated();

        branchPresenterShouldBeCreatedAndShown(branchPresenter, BRANCH_WIDTH);
        branchPresenterShouldBeCreatedAndShown(branchPresenter2, BRANCH_WIDTH);

        branchesShouldBeAddedOnViewIfOrientationIsHorizontal();

        viewSizeShouldBeChanged(2 * BRANCH_HEIGHT, BRANCH_WIDTH + DEFAULT_SIZE);
    }

    @Test
    public void viewShouldBePreparedWhenDoesNotHaveBranchesAndNeedToShowTitle() throws Exception {
        prepareComponents(NEEDS_TO_SHOW_ICON_AND_TITLE, Collections.<Branch>emptyList());

        createPresenter();

        verifyConstructorAction(NEEDS_TO_SHOW_ICON_AND_TITLE);

        verify(view).removeBranches();
        verify(element, times(2)).isRoot();

        verifyShowTitle(false);

        verify(elementWidgetFactory, never()).createContainer(any(Branch.class));

        branchPresenterShouldBeNotCreated(branchPresenter);

        verify(view, never()).addBranch(any(BranchPresenter.class));

        viewSizeShouldBeChanged(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    @Test
    public void viewShouldBePreparedWhenDoesNotHaveBranchesAndNotNeedToShowTitle() throws Exception {
        prepareComponents(false, Collections.<Branch>emptyList());

        createPresenter();

        verifyConstructorAction(false);

        verify(view).removeBranches();
        verify(element, times(2)).isRoot();

        verifyShowTitle(false);

        verify(elementWidgetFactory, never()).createContainer(any(Branch.class));

        branchPresenterShouldBeNotCreated(branchPresenter);

        verify(view, never()).addBranch(any(BranchPresenter.class));

        viewSizeShouldBeChanged(DEFAULT_SIZE, 0);
    }

    @Test
    public void viewShouldBePreparedWhenHasBranchesAndNotNeedToShowTitle() throws Exception {
        when(element.isHorizontalOrientation()).thenReturn(true);
        prepareComponents(false, Arrays.asList(branch1, branch2));
        prepareBranchIds();

        prepareBranchWidgetSize(branchPresenter, BRANCH_HEIGHT, BRANCH_WIDTH);
        prepareBranchWidgetSize(branchPresenter2, BRANCH_HEIGHT, BRANCH_WIDTH);

        prepareElementWidgetFactory();

        createPresenter();

        verifyConstructorAction(false);

        verify(view).removeBranches();

        verifyShowTitle(true);

        elementWidgetsShouldBeCreated();

        verify(element, times(5)).isRoot();

        verify(branchPresenter).addElementChangedListener(presenter);
        verify(branchPresenter2).addElementChangedListener(presenter);

        branchesShouldBeAddedOnViewIfOrientationIsHorizontal();

        viewSizeShouldBeChanged(2 * BRANCH_HEIGHT, BRANCH_WIDTH);
    }

    @Test
    public void viewShouldBePreparedWhenFirstBranchIsTheBiggestOne() throws Exception {
        prepareComponents(false, Arrays.asList(branch1, branch2));

        when(element.isHorizontalOrientation()).thenReturn(true);
        prepareBranchIds();

        prepareBranchWidgetSize(branchPresenter, BRANCH_HEIGHT, MAX_BRANCH_WIDTH);
        prepareBranchWidgetSize(branchPresenter2, BRANCH_HEIGHT, BRANCH_WIDTH);

        prepareElementWidgetFactory();

        createPresenter();

        verifyConstructorAction(false);

        verify(view).removeBranches();
        verify(element, times(5)).isRoot();

        verifyShowTitle(true);

        elementWidgetsShouldBeCreated();

        verify(branchPresenter).addElementChangedListener(presenter);
        verify(branchPresenter2).addElementChangedListener(presenter);

        branchesShouldBeAddedOnViewIfOrientationIsHorizontal();

        viewSizeShouldBeChanged(2 * BRANCH_HEIGHT, MAX_BRANCH_WIDTH);
    }

    @Test
    public void viewShouldBePreparedWhenSecondBranchIsTheBiggestOne() throws Exception {
        prepareComponents(NEEDS_TO_SHOW_ICON_AND_TITLE, Arrays.asList(branch1, branch2));

        when(element.isHorizontalOrientation()).thenReturn(true);
        prepareBranchIds();

        prepareBranchWidgetSize(branchPresenter, BRANCH_HEIGHT, BRANCH_WIDTH);
        prepareBranchWidgetSize(branchPresenter2, BRANCH_HEIGHT, MAX_BRANCH_WIDTH);

        prepareElementWidgetFactory();

        createPresenter();

        verifyConstructorAction(NEEDS_TO_SHOW_ICON_AND_TITLE);

        verify(view).removeBranches();
        verify(element, times(5)).isRoot();

        verifyShowTitle(true);

        elementWidgetsShouldBeCreated();

        branchPresenterShouldBeCreatedAndShown(branchPresenter, MAX_BRANCH_WIDTH);
        branchPresenterShouldBeCreatedAndShown(branchPresenter2, MAX_BRANCH_WIDTH);

        branchesShouldBeAddedOnViewIfOrientationIsHorizontal();

        viewSizeShouldBeChanged(2 * BRANCH_HEIGHT, MAX_BRANCH_WIDTH + DEFAULT_SIZE);
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        assertEquals(view, presenter.getView());
    }

    @Test
    public void viewHeightShouldBeReturned() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(view.getHeight()).thenReturn(BRANCH_HEIGHT);

        assertEquals(BRANCH_HEIGHT, presenter.getHeight());

        verify(view).getHeight();
    }

    @Test
    public void heightShouldBeChanged() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.setHeight(BRANCH_HEIGHT);

        verify(view).setHeight(BRANCH_HEIGHT);

        int branchHeight = BRANCH_HEIGHT / 2;
        verify(branchPresenter).setHeight(branchHeight);
        verify(branchPresenter2).setHeight(branchHeight);
    }

    @Test
    public void viewWidthShouldBeReturned() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(view.getWidth()).thenReturn(BRANCH_WIDTH);

        assertEquals(BRANCH_WIDTH, presenter.getWidth());

        verify(view).getWidth();
    }

    @Test
    public void widthShouldBeChangedIfOrientationIsHorizontal() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.setWidth(BRANCH_WIDTH);

        verify(view).setWidth(BRANCH_WIDTH);

        verify(branchPresenter).setWidth(BRANCH_WIDTH);
        verify(branchPresenter2).setWidth(BRANCH_WIDTH);
    }

    @Test
    public void widthShouldBeChangedIfOrientationIsVertical() throws Exception {
        prepareDefaultUseCaseForVerticalOrientation();

        presenter.setWidth(BRANCH_WIDTH);

        verify(view).setWidth(BRANCH_WIDTH);

        int branchWidth = BRANCH_WIDTH / 2;
        verify(branchPresenter).setWidth(branchWidth);
        verify(branchPresenter2).setWidth(branchWidth);
    }

    @Test
    public void xPositionShouldBeReturned() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(element.getX()).thenReturn(X_POSITION);

        assertEquals(X_POSITION, presenter.getX());

        verify(element).getX();
    }

    @Test
    public void yPositionShouldBeReturned() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(element.getY()).thenReturn(Y_POSITION);

        assertEquals(Y_POSITION, presenter.getY());

        verify(element).getY();
    }

    @Test
    public void yPositionShouldBeChanged() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.setY(Y_POSITION);

        verify(element).setY(Y_POSITION);
        verify(view).setY(Y_POSITION);
    }

    @Test
    public void xPositionShouldBeChanged() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.setX(X_POSITION);

        verify(element).setX(X_POSITION);
        verify(view).setX(X_POSITION);
    }

    @Test
    public void currentElementShouldBeSelected() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onStateChanged(element);

        verify(view).unSelectBelowCursor();

        verify(branchPresenter).resetToDefaultState();
        verify(branchPresenter2).resetToDefaultState();

        verify(view).select();
        verify(view, never()).unselect();
    }

    @Test
    public void currentElementShouldBeNotSelected() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onStateChanged(mock(Element.class));

        verify(view).unSelectBelowCursor();

        verify(branchPresenter).resetToDefaultState();
        verify(branchPresenter2).resetToDefaultState();

        verify(view, never()).select();
        verify(view).unselect();
    }

    @Test
    public void elementShouldBeSelected() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onMouseLeftButtonClicked();

        verify(editorState).resetState();
        verify(selectionManager).setElement(element);
    }

    @Test
    public void orientationOfHeaderPanelShouldBeChanged() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.setHorizontalHeaderPanelOrientation(anyBoolean());

        verify(view).setHorizontalHeaderPanelOrientation(anyBoolean());
    }

    @Test
    public void contextMenuShouldBeShown() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(element.isRoot()).thenReturn(false);

        presenter.onMouseRightButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(selectionManager).setElement(element);

        verify(view).showContextMenu(X_POSITION, Y_POSITION);
    }

    @Test
    public void contextMenuShouldBeNotShown() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(element.isRoot()).thenReturn(true);

        presenter.onMouseRightButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(selectionManager).setElement(element);

        verify(view, never()).showContextMenu(X_POSITION, Y_POSITION);
    }

    @Test
    public void movingElementListenerShouldBeNotifiedWhenElementIsMovedAndParentIsEmpty() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.addElementMoveListener(movingElementListener);

        presenter.onElementDragged(X_POSITION, Y_POSITION);
        presenter.onElementDragged(X_POSITION, Y_POSITION);
        presenter.onElementDragged(X_POSITION, Y_POSITION);

        presenter.onDragFinished();

        verify(movingElementListener).onElementMoved(element, 0, 0);
    }

    @Test
    public void movingElementListenerShouldBeNotifiedWhenElementIsMoved() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        BranchPresenter parent = mock(BranchPresenter.class);

        int value = 50;
        when(parent.getX()).thenReturn(value);
        when(parent.getY()).thenReturn(value);

        presenter.addElementMoveListener(movingElementListener);
        presenter.setParent(parent);

        presenter.onElementDragged(X_POSITION, Y_POSITION);
        presenter.onElementDragged(X_POSITION, Y_POSITION);
        presenter.onElementDragged(X_POSITION, Y_POSITION);

        presenter.onDragFinished();

        verify(movingElementListener).onElementMoved(element, value, value);
    }

    @Test
    public void elementShouldBeNotSelectedWhenMouseOverAndDoNotHaveInformationAboutThisElement() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onMouseOver();

        verify(elementCreatorsManager).getElementNameByState(anyString());
        verify(innerElementsValidator, never()).canInsertElement(anyString(), anyString());
        verify(view, never()).selectBelowCursor(anyBoolean());
    }

    @Test
    public void elementShouldBeNotSelectedWhenMouseOver() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(elementCreatorsManager.getElementNameByState(anyString())).thenReturn(ELEMENT_NAME);
        when(innerElementsValidator.canInsertElement(anyString(), anyString())).thenReturn(true);

        presenter.onMouseOver();

        verify(elementCreatorsManager).getElementNameByState(anyString());
        verify(innerElementsValidator).canInsertElement(ELEMENT_NAME, ELEMENT_NAME);
        verify(view).selectBelowCursor(false);
    }

    @Test
    public void elementShouldBeSelectedWhenMouseOver() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        when(elementCreatorsManager.getElementNameByState(anyString())).thenReturn(ELEMENT_NAME);
        when(innerElementsValidator.canInsertElement(anyString(), anyString())).thenReturn(false);

        presenter.onMouseOver();

        verify(elementCreatorsManager).getElementNameByState(anyString());
        verify(innerElementsValidator).canInsertElement(ELEMENT_NAME, ELEMENT_NAME);
        verify(view).selectBelowCursor(true);
    }

    @Test
    public void elementShouldBeUnSelected() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onMouseOut();

        verify(view).unSelectBelowCursor();
    }

    @Test
    public void deletingElementListenerShouldBeNotifiedWhenDeleteActionIsClicked() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.addElementDeleteListener(deletingElementListener);

        presenter.onDeleteActionClicked();

        verify(view).hideContextMenu();
        verify(deletingElementListener).onElementDeleted(element);
    }

    @Test
    @Ignore
    public void amountOfBranchesShouldBeChanged() throws Exception {
        // TODO needs to add this test when the dialog of changing amount of branches was changed
    }

    @Test
    public void widgetShouldBeUnSubscribedFromSelectionManager() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.unsubscribeWidget();

        verify(selectionManager).removeListener(presenter);
    }

    @Test
    public void changingElementListenerShouldBeNotifiedWhenChildElementIsChanged() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onElementChanged();

        verify(changingElementListener).onElementChanged();
    }

    @Test
    public void widgetShouldBeUpdated() throws Exception {
        prepareDefaultUseCaseForHorizontalOrientation();

        presenter.onElementChanged();

        verify(changingElementListener).onElementChanged();
    }

}