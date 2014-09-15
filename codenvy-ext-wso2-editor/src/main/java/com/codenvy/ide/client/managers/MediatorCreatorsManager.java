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
package com.codenvy.ide.client.managers;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.util.loging.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * The manager of mediator creators. It contains all available creator for mediators. One can get needed creator by mediator name.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@Singleton
public class MediatorCreatorsManager {

    private final Map<String, Provider<? extends Element>> stateProviders;
    private final Map<String, String>                      elementNames;
    private final Map<String, String>                      serializeNames;
    private final WSO2EditorLocalizationConstant           locale;

    @Inject
    public MediatorCreatorsManager(WSO2EditorLocalizationConstant locale) {
        stateProviders = new HashMap<>();
        elementNames = new HashMap<>();
        serializeNames = new HashMap<>();

        this.locale = locale;
    }

    /**
     * Registers a new creator. In case element with the same name already exists a new one replaces the old one.
     *
     * @param name
     *         mediator name
     * @param serializationName
     *         serialize name of mediator
     * @param state
     *         creating state for current mediator
     * @param provider
     *         mediator creator that needs to be added
     */
    public void register(@Nonnull String name,
                         @Nonnull String serializationName,
                         @Nonnull String state,
                         @Nonnull Provider<? extends Element> provider) {
        if (stateProviders.containsKey(state)) {
            Log.error(getClass(), locale.errorToolbarEditorStateWasAlreadyAdded(state));
            return;
        }

        stateProviders.put(state, provider);
        serializeNames.put(serializationName, state);
        elementNames.put(state, name);
    }

    /**
     * Returns a creator for given name.
     *
     * @param serializeName
     *         serialize name of creator that needs to be found
     * @return a mediator creator
     */
    @Nullable
    public Provider<? extends Element> getProviderBySerializeName(@Nonnull String serializeName) {
        return stateProviders.get(serializeNames.get(serializeName));
    }

    /**
     * Returns a creator for given state.
     *
     * @param state
     *         state of creator that needs to be found
     * @return a mediator creator
     */
    @Nullable
    public Provider<? extends Element> getProviderByState(@Nonnull String state) {
        return stateProviders.get(state);
    }

    /**
     * Returns an element name for given state.
     *
     * @param state
     *         state of creator that needs to be found
     * @return a element name
     */
    @Nullable
    public String getElementNameByState(@Nonnull String state) {
        return elementNames.get(state);
    }

}