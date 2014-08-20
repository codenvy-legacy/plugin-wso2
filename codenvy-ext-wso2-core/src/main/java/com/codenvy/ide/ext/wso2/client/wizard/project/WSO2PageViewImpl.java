/*
 * Copyright [2014] Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.wso2.client.wizard.project;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The implementation of {@link WSO2PageView}.
 *
 * @author Valeriy Svydenko
 */
public class WSO2PageViewImpl extends Composite implements WSO2PageView {

    @Inject
    public WSO2PageViewImpl(WSO2PageViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
    }

    @Singleton
    interface WSO2PageViewImplUiBinder extends UiBinder<DockLayoutPanel, WSO2PageViewImpl> {
    }

}
