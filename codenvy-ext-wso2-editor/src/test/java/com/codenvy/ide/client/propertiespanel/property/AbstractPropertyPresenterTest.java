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

import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractPropertyPresenterTest<P extends AbstractPropertyPresenter, V extends AbstractPropertyView> {

    protected V view;
    protected P presenter;

    @Test
    public void viewShouldBePrepared() throws Exception {
        verify(view).setDelegate(presenter);
    }

    @Test
    public void viewShouldBeReturned() throws Exception {
        assertEquals(view, presenter.getView());
    }

    @Test
    public void titleShouldBeSet() throws Exception {
        final String title = "title";

        presenter.setTitle(title);

        verify(view).setTitle(title);
    }

    @Test
    public void widgetVisibleShouldBeSet() throws Exception {
        presenter.setVisible(true);

        verify(view).setVisible(true);
    }

    @Test
    public void borderVisibleShouldBeSet() throws Exception {
        presenter.setTopBorderVisible(true);

        verify(view).setBorderVisible(true);
    }

}