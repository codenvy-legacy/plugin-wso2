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
package com.codenvy.ide.client.propertiespanel.mediators.arguments;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EVALUATOR;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.ArgType;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.Evaluator;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of element's arguments.
 * Logic which provides the class allows add, remove and edit argument properties of mediator via special dialog window.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class ArgumentsConfigPresenter implements ArgumentsConfigView.ActionDelegate {

    private final WSO2EditorLocalizationConstant local;
    private final ArgumentsConfigView            argView;
    private final Provider<Arg>                  argProvider;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;

    private AddArgumentCallBack argumentCallBack;
    private Array<Arg>          arrayTemporary;
    private Arg                 selectedArg;
    private int                 index;

    @Inject
    public ArgumentsConfigPresenter(ArgumentsConfigView argumentsConfigView,
                                    NameSpaceEditorPresenter nameSpacePresenter,
                                    WSO2EditorLocalizationConstant local,
                                    Provider<Arg> argProvider) {
        this.local = local;
        this.nameSpacePresenter = nameSpacePresenter;
        this.argView = argumentsConfigView;
        this.argProvider = argProvider;
        this.argView.setDelegate(this);
        this.index = -1;

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                selectedArg.putProperty(ARG_EXPRESSION, expression);
                selectedArg.putProperty(ARG_NAMESPACES, nameSpaces);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onSelectedArg(@Nonnull Arg arg) {
        this.selectedArg = arg;
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelButtonClicked() {
        argView.hideWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void onAddArgButtonClicked() {
        String value = argView.getValueExpression().isEmpty() ? "default" : argView.getValueExpression();
        String evaluator = argView.getEvaluator().isEmpty() ? "xml" : argView.getEvaluator();
        String type = argView.getTypeValue().isEmpty() ? "Value" : argView.getTypeValue();

        Arg arg = argProvider.get();
        arg.putProperty(ARG_TYPE, ArgType.getItemByValue(type));
        arg.putProperty(ARG_EVALUATOR, Evaluator.getItemByValue(evaluator));
        arg.putProperty(ARG_VALUE, value);

        argView.setValueExpression("");
        argView.clearEvaluator();

        if (index != -1) {
            arrayTemporary.set(index, arg);
            index = -1;
        } else {
            arrayTemporary.add(arg);
        }

        argView.setArgs(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoveArgButtonClicked() {
        arrayTemporary.remove(selectedArg);

        argView.setArgs(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditArgsButtonClicked() {
        Array<NameSpace> nameSpaces = selectedArg.getProperty(ARG_NAMESPACES);

        if (nameSpaces == null) {
            return;
        }

        nameSpacePresenter.showWindowWithParameters(nameSpaces,
                                                    addNameSpacesCallBack,
                                                    local.argsLabel(),
                                                    selectedArg.getProperty(ARG_EXPRESSION));
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        String expression = selectedArg.getProperty(ARG_EXPRESSION);
        Evaluator evaluator = selectedArg.getProperty(ARG_EVALUATOR);
        ArgType argType = selectedArg.getProperty(ARG_TYPE);

        if (expression == null || evaluator == null || argType == null) {
            return;
        }

        argView.setValueExpression(expression);

        argView.setEvaluator();
        argView.selectEvaluator(evaluator.getValue());

        argView.setTypeValue();
        argView.selectType(argType.getValue());

        index = arrayTemporary.indexOf(selectedArg);

        argView.setArgs(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onOkButtonClicked() {
        argumentCallBack.onArgumentsChanged(arrayTemporary);

        argView.hideWindow();
    }

    /**
     * Shows dialog window for editing properties.
     */
    public void showConfigWindow(@Nonnull Array<Arg> args, @Nonnull AddArgumentCallBack callback) {
        arrayTemporary = Collections.createArray();
        argumentCallBack = callback;

        for (Arg arg : args.asIterable()) {
            arrayTemporary.add(arg);
        }

        argView.setArgs(arrayTemporary);

        argView.showWindow();
    }
}