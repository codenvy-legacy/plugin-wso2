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
package com.codenvy.ide.client.propertiespanel.switchmediator;

import com.codenvy.ide.client.propertiespanel.switchmediator.branch.BranchFiledPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 */
public class SwitchPropertiesPanelViewImpl extends SwitchPropertiesPanelView {
    interface Switch_mediatorPropertiesPanelViewImplUiBinder extends UiBinder<Widget, SwitchPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox   sourceXpath;
    @UiField
    FlowPanel regexpPanel;

    @Inject
    public SwitchPropertiesPanelViewImpl(Switch_mediatorPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setSourceXpath(@Nullable String sourceXpath) {
        this.sourceXpath.setText(sourceXpath);
    }

    /** {@inheritDoc} */
    @Override
    public void addBranchField(@Nonnull BranchFiledPresenter branchFiled) {
        regexpPanel.add(branchFiled.getView());
    }

    /** {@inheritDoc} */
    @Override
    public void removeBranchFields() {
        regexpPanel.clear();
    }

    /** {@inheritDoc} */
    @Override
    protected void onDetach() {
        delegate.onWidgetDetached();
    }

    @UiHandler("btnSourceXpath")
    public void onEditXpathButtonClicked(ClickEvent event) {
        delegate.onEditXpathButtonClicked();
    }

}