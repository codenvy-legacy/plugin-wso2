/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.Send;
import com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer;
import com.codenvy.ide.client.inject.factories.PropertiesPanelWidgetFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Send.BUILD_MESSAGE;
import static com.codenvy.ide.client.elements.mediators.Send.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.Send.DYNAMIC_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Send.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Send.SEQUENCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.Send.SKIP_SERIALIZATION;
import static com.codenvy.ide.client.elements.mediators.Send.STATIC_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.DYNAMIC;
import static com.codenvy.ide.client.elements.mediators.Send.SequenceType.STATIC;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Send mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SendPropertiesPanelPresenter extends AbstractPropertiesPanel<Send, PropertiesPanelView> {
    private final ResourceKeyEditorPresenter     keyPresenter;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final WSO2EditorLocalizationConstant locale;

    private ListPropertyPresenter    skipSerialization;
    private ListPropertyPresenter    receivingSequencerType;
    private ListPropertyPresenter    buildMessageBeforeSending;
    private ComplexPropertyPresenter dynamicRec;
    private ComplexPropertyPresenter staticRec;
    private SimplePropertyPresenter  description;

    @Inject
    public SendPropertiesPanelPresenter(PropertiesPanelView view,
                                        PropertyTypeManager propertyTypeManager,
                                        ResourceKeyEditorPresenter keyPresenter,
                                        NameSpaceEditorPresenter nameSpacePresenter,
                                        WSO2EditorLocalizationConstant locale,
                                        PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                                        Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                                        SimplePropertyPresenter description,
                                        Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {

        super(view, propertyTypeManager);

        this.locale = locale;
        this.keyPresenter = keyPresenter;
        this.nameSpaceEditorPresenter = nameSpacePresenter;

        prepareView(propertiesPanelWidgetFactory,
                    listPropertyPresenterProvider,
                    description,
                    complexPropertyPresenterProvider);
    }

    private void prepareView(@Nonnull PropertiesPanelWidgetFactory propertiesPanelWidgetFactory,
                             @Nonnull Provider<ListPropertyPresenter> listPropertyPresenterProvider,
                             @Nonnull SimplePropertyPresenter description,
                             @Nonnull Provider<ComplexPropertyPresenter> complexPropertyPresenterProvider) {
        PropertyGroupPresenter basicGroup = propertiesPanelWidgetFactory.createPropertyGroupPresenter(locale.miscGroupTitle());
        this.view.addGroup(basicGroup);

        skipSerialization = listPropertyPresenterProvider.get();
        skipSerialization.setTitle(locale.skipSerialization());
        skipSerialization.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                Boolean skipSerialization = Boolean.valueOf(property);

                element.putProperty(SKIP_SERIALIZATION, skipSerialization);
                showSerializationFields(skipSerialization);
            }
        });

        basicGroup.addItem(skipSerialization);

        receivingSequencerType = listPropertyPresenterProvider.get();
        receivingSequencerType.setTitle(locale.receivingSequenceType());
        receivingSequencerType.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                applySequenceType(SequenceType.getItemByValue(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(receivingSequencerType);

        buildMessageBeforeSending = listPropertyPresenterProvider.get();
        buildMessageBeforeSending.setTitle(locale.buildMessageBeforeSending());
        buildMessageBeforeSending.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(BUILD_MESSAGE, Boolean.valueOf(property));

                notifyListeners();
            }
        });

        basicGroup.addItem(buildMessageBeforeSending);

        final AddNameSpacesCallBack addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(DYNAMIC_EXPRESSION, expression);
                element.putProperty(NAMESPACES, nameSpaces);

                dynamicRec.setProperty(expression);

                notifyListeners();
            }
        };

        dynamicRec = complexPropertyPresenterProvider.get();
        dynamicRec.setTitle(locale.dynamicReceivingSequence());
        dynamicRec.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(NAMESPACES);
                String expression = element.getProperty(DYNAMIC_EXPRESSION);

                if (expression != null && nameSpaces != null) {
                    nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                      addNameSpacesCallBack,
                                                                      locale.sendNamespaceLabel(),
                                                                      expression);
                }
            }
        });

        basicGroup.addItem(dynamicRec);

        final ChangeResourceKeyCallBack keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.putProperty(STATIC_EXPRESSION, key);

                staticRec.setProperty(key);

                notifyListeners();
            }
        };

        staticRec = complexPropertyPresenterProvider.get();
        staticRec.setTitle(locale.staticReceivingSequence());
        staticRec.addEditButtonClickedListener(new ComplexPropertyPresenter.EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String expression = element.getProperty(STATIC_EXPRESSION);
                if (expression != null) {
                    keyPresenter.showDialog(expression, keyCallBack);
                }
            }
        });

        basicGroup.addItem(staticRec);

        this.description = description;
        this.description.setTitle(locale.addressEndpointDescription());
        this.description.addPropertyValueChangedListener(new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        });

        basicGroup.addItem(description);
    }

    private void showSerializationFields(@Nonnull Boolean skipSerialization) {
        element.putProperty(SKIP_SERIALIZATION, skipSerialization);

        dynamicRec.setVisible(DYNAMIC.equals(element.getProperty(SEQUENCE_TYPE)) & !skipSerialization);
        staticRec.setVisible(STATIC.equals(element.getProperty(SEQUENCE_TYPE)) & !skipSerialization);
        receivingSequencerType.setVisible(!skipSerialization);
        buildMessageBeforeSending.setVisible(!skipSerialization);
        description.setVisible(!skipSerialization);

        notifyListeners();
    }

    /**
     * Sets sequence type value to element from special place of view and displaying properties panel to a certain value of sequence
     * type
     */
    private void applySequenceType(@Nonnull SequenceType sequenceType) {
        element.putProperty(SEQUENCE_TYPE, sequenceType);

        setDefaultPanelView();

        switch (sequenceType) {
            case STATIC:
                dynamicRec.setVisible(false);
                break;

            case DYNAMIC:
                staticRec.setVisible(false);
                break;

            default:
                staticRec.setVisible(false);
                dynamicRec.setVisible(false);
                break;
        }
    }

    /** Sets default value of panel visibility */
    private void setDefaultPanelView() {
        receivingSequencerType.setVisible(true);
        buildMessageBeforeSending.setVisible(true);
        dynamicRec.setVisible(true);
        staticRec.setVisible(true);
        description.setVisible(true);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        List<String> booleanValues = propertyTypeManager.getValuesByName(CommonPropertyTypeInitializer.BOOLEAN_TYPE_NAME);

        receivingSequencerType.setValues(propertyTypeManager.getValuesByName(SequenceType.TYPE_NAME));
        SequenceType sType = element.getProperty(SEQUENCE_TYPE);
        if (sType != null) {
            receivingSequencerType.selectValue(sType.getValue());
            applySequenceType(sType);
        }

        skipSerialization.setValues(booleanValues);
        Boolean serialization = element.getProperty(SKIP_SERIALIZATION);
        if (serialization != null) {
            skipSerialization.selectValue(serialization.toString());
            showSerializationFields(serialization);
        }

        buildMessageBeforeSending.setValues(booleanValues);
        buildMessageBeforeSending.selectValue(String.valueOf(element.getProperty(BUILD_MESSAGE)));

        String description = element.getProperty(DESCRIPTION);
        this.description.setProperty(description != null ? description : "");

        String staticExpression = element.getProperty(STATIC_EXPRESSION);
        staticRec.setProperty(staticExpression != null ? staticExpression : "");

        String dynamicExpression = element.getProperty(DYNAMIC_EXPRESSION);
        dynamicRec.setProperty(dynamicExpression != null ? dynamicExpression : "");
    }

}