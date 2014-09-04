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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;

/**
 * The abstract view's representation of current connector's properties panel. It provides an ability to show all available properties
 * of the connector and edit it. To display the parameter of connector we need four panels. Depends on current connector, the number
 * of displaying panels may be changed. For displaying or hiding parameter panels of connector we have some methods in our interface.
 * For example setVisibleFirstPanel(boolean isVisible). For detailed settings any panel we have methods which allows show and hide
 * button or set enable and disable text box. For example setEnableFirstTextBox(boolean isEnable) and
 * setVisibleFirstButton(boolean isVisible). Each panel has its index number which corresponds to index number of connector's parameter
 * on properties panel.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(GeneralConnectorPanelViewImpl.class)
public abstract class GeneralConnectorPanelView extends AbstractView<GeneralConnectorPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link GeneralConnectorPanelPresenter} which
     * calls from the view. These methods defines some actions when user change properties of connector.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed parameter editor type field. */
        void onParameterEditorTypeChanged();

        /** Performs any actions appropriate in response to the user having changed available configs field. */
        void onAvailableConfigsChanged();

        /** Performs any actions appropriate in response to the user having changed configRef field. */
        void onConfigRefChanged();

        /** Performs any actions appropriate in response to the showing connector configuration parameter window. */
        void onCreateNewConfigBtnClicked();

        /** Performs any actions appropriate in response to the user changes value of first text box on view. */
        void onFirstTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of second text box on view. */
        void onSecondTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of third text box on view. */
        void onThirdTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of fourth text box on view. */
        void onFourthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user clicked button on first panel of view. */
        void onFirstButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on second panel of view. */
        void onSecondButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on third panel of view. */
        void onThirdButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on fourth panel of view. */
        void onFourthButtonClicked();

    }

    /** @return parameter editor type value from the special place on the view */
    @Nonnull
    public abstract String getParameterEditorType();

    /** @return parameter available config from the special place on the view */
    @Nonnull
    public abstract String getAvailableConfig();

    /**
     * Set parameter editor types value.
     *
     * @param states
     *         values of parameter editor type
     */
    public abstract void setParameterEditorType(@Nonnull List<String> states);

    /**
     * Sets available configs to the special place on the view which uses for showing Available Config parameter.
     *
     * @param configs
     *         all available configs
     */
    public abstract void setAvailableConfigs(@Nonnull List<String> configs);

    /**
     * Add available config.
     *
     * @param state
     *         value of available config
     */
    public abstract void addAvailableConfigs(@Nonnull String state);

    /** @return configuration reference value from the special place on the view which uses for showing configRef parameter */
    @Nonnull
    public abstract String getConfigRef();

    /**
     * Sets configuration reference value to the special place on the view which uses for showing password parameter.
     *
     * @param configRef
     *         value of configuration reference
     */
    public abstract void setConfigRef(@Nonnull String configRef);

    /**
     * Select parameter editor type to the special place on the view.
     *
     * @param parameterEditorType
     *         parameter value
     */
    public abstract void selectParameterEditorType(@Nonnull ParameterEditorType parameterEditorType);

    /** @return value of parameter from first panel's text box view */
    @Nonnull
    public abstract String getFirstTextBoxValue();

    /**
     * Sets value of parameter to first panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    public abstract void setFirstTextBoxValue(@Nonnull String value);

    /** @return value of parameter from second panel's text box view */
    @Nonnull
    public abstract String getSecondTextBoxValue();

    /**
     * Sets value of parameter to second panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    public abstract void setSecondTextBoxValue(@Nonnull String value);

    /** @return value of parameter from third panel's text box view */
    @Nonnull
    public abstract String getThirdTextBoxValue();

    /**
     * Sets value of parameter to third panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    public abstract void setThirdTextBoxValue(@Nonnull String value);

    /** @return value of parameter from fourth panel's text box view */
    @Nonnull
    public abstract String getFourthTextBoxValue();

    /**
     * Sets value of parameter to fourth panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    public abstract void setFourthTextBoxValue(@Nonnull String value);

    public abstract void setVisibleFirstPanel(boolean isVisible);

    public abstract void setVisibleSecondPanel(boolean isVisible);

    public abstract void setVisibleThirdPanel(boolean isVisible);

    public abstract void setVisibleFourthPanel(boolean isVisible);

    public abstract void setFirstLabelTitle(@Nonnull String title);

    public abstract void setSecondLabelTitle(@Nonnull String title);

    public abstract void setThirdLabelTitle(@Nonnull String title);

    public abstract void setFourthLabelTitle(@Nonnull String title);

    public abstract void setEnableFirstTextBox(boolean isEnable);

    public abstract void setEnableSecondTextBox(boolean isEnable);

    public abstract void setEnableThirdTextBox(boolean isEnable);

    public abstract void setEnableFourthTextBox(boolean isEnable);

    public abstract void setVisibleFirstButton(boolean isVisible);

    public abstract void setVisibleSecondButton(boolean isVisible);

    public abstract void setVisibleThirdButton(boolean isVisible);

    public abstract void setVisibleFourthButton(boolean isVisible);

}
