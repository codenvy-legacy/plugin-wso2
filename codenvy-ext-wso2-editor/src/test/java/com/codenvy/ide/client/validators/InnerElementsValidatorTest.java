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
package com.codenvy.ide.client.validators;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class InnerElementsValidatorTest {

    private static final String ELEMENT1 = "Element1";
    private static final String ELEMENT2 = "Element2";
    private static final String ELEMENT3 = "Element3";

    private InnerElementsValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new InnerElementsValidator();
    }

    @Test
    public void connectionShouldBeAbleToCreateWhenAllowAndDisallowRulesAreEmpty() throws Exception {
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT2));
    }

    @Test
    public void elementShouldBeAbleToCreateWhenAllowRuleIsAdded() throws Exception {
        validator.addAllowRule(ELEMENT1, ELEMENT1);

        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT2));
    }

    @Test
    public void elementShouldBeAbleToCreateWhenAllowRulesAreAdded() throws Exception {
        validator.addAllowRules(ELEMENT1, Arrays.asList(ELEMENT1));

        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT2));
    }

    @Test
    public void elementShouldBeAbleToCreateWhenAllowRulesAreAdded2() throws Exception {
        validator.addAllowRules(ELEMENT1, Arrays.asList(ELEMENT1));
        validator.addAllowRule(ELEMENT1, ELEMENT2);

        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT2));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT3));
    }

    @Test
    public void elementShouldBeAbleToCreateWhenAllowRulesAreAdded3() throws Exception {
        validator.addAllowRule(ELEMENT1, ELEMENT2);
        validator.addAllowRules(ELEMENT1, Arrays.asList(ELEMENT1));

        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT2));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT3));
    }

    @Test
    public void elementShouldBeNotAbleToCreateWhenDisallowRuleIsAdded() throws Exception {
        validator.addDisallowRule(ELEMENT1, ELEMENT1);

        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT2));
    }

    @Test
    public void elementShouldBeNotAbleToCreateWhenDisallowRulesAreAdded() throws Exception {
        validator.addDisallowRules(ELEMENT1, Arrays.asList(ELEMENT1));

        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT2));
    }

    @Test
    public void elementShouldBeNotAbleToCreateWhenDisallowRulesAreAdded2() throws Exception {
        validator.addDisallowRules(ELEMENT1, Arrays.asList(ELEMENT1));
        validator.addDisallowRule(ELEMENT1, ELEMENT2);

        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT2));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT3));
    }

    @Test
    public void elementShouldBeNotAbleToCreateWhenDisallowRulesAreAdded3() throws Exception {
        validator.addDisallowRule(ELEMENT1, ELEMENT2);
        validator.addDisallowRules(ELEMENT1, Arrays.asList(ELEMENT1));

        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT1));
        assertFalse(validator.canInsertElement(ELEMENT1, ELEMENT2));
        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT3));
    }

    @Test
    public void elementShouldBeAbleToCreateWhenAllowAndDisallowRulesAreAdded() throws Exception {
        validator.addAllowRule(ELEMENT1, ELEMENT1);
        validator.addDisallowRule(ELEMENT1, ELEMENT1);

        assertTrue(validator.canInsertElement(ELEMENT1, ELEMENT1));
    }

}