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
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.elements.widgets.branch.BranchView;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.validators.ConnectionsValidator;
import com.codenvy.ide.client.validators.InnerElementsValidator;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.widgets.branch.BranchView.ARROW_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.BORDER_SIZE;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.DEFAULT_HEIGHT;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.DEFAULT_WIDTH;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.ELEMENTS_PADDING;
import static com.codenvy.ide.client.elements.widgets.branch.BranchView.TITLE_WIDTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class BranchPresenterTest {

    private static final String TITLE              = "title";
    private static final String ELEMENT_NAME       = "element name";
    private static final String ELEMENT1_ID        = "element1 id";
    private static final String ELEMENT2_ID        = "element2 id";
    private static final int    ELEMENT_WIDTH      = 100;
    private static final int    ELEMENT_HEIGHT     = 100;
    private static final int    VIEW_HEIGHT        = 1000;
    private static final int    MAX_ELEMENT_HEIGHT = 200;
    private static final int    X_POSITION         = 100;
    private static final int    Y_POSITION         = 100;

    @Mock
    private BranchView             view;
    @Mock
    private ElementCreatorsManager elementCreatorsManager;
    @Mock
    private ConnectionsValidator   connectionsValidator;
    @Mock
    private InnerElementsValidator innerElementsValidator;
    @Mock
    private ElementWidgetFactory   elementWidgetFactory;
    @Mock
    private EditorState            editorState;
    @Mock
    private SelectionManager       selectionManager;

    @Mock
    private Branch                      branch;
    @Mock
    private Element                     element;
    @Mock
    private Element                     element2;
    @Mock
    private Provider<? extends Element> elementProvider;
    @Mock
    private ElementChangedListener      changingElementListener;

    @Mock
    private ElementPresenter elementPresenter;
    @Mock
    private ElementPresenter elementPresenter2;

    private BranchPresenter presenter;

    private void createPresenter() {
        when(elementWidgetFactory.createContainerView(any(Branch.class))).thenReturn(view);

        presenter = new BranchPresenter(connectionsValidator,
                                        innerElementsValidator,
                                        elementWidgetFactory,
                                        elementCreatorsManager,
                                        editorState,
                                        selectionManager,
                                        branch);
    }

    private void prepareStartView() {
        prepareGeneralElements();

        createPresenter();
    }

    private void prepareGeneralElements() {
        prepareBranchMock(branch, Arrays.asList(element, element2));

        prepareElementIds();

        prepareElementPresenterSize(elementPresenter, ELEMENT_HEIGHT, ELEMENT_WIDTH);
        prepareElementPresenterSize(elementPresenter2, ELEMENT_HEIGHT, ELEMENT_WIDTH);

        prepareCreateElementWidgetFactory();

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);
    }

    private void prepareElementPresenterSize(ElementPresenter elementPresenter, int height, int width) {
        when(elementPresenter.getHeight()).thenReturn(height);
        when(elementPresenter.getWidth()).thenReturn(width);
    }

    private void prepareBranchMock(Branch branch, List<Element> elements) {
        when(branch.getTitle()).thenReturn(TITLE);
        when(branch.getElements()).thenReturn(elements);
        when(branch.hasElements()).thenReturn(!elements.isEmpty());
    }

    private void prepareElementIds() {
        when(element.getId()).thenReturn(ELEMENT1_ID);
        when(element2.getId()).thenReturn(ELEMENT2_ID);
    }

    private void prepareCreateElementWidgetFactory() {
        when(elementWidgetFactory.createElementPresenter(element)).thenReturn(elementPresenter);
        when(elementWidgetFactory.createElementPresenter(element2)).thenReturn(elementPresenter2);
    }

    private void prepareDefaultUseCase() {
        prepareStartView();

        resetMocksUseInPrepareCase();

        prepareGeneralElements();

        presenter.addElementChangedListener(changingElementListener);
    }

    private AbstractElement prepareCreatorManagerAndReturnCreatedElement() {
        when(elementCreatorsManager.getProviderByState(anyString())).then(new Answer<Object>() {
            @Override
            public Provider<? extends Element> answer(InvocationOnMock invocation) throws Throwable {
                // we must use do answer for returning needed provider. in other case it is impossible.
                return elementProvider;
            }
        });

        AbstractElement element = mock(AbstractElement.class);
        when(elementProvider.get()).thenReturn(element);

        return element;
    }

    private void resetMocksUseInPrepareCase() {
        reset(branch);
        reset(element);
        reset(element2);
        reset(elementPresenter);
        reset(elementPresenter2);
        reset(elementWidgetFactory);
        reset(view);
    }

    private void verifyElementChanges() {
        verifyViewRedraw();

        verify(changingElementListener).onElementChanged();
    }

    private void verifyViewRedraw() {
        verify(view).clear();

        elementShouldBeChangedXYPosition(element, ARROW_PADDING, 0);
        elementShouldBeChangedXYPosition(element2, 2 * ARROW_PADDING + ELEMENT_WIDTH, 0);

        verify(elementWidgetFactory, never()).createElementPresenter(any(Element.class));

        listenersShouldBeNotAdded(elementPresenter);
        listenersShouldBeNotAdded(elementPresenter2);

        elementsShouldBeAddedOnView();

        viewSizeShouldBeChanged(ELEMENTS_PADDING + ELEMENT_HEIGHT, 3 * ARROW_PADDING + 2 * ELEMENT_WIDTH);

        int top = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        topPositionOfElementsShouldBeChanged(top, top);
    }

    private void elementShouldBeNotChangedXYPosition(Element element) {
        verify(element, never()).setX(anyInt());
        verify(element, never()).setY(anyInt());
    }

    private void elementShouldBeNotChangedXYPosition(Element element, int x, int y) {
        verify(element, never()).setX(x);
        verify(element, never()).setY(y);
    }

    private void elementShouldBeChangedXYPosition(Element element, int x, int y) {
        verify(element).setX(x);
        verify(element).setY(y);
    }

    private void listenersShouldBeAdded(ElementPresenter elementPresenter) {
        verify(elementPresenter).addElementChangedListener(presenter);
        verify(elementPresenter).addElementMoveListener(presenter);
        verify(elementPresenter).addElementDeleteListener(presenter);
    }

    private void listenersShouldBeNotAdded(ElementPresenter elementPresenter) {
        verify(elementPresenter, never()).addElementChangedListener(presenter);
        verify(elementPresenter, never()).addElementMoveListener(presenter);
        verify(elementPresenter, never()).addElementDeleteListener(presenter);
    }

    private void verifyConstructorExecution() {
        verify(view).setDelegate(presenter);
        verify(view).setTitle(TITLE);

        verify(branch).getTitle();
    }

    private void elementWidgetsShouldBeCreated() {
        verify(elementWidgetFactory).createElementPresenter(element);
        verify(elementWidgetFactory).createElementPresenter(element2);
    }

    private void elementsShouldBeAddedOnView() {
        verify(view).addElement(ARROW_PADDING, 0, elementPresenter);
        verify(view).addElement(2 * ARROW_PADDING + ELEMENT_WIDTH, 0, elementPresenter2);
    }

    private void viewSizeShouldBeChanged(int height, int width) {
        verify(view).setHeight(height);
        verify(view).setWidth(width);
    }

    private void topPositionOfElementsShouldBeChanged(int firstElementTop, int secondElementTop) {
        verify(elementPresenter).setY(firstElementTop);
        verify(elementPresenter2).setY(secondElementTop);
    }

    @Test
    public void viewShouldBePreparedWhenDoNotNeedToShowTitle() throws Exception {
        viewShouldBePreparedGeneralCase();

        viewSizeShouldBeChanged(ELEMENTS_PADDING + ELEMENT_HEIGHT, 3 * ARROW_PADDING + 2 * ELEMENT_WIDTH);
    }

    private void viewShouldBePreparedGeneralCase() {
        prepareStartView();

        verifyConstructorExecution();

        verify(view).clear();

        elementShouldBeChangedXYPosition(element, ARROW_PADDING, 0);
        elementShouldBeChangedXYPosition(element2, 2 * ARROW_PADDING + ELEMENT_WIDTH, 0);

        elementWidgetsShouldBeCreated();

        listenersShouldBeAdded(elementPresenter);
        listenersShouldBeAdded(elementPresenter2);

        elementsShouldBeAddedOnView();

        int top = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        topPositionOfElementsShouldBeChanged(top, top);
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        when(branch.getParent()).thenReturn(element);
        when(element.needsToShowIconAndTitle()).thenReturn(true);

        viewShouldBePreparedGeneralCase();

        viewSizeShouldBeChanged(ELEMENTS_PADDING + ELEMENT_HEIGHT, 3 * ARROW_PADDING + 2 * ELEMENT_WIDTH + TITLE_WIDTH);
    }

    @Test
    public void viewShouldBePreparedWhenBranchDoesNotHaveAnyElements() throws Exception {
        prepareBranchMock(branch, Collections.<Element>emptyList());

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);

        createPresenter();

        verifyConstructorExecution();

        verify(view).clear();

        elementShouldBeNotChangedXYPosition(element);
        elementShouldBeNotChangedXYPosition(element2);

        verify(elementWidgetFactory, never()).createElementPresenter(any(Element.class));

        listenersShouldBeNotAdded(elementPresenter);
        listenersShouldBeNotAdded(elementPresenter2);

        verify(view, never()).addElement(anyInt(), anyInt(), any(ElementPresenter.class));

        viewSizeShouldBeChanged(DEFAULT_HEIGHT, DEFAULT_WIDTH);

        verify(elementPresenter, never()).setY(anyInt());
        verify(elementPresenter2, never()).setY(anyInt());
    }

    @Test
    public void viewShouldBePreparedWhenFirstElementIsBiggest() throws Exception {
        prepareBranchMock(branch, Arrays.asList(element, element2));

        prepareElementIds();

        prepareElementPresenterSize(elementPresenter, MAX_ELEMENT_HEIGHT, ELEMENT_WIDTH);
        prepareElementPresenterSize(elementPresenter2, ELEMENT_HEIGHT, ELEMENT_WIDTH);

        prepareCreateElementWidgetFactory();

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);

        createPresenter();

        verifyConstructorExecution();

        verify(view).clear();

        elementShouldBeChangedXYPosition(element, ARROW_PADDING, 0);
        elementShouldBeChangedXYPosition(element2, 2 * ARROW_PADDING + ELEMENT_WIDTH, 0);

        elementWidgetsShouldBeCreated();

        listenersShouldBeAdded(elementPresenter);
        listenersShouldBeAdded(elementPresenter2);

        elementsShouldBeAddedOnView();

        viewSizeShouldBeChanged(ELEMENTS_PADDING + MAX_ELEMENT_HEIGHT, 3 * ARROW_PADDING + 2 * ELEMENT_WIDTH);

        int firstTop = VIEW_HEIGHT / 2 - MAX_ELEMENT_HEIGHT / 2;
        int secondTop = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        topPositionOfElementsShouldBeChanged(firstTop, secondTop);
    }

    @Test
    public void viewShouldBePreparedWhenSecondElementIsBiggest() throws Exception {
        prepareBranchMock(branch, Arrays.asList(element, element2));

        prepareElementIds();

        prepareElementPresenterSize(elementPresenter, ELEMENT_HEIGHT, ELEMENT_WIDTH);
        prepareElementPresenterSize(elementPresenter2, MAX_ELEMENT_HEIGHT, ELEMENT_WIDTH);

        prepareCreateElementWidgetFactory();

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);

        createPresenter();

        verifyConstructorExecution();

        verify(view).clear();

        elementShouldBeChangedXYPosition(element, ARROW_PADDING, 0);
        elementShouldBeChangedXYPosition(element2, 2 * ARROW_PADDING + ELEMENT_WIDTH, 0);

        elementWidgetsShouldBeCreated();

        listenersShouldBeAdded(elementPresenter);
        listenersShouldBeAdded(elementPresenter2);

        elementsShouldBeAddedOnView();

        viewSizeShouldBeChanged(ELEMENTS_PADDING + MAX_ELEMENT_HEIGHT, 3 * ARROW_PADDING + 2 * ELEMENT_WIDTH);

        int firstTop = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        int secondTop = VIEW_HEIGHT / 2 - MAX_ELEMENT_HEIGHT / 2;
        topPositionOfElementsShouldBeChanged(firstTop, secondTop);
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        prepareStartView();

        assertEquals(view, presenter.getView());
    }

    @Test
    public void viewHeightShouldBeReturned() throws Exception {
        prepareDefaultUseCase();

        when(view.getHeight()).thenReturn(ELEMENT_HEIGHT);

        assertEquals(ELEMENT_HEIGHT, presenter.getHeight());

        verify(view).getHeight();
    }

    @Test
    public void viewSizeShouldBeWithTopBorder() throws Exception {
        prepareDefaultUseCase();

        when(view.getHeight()).thenReturn(ELEMENT_HEIGHT);
        presenter.setVisibleTopBorder(true);

        assertEquals(ELEMENT_HEIGHT + BORDER_SIZE, presenter.getHeight());

        verify(view).getHeight();
    }

    @Test
    public void heightShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        presenter.setHeight(ELEMENT_HEIGHT);

        verify(view).setHeight(ELEMENT_HEIGHT);

        verify(view).getHeight();

        int top = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        verify(elementPresenter).setY(top);
        verify(elementPresenter2).setY(top);
    }

    @Test
    public void viewWidthShouldBeReturned() throws Exception {
        prepareDefaultUseCase();

        when(view.getWidth()).thenReturn(ELEMENT_WIDTH);

        assertEquals(ELEMENT_WIDTH, presenter.getWidth());

        verify(view).getWidth();
    }

    @Test
    public void widthShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        presenter.setWidth(ELEMENT_WIDTH);

        verify(view).setWidth(ELEMENT_WIDTH);
    }

    @Test
    public void newElementShouldBeNotCreatedWhenProvideIsNotFound() throws Exception {
        prepareDefaultUseCase();

        presenter.onMouseLeftButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(view).setDefaultCursor();

        verify(selectionManager).setElement(isNull(Element.class));
        verify(branch, never()).addElement(any(Element.class));
    }

    @Test
    public void newElementShouldBeNotCreatedWhenCanNotInsertElement() throws Exception {
        prepareDefaultUseCase();

        prepareCreatorManagerAndReturnCreatedElement();

        when(connectionsValidator.canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt())).thenReturn(false);

        presenter.onMouseLeftButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(view).setDefaultCursor();

        verify(selectionManager).setElement(isNull(Element.class));
        verify(branch, never()).addElement(any(Element.class));
    }

    @Test
    public void newElementShouldBeNotCreatedWhenBranchParentIsEmpty() throws Exception {
        prepareDefaultUseCase();

        when(innerElementsValidator.canInsertElement(anyString(), anyString())).thenReturn(false);

        presenter.onMouseLeftButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(view).setDefaultCursor();

        verify(selectionManager).setElement(isNull(Element.class));
        verify(branch, never()).addElement(any(Element.class));
    }

    @Test
    public void newElementShouldBeNotCreatedWhenBranchParentIsNotEmptyAndCanNotInsertElement() throws Exception {
        prepareDefaultUseCase();

        when(branch.getParent()).thenReturn(element2);
        when(innerElementsValidator.canInsertElement(anyString(), anyString())).thenReturn(false);

        presenter.onMouseLeftButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(view).setDefaultCursor();

        verify(selectionManager).setElement(element2);
        verify(branch, never()).addElement(any(Element.class));
    }

    @Test
    public void newElementShouldBeCreated() throws Exception {
        prepareDefaultUseCase();

        AbstractElement creatingElement = prepareCreatorManagerAndReturnCreatedElement();

        when(connectionsValidator.canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt())).thenReturn(true);

        presenter.onMouseLeftButtonClicked(X_POSITION, Y_POSITION);

        verify(editorState).resetState();
        verify(view).setDefaultCursor();

        verify(selectionManager, never()).setElement(isNull(Element.class));
        verify(selectionManager).setElement(creatingElement);

        elementShouldBeChangedXYPosition(creatingElement, X_POSITION, Y_POSITION);

        verify(branch).addElement(creatingElement);

        verifyElementChanges();
    }

    @Test
    public void parentElementShouldBeSelectedWhenRightMouseButtonIsClicked() throws Exception {
        prepareDefaultUseCase();

        when(branch.getParent()).thenReturn(element2);

        presenter.onMouseRightButtonClicked();

        verify(selectionManager).setElement(element2);
    }

    @Test
    public void elementShouldBeNotSelected() throws Exception {
        prepareDefaultUseCase();

        presenter.onMouseMoved(X_POSITION, Y_POSITION);

        verify(connectionsValidator, never()).canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt());
        verify(view, never()).setApplyCursor();
        verify(view, never()).setErrorCursor();
    }

    @Test
    public void elementShouldBeSelectedAsErrorElement() throws Exception {
        prepareDefaultUseCase();

        when(elementCreatorsManager.getElementNameByState(anyString())).thenReturn(ELEMENT_NAME);
        when(connectionsValidator.canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt())).thenReturn(false);

        presenter.onMouseMoved(X_POSITION, Y_POSITION);

        verify(connectionsValidator).canInsertElement(branch, ELEMENT_NAME, X_POSITION, Y_POSITION);
        verify(view, never()).setApplyCursor();
        verify(view).setErrorCursor();
    }

    @Test
    public void elementShouldBeSelectedAsApplyElement() throws Exception {
        prepareDefaultUseCase();

        when(elementCreatorsManager.getElementNameByState(anyString())).thenReturn(ELEMENT_NAME);
        when(connectionsValidator.canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt())).thenReturn(true);

        presenter.onMouseMoved(X_POSITION, Y_POSITION);

        verify(connectionsValidator).canInsertElement(branch, ELEMENT_NAME, X_POSITION, Y_POSITION);
        verify(view).setApplyCursor();
        verify(view, never()).setErrorCursor();
    }

    @Test
    public void cursorShouldBeChangedWhenMouseIsOut() throws Exception {
        prepareDefaultUseCase();

        presenter.onMouseOut();

        verify(view).setDefaultCursor();
    }

    @Test
    public void elementShouldBeNotDeletedWhenNoElementIsSelected() throws Exception {
        prepareDefaultUseCase();

        presenter.onDeleteButtonPressed();

        verify(connectionsValidator, never()).canRemoveElement(any(Branch.class), anyString());
        verify(branch, never()).removeElement(any(Element.class));
        verify(selectionManager, never()).setElement(any(Element.class));
        verify(view, never()).clear();
    }

    @Test
    public void elementShouldBeNotDeletedWhenElementCanBeRemoved() throws Exception {
        prepareDefaultUseCase();

        when(selectionManager.getElement()).thenReturn(element);

        presenter.onDeleteButtonPressed();

        verify(connectionsValidator).canRemoveElement(branch, ELEMENT1_ID);
        verify(branch, never()).removeElement(any(Element.class));
        verify(selectionManager, never()).setElement(any(Element.class));
        verify(view, never()).clear();
    }

    @Test
    public void elementShouldBeDeleted() throws Exception {
        prepareStartView();

        resetMocksUseInPrepareCase();

        prepareBranchMock(branch, Arrays.asList(element2));

        prepareElementIds();

        prepareElementPresenterSize(elementPresenter2, ELEMENT_HEIGHT, ELEMENT_WIDTH);

        prepareCreateElementWidgetFactory();

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);

        when(selectionManager.getElement()).thenReturn(element);
        when(connectionsValidator.canRemoveElement(any(Branch.class), anyString())).thenReturn(true);

        presenter.onDeleteButtonPressed();

        verify(connectionsValidator).canRemoveElement(branch, ELEMENT1_ID);

        verify(branch).removeElement(element);
        verify(selectionManager).setElement(isNull(Element.class));

        verify(view).clear();

        elementShouldBeNotChangedXYPosition(element);
        elementShouldBeChangedXYPosition(element2, ARROW_PADDING, 0);

        verify(elementWidgetFactory, never()).createElementPresenter(any(Element.class));

        listenersShouldBeNotAdded(elementPresenter);
        listenersShouldBeNotAdded(elementPresenter2);

        verify(view, never()).addElement(ARROW_PADDING, 0, elementPresenter);
        verify(view).addElement(ARROW_PADDING, 0, elementPresenter2);

        viewSizeShouldBeChanged(ELEMENTS_PADDING + ELEMENT_HEIGHT, 2 * ARROW_PADDING + ELEMENT_WIDTH);

        verify(elementPresenter, never()).setY(anyInt());

        int top = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        verify(elementPresenter2).setY(top);
    }

    @Test
    public void elementShouldBeRemovedWhenContextMenuActionIsClicked() throws Exception {
        prepareStartView();

        resetMocksUseInPrepareCase();

        prepareBranchMock(branch, Arrays.asList(element2));

        prepareElementIds();

        prepareElementPresenterSize(elementPresenter2, ELEMENT_HEIGHT, ELEMENT_WIDTH);

        prepareCreateElementWidgetFactory();

        when(view.getHeight()).thenReturn(VIEW_HEIGHT);

        when(selectionManager.getElement()).thenReturn(element);
        when(connectionsValidator.canRemoveElement(any(Branch.class), anyString())).thenReturn(true);

        presenter.onElementDeleted(element);

        verify(connectionsValidator).canRemoveElement(branch, ELEMENT1_ID);

        verify(branch).removeElement(element);
        verify(selectionManager).setElement(isNull(Element.class));

        verify(view).clear();

        elementShouldBeNotChangedXYPosition(element);
        elementShouldBeChangedXYPosition(element2, ARROW_PADDING, 0);

        verify(elementWidgetFactory, never()).createElementPresenter(any(Element.class));

        listenersShouldBeNotAdded(elementPresenter);
        listenersShouldBeNotAdded(elementPresenter2);

        verify(view, never()).addElement(ARROW_PADDING, 0, elementPresenter);
        verify(view).addElement(ARROW_PADDING, 0, elementPresenter2);

        viewSizeShouldBeChanged(ELEMENTS_PADDING + ELEMENT_HEIGHT, 2 * ARROW_PADDING + ELEMENT_WIDTH);

        verify(elementPresenter, never()).setY(anyInt());
        verify(elementPresenter).unsubscribeWidget();

        int top = VIEW_HEIGHT / 2 - ELEMENT_HEIGHT / 2;
        verify(elementPresenter2).setY(top);
    }

    @Test
    public void elementShouldBeMovedWhenItIsImpossibleToRemoveIt() throws Exception {
        prepareDefaultUseCase();

        presenter.onElementMoved(element, X_POSITION, Y_POSITION);

        elementShouldBeNotChangedXYPosition(element, X_POSITION, Y_POSITION);

        verifyElementChanges();
    }

    @Test
    public void elementShouldBeMovedWhenItIsImpossibleToAddItInNewPlace() throws Exception {
        prepareDefaultUseCase();

        when(connectionsValidator.canRemoveElement(any(Branch.class), anyString())).thenReturn(true);

        presenter.onElementMoved(element, X_POSITION, Y_POSITION);

        elementShouldBeNotChangedXYPosition(element, X_POSITION, Y_POSITION);

        verifyElementChanges();
    }

    @Test
    public void elementShouldBeMoved() throws Exception {
        prepareDefaultUseCase();

        when(connectionsValidator.canRemoveElement(any(Branch.class), anyString())).thenReturn(true);
        when(connectionsValidator.canInsertElement(any(Branch.class), anyString(), anyInt(), anyInt())).thenReturn(true);

        presenter.onElementMoved(element, X_POSITION, Y_POSITION);

        elementShouldBeChangedXYPosition(element, X_POSITION, Y_POSITION);

        verifyElementChanges();
    }

    @Test
    public void parentElementShouldBeNotifiedAboutChangingCurrentElement() throws Exception {
        prepareDefaultUseCase();

        presenter.onElementChanged();

        verifyElementChanges();
    }

    @Test
    public void listenerShouldBeNotified() throws Exception {
        prepareDefaultUseCase();

        presenter.notifyElementChangedListeners();

        verify(changingElementListener).onElementChanged();
    }

    @Test
    public void defaultStateShouldBeApplied() throws Exception {
        prepareDefaultUseCase();

        presenter.resetToDefaultState();

        verify(view).setDefaultCursor();
    }

    @Test
    public void visibleTopBorderStateShouldBeChanged() throws Exception {
        prepareDefaultUseCase();

        presenter.setVisibleTopBorder(true);

        verify(view).setVisibleTopBorder(true);
    }

}