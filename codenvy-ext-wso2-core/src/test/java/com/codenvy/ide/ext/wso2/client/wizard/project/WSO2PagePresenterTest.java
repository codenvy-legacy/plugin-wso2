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
package com.codenvy.ide.ext.wso2.client.wizard.project;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwt.test.Mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class WSO2PagePresenterTest {

    @Mock
    private WSO2PageView      view;
    @Mock
    private AcceptsOneWidget  container;
    @InjectMocks
    private WSO2PagePresenter presenter;

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(view).setDelegate(presenter);
    }

    @Test
    public void noticeShouldBeReturned() throws Exception {
        assertThat(presenter.getNotice(), is(nullValue()));
    }

    @Test
    public void presenterShouldBeCompleted() throws Exception {
        assertThat(presenter.isCompleted(), is(true));
    }

    @Test
    public void viewShouldBeShown() throws Exception {
        presenter.go(container);

        verify(container).setWidget(view);
    }

}