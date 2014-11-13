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
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Switch.DEFAULT_CASE_TITLE;
import static com.codenvy.ide.client.elements.mediators.Switch.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Switch.REGEXP_ATTRIBUTE_NAME;
import static com.codenvy.ide.client.elements.mediators.Switch.SOURCE_XPATH;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class SwitchPropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<SwitchPropertiesPanelPresenter> {

    private static final String GROUP_TITLE              = "groupTitle";
    private static final String SOURCE_X_PATH_ITEM_TITLE = "itemTitle";

    private static final String SOME_TEXT = "someText";

    @Captor
    private ArgumentCaptor<AddNameSpacesCallBack>        addNameSpacesCaptor;
    @Captor
    private ArgumentCaptor<PropertyValueChangedListener> valueChangedListenerCaptor;

    @Mock
    private SimplePropertyPresenter           simplePropertyPresenter;
    @Mock
    private NameSpaceEditorPresenter          nameSpaceEditorPresenter;
    @Mock
    private Provider<SimplePropertyPresenter> simplePropertyPresenterProvider;
    @Mock
    private PropertyGroupPresenter            basicGroup;
    @Mock
    private ComplexPropertyPresenter          sourceXPath;
    @Mock
    private List<NameSpace>                   nameSpaceList;
    @Mock
    private Branch                            branch;
    @Mock
    private Switch                            element;

    private EditButtonClickedListener sourceXpathListener;

    @Before
    public void setUp() throws Exception {
        prepareViewAndItems();
        when(locale.switchSourceXPath()).thenReturn(SOURCE_X_PATH_ITEM_TITLE);

        presenter = new SwitchPropertiesPanelPresenter(view,
                                                       propertyTypeManager,
                                                       nameSpaceEditorPresenter,
                                                       simplePropertyPresenterProvider,
                                                       locale,
                                                       propertyPanelFactory,
                                                       selectionManager);

        groupShouldBeAdded(basicGroup);
        itemsShouldBeAdded(basicGroup, sourceXPath);

        presenter.addListener(listener);
        presenter.setElement(element);

        sourceXpathListener = getEditButtonClickedListener(SOURCE_X_PATH_ITEM_TITLE);
    }

    private void prepareViewAndItems() {
        when(locale.miscGroupTitle()).thenReturn(GROUP_TITLE);
        prepareCreatingGroup(GROUP_TITLE, basicGroup);
        prepareCreatingComplexProperty(SOURCE_X_PATH_ITEM_TITLE, sourceXPath);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        oneGroupShouldBeUnfolded(basicGroup);
    }

    @Test
    public void sourceXpathListenerShouldBeDone() throws Exception {
        when(locale.switchXpathTitle()).thenReturn(SOME_TEXT);
        prepareElement(element, SOURCE_XPATH, SOME_TEXT);
        prepareElement(element, NAMESPACES, nameSpaceList);

        sourceXpathListener.onEditButtonClicked();

        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(SOURCE_XPATH);

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaceList),
                                                                  any(AddNameSpacesCallBack.class),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));
    }

    @Test
    public void sourceXpathListenerShouldNotBeDoneWhenNameSpacesIsNull() throws Exception {
        prepareElement(element, SOURCE_XPATH, SOME_TEXT);
        prepareElement(element, NAMESPACES, null);

        sourceXpathListener.onEditButtonClicked();

        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(SOURCE_XPATH);

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
    }

    @Test
    public void sourceXpathListenerShouldNotBeDoneWhenSourceXpathIsNull() throws Exception {
        prepareElement(element, SOURCE_XPATH, null);
        prepareElement(element, NAMESPACES, nameSpaceList);

        sourceXpathListener.onEditButtonClicked();

        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(SOURCE_XPATH);

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
    }

    @Test
    public void sourceXpathPropertyShouldBeSet() throws Exception {
        when(element.getProperty(SOURCE_XPATH)).thenReturn(SOME_TEXT);

        presenter.go(container);

        verify(sourceXPath).setProperty(SOME_TEXT);
    }

    @Test
    public void branchShouldBeAdded() throws Exception {
        when(element.getBranches()).thenReturn(Arrays.asList(branch));
        when(branch.getTitle()).thenReturn(SOME_TEXT);
        when(simplePropertyPresenterProvider.get()).thenReturn(simplePropertyPresenter);
        when(branch.getAttributeByName(REGEXP_ATTRIBUTE_NAME)).thenReturn(SOME_TEXT);

        presenter.go(container);

        verify(element).getBranches();
        verify(branch, times(2)).getTitle();
        verify(simplePropertyPresenterProvider).get();
        verify(simplePropertyPresenter).setTitle(SOME_TEXT + ' ' + 0);
        verify(simplePropertyPresenter).setProperty(SOME_TEXT);
        verify(branch).getAttributeByName(REGEXP_ATTRIBUTE_NAME);
        verify(simplePropertyPresenter).addPropertyValueChangedListener(any(PropertyValueChangedListener.class));
        verify(basicGroup).addItem(simplePropertyPresenter);
    }

    @Test
    public void panelShouldBeCleared() throws Exception {
        when(element.getBranches()).thenReturn(Arrays.asList(branch));
        when(simplePropertyPresenterProvider.get()).thenReturn(simplePropertyPresenter);

        presenter.go(container);

        reset(simplePropertyPresenter);

        presenter.go(container);

        verify(simplePropertyPresenter).removePropertyValueChangedListeners();
        verify(basicGroup).removeItem(simplePropertyPresenter);
    }

    @Test
    public void regexListenerShouldBeDone() throws Exception {
        when(element.getBranches()).thenReturn(Arrays.asList(branch));
        when(simplePropertyPresenterProvider.get()).thenReturn(simplePropertyPresenter);

        presenter.go(container);

        verify(simplePropertyPresenter).addPropertyValueChangedListener(valueChangedListenerCaptor.capture());

        PropertyValueChangedListener listener = valueChangedListenerCaptor.getValue();
        listener.onPropertyChanged(SOME_TEXT);

        verify(branch).addAttribute(REGEXP_ATTRIBUTE_NAME, SOME_TEXT);
        listenerShouldBeNotified();
    }

    @Test
    public void addNameSpaceListenerShouldBeDone() throws Exception {
        prepareElement(element, SOURCE_XPATH, SOME_TEXT);
        prepareElement(element, NAMESPACES, nameSpaceList);
        when(locale.switchXpathTitle()).thenReturn(SOME_TEXT);

        sourceXpathListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaceList),
                                                                  addNameSpacesCaptor.capture(),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));

        AddNameSpacesCallBack nameSpacesCallBack = addNameSpacesCaptor.getValue();

        nameSpacesCallBack.onNameSpacesChanged(nameSpaceList, SOME_TEXT);

        verify(element).putProperty(NAMESPACES, nameSpaceList);
        verify(element).putProperty(SOURCE_XPATH, SOME_TEXT);
        verify(sourceXPath).setProperty(SOME_TEXT);
        listenerShouldBeNotified();
    }

    @Test
    public void panelShouldNotBeCreatedIfItExists() throws Exception {
        when(element.getBranches()).thenReturn(Arrays.asList(branch));
        when(branch.getTitle()).thenReturn(DEFAULT_CASE_TITLE);

        presenter.go(container);

        verify(simplePropertyPresenterProvider, never()).get();
    }

}