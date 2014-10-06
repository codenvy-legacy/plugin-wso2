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
package com.codenvy.ide.client.elements.mediators;

import javax.annotation.Nonnull;

/**
 * The class contains constant values of properties which can be used by different elements.
 *
 * @author Dmitry Shnurenko
 */
public enum Action {
    SET("set"), REMOVE("remove");

    public static final String TYPE_NAME = "Action";

    private final String value;

    Action(@Nonnull String value) {
        this.value = value;
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    @Nonnull
    public static Action getItemByValue(@Nonnull String value) {
        if ("set".equals(value)) {
            return SET;
        } else {
            return REMOVE;
        }
    }

}