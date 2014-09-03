/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.AvailableConfigs.EMPTY;
import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.AvailableConfigs.SELECT_FROM_CONFIG;

/**
 * The Class contains state of available configuration parameter, which is general for all SalesForce connectors.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public class GeneralPropertyManager {

    private final List<String>                  availableConfigs;
    private final List<GeneralPropertyListener> listeners;

    @Inject
    public GeneralPropertyManager() {
        listeners = new ArrayList<>();
        availableConfigs = new ArrayList<>();

        availableConfigs.addAll(Arrays.asList(EMPTY.getValue(), SELECT_FROM_CONFIG.getValue()));
    }

    public void addNewConfig(@Nonnull String value) {
        availableConfigs.add(value);

        notifyListeners();
    }

    public void addListener(@Nonnull GeneralPropertyListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        String property = availableConfigs.get(availableConfigs.size() - 1);

        for (GeneralPropertyListener listener : listeners) {
            listener.onGeneralPropertyChanged(property);
        }
    }

    @Nonnull
    public List<String> getAvailableConfigs() {
        return availableConfigs;
    }

    /**
     * Interface which need to implement all Connector's presenters, to add property to the general list when
     * one of connectors will add available configuration parameter
     */
    public interface GeneralPropertyListener {

        /**
         * Calls when one of the connectors add available configuration parameter.
         *
         * @param property
         *         name of available configuration parameter which need to add
         */
        void onGeneralPropertyChanged(@Nonnull String property);
    }

}
