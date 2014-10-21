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
package com.codenvy.ide.client.propertiespanel;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.AbstractEntityElement.Key;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractPropertiesPanelTest<V extends AbstractPropertiesPanel> {

    protected static final List<String> VALUES = Collections.emptyList();

    @Mock
    protected PropertiesPanelView            view;
    @Mock
    protected PropertyTypeManager            propertyTypeManager;
    @Mock
    protected WSO2EditorLocalizationConstant locale;
    @Mock
    protected PropertyPanelFactory           propertyPanelFactory;
    @Mock
    protected PropertyChangedListener        listener;
    @Mock
    protected AcceptsOneWidget               container;
    @Mock
    protected SelectionManager               selectionManager;

    protected V presenter;

    protected void listenerShouldBeNotified() {
        verify(listener).onPropertyChanged();
    }

    protected void noListenerShouldBeNotified() {
        verify(listener, never()).onPropertyChanged();
    }

    protected void prepareCreatingGroup(String title, PropertyGroupPresenter group) {
        when(propertyPanelFactory.createGroupProperty(eq(title))).thenReturn(group);
    }

    protected void prepareCreatingSimpleProperty(String title, SimplePropertyPresenter simpleProperty) {
        when(propertyPanelFactory.createSimpleProperty(eq(title), any(PropertyValueChangedListener.class))).thenReturn(simpleProperty);
    }

    protected void prepareCreatingListProperty(String title, ListPropertyPresenter listProperty) {
        when(propertyPanelFactory.createListProperty(eq(title), any(PropertyValueChangedListener.class))).thenReturn(listProperty);
    }

    protected void prepareCreatingComplexProperty(String title, ComplexPropertyPresenter complexProperty) {
        when(propertyPanelFactory.createComplexProperty(eq(title), any(EditButtonClickedListener.class)))
                .thenReturn(complexProperty);
    }

    protected void preparePropertyTypeManager(String type, List<String> values) {
        when(propertyTypeManager.getValuesByName(eq(type))).thenReturn(values);
    }

    protected void propertyTypeManagerShouldBeUsed(String type) {
        verify(propertyTypeManager).getValuesByName(eq(type));
    }

    protected <T> void prepareElement(AbstractElement element, Key<T> key, T value) {
        when(element.getProperty(eq(key))).thenReturn(value);
    }

    protected void groupShouldBeAdded(PropertyGroupPresenter group) {
        verify(view).addGroup(group);
    }

    protected void itemsShouldBeAdded(PropertyGroupPresenter group, AbstractPropertyPresenter... properties) {
        for (AbstractPropertyPresenter property : properties) {
            verify(group).addItem(property);
        }
    }

    protected PropertyValueChangedListener getListPropertyChangedListener(String title) {
        ArgumentCaptor<PropertyValueChangedListener> listenerCaptor = ArgumentCaptor.forClass(PropertyValueChangedListener.class);

        verify(propertyPanelFactory).createListProperty(eq(title), listenerCaptor.capture());

        return listenerCaptor.getValue();
    }

    protected PropertyValueChangedListener getSimplePropertyChangedListener(String title) {
        ArgumentCaptor<PropertyValueChangedListener> listenerCaptor = ArgumentCaptor.forClass(PropertyValueChangedListener.class);

        verify(propertyPanelFactory).createSimpleProperty(eq(title), listenerCaptor.capture());

        return listenerCaptor.getValue();
    }

    protected EditButtonClickedListener getEditButtonClickedListener(String title) {
        ArgumentCaptor<EditButtonClickedListener> listenerCaptor = ArgumentCaptor.forClass(EditButtonClickedListener.class);

        verify(propertyPanelFactory).createComplexProperty(eq(title), listenerCaptor.capture());

        return listenerCaptor.getValue();
    }

    protected void groupsShouldBeUnfolded(PropertyGroupPresenter... groups) {
        for (PropertyGroupPresenter group : groups) {
            verify(group).unfold();
        }
    }

    protected void oneGroupShouldBeUnfolded(PropertyGroupPresenter group) {
        verify(group).unfold();

        verify(group).setTitleVisible(false);
    }

    protected void listValuesShouldBeSet(ListPropertyPresenter listProperty, List<String> values) {
        verify(listProperty).setValues(values);
    }

    protected void noListValuesShouldBeSet(ListPropertyPresenter listProperty) {
        verify(listProperty, never()).setValues(anyListOf(String.class));
    }

    protected void listItemShouldBeSelected(ListPropertyPresenter listProperty, String values) {
        verify(listProperty).selectValue(values);
    }

    protected void noListItemShouldBeSelected(ListPropertyPresenter listProperty) {
        verify(listProperty, never()).selectValue(anyString());
    }

    protected void propertyValueShouldBeReturned(AbstractElement element, Key key) {
        verify(element).getProperty(eq(key));
    }

    protected <T> void propertyValueShouldBeChanged(AbstractElement element, Key<T> key, T value) {
        verify(element).putProperty(eq(key), eq(value));
    }

    protected void visibleStateShouldBeChanged(AbstractPropertyPresenter property, boolean visible) {
        verify(property).setVisible(visible);
    }

    protected void visibleStateShouldBeTheSame(AbstractPropertyPresenter property) {
        verify(property, never()).setVisible(anyBoolean());
    }

    protected void simplePropertyValueShouldBeChanged(SimplePropertyPresenter simpleProperty, String value) {
        verify(simpleProperty).setProperty(eq(value));
    }

    protected void simplePropertyValueShouldBeTheSame(SimplePropertyPresenter simpleProperty) {
        verify(simpleProperty, never()).setProperty(anyString());
    }

    protected void complexPropertyValueShouldBeChanged(ComplexPropertyPresenter complexProperty, String value) {
        verify(complexProperty).setProperty(eq(value));
    }

    protected void simplePropertyShouldBePrepared(SimplePropertyPresenter simpleProperty,
                                                  AbstractElement element,
                                                  Key key,
                                                  String value) {
        propertyValueShouldBeReturned(element, key);
        simplePropertyValueShouldBeChanged(simpleProperty, value);
    }

    protected <T> void propertyChangedGeneralListenerShouldBeExecuted(AbstractElement element, Key<T> key, T value) {
        propertyValueShouldBeChanged(element, key, value);
        listenerShouldBeNotified();
    }

    @Test
    public void listenersShouldBeNotified() throws Exception {
        presenter.addListener(listener);

        presenter.notifyListeners();

        verify(listener, times(2)).onPropertyChanged();
    }

    @Test
    public void listenerShouldBeRemoved() throws Exception {
        presenter.addListener(listener);
        presenter.addListener(listener);

        presenter.notifyListeners();

        verify(listener, times(3)).onPropertyChanged();

        reset(listener);

        presenter.removeListener(listener);
        presenter.removeListener(listener);

        presenter.notifyListeners();

        listenerShouldBeNotified();
    }

    @Test
    public abstract void groupsShouldBeShown();

    @Test
    public void noElementShouldBeSelectedWhenOneClickedOnEmptyPlace() throws Exception {
        presenter.onMainPanelClicked();

        verify(selectionManager).setElement(isNull(Element.class));
    }

}