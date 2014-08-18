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
package com.codenvy.ide.client.propertiespanel.propertyconfig;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.collections.Array;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public interface AddNameSpacesCallBack {
    /**
     * Performs some actions when namespaces was changed.
     *
     * @param nameSpaces
     *         changed list of namespaces
     * @param expression
     *         value of expression from special place of view
     */
    void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression);
}
