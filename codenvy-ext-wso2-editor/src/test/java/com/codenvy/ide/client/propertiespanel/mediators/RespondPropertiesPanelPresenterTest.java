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

import com.codenvy.ide.client.elements.mediators.LoopBack;
import com.codenvy.ide.client.elements.mediators.Respond;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanelTest;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
public class RespondPropertiesPanelPresenterTest extends AbstractPropertiesPanelTest<RespondPropertiesPanelPresenter> {

    private static final String GROUP_TITLE = "Miscellaneous";
    private static final String ITEM_TITLE  = "Description";

    @Mock
    private PropertyGroupPresenter  groupPresenter;
    @Mock
    private SimplePropertyPresenter description;
    @Mock
    private Respond                 element;

    private PropertyValueChangedListener descriptionListener;

    @Before
    public void setUp() throws Exception {
        prepareGroupAndItems();

        presenter = new RespondPropertiesPanelPresenter(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);

        presenter.addListener(listener);
        presenter.setElement(element);

        descriptionListener = getSimplePropertyChangedListener(ITEM_TITLE);
    }

    private void prepareGroupAndItems() {
        when(locale.miscGroupTitle()).thenReturn(GROUP_TITLE);
        when(locale.description()).thenReturn(ITEM_TITLE);

        prepareCreatingGroup(GROUP_TITLE, groupPresenter);
        prepareCreatingSimpleProperty(ITEM_TITLE, description);
    }

    @Override
    public void groupsShouldBeShown() {
        presenter.go(container);

        verify(container).setWidget(view);

        oneGroupShouldBeUnfolded(groupPresenter);
    }

    @Test
    public void descriptionPropertyListenerShouldBeDone() throws Exception {
        descriptionListener.onPropertyChanged(ITEM_TITLE);

        propertyChangedGeneralListenerShouldBeExecuted(element, LoopBack.DESCRIPTION, ITEM_TITLE);
    }

}