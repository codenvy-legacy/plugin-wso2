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
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.action.ActionEvent;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.upload.ImportFilePresenter;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link ImportSynapseAction}.
 *
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ImportSynapseActionTest {

    @Mock
    private Provider<ImportFilePresenter> importFilePresenter;
    @Mock
    private ImportFilePresenter           presenter;
    @Mock
    private WSO2Resources                 wso2Resources;
    @Mock
    private LocalizationConstant          locale;
    @Mock
    private ActionEvent                   actionEvent;
    @InjectMocks
    private ImportSynapseAction           action;

    @Before
    public void setUp() throws Exception {
        when(importFilePresenter.get()).thenReturn(presenter);

        verify(locale).wso2ImportSynapseConfig();
        verify(locale).wso2ImportActionDescription();
        verify(wso2Resources).synapseIcon();
    }

    @Test
    public void importWindowShouldBeShow() {
        action.actionPerformed(actionEvent);

        verify(presenter).showDialog();
    }
}