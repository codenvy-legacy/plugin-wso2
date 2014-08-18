/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
 * All Rights Reserved.
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
package com.codenvy.ide.client.propertiespanel.root;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class RootPropertiesPanelViewImpl extends RootPropertiesPanelView {
    interface RootPropertiesPanelViewImplUiBinder extends UiBinder<Widget, RootPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox onError;

    @Inject
    public RootPropertiesPanelViewImpl(RootPropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getName() {
        return name.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nonnull String name) {
        this.name.setText(name);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getOnError() {
        return onError.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setOnError(@Nonnull String onError) {
        this.onError.setText(onError);
    }

    @UiHandler("name")
    public void onNameChanged(KeyUpEvent event) {
        delegate.onNameChanged();
    }

    @UiHandler("onError")
    public void onOnErrorChanged(KeyUpEvent event) {
        delegate.onOnErrorChanged();
    }

}