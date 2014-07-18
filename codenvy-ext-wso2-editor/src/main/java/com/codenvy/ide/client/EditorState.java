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
package com.codenvy.ide.client;

import javax.annotation.Nonnull;

/**
 * The class that represents a state of graphical editor. It contains general parameter of available states. The available states will be
 * generated for current graphical editor.
 *
 * @param <T>
 *         type of available editor states
 * @author Andrey Plotnikov
 */
public class EditorState<T> {

    private T state;

    public EditorState(T state) {
        this.state = state;
    }

    /** @return current editor state */
    @Nonnull
    public T getState() {
        return state;
    }

    /**
     * Change current editor state.
     *
     * @param state
     *         state that need to be applied
     */
    public void setState(@Nonnull T state) {
        this.state = state;
    }

}