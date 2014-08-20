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
package com.codenvy.ide.client.propertiespanel.switchmediator.branch;

import com.codenvy.ide.client.EditorResources;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 */
public class BranchFiledViewImpl extends BranchFiledView {

    @Singleton
    interface BranchFiledViewImplUiBinder extends UiBinder<Widget, BranchFiledViewImpl> {
    }

    @UiField
    TextBox regExp;
    @UiField
    Label   caseTitle;

    @UiField(provided = true)
    final EditorResources res;

    @Inject
    public BranchFiledViewImpl(BranchFiledViewImplUiBinder ourUiBinder, EditorResources res) {
        this.res = res;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
        caseTitle.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setRegExp(@Nullable String regExp) {
        this.regExp.setText(regExp);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getRegExp() {
        return regExp.getText();
    }

    @UiHandler("regExp")
    public void onRegExpChanged(KeyUpEvent event) {
        delegate.onRegExpChanged();
    }

}