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
package com.codenvy.ide.client.propertiespanel.sequence;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class SequencePropertiesPanelViewImpl extends SequencePropertiesPanelView {

    interface SequencePropertiesPanelViewImplUiBinder extends UiBinder<Widget, SequencePropertiesPanelViewImpl> {
    }

    @UiField
    ListBox referringSequenceType;
    @UiField
    TextBox staticReferenceKey;

    @Inject
    public SequencePropertiesPanelViewImpl(SequencePropertiesPanelViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public String getReferringSequenceType() {
        int index = referringSequenceType.getSelectedIndex();
        return index != -1 ? referringSequenceType.getValue(referringSequenceType.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setReferringSequenceType(List<String> referringSequenceType) {
        if (referringSequenceType == null) {
            return;
        }
        this.referringSequenceType.clear();
        for (String value : referringSequenceType) {
            this.referringSequenceType.addItem(value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void selectReferringSequenceType(String referringSequenceType) {
        for (int i = 0; i < this.referringSequenceType.getItemCount(); i++) {
            if (this.referringSequenceType.getValue(i).equals(referringSequenceType)) {
                this.referringSequenceType.setItemSelected(i, true);
                return;
            }
        }
    }

    @UiHandler("referringSequenceType")
    public void onReferringSequenceTypeChanged(ChangeEvent event) {
        delegate.onReferringSequenceTypeChanged();
    }

    /** {@inheritDoc} */
    @Override
    public String getStaticReferenceKey() {
        return String.valueOf(staticReferenceKey.getText());
    }

    /** {@inheritDoc} */
    @Override
    public void setStaticReferenceKey(String staticReferenceKey) {
        this.staticReferenceKey.setText(staticReferenceKey);
    }

    @UiHandler("staticReferenceKey")
    public void onStaticReferenceKeyChanged(KeyUpEvent event) {
        delegate.onStaticReferenceKeyChanged();
    }

}