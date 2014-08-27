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
package com.codenvy.ide.client.propertiespanel.inline;

import com.codenvy.ide.ui.dialogs.info.Info;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The presenter that provides a business logic of dialog window for editing inline property.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class InlineConfigurationPresenter implements InlineConfigurationView.ActionDelegate {

    private final InlineConfigurationView    view;
    private       ChangeInlineFormatCallBack changeInlineFormatCallBack;

    @Inject
    public InlineConfigurationPresenter(InlineConfigurationView inlineConfigurationView) {
        this.view = inlineConfigurationView;
        this.view.setDelegate(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkClicked(@Nonnull String value) {
        try {
            Document document = XMLParser.parse(value);
            view.closeDialog();
            changeInlineFormatCallBack.onInlineChanged(document.toString());
        } catch (DOMParseException e) {
            Info info = new Info("Malformed xml");
            info.show();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelClicked() {
        view.closeDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void onValueChanged() {
        view.setEnableBtnOk();
    }

    /**
     * Show dialog window for editing inline parameter.
     *
     * @param content
     *         value of inline property
     * @param title
     *         title of dialog window
     * @param callBack
     *         callback which need to set inline value to element
     */
    public void showDialog(@Nonnull String content,
                           @Nonnull String title,
                           @Nonnull ChangeInlineFormatCallBack callBack) {
        changeInlineFormatCallBack = callBack;
        view.setWindowTitle(title);
        view.showDialog(content);
    }

}