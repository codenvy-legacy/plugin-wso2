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
package com.codenvy.ide.client.propertiespanel.connectors.base;

import com.codenvy.ide.api.mvp.View;
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
@ImplementedBy(GeneralPropertiesPanelViewImpl.class)
public interface GeneralPropertiesPanelView extends View<GeneralPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of general presenter of current type of connectors which
     * calls from the view. These methods defines some actions when user change properties of connector.
     */
    public interface ActionDelegate {

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

        /** Performs any actions appropriate in response to the user changes value of fifth text box on view. */
        void onFifthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of sixes text box on view. */
        void onSixthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of seventh text box on view. */
        void onSeventhTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of eighth text box on view. */
        void onEighthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of ninth text box on view. */
        void onNinthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of tenth text box on view. */
        void onTenthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of eleventh text box on view. */
        void onEleventhTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user clicked button on first panel of view. */
        void onFirstButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on second panel of view. */
        void onSecondButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on third panel of view. */
        void onThirdButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on fourth panel of view. */
        void onFourthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on fifth panel of view. */
        void onFifthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on sixes panel of view. */
        void onSixthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on seventh panel of view. */
        void onSeventhButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on eighth panel of view. */
        void onEighthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on ninth panel of view. */
        void onNinthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on tenth panel of view. */
        void onTenthButtonClicked();

        /** Performs any actions appropriate in response to the user clicked button on eleventh panel of view. */
        void onEleventhButtonClicked();

    }

    /** @return parameter editor type value from the special place on the view */
    @Nonnull
    String getParameterEditorType();

    /** @return parameter available config from the special place on the view */
    @Nonnull
    String getAvailableConfig();

    /**
     * Set parameter editor types value.
     *
     * @param states
     *         values of parameter editor type
     */
    void setParameterEditorType(@Nonnull List<String> states);

    /**
     * Sets available configs to the special place on the view which uses for showing Available Config parameter.
     *
     * @param configs
     *         all available configs
     */
    void setAvailableConfigs(@Nonnull List<String> configs);

    /**
     * Add available config.
     *
     * @param state
     *         value of available config
     */
    void addAvailableConfigs(@Nonnull String state);

    /** @return configuration reference value from the special place on the view which uses for showing configRef parameter */
    @Nonnull
    String getConfigRef();

    /**
     * Sets configuration reference value to the special place on the view which uses for showing password parameter.
     *
     * @param configRef
     *         value of configuration reference
     */
    void setConfigRef(@Nonnull String configRef);

    /**
     * Select parameter editor type to the special place on the view.
     *
     * @param parameterEditorType
     *         parameter value
     */
    void selectParameterEditorType(@Nonnull ParameterEditorType parameterEditorType);

    /** @return value of parameter from first panel's text box view */
    @Nonnull
    String getFirstTextBoxValue();

    /**
     * Sets value of parameter to first panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    void setFirstTextBoxValue(@Nonnull String value);

    /** @return value of parameter from second panel's text box view */
    @Nonnull
    String getSecondTextBoxValue();

    /**
     * Sets value of parameter to second panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    void setSecondTextBoxValue(@Nonnull String value);

    /** @return value of parameter from third panel's text box view */
    @Nonnull
    String getThirdTextBoxValue();

    /**
     * Sets value of parameter to third panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    void setThirdTextBoxValue(@Nonnull String value);

    /** @return value of parameter from fourth panel's text box view */
    @Nonnull
    String getFourthTextBoxValue();

    /**
     * Sets value of parameter to fourth panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    void setFourthTextBoxValue(@Nonnull String value);

    /** @return value of parameter from fifth panel's text box view */
    @Nonnull
    String getFifthTextBoxValue();

    /**
     * Sets value of parameter to fifth panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    void setFifthTextBoxValue(@Nonnull String value);

    @Nonnull
    String getSixthTextBoxValue();

    void setSixthTextBoxValue(@Nonnull String value);

    @Nonnull
    String getSeventhTextBoxValue();

    void setSeventhTextBoxValue(@Nonnull String value);

    @Nonnull
    String getEighthTextBoxValue();

    void setEighthTextBoxValue(@Nonnull String value);

    @Nonnull
    String getNinthTextBoxValue();

    void setNinthTextBoxValue(@Nonnull String value);

    @Nonnull
    String getTenthTextBoxValue();

    void setTenthTextBoxValue(@Nonnull String value);

    @Nonnull
    String getEleventhTextBoxValue();

    void setEleventhTextBoxValue(@Nonnull String value);

    void setVisibleFirstPanel(boolean isVisible);

    void setVisibleSecondPanel(boolean isVisible);

    void setVisibleThirdPanel(boolean isVisible);

    void setVisibleFourthPanel(boolean isVisible);

    void setVisibleFifthPanel(boolean isVisible);

    void setVisibleSixthPanel(boolean isVisible);

    void setVisibleSeventhPanel(boolean isVisible);

    void setVisibleEighthPanel(boolean isVisible);

    void setVisibleNinthPanel(boolean isVisible);

    void setVisibleTenthPanel(boolean isVisible);

    void setVisibleEleventhPanel(boolean isVisible);

    void setFirstLabelTitle(@Nonnull String title);

    void setSecondLabelTitle(@Nonnull String title);

    void setThirdLabelTitle(@Nonnull String title);

    void setFourthLabelTitle(@Nonnull String title);

    void setFifthLabelTitle(@Nonnull String title);

    void setSixthLabelTitle(@Nonnull String title);

    void setSeventhLabelTitle(@Nonnull String title);

    void setEighthLabelTitle(@Nonnull String title);

    void setNinthLabelTitle(@Nonnull String title);

    void setTenthLabelTitle(@Nonnull String title);

    void setEleventhLabelTitle(@Nonnull String title);

    void setEnableFirstTextBox(boolean isEnable);

    void setEnableSecondTextBox(boolean isEnable);

    void setEnableThirdTextBox(boolean isEnable);

    void setEnableFourthTextBox(boolean isEnable);

    void setEnableFifthTextBox(boolean isEnable);

    void setEnableSixthTextBox(boolean isEnable);

    void setEnableSeventhTextBox(boolean isEnable);

    void setEnableEighthTextBox(boolean isEnable);

    void setEnableNinthTextBox(boolean isEnable);

    void setEnableTenthTextBox(boolean isEnable);

    void setEnableEleventhTextBox(boolean isEnable);

    void setVisibleFirstButton(boolean isVisible);

    void setVisibleSecondButton(boolean isVisible);

    void setVisibleThirdButton(boolean isVisible);

    void setVisibleFourthButton(boolean isVisible);

    void setVisibleFifthButton(boolean isVisible);

    void setVisibleSixthButton(boolean isVisible);

    void setVisibleSeventhButton(boolean isVisible);

    void setVisibleEighthButton(boolean isVisible);

    void setVisibleNinthButton(boolean isVisible);

    void setVisibleTenthButton(boolean isVisible);

    void setVisibleEleventhButton(boolean isVisible);

}
