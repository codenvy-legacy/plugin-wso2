/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
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
package com.codenvy.ide.ext.wso2.client.wizard.files.view;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.validation.constraints.NotNull;

/**
 * The implementation of {@link CreateResourceView}.
 *
 * @author Andrey Plotnikov
 */
public class CreateResourceViewImpl extends Composite implements CreateResourceView {
    interface CreateResourceViewImplUiBinder extends UiBinder<Widget, CreateResourceViewImpl> {
    }

    @UiField
    Label   resourceNameTitle;
    @UiField
    TextBox resourceName;
    private ActionDelegate delegate;

    @Inject
    public CreateResourceViewImpl(CreateResourceViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setResourceNameTitle(@NotNull String title) {
        resourceNameTitle.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public String getResourceName() {
        return resourceName.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setResourceName(@NotNull String name) {
        resourceName.setText(name);
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("resourceName")
    public void onValueChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }
}