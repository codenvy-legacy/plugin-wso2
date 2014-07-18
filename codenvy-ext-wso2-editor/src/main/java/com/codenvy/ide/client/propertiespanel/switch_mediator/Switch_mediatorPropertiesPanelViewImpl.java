/*
 * Copyright 2014 Codenvy, S.A.
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
package com.codenvy.ide.client.propertiespanel.switch_mediator;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class Switch_mediatorPropertiesPanelViewImpl extends Switch_mediatorPropertiesPanelView {

    interface Switch_mediatorPropertiesPanelViewImplUiBinder extends UiBinder<Widget, Switch_mediatorPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox sourceXpath;
    @UiField
    TextBox caseBranches;

    @Inject
    public Switch_mediatorPropertiesPanelViewImpl(Switch_mediatorPropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getSourceXpath() {
        return String.valueOf(sourceXpath.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceXpath(String sourceXpath) {
        this.sourceXpath.setText(sourceXpath);
    }

    @UiHandler("sourceXpath")
    public void onSourceXpathChanged(KeyUpEvent event) {
        delegate.onSourceXpathChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getCaseBranches() {
        return String.valueOf(caseBranches.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setCaseBranches(String caseBranches) {
        this.caseBranches.setText(caseBranches);
    }

    @UiHandler("caseBranches")
    public void onCaseBranchesChanged(KeyUpEvent event) {
        delegate.onCaseBranchesChanged();
    }

}