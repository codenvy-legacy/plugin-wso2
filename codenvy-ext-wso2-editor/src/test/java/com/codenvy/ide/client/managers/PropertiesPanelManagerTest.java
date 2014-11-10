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
package com.codenvy.ide.client.managers;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.mediators.Property;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertyChangedListener;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesPanelManagerTest {

    private Log log;

    @Mock
    private AcceptsOneWidget                  container;
    @Mock
    private AbstractPropertiesPanel<Log>      logPropertiesPanel;
    @Mock
    private AbstractPropertiesPanel<Property> propertyPropertiesPanel;
    @Mock
    private SelectionManager                  selectionManager;
    @Mock
    private PropertyChangedListener           listener;
    @Mock
    private EditorResources                   resources;

    @InjectMocks
    private PropertiesPanelManager manager;

    @Before
    public void setUp() {
        manager.register(Log.class, logPropertiesPanel);
        manager.setContainer(container);
        log = new Log(resources, null, null, null);
    }

    @Test
    public void propertyPanelShouldBeNotShowedIfRegisteredElementIsNull() throws Exception {
        manager.show(null);

        verify(logPropertiesPanel, never()).setElement(any(Log.class));
        verify(logPropertiesPanel, never()).go(any(AcceptsOneWidget.class));
        verify(logPropertiesPanel, never()).addListener(manager);
    }

    @Test
    public void propertyPanelShouldBeShowed() throws Exception {
        manager.show(log);

        verify(logPropertiesPanel).setElement(any(Log.class));
        verify(logPropertiesPanel).go(any(AcceptsOneWidget.class));
        verify(logPropertiesPanel).addListener(manager);
    }

    @Test
    public void propertyPanelShouldBeShowedIfStateChanged() throws Exception {
        manager.onStateChanged(log);

        verify(logPropertiesPanel).setElement(any(Log.class));
        verify(logPropertiesPanel).go(any(AcceptsOneWidget.class));
        verify(logPropertiesPanel).addListener(manager);
    }

    @Test
    public void propertyPanelShouldBeShowedIfStateChangedWithNullElement() throws Exception {
        manager.onStateChanged(null);

        verify(logPropertiesPanel, never()).setElement(any(Log.class));
        verify(logPropertiesPanel, never()).go(any(AcceptsOneWidget.class));
        verify(logPropertiesPanel, never()).addListener(manager);
    }

    @Test
    public void listenerShouldBeRemovedIfShownPanelIsChanged() throws Exception {
        manager.register(Property.class, propertyPropertiesPanel);
        Property property = new Property(resources, null, null, null);

        manager.show(log);
        manager.show(property);

        verify(logPropertiesPanel).setElement(any(Log.class));
        verify(logPropertiesPanel).go(container);
        verify(logPropertiesPanel).removeListener(manager);

        verify(propertyPropertiesPanel).setElement(any(Property.class));
        verify(propertyPropertiesPanel).go(container);
        verify(propertyPropertiesPanel, never()).removeListener(manager);
    }

    @Test
    public void propertyShouldBeChangedAndListenersNotified() throws Exception {
        manager.addPropertyChangedListener(listener);
        manager.notifyPropertyChangedListeners();

        verify(listener).onPropertyChanged();
    }

    @Test
    public void listenerShouldBeNotifiedWhenPropertyChanged() throws Exception {
        manager.addPropertyChangedListener(listener);
        manager.onPropertyChanged();

        verify(listener).onPropertyChanged();
    }
}