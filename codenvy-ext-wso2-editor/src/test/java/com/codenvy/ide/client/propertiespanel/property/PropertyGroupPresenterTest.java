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
package com.codenvy.ide.client.propertiespanel.property;

import com.codenvy.ide.client.inject.factories.PropertiesGroupFactory;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyGroupPresenterTest {

    private static final String STRING = "some text";

    @Mock
    private AbstractPropertyPresenter property;
    @Mock
    private PropertyGroupView         view;
    @Mock
    private PropertiesGroupFactory    propertiesGroupFactory;

    private PropertyGroupPresenter presenter;

    @Before
    public void setUp() throws Exception {
        when(propertiesGroupFactory.createPropertyGroupView(anyString())).thenReturn(view);

        presenter = new PropertyGroupPresenter(propertiesGroupFactory, STRING);
    }

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(propertiesGroupFactory).createPropertyGroupView(STRING);

        verify(view).setDelegate(presenter);

        verify(view).collapsePropertyGroup();
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void propertyShouldBeAdded() throws Exception {
        presenter.addItem(property);

        verify(view).addProperty(property);
    }

    @Test
    public void propertyShouldBeRemoved() throws Exception {
        presenter.removeItem(property);

        verify(view).removeProperty(property);
    }

    @Test
    public void itemsPanelShouldBeExpended() throws Exception {
        reset(view);

        presenter.onPropertyGroupClicked();

        verify(view).expendPropertyGroup();
        verify(view).setBorderVisible(true);
    }

    @Test
    public void itemsPanelShouldBeNotCollapses() throws Exception {
        presenter.onPropertyGroupClicked();

        reset(view);

        presenter.onPropertyGroupClicked();

        verify(view, never()).expendPropertyGroup();
        verify(view).collapsePropertyGroup();
        verify(view).setBorderVisible(false);
    }

    @Test
    public void itemsPanelShouldBeUnfolded() {
        reset(view);

        presenter.unfold();

        verify(view).collapsePropertyGroup();
    }

    @Test
    public void itemsPanelShouldBeFolded() {
        reset(view);

        presenter.fold();

        verify(view).expendPropertyGroup();
    }

    @Test
    public void visibleTitlePanelShouldBeSet() throws Exception {
        presenter.setTitleVisible(true);

        verify(view).setTitleVisible(true);
    }

    @Test
    public void visibleFirstItemShouldBeChanged() throws Exception {
        presenter.addItem(property);

        verify(view).addProperty(property);

        verify(property).setTopBorderVisible(false);
    }

}