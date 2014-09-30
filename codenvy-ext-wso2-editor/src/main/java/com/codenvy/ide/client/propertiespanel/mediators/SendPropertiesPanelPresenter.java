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
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
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
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Send mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SendPropertiesPanelPresenter extends AbstractPropertiesPanel<Send> {

    private final ResourceKeyEditorPresenter keyPresenter;
    private final NameSpaceEditorPresenter   nameSpaceEditorPresenter;

    private final AddNameSpacesCallBack     addNameSpacesCallBack;
    private final ChangeResourceKeyCallBack keyCallBack;

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
                                        PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.keyPresenter = keyPresenter;
        this.nameSpaceEditorPresenter = nameSpacePresenter;

        addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(DYNAMIC_EXPRESSION, expression);
                element.putProperty(NAMESPACES, nameSpaces);

                dynamicRec.setProperty(expression);

                notifyListeners();
            }
        };

        keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.putProperty(STATIC_EXPRESSION, key);

                staticRec.setProperty(key);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener skipSerializationListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                Boolean skipSerialization = Boolean.valueOf(property);

                element.putProperty(SKIP_SERIALIZATION, skipSerialization);
                showSerializationFields(skipSerialization);
            }
        };
        skipSerialization = createListProperty(basicGroup, locale.skipSerialization(), skipSerializationListener);

        PropertyValueChangedListener recevSequenceListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                applySequenceType(SequenceType.getItemByValue(property));

                notifyListeners();
            }
        };
        receivingSequencerType = createListProperty(basicGroup, locale.receivingSequenceType(), recevSequenceListener);

        PropertyValueChangedListener buildMessageListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(BUILD_MESSAGE, Boolean.valueOf(property));

                notifyListeners();
            }
        };
        buildMessageBeforeSending = createListProperty(basicGroup, locale.buildMessageBeforeSending(), buildMessageListener);

        EditButtonClickedListener dynamicRecListener = new EditButtonClickedListener() {
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
        };
        dynamicRec = createComplexProperty(basicGroup, locale.dynamicReceivingSequence(), dynamicRecListener);

        EditButtonClickedListener staticRecListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String expression = element.getProperty(STATIC_EXPRESSION);
                if (expression != null) {
                    keyPresenter.showDialog(expression, keyCallBack);
                }
            }
        };
        staticRec = createComplexProperty(basicGroup, locale.staticReceivingSequence(), staticRecListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(basicGroup, locale.description(), descriptionListener);
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

        displayReceiveSequenceTypeParameter();

        displaySkipSerializeParameter(booleanValues);

        buildMessageBeforeSending.setValues(booleanValues);
        buildMessageBeforeSending.selectValue(String.valueOf(element.getProperty(BUILD_MESSAGE)));

        String descriptionValue = element.getProperty(DESCRIPTION);
        description.setProperty(descriptionValue != null ? descriptionValue : "");

        String staticExpression = element.getProperty(STATIC_EXPRESSION);
        staticRec.setProperty(staticExpression != null ? staticExpression : "");

        String dynamicExpression = element.getProperty(DYNAMIC_EXPRESSION);
        dynamicRec.setProperty(dynamicExpression != null ? dynamicExpression : "");
    }

    private void displayReceiveSequenceTypeParameter() {
        receivingSequencerType.setValues(propertyTypeManager.getValuesByName(SequenceType.TYPE_NAME));
        SequenceType sType = element.getProperty(SEQUENCE_TYPE);
        if (sType == null) {
            return;
        }

        applySequenceType(sType);

        receivingSequencerType.selectValue(sType.getValue());
    }

    private void displaySkipSerializeParameter(@Nonnull List<String> booleanValues) {
        skipSerialization.setValues(booleanValues);
        Boolean serialization = element.getProperty(SKIP_SERIALIZATION);
        if (serialization == null) {
            return;
        }

        showSerializationFields(serialization);

        skipSerialization.selectValue(serialization.toString());
    }

}