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

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Call;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;

import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Call.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Call.ENDPOINT_TYPE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.INLINE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.NONE;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.REGISTRYKEY;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.TYPE_NAME;
import static com.codenvy.ide.client.elements.mediators.Call.EndpointType.XPATH;
import static com.codenvy.ide.client.elements.mediators.Call.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Call.REGISTRY_KEY;
import static com.codenvy.ide.client.elements.mediators.Call.X_PATH;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class CallPropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<CallPropertiesPanelPresenter> {

    private static final String GROUP_TITLE                      = "groupTitle";
    private static final String ENDPOINT_TYPE_ITEM_TITLE         = "endpointTypeItemTitle";
    private static final String ENDPOINT_REGISTRY_KEY_ITEM_TITLE = "endpointRegistryItemTitle";
    private static final String ENDPOINT_XPATH_ITEM_TITLE        = "endpointXpathItemTitle";
    private static final String DESCRIPTION_ITEM_TITLE           = "descriptionItemTitle";

    private static final String SOME_TEXT = "someText";

    @Captor
    private ArgumentCaptor<AddNameSpacesCallBack> addNameSpacesCallBackCaptor;

    @Mock
    private PropertyGroupPresenter   groupPresenter;
    @Mock
    private NameSpaceEditorPresenter nameSpaceEditorPresenter;
    @Mock
    private AddNameSpacesCallBack    addNameSpacesCallBack;
    @Mock
    private List<NameSpace>          nameSpaces;
    @Mock
    private List<String>             typeValues;

    @Mock
    private ListPropertyPresenter    endpointTypeList;
    @Mock
    private SimplePropertyPresenter  endpointRegistryKeyPanel;
    @Mock
    private ComplexPropertyPresenter endpointXpathPanel;
    @Mock
    private SimplePropertyPresenter  descriptionPanel;

    @Mock
    private Call element;

    private PropertyValueChangedListener endpointTypeListener;
    private PropertyValueChangedListener endpointRegistryKeyListener;
    private EditButtonClickedListener    buttonClickListener;
    private PropertyValueChangedListener descriptionListener;

    @Before
    public void setUp() throws Exception {
        prepareViewAndItems();

        presenter = new CallPropertiesPanelPresenter(view,
                                                     propertyTypeManager,
                                                     nameSpaceEditorPresenter,
                                                     locale,
                                                     propertyPanelFactory,
                                                     selectionManager);

        groupShouldBeAdded(groupPresenter);
        itemsShouldBeAdded(groupPresenter, endpointTypeList, endpointRegistryKeyPanel, endpointXpathPanel, descriptionPanel);

        presenter.addListener(listener);
        presenter.setElement(element);

        endpointTypeListener = getListPropertyChangedListener(ENDPOINT_TYPE_ITEM_TITLE);
        endpointRegistryKeyListener = getSimplePropertyChangedListener(ENDPOINT_REGISTRY_KEY_ITEM_TITLE);
        buttonClickListener = getEditButtonClickedListener(ENDPOINT_XPATH_ITEM_TITLE);
        descriptionListener = getSimplePropertyChangedListener(DESCRIPTION_ITEM_TITLE);
    }

    private void prepareViewAndItems() {
        when(locale.miscGroupTitle()).thenReturn(GROUP_TITLE);
        when(locale.endpointType()).thenReturn(ENDPOINT_TYPE_ITEM_TITLE);
        when(locale.endpointRegistryKey()).thenReturn(ENDPOINT_REGISTRY_KEY_ITEM_TITLE);
        when(locale.endpointXpath()).thenReturn(ENDPOINT_XPATH_ITEM_TITLE);
        when(locale.description()).thenReturn(DESCRIPTION_ITEM_TITLE);

        prepareCreatingGroup(GROUP_TITLE, groupPresenter);
        prepareCreatingListProperty(ENDPOINT_TYPE_ITEM_TITLE, endpointTypeList);
        prepareCreatingSimpleProperty(ENDPOINT_REGISTRY_KEY_ITEM_TITLE, endpointRegistryKeyPanel);
        prepareCreatingComplexProperty(ENDPOINT_XPATH_ITEM_TITLE, endpointXpathPanel);
        prepareCreatingSimpleProperty(DESCRIPTION_ITEM_TITLE, descriptionPanel);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        oneGroupShouldBeUnfolded(groupPresenter);
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsRegistryKeyAndKeyIsNotNull() throws Exception {
        when(element.getProperty(REGISTRY_KEY)).thenReturn(SOME_TEXT);

        endpointTypeListener.onPropertyChanged(REGISTRYKEY.name());

        verify(element).putProperty(ENDPOINT_TYPE, REGISTRYKEY);
        verify(endpointXpathPanel).setVisible(false);
        verify(endpointRegistryKeyPanel).setVisible(true);
        verify(endpointRegistryKeyPanel).setProperty(SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsRegistryKeyAndKeyIsNull() throws Exception {
        prepareElement(element, REGISTRY_KEY, null);

        endpointTypeListener.onPropertyChanged(REGISTRYKEY.name());

        verify(element).putProperty(ENDPOINT_TYPE, REGISTRYKEY);
        verify(endpointXpathPanel).setVisible(false);
        verify(endpointRegistryKeyPanel).setVisible(true);
        verify(endpointRegistryKeyPanel).setProperty("");

        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsXPath() throws Exception {
        prepareElement(element, X_PATH, SOME_TEXT);

        endpointTypeListener.onPropertyChanged(XPATH.name());

        verify(element).putProperty(ENDPOINT_TYPE, XPATH);
        verify(endpointXpathPanel).setVisible(true);
        verify(endpointRegistryKeyPanel).setVisible(false);
        verify(endpointXpathPanel).setProperty(SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsNone() throws Exception {
        endpointTypeListener.onPropertyChanged(NONE.name());

        verify(element).putProperty(ENDPOINT_TYPE, NONE);
        verify(endpointXpathPanel).setVisible(false);
        verify(endpointRegistryKeyPanel).setVisible(false);

        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsInline() throws Exception {
        endpointTypeListener.onPropertyChanged(INLINE.name());

        verify(element).putProperty(ENDPOINT_TYPE, INLINE);
        verify(endpointXpathPanel).setVisible(false);
        verify(endpointRegistryKeyPanel).setVisible(false);

        listenerShouldBeNotified();
    }

    @Test
    public void endpointRegistryKeyListenerShouldBeDone() throws Exception {
        endpointRegistryKeyListener.onPropertyChanged(SOME_TEXT);

        verify(element).putProperty(REGISTRY_KEY, SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void buttonClickListenerShouldBeDone() throws Exception {
        prepareElement(element, NAMESPACES, nameSpaces);
        prepareElement(element, X_PATH, SOME_TEXT);
        when(locale.expressionTitle()).thenReturn(SOME_TEXT);

        buttonClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  addNameSpacesCallBackCaptor.capture(),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));
        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(X_PATH);
    }

    @Test
    public void buttonClickListenerShouldBeDoneWhenNameSpacesIsNull() throws Exception {
        prepareElement(element, NAMESPACES, null);
        prepareElement(element, X_PATH, SOME_TEXT);

        buttonClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(X_PATH);
    }

    @Test
    public void buttonClickListenerShouldBeDoneWhenXpathIsNull() throws Exception {
        prepareElement(element, NAMESPACES, nameSpaces);
        prepareElement(element, X_PATH, null);

        buttonClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
        verify(element).getProperty(NAMESPACES);
        verify(element).getProperty(X_PATH);
    }

    @Test
    public void descriptionListenerShouldBeDone() throws Exception {
        descriptionListener.onPropertyChanged(SOME_TEXT);

        verify(element).putProperty(DESCRIPTION, SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void addNameSpaceListenerShouldBeDone() throws Exception {
        prepareElement(element, NAMESPACES, nameSpaces);
        prepareElement(element, X_PATH, SOME_TEXT);
        when(locale.expressionTitle()).thenReturn(SOME_TEXT);

        buttonClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  addNameSpacesCallBackCaptor.capture(),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));

        AddNameSpacesCallBack nameSpacesCallBack = addNameSpacesCallBackCaptor.getValue();

        nameSpacesCallBack.onNameSpacesChanged(nameSpaces, SOME_TEXT);

        verify(element).putProperty(NAMESPACES, nameSpaces);
        verify(element).putProperty(X_PATH, SOME_TEXT);
        verify(endpointXpathPanel).setProperty(SOME_TEXT);
        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeParametersShouldBeSelected() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, ENDPOINT_TYPE, EndpointType.XPATH);

        presenter.go(container);

        verify(endpointTypeList).setValues(typeValues);
        verify(endpointTypeList).selectValue(EndpointType.XPATH.name());
    }

    @Test
    public void endpointTypeParametersShouldNotBeSelectedWhenTypeIsNull() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, ENDPOINT_TYPE, null);

        presenter.go(container);

        verify(endpointTypeList).setValues(typeValues);
        verify(endpointTypeList, never()).selectValue(anyString());
    }

    @Test
    public void descriptionValueShouldBeSet() throws Exception {
        prepareElement(element, DESCRIPTION, SOME_TEXT);

        presenter.go(container);

        verify(descriptionPanel).setProperty(SOME_TEXT);
    }

    @Test
    public void emptyDescriptionValueShouldBeSetWhenDescriptionIsNull() throws Exception {
        prepareElement(element, DESCRIPTION, null);

        presenter.go(container);

        verify(descriptionPanel).setProperty("");
    }
}