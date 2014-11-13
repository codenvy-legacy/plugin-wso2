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
import com.codenvy.ide.client.elements.mediators.Filter;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
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

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Filter.CONDITION_TYPE;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.SOURCE_AND_REGEX;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.TYPE_NAME;
import static com.codenvy.ide.client.elements.mediators.Filter.ConditionType.XPATH;
import static com.codenvy.ide.client.elements.mediators.Filter.REGULAR_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE;
import static com.codenvy.ide.client.elements.mediators.Filter.SOURCE_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.XPATH_NAMESPACE;
import static com.codenvy.ide.client.elements.mediators.Filter.X_PATH;
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
public class FilterPropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<FilterPropertiesPanelPresenter> {

    private static final String GROUP_TITLE               = "groupTitle";
    private static final String CONDITION_TYPE_ITEM_TITLE = "conditionTypeTitle";
    private static final String XPATH_ITEM_TITLE          = "xPathTitle";
    private static final String SOURCE_ITEM_TITLE         = "sourceTitle";
    private static final String REGULAR_EXPRESSION_TITLE  = "regularExpressionTitle";

    private static final String SOME_TEXT = "someText";

    @Captor
    private ArgumentCaptor<AddNameSpacesCallBack> addNameSpacesCallBackArgumentCaptor;

    @Mock
    private PropertyGroupPresenter   groupPresenter;
    @Mock
    private NameSpaceEditorPresenter nameSpaceEditorPresenter;
    @Mock
    private List<NameSpace>          nameSpaces;
    @Mock
    private List<String>             typeValues;
    @Mock
    private AddPropertyCallback      addPropertyCallback;

    @Mock
    private ListPropertyPresenter    conditionType;
    @Mock
    private ComplexPropertyPresenter source;
    @Mock
    private ComplexPropertyPresenter xPath;
    @Mock
    private SimplePropertyPresenter  regularExpression;

    @Mock
    private Filter element;

    private PropertyValueChangedListener conditionTypeListener;
    private EditButtonClickedListener    sourceBtnClickListener;
    private EditButtonClickedListener    xPathBtnClickListener;
    private PropertyValueChangedListener regularExpressionListener;

    @Before
    public void setUp() throws Exception {
        prepareViewAndItems();

        presenter = new FilterPropertiesPanelPresenter(view,
                                                       propertyTypeManager,
                                                       nameSpaceEditorPresenter,
                                                       locale,
                                                       propertyPanelFactory,
                                                       selectionManager);

        groupShouldBeAdded(groupPresenter);
        itemsShouldBeAdded(groupPresenter, conditionType, source, xPath, regularExpression);

        presenter.addListener(listener);
        presenter.setElement(element);

        conditionTypeListener = getListPropertyChangedListener(CONDITION_TYPE_ITEM_TITLE);
        sourceBtnClickListener = getEditButtonClickedListener(SOURCE_ITEM_TITLE);
        xPathBtnClickListener = getEditButtonClickedListener(XPATH_ITEM_TITLE);
        regularExpressionListener = getSimplePropertyChangedListener(REGULAR_EXPRESSION_TITLE);
    }

    private void prepareViewAndItems() {
        when(locale.miscGroupTitle()).thenReturn(GROUP_TITLE);
        when(locale.conditionType()).thenReturn(CONDITION_TYPE_ITEM_TITLE);
        when(locale.filterSource()).thenReturn(SOURCE_ITEM_TITLE);
        when(locale.regularExpression()).thenReturn(REGULAR_EXPRESSION_TITLE);
        when(locale.filterXpath()).thenReturn(XPATH_ITEM_TITLE);

        prepareCreatingGroup(GROUP_TITLE, groupPresenter);
        prepareCreatingListProperty(CONDITION_TYPE_ITEM_TITLE, conditionType);
        prepareCreatingSimpleProperty(REGULAR_EXPRESSION_TITLE, regularExpression);
        prepareCreatingComplexProperty(SOURCE_ITEM_TITLE, source);
        prepareCreatingComplexProperty(XPATH_ITEM_TITLE, xPath);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        oneGroupShouldBeUnfolded(groupPresenter);
    }

    @Test
    public void conditionTypeListenerShouldBeDoneWhenTypeIsSourceAndKeyIsNotNull() throws Exception {
        conditionTypeListener.onPropertyChanged(SOURCE_AND_REGEX.name());

        verify(element).putProperty(CONDITION_TYPE, SOURCE_AND_REGEX);

        listenerShouldBeNotified();
    }

    @Test
    public void conditionTypeListenerShouldBeDoneWhenTypeIsSourceAndKeyIsNull() throws Exception {
        prepareElement(element, CONDITION_TYPE, null);

        conditionTypeListener.onPropertyChanged(SOURCE_AND_REGEX.name());

        verify(element).putProperty(CONDITION_TYPE, SOURCE_AND_REGEX);

        listenerShouldBeNotified();
    }

    @Test
    public void conditionTypeListenerShouldBeDoneWhenTypeIsXPath() throws Exception {
        conditionTypeListener.onPropertyChanged(XPATH.name());

        verify(element).putProperty(CONDITION_TYPE, XPATH);

        listenerShouldBeNotified();
    }


    @Test
    public void sourceButtonClickListenerShouldBeDone() throws Exception {
        prepareElement(element, SOURCE_NAMESPACE, nameSpaces);
        when(locale.filterSourceTitle()).thenReturn(SOME_TEXT);
        when(element.getProperty(SOURCE)).thenReturn(SOME_TEXT);

        sourceBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  any(AddNameSpacesCallBack.class),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));
        verify(element).getProperty(SOURCE_NAMESPACE);
    }

    @Test
    public void sourceButtonClickListenerShouldBeDoneWhenParametersIsNull() throws Exception {
        prepareElement(element, SOURCE_NAMESPACE, null);

        sourceBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
    }

    @Test
    public void xPathButtonClickListenerShouldBeDone() throws Exception {
        prepareElement(element, XPATH_NAMESPACE, nameSpaces);
        when(locale.filterXpathTitle()).thenReturn(SOME_TEXT);
        when(element.getProperty(X_PATH)).thenReturn(SOME_TEXT);

        xPathBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  any(AddNameSpacesCallBack.class),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));
        verify(element).getProperty(XPATH_NAMESPACE);
    }

    @Test
    public void xPathButtonClickListenerShouldBeDoneWhenParametersIsNull() throws Exception {
        prepareElement(element, XPATH_NAMESPACE, null);

        xPathBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter, never()).showWindowWithParameters(Matchers.<List<NameSpace>>anyObject(),
                                                                           any(AddNameSpacesCallBack.class),
                                                                           anyString(),
                                                                           anyString());
    }

    @Test
    public void regularExpressionListenerShouldBeDone() throws Exception {
        regularExpressionListener.onPropertyChanged(SOME_TEXT);

        verify(element).putProperty(REGULAR_EXPRESSION, SOME_TEXT);

        listenerShouldBeNotified();
    }

    @Test
    public void addSourceNamespaceListenerShouldBeDone() throws Exception {
        prepareAddSourceNameSpaceBackMocksAndCallCallback(SOME_TEXT);

        verify(element).putProperty(SOURCE_NAMESPACE, nameSpaces);
        listenerShouldBeNotified();
    }

    private void prepareAddSourceNameSpaceBackMocksAndCallCallback(@Nonnull String expressionValue) throws Exception {
        prepareElement(element, SOURCE_NAMESPACE, nameSpaces);
        prepareElement(element, SOURCE, SOME_TEXT);
        when(locale.filterSourceTitle()).thenReturn(SOME_TEXT);

        sourceBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  addNameSpacesCallBackArgumentCaptor.capture(),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));

        AddNameSpacesCallBack propertyCallback = addNameSpacesCallBackArgumentCaptor.getValue();

        propertyCallback.onNameSpacesChanged(nameSpaces, expressionValue);
    }

    @Test
    public void addXPathNamespaceListenerShouldBeDone() throws Exception {
        prepareAddXPathNameSpaceBackMocksAndCallCallback(SOME_TEXT);

        verify(element).putProperty(XPATH_NAMESPACE, nameSpaces);
        listenerShouldBeNotified();
    }

    private void prepareAddXPathNameSpaceBackMocksAndCallCallback(@Nonnull String expressionValue) throws Exception {
        prepareElement(element, XPATH_NAMESPACE, nameSpaces);
        prepareElement(element, X_PATH, SOME_TEXT);
        when(locale.filterXpathTitle()).thenReturn(SOME_TEXT);

        xPathBtnClickListener.onEditButtonClicked();

        verify(nameSpaceEditorPresenter).showWindowWithParameters(eq(nameSpaces),
                                                                  addNameSpacesCallBackArgumentCaptor.capture(),
                                                                  eq(SOME_TEXT),
                                                                  eq(SOME_TEXT));

        AddNameSpacesCallBack xPathNameSpaceCallback = addNameSpacesCallBackArgumentCaptor.getValue();

        xPathNameSpaceCallback.onNameSpacesChanged(nameSpaces, expressionValue);
    }

    @Test
    public void conditionTypeParametersShouldBeSelected() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, CONDITION_TYPE, XPATH);

        presenter.go(container);

        verify(conditionType).setValues(typeValues);
        verify(conditionType).selectValue(XPATH.name());
    }

    @Test
    public void conditionTypeParametersShouldNotBeSelectedWhenTypeIsNull() throws Exception {
        preparePropertyTypeManager(TYPE_NAME, typeValues);
        prepareElement(element, CONDITION_TYPE, null);

        presenter.go(container);

        verify(conditionType).setValues(typeValues);
        verify(conditionType, never()).selectValue(anyString());
    }

}