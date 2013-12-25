/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.action;

import com.codenvy.ide.api.ui.action.ActionEvent;
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