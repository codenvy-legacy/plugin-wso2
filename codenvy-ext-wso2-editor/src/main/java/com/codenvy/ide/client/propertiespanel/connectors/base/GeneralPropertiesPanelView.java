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
@ImplementedBy(GeneralPropertiesPanelViewImpl.class)
public abstract class GeneralPropertiesPanelView extends AbstractView<GeneralPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of general presenter of current type of connectors which
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

        /** Performs any actions appropriate in response to the user changes value of fifth text box on view. */
        void onFifthTextBoxValueChanged();

        /** Performs any actions appropriate in response to the user changes value of sixes text box on view. */
        void onSixesTextBoxValueChanged();

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
        void onSixesButtonClicked();

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

    /** @return value of parameter from fifth panel's text box view */
    @Nonnull
    public abstract String getFifthTextBoxValue();

    /**
     * Sets value of parameter to fifth panel's text box on view.
     *
     * @param value
     *         value of parameter which need to set
     */
    public abstract void setFifthTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getSixesTextBoxValue();

    public abstract void setSixesTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getSeventhTextBoxValue();

    public abstract void setSeventhTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getEighthTextBoxValue();

    public abstract void setEighthTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getNinthTextBoxValue();

    public abstract void setNinthTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getTenthTextBoxValue();

    public abstract void setTenthTextBoxValue(@Nonnull String value);

    @Nonnull
    public abstract String getEleventhTextBoxValue();

    public abstract void setEleventhTextBoxValue(@Nonnull String value);

    public abstract void setVisibleFirstPanel(boolean isVisible);

    public abstract void setVisibleSecondPanel(boolean isVisible);

    public abstract void setVisibleThirdPanel(boolean isVisible);

    public abstract void setVisibleFourthPanel(boolean isVisible);

    public abstract void setVisibleFifthPanel(boolean isVisible);

    public abstract void setVisibleSixesPanel(boolean isVisible);

    public abstract void setVisibleSeventhPanel(boolean isVisible);

    public abstract void setVisibleEighthPanel(boolean isVisible);

    public abstract void setVisibleNinthPanel(boolean isVisible);

    public abstract void setVisibleTenthPanel(boolean isVisible);

    public abstract void setVisibleEleventhPanel(boolean isVisible);

    public abstract void setFirstLabelTitle(@Nonnull String title);

    public abstract void setSecondLabelTitle(@Nonnull String title);

    public abstract void setThirdLabelTitle(@Nonnull String title);

    public abstract void setFourthLabelTitle(@Nonnull String title);

    public abstract void setFifthLabelTitle(@Nonnull String title);

    public abstract void setSixesLabelTitle(@Nonnull String title);

    public abstract void setSeventhLabelTitle(@Nonnull String title);

    public abstract void setEighthLabelTitle(@Nonnull String title);

    public abstract void setNinthLabelTitle(@Nonnull String title);

    public abstract void setTenthLabelTitle(@Nonnull String title);

    public abstract void setEleventhLabelTitle(@Nonnull String title);

    public abstract void setEnableFirstTextBox(boolean isEnable);

    public abstract void setEnableSecondTextBox(boolean isEnable);

    public abstract void setEnableThirdTextBox(boolean isEnable);

    public abstract void setEnableFourthTextBox(boolean isEnable);

    public abstract void setEnableFifthTextBox(boolean isEnable);

    public abstract void setEnableSixesTextBox(boolean isEnable);

    public abstract void setEnableSeventhTextBox(boolean isEnable);

    public abstract void setEnableEighthTextBox(boolean isEnable);

    public abstract void setEnableNinthTextBox(boolean isEnable);

    public abstract void setEnableTenthTextBox(boolean isEnable);

    public abstract void setEnableEleventhTextBox(boolean isEnable);

    public abstract void setVisibleFirstButton(boolean isVisible);

    public abstract void setVisibleSecondButton(boolean isVisible);

    public abstract void setVisibleThirdButton(boolean isVisible);

    public abstract void setVisibleFourthButton(boolean isVisible);

    public abstract void setVisibleFifthButton(boolean isVisible);

    public abstract void setVisibleSixesButton(boolean isVisible);

    public abstract void setVisibleSeventhButton(boolean isVisible);

    public abstract void setVisibleEighthButton(boolean isVisible);

    public abstract void setVisibleNinthButton(boolean isVisible);

    public abstract void setVisibleTenthButton(boolean isVisible);

    public abstract void setVisibleEleventhButton(boolean isVisible);

}
