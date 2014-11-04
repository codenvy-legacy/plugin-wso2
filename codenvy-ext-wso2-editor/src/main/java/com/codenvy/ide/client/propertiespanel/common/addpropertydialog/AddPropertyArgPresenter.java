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
package com.codenvy.ide.client.propertiespanel.common.addpropertydialog;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyView;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EVALUATOR;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of Arg element of Payload
 * mediator properties. Logic which provides the class allows add, remove and edit properties of Address endpoint via
 * special dialog window.
 *
 * @author Dmitry Shnurenko
 */
public class AddPropertyArgPresenter extends AddPropertyPresenter<Arg> {

    private final static int DIALOG_HEIGHT = 120;

    private final AddNameSpacesCallBack nameSpacesCallBack;

    private SimplePropertyPresenter  value;
    private ComplexPropertyPresenter expression;
    private ListPropertyPresenter    type;
    private ListPropertyPresenter    evaluator;

    @Inject
    public AddPropertyArgPresenter(AddPropertyView view,
                                   PropertyPanelFactory propertyPanelFactory,
                                   PropertyTypeManager propertyTypeManager,
                                   Provider<Arg> propertyProvider,
                                   NameSpaceEditorPresenter nameSpacePresenter,
                                   WSO2EditorLocalizationConstant local) {

        super(view, propertyPanelFactory, propertyTypeManager, nameSpacePresenter, local, propertyProvider);

        nameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String valueExpression) {
                selectedProperty.putProperty(ARG_NAMESPACES, nameSpaces);
                selectedProperty.putProperty(ARG_EXPRESSION, valueExpression);

                expression.setProperty(valueExpression);
            }
        };

        initializePanels();
    }

    private void initializePanels() {
        value = createSimplePanel(ARG_VALUE, local.columnValue());

        expression = createComplexPanel(ARG_NAMESPACES, ARG_EXPRESSION, nameSpacesCallBack, local.columnExpression());

        PropertyValueChangedListener typeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                ArgType type = ArgType.getItemByValue(property);

                selectedProperty.putProperty(ARG_TYPE, type);

                boolean visible = EXPRESSION.equals(type);

                expression.setVisible(visible);
                value.setVisible(!visible);
                evaluator.setVisible(visible);
            }
        };
        type = propertyPanelFactory.createListProperty(local.columnType(), typeListener);

        PropertyValueChangedListener evaluatorListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                selectedProperty.putProperty(ARG_EVALUATOR, Evaluator.getItemByValue(property));
            }
        };
        evaluator = propertyPanelFactory.createListProperty(local.columnEvaluator(), evaluatorListener);

        view.addPanel(value);
        view.addPanel(expression);
        view.addPanel(type);
        view.addPanel(evaluator);

        view.setDialogHeight(DIALOG_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog(@Nullable Arg arg, @Nonnull AddPropertyCallBack<Arg> callBack) {
        this.callBack = callBack;

        selectedProperty = arg == null ? propertyProvider.get() : arg.copy();

        ArgType argType = selectedProperty.getProperty(ARG_TYPE);
        Evaluator evaluatorType = selectedProperty.getProperty(ARG_EVALUATOR);

        if (argType == null || evaluatorType == null) {
            return;
        }

        boolean isVisible = EXPRESSION.equals(argType);

        expression.setVisible(isVisible);
        evaluator.setVisible(isVisible);
        value.setVisible(!isVisible);

        value.setProperty(selectedProperty.getProperty(ARG_VALUE));
        expression.setProperty(selectedProperty.getProperty(ARG_EXPRESSION));

        type.setValues(propertyTypeManager.getValuesByName(ArgType.TYPE_NAME));
        type.selectValue(argType.getValue());

        evaluator.setValues(propertyTypeManager.getValuesByName(Evaluator.TYPE_NAME));
        evaluator.selectValue(evaluatorType.getValue());

        view.setTitle(local.propertyArgEditTableTitle());

        view.showWindow();
    }

}