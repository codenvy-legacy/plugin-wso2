/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.elements.connectors;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines methods which need for each property manager connector. All connector's property managers must implements this interface.
 *
 * @author Dmitry Shnurenko
 */
public interface ConnectorPropertyManager {

    /**
     * Adds new configuration to list which saves all names of configuration for current group of connectors
     *
     * @param name
     *         name of configuration which need to add
     */
    void addNewConfig(@Nonnull String name);

    /**
     * Adds properties panel, which need react on changing of available configuration parameter, to list of listeners.
     * All properties panel of connectors must implement {@link ConnectorPropertyListener}
     *
     * @param listener
     *         properties panel which need to add
     */
    void addListener(@Nonnull ConnectorPropertyListener listener);

    /** @return list of names of available configuration parameters */
    @Nonnull
    List<String> getAvailableConfigs();

    /**
     * Interface which need to implement all Connector's presenters, to add property to the general list when
     * one of connectors will add available configuration parameter
     */
    public interface ConnectorPropertyListener {
        /**
         * Calls when one of the connectors add available configuration parameter.
         *
         * @param property
         *         name of available configuration parameter which need to add
         */
        void onGeneralPropertyChanged(@Nonnull String property);
    }
}
