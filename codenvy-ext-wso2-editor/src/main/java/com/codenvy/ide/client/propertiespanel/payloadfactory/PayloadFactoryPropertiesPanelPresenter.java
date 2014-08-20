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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.payload.Arg;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.arguments.AddArgumentCallBack;
import com.codenvy.ide.client.propertiespanel.arguments.ArgumentsConfigPresenter;
import com.codenvy.ide.client.propertiespanel.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.payload.Format.FormatType;
import static com.codenvy.ide.client.elements.payload.Format.MediaType;

/**
 * The property panel of PayloadFactory mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactoryPropertiesPanelPresenter extends AbstractPropertiesPanel<PayloadFactory, PayloadFactoryPropertiesPanelView>
        implements PayloadFactoryPropertiesPanelView.ActionDelegate {

    private final ChangeInlineFormatCallBack   changeInlineFormatCallBack;
    private final ChangeResourceKeyCallBack    changeResourceKeyCallBack;
    private final AddArgumentCallBack          argumentCallBack;
    private final InlineConfigurationPresenter inlineConfigurationPresenter;
    private final ResourceKeyEditorPresenter   resourceKeyEditorPresenter;
    private final ArgumentsConfigPresenter     argumentsConfigPresenter;

    private final WSO2EditorLocalizationConstant locale;

    @Inject
    public PayloadFactoryPropertiesPanelPresenter(PayloadFactoryPropertiesPanelView view,
                                                  PropertyTypeManager propertyTypeManager,
                                                  InlineConfigurationPresenter inlineConfigurationPresenter,
                                                  ResourceKeyEditorPresenter resourceKeyEditorPresenter,
                                                  ArgumentsConfigPresenter argumentsConfigPresenter,
                                                  final WSO2EditorLocalizationConstant locale) {
        super(view, propertyTypeManager);

        this.locale = locale;

        this.inlineConfigurationPresenter = inlineConfigurationPresenter;
        this.resourceKeyEditorPresenter = resourceKeyEditorPresenter;
        this.argumentsConfigPresenter = argumentsConfigPresenter;

        this.changeInlineFormatCallBack = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.getFormat().setFormatInline(inline);

                PayloadFactoryPropertiesPanelPresenter.this.view.setFormat(inline);

                notifyListeners();
            }
        };

        this.changeResourceKeyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.getFormat().setFormatKey(key);

                PayloadFactoryPropertiesPanelPresenter.this.view.setFormatKey(key);

                notifyListeners();
            }
        };

        this.argumentCallBack = new AddArgumentCallBack() {
            @Override
            public void onArgumentsChanged(@Nonnull Array<Arg> args) {
                element.setArgs(args);

                PayloadFactoryPropertiesPanelPresenter.this.view.setArgs(locale.payloadFactoryArguments());

                notifyListeners();
            }
        };

    }

    /** Sets payload format value to element from special place of view and displaying properties panel to a certain value of payload format */
    private void applyPayloadFormat() {
        FormatType payloadFormat = FormatType.valueOf(view.getPayloadFormat());
        element.getFormat().setFormatType(payloadFormat);

        view.setVisibleFormatPanel(FormatType.Inline.equals(payloadFormat));
        view.setVisibleFormatKeyPanel(!FormatType.Inline.equals(payloadFormat));
    }

    /** {@inheritDoc} */
    @Override
    public void onPayloadFormatChanged() {
        applyPayloadFormat();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onMediaTypeChanged() {
        element.getFormat().setMediaType(MediaType.valueOf(view.getMediaType()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void showFormatConfigurationWindow() {
        inlineConfigurationPresenter.showDialog(element.getFormat().getFormatInline(),
                                                locale.inlineTitle(),
                                                changeInlineFormatCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void showArgsConfigurationWindow() {
        argumentsConfigPresenter.showConfigWindow(element.getArgs(), argumentCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void showKeyEditorWindow(@Nonnull String key) {
        resourceKeyEditorPresenter.showDialog(key, changeResourceKeyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setPayloadFormats(propertyTypeManager.getValuesByName(FormatType.TYPE_NAME));
        view.selectPayloadFormat(element.getFormat().getFormatType().name());

        view.setFormat(element.getFormat().getFormatInline());
        view.setFormatKey(element.getFormat().getFormatKey());
        applyPayloadFormat();

        view.setMediaTypes(propertyTypeManager.getValuesByName(MediaType.TYPE_NAME));
        view.selectMediaType(element.getFormat().getMediaType().name());

        view.setDescription(element.getDescription());
    }
}