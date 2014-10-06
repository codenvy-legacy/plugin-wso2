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

import org.junit.Test;

import static com.codenvy.ide.client.elements.mediators.Action.REMOVE;
import static com.codenvy.ide.client.elements.mediators.Action.SET;
import static org.junit.Assert.assertEquals;

/**
 * @author Valeriy Svydenko
 */
public class ActionTest {
    private static final String SET_VALUE    = "set";
    private static final String REMOVE_VALUE = "remove";
    private static final String SOME_STRING  = "string";

    @Test
    public void valueOfSetShouldBeReturned() throws Exception {
        assertEquals(SET_VALUE, SET.getValue());
    }

    @Test
    public void valueOfRemoveShouldBeReturned() throws Exception {
        assertEquals(REMOVE_VALUE, REMOVE.getValue());
    }

    @Test
    public void setValueOfActionShouldBeReturned() throws Exception {
        assertEquals(SET, Action.getItemByValue(SET_VALUE));
    }

    @Test
    public void removeValueOfActionShouldBeReturned1() throws Exception {
        assertEquals(REMOVE, Action.getItemByValue(REMOVE_VALUE));
    }

    @Test
    public void removeValueOfActionShouldBeReturned2() throws Exception {
        assertEquals(REMOVE, Action.getItemByValue(SOME_STRING));
    }

}