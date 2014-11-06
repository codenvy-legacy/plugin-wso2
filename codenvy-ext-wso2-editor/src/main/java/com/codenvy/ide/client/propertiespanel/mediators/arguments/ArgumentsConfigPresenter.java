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
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.AddPropertyArgPresenter;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.AddPropertyCallBack;
import com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general.AddPropertyPresenter;
import com.codenvy.ide.ui.dialogs.DialogFactory;
import com.codenvy.ide.ui.dialogs.message.MessageDialog;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.Arg.ARG_VALUE;
import static com.codenvy.ide.client.elements.mediators.payload.Arg.copyArgsList;

/**
 * The class provides the business logic that allows editor to react on user's action related to change of element's arguments.
 * Logic which provides the class allows add, remove and edit argument properties of mediator via special dialog window.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
public class ArgumentsConfigPresenter implements ArgumentsConfigView.ActionDelegate {

    private final ArgumentsConfigView       argView;
    private final AddPropertyPresenter<Arg> addPropertyArgPresenter;
    private final AddPropertyCallBack<Arg>  addPropertyCallBack;
    private final AddPropertyCallBack<Arg>  editPropertyCallBack;

    private AddArgumentCallBack argumentCallBack;
    private List<Arg>           arrayTemporary;
    private Arg                 selectedArg;

    @Inject
    public ArgumentsConfigPresenter(ArgumentsConfigView argumentsConfigView,
                                    AddPropertyArgPresenter addPropertyArgPresenter,
                                    WSO2EditorLocalizationConstant locale,
                                    DialogFactory dialogFactory) {

        this.argView = argumentsConfigView;
        this.addPropertyArgPresenter = addPropertyArgPresenter;
        this.argView.setDelegate(this);

        final MessageDialog errorMessageDialog =
                dialogFactory.createMessageDialog(locale.errorMessage(), locale.nameAlreadyExistsError(), null);

        this.addPropertyCallBack = new AddPropertyCallBack<Arg>() {
            @Override
            public void onPropertyChanged(@Nonnull Arg property) {
                if (!arrayTemporary.contains(property)) {
                    arrayTemporary.add(property);

                    ArgumentsConfigPresenter.this.argView.setArgs(arrayTemporary);

                    ArgumentsConfigPresenter.this.addPropertyArgPresenter.hideDialog();
                } else {
                    errorMessageDialog.show();
                }
            }
        };

        this.editPropertyCallBack = new AddPropertyCallBack<Arg>() {
            @Override
            public void onPropertyChanged(@Nonnull Arg property) {
                int index = arrayTemporary.indexOf(selectedArg);

                String innerPropertyName = property.getProperty(ARG_VALUE);
                String selectedPropertyName = selectedArg.getProperty(ARG_VALUE);

                if (innerPropertyName == null || selectedPropertyName == null) {
                    return;
                }

                if (innerPropertyName.equals(selectedPropertyName) || !arrayTemporary.contains(property)) {
                    arrayTemporary.set(index, property);

                    ArgumentsConfigPresenter.this.argView.setArgs(arrayTemporary);

                    ArgumentsConfigPresenter.this.addPropertyArgPresenter.hideDialog();

                } else {
                    errorMessageDialog.show();
                }
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
        addPropertyArgPresenter.showDialog(null, addPropertyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void onRemoveArgButtonClicked() {
        arrayTemporary.remove(selectedArg);

        argView.setArgs(arrayTemporary);
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        addPropertyArgPresenter.showDialog(selectedArg, editPropertyCallBack);
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
    public void showConfigWindow(@Nonnull List<Arg> args, @Nonnull AddArgumentCallBack callback) {
        arrayTemporary = copyArgsList(args);
        argumentCallBack = callback;

        argView.setArgs(arrayTemporary);

        argView.showWindow();
    }
}