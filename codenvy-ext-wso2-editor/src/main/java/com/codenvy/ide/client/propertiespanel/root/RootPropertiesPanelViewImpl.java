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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of 'Root' property panel for editing property of 'Root' element.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class RootPropertiesPanelViewImpl extends RootPropertiesPanelView {

    @Singleton
    interface RootPropertiesPanelViewImplUiBinder extends UiBinder<Widget, RootPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox onError;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public RootPropertiesPanelViewImpl(RootPropertiesPanelViewImplUiBinder ourUiBinder,
                                       EditorResources res,
                                       WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

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