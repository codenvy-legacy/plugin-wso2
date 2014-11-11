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

import com.codenvy.ide.client.elements.mediators.CallTemplate;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
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

import java.util.List;

import static com.codenvy.ide.client.elements.mediators.CallTemplate.AVAILABLE_TEMPLATES;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.EMPTY;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.SDF;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.SELECT_FROM_TEMPLATE;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.AvailableTemplates.TYPE_NAME;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.PARAMETERS;
import static com.codenvy.ide.client.elements.mediators.CallTemplate.TARGET_TEMPLATES;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class CallTemplatePropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<CallTemplatePropertiesPanelPresenter> {

    private static final String GROUP_TITLE                    = "groupTitle";
    private static final String AVAILABLE_TEMPLATES_ITEM_TITLE = "availableTemplatesItemTitle";
    private static final String TARGET_TEMPLATE_ITEM_TITLE     = "targetTemplateItemTitle";
    private static final String PARAMETERS_ITEM_TITLE          = "parametersItemTitle";
    private static final String DESCRIPTION_ITEM_TITLE         = "descriptionItemTitle";

    private static final String SOME_TEXT = "someText";

    @Captor
    private ArgumentCaptor<AddPropertyCallback> addPropertyCallBackCaptor;

    @Mock
    private PropertyGroupPresenter  groupPresenter;
    @Mock
    private List<Property>          properties;
    @Mock
    private List<String>            typeValues;
    @Mock
    private PropertyConfigPresenter propertyConfigPresenter;
    @Mock
    private AddPropertyCallback     addPropertyCallback;

    @Mock
    private ListPropertyPresenter    availableTemplates;
    @Mock
    private SimplePropertyPresenter  targetTemplate;
    @Mock
    private ComplexPropertyPresenter parameters;
    @Mock
    private SimplePropertyPresenter  description;

    @Mock
    private CallTemplate element;

    private PropertyValueChangedListener availableTemplatesListener;
    private PropertyValueChangedListener targetTemplateListener;
    private EditButtonClickedListener    btnClickListener;
    private PropertyValueChangedListener descriptionListener;

    @Before
    public void setUp() throws Exception {
        prepareViewAndItems();

        presenter = new CallTemplatePropertiesPanelPresenter(view,
                                                             propertyTypeManager,
                                                             propertyConfigPresenter,
                                                             locale,
                                                             propertyPanelFactory,
                                                             selectionManager);

        groupShouldBeAdded(groupPresenter);
        itemsShouldBeAdded(groupPresenter, availableTemplates, targetTemplate, parameters, description);

        presenter.addListener(listener);
        presenter.setElement(element);

        availableTemplatesListener = getListPropertyChangedListener(AVAILABLE_TEMPLATES_ITEM_TITLE);
        targetTemplateListener = getSimplePropertyChangedListener(TARGET_TEMPLATE_ITEM_TITLE);
        btnClickListener = getEditButtonClickedListener(PARAMETERS_ITEM_TITLE);
        descriptionListener = getSimplePropertyChangedListener(DESCRIPTION_ITEM_TITLE);
    }

    private void prepareViewAndItems() {
        when(locale.miscGroupTitle()).thenReturn(GROUP_TITLE);
        when(locale.availableTemplate()).thenReturn(AVAILABLE_TEMPLATES_ITEM_TITLE);
        when(locale.targetTemplate()).thenReturn(TARGET_TEMPLATE_ITEM_TITLE);
        when(locale.calltemplateParameters()).thenReturn(PARAMETERS_ITEM_TITLE);
        when(locale.description()).thenReturn(DESCRIPTION_ITEM_TITLE);

        prepareCreatingGroup(GROUP_TITLE, groupPresenter);
        prepareCreatingListProperty(AVAILABLE_TEMPLATES_ITEM_TITLE, availableTemplates);
        prepareCreatingSimpleProperty(TARGET_TEMPLATE_ITEM_TITLE, targetTemplate);
        prepareCreatingComplexProperty(PARAMETERS_ITEM_TITLE, parameters);
        prepareCreatingSimpleProperty(DESCRIPTION_ITEM_TITLE, description);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        oneGroupShouldBeUnfolded(groupPresenter);
    }

    @Test
    public void availableTemplatesListenerShouldBeDoneWhenTypeIsSDFAndKeyIsNotNull() throws Exception {
        availableTemplatesListener.onPropertyChanged(SDF.getValue());

        verify(element).putProperty(AVAILABLE_TEMPLATES, SDF);

        listenerShouldBeNotified();
    }

    @Test
    public void availableTemplatesListenerShouldBeDoneWhenTypeIsSDFAndKeyIsNull() throws Exception {
        prepareElement(element, AVAILABLE_TEMPLATES, null);

        availableTemplatesListener.onPropertyChanged(SDF.getValue());

        verify(element).putProperty(AVAILABLE_TEMPLATES, SDF);

        listenerShouldBeNotified();
    }

    @Test
    public void availableTemplatesListenerShouldBeDoneWhenTypeIsSelectFromTemplates() throws Exception {
        availableTemplatesListener.onPropertyChanged(SELECT_FROM_TEMPLATE.getValue());

        verify(element).putProperty(AVAILABLE_TEMPLATES, SELECT_FROM_TEMPLATE);

        listenerShouldBeNotified();
    }

    @Test
    public void endpointTypeListenerShouldBeDoneWhenTypeIsEmpty() throws Exception {
        availableTemplatesListener.onPropertyChanged(EMPTY.getValue());

        verify(element).putProperty(AVAILABLE_TEMPLATES, EMPTY);

        listenerShouldBeNotified();
    }

    @Test
    public void targetTemplateListenerShouldBeDone() throws Exception {
        targetTemplateListener.onPropertyChanged(SOME_TEXT);

        verify(element).putProperty(TARGET_TEMPLATES, SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void buttonClickListenerShouldBeDone() throws Exception {
        prepareElement(element, PARAMETERS, properties);
        when(locale.calltemplateParameters()).thenReturn(SOME_TEXT);

        btnClickListener.onEditButtonClicked();

        verify(propertyConfigPresenter).showConfigWindow(eq(properties),
                                                         eq(SOME_TEXT),
                                                         addPropertyCallBackCaptor.capture());
        verify(element).getProperty(PARAMETERS);
    }

    @Test
    public void buttonClickListenerShouldBeDoneWhenParametersIsNull() throws Exception {
        prepareElement(element, PARAMETERS, null);

        btnClickListener.onEditButtonClicked();

        verify(propertyConfigPresenter, never()).showConfigWindow(Matchers.<List<Property>>anyObject(),
                                                                  anyString(),
                                                                  any(AddPropertyCallback.class));
        verify(element).getProperty(PARAMETERS);
    }

    @Test
    public void descriptionListenerShouldBeDone() throws Exception {
        descriptionListener.onPropertyChanged(SOME_TEXT);

        verify(element).putProperty(DESCRIPTION, SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void addPropertyListenerShouldBeDone() throws Exception {
        prepareAddPropertyCallBackMocksAndCallCallback();

        verify(element).putProperty(PARAMETERS, properties);
        listenerShouldBeNotified();
    }

    private void prepareAddPropertyCallBackMocksAndCallCallback() throws Exception {
        prepareElement(element, PARAMETERS, properties);
        when(locale.calltemplateParameters()).thenReturn(SOME_TEXT);

        btnClickListener.onEditButtonClicked();

        verify(propertyConfigPresenter).showConfigWindow(eq(properties),
                                                         eq(SOME_TEXT),
                                                         addPropertyCallBackCaptor.capture());

        AddPropertyCallback propertyCallback = addPropertyCallBackCaptor.getValue();

        propertyCallback.onPropertiesChanged(properties);
    }

    @Test
    public void availableTemplateParametersShouldBeSelected() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, AVAILABLE_TEMPLATES, SDF);

        presenter.go(container);

        verify(availableTemplates).setValues(typeValues);
        verify(availableTemplates).selectValue(SDF.getValue());
    }

    @Test
    public void availableTemplateParametersShouldNotBeSelectedWhenTypeIsNull() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, AVAILABLE_TEMPLATES, null);

        presenter.go(container);

        verify(availableTemplates).setValues(typeValues);
        verify(availableTemplates, never()).selectValue(anyString());
    }

    @Test
    public void descriptionValueShouldBeSet() throws Exception {
        prepareElement(element, DESCRIPTION, SOME_TEXT);

        presenter.go(container);

        verify(description).setProperty(SOME_TEXT);
    }

}