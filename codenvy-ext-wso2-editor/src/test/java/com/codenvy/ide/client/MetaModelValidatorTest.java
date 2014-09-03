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

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class MetaModelValidatorTest {
    private static final String ELEMENT1 = "Element1";
    private static final String ELEMENT2 = "Element2";

    @Mock
    private Branch  branch;
    @Mock
    private Element element;
    @Mock
    private Element firstElement;
    @Mock
    private Element secondElement;
    @Mock
    private Element thirdElement;

    private MetaModelValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new MetaModelValidator();

        validator.register(ELEMENT1, Arrays.asList(ELEMENT1));
    }

    @Test
    public void connectionShouldBeAbleToCreate() throws Exception {
        assertTrue(validator.canCreateConnection(ELEMENT1, ELEMENT1));
    }

    @Test
    public void connectionShouldNotBeAbleToCreateWhenRuleIsAbsent() throws Exception {
        assertFalse(validator.canCreateConnection(ELEMENT1, ELEMENT2));
    }

    @Test
    public void connectionShouldNotBeAbleToCreateWhenRuleIsAbsent2() throws Exception {
        assertFalse(validator.canCreateConnection(ELEMENT2, ELEMENT1));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenCurrentElementIsFirst() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertTrue(validator.canInsertElement(branch, ELEMENT1, 0, 100));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenCurrentElementIsLast() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertTrue(validator.canInsertElement(branch, ELEMENT1, 300, 100));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenContainerHaveNoElement() throws Exception {
        when(branch.getElements()).thenReturn(Arrays.<Element>asList());

        assertTrue(validator.canInsertElement(branch, ELEMENT1, 100, 100));
    }

    @Test
    public void elementShouldBeAbleToInsert() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertTrue(validator.canInsertElement(branch, ELEMENT1, 150, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsFirstAndItIsImpossibleToCreateConnectionWithSecondElement()
            throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT2, 0, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsFirstAndItIsImpossibleToCreateConnectionWithSecondElement2()
            throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT2, 100, 50));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsLast() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT1, 200, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsLast2() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT1, 100, 150));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenElementBeforeInsertPositionIsNotAbleToCreateConnection() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT2, 150, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenElementAfterInsertPositionIsNotAbleToCreateConnection() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertFalse(validator.canInsertElement(branch, ELEMENT1, 150, 100));
    }


    @Test
    public void elementShouldBeAbleToRemoveWhenItIsFirstElement() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getId()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getId()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertTrue(validator.canRemoveElement(branch, ELEMENT1));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsLastElement() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getId()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getId()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement));

        assertTrue(validator.canRemoveElement(branch, ELEMENT2));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenElementIsAbsent() throws Exception {
        assertTrue(validator.canRemoveElement(branch, ELEMENT1));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsPossibleToCreateConnectionBetweenNearestElements() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);
        when(firstElement.getId()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT1);
        when(secondElement.getId()).thenReturn(ELEMENT2);

        when(thirdElement.getX()).thenReturn(300);
        when(thirdElement.getY()).thenReturn(100);
        when(thirdElement.getElementName()).thenReturn(ELEMENT1);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement, thirdElement));

        assertTrue(validator.canRemoveElement(branch, ELEMENT2));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsImpossibleToCreateConnectionBetweenNearestElements() throws Exception {
        when(firstElement.getX()).thenReturn(100);
        when(firstElement.getY()).thenReturn(100);
        when(firstElement.getElementName()).thenReturn(ELEMENT1);
        when(firstElement.getId()).thenReturn(ELEMENT1);

        when(secondElement.getX()).thenReturn(200);
        when(secondElement.getY()).thenReturn(100);
        when(secondElement.getElementName()).thenReturn(ELEMENT1);
        when(secondElement.getId()).thenReturn(ELEMENT2);

        when(thirdElement.getX()).thenReturn(300);
        when(thirdElement.getY()).thenReturn(100);
        when(thirdElement.getElementName()).thenReturn(ELEMENT2);

        when(branch.getElements()).thenReturn(Arrays.asList(firstElement, secondElement, thirdElement));

        assertFalse(validator.canRemoveElement(branch, ELEMENT2));
    }

}