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
package com.codenvy.ide.client.propertiespanel.arguments;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.payload.Arg;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.payload.Arg.ArgType;

/**
 * The argument of mediator.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ArgumentsConfigPresenter implements ArgumentsConfigView.ActionDelegate {

    private final WSO2EditorLocalizationConstant local;
    private final ArgumentsConfigView            argView;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          addNameSpacesCallBack;
    private       AddArgumentCallBack            argumentCallBack;
    private       Array<Arg>                     arrayTemporary;
    private       Arg                            selectedArg;
    private       int                            index;

    @Inject
    public ArgumentsConfigPresenter(ArgumentsConfigView argumentsConfigView,
                                    NameSpaceEditorPresenter nameSpacePresenter,
                                    WSO2EditorLocalizationConstant local) {
        this.local = local;
        this.nameSpacePresenter = nameSpacePresenter;
        this.argView = argumentsConfigView;
        this.argView.setDelegate(this);
        this.index = -1;

        this.addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nullable String expression) {
                selectedArg.setExpression(expression);
                selectedArg.setNameSpaces(nameSpaces);
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

        Arg arg = new Arg();
        arg.setType(ArgType.valueOf(type));
        arg.setEvaluator(Arg.Evaluator.valueOf(evaluator));
        arg.setValue(value);

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
        nameSpacePresenter.showWindowWithParameters(selectedArg.getNameSpaces(),
                                                    addNameSpacesCallBack,
                                                    local.argsLabel(),
                                                    selectedArg.getExpression());
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        argView.setValueExpression(selectedArg.getValue());

        argView.setEvaluator();
        argView.selectEvaluator(String.valueOf(selectedArg.getEvaluator()));

        argView.setTypeValue();
        argView.selectType(String.valueOf(selectedArg.getType()));

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