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

import com.codenvy.ide.client.elements.Shape;

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
    private static final String ELEMENT1   = "Element1";
    private static final String ELEMENT2   = "Element2";
    private static final String CONNECTION = "Connection";

    @Mock
    private Shape shape;
    @Mock
    private Shape firstShape;
    @Mock
    private Shape secondShape;
    @Mock
    private Shape thirdShape;

    private MetaModelValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new MetaModelValidator();

        validator.register(ELEMENT1, CONNECTION, Arrays.asList(ELEMENT1));
    }

    @Test
    public void connectionShouldBeAbleToCreate() throws Exception {
        assertTrue(validator.canCreateConnection(ELEMENT1, CONNECTION, ELEMENT1));
    }

    @Test
    public void connectionShouldNotBeAbleToCreateWhenRuleIsAbsent() throws Exception {
        assertFalse(validator.canCreateConnection(ELEMENT1, CONNECTION, ELEMENT2));
    }

    @Test
    public void connectionShouldNotBeAbleToCreateWhenRuleIsAbsent2() throws Exception {
        assertFalse(validator.canCreateConnection(ELEMENT2, CONNECTION, ELEMENT1));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenCurrentElementIsFirst() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertTrue(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 0, 100));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenCurrentElementIsLast() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));
        when(shape.getElementName()).thenReturn(ELEMENT1);

        assertTrue(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 300, 100));
    }

    @Test
    public void elementShouldBeAbleToInsertWhenContainerHaveNoElement() throws Exception {
        when(shape.getShapes()).thenReturn(Arrays.<Shape>asList());

        assertTrue(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 100, 100));
    }

    @Test
    public void elementShouldBeAbleToInsert() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertTrue(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 150, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsFirstAndItIsImpossibleToCreateConnectionWithSecondElement()
            throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT2, 0, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsFirstAndItIsImpossibleToCreateConnectionWithSecondElement2()
            throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT2, 100, 50));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsLast() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 200, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenCurrentElementIsLast2() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 100, 150));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenElementBeforeInsertPositionIsNotAbleToCreateConnection() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT2, 150, 100));
    }

    @Test
    public void elementShouldBeNotAbleToInsertWhenElementAfterInsertPositionIsNotAbleToCreateConnection() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertFalse(validator.canInsertElement(shape, CONNECTION, ELEMENT1, 150, 100));
    }


    @Test
    public void elementShouldBeAbleToRemoveWhenItIsFirstElement() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getId()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getId()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertTrue(validator.canRemoveElement(shape, ELEMENT1, CONNECTION));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsLastElement() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getId()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getId()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape));

        assertTrue(validator.canRemoveElement(shape, ELEMENT2, CONNECTION));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenElementIsAbsent() throws Exception {
        assertTrue(validator.canRemoveElement(shape, ELEMENT1, CONNECTION));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsPossibleToCreateConnectionBetweenNearestElements() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);
        when(firstShape.getId()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT1);
        when(secondShape.getId()).thenReturn(ELEMENT2);

        when(thirdShape.getX()).thenReturn(300);
        when(thirdShape.getY()).thenReturn(100);
        when(thirdShape.getElementName()).thenReturn(ELEMENT1);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape, thirdShape));

        assertTrue(validator.canRemoveElement(shape, ELEMENT2, CONNECTION));
    }

    @Test
    public void elementShouldBeAbleToRemoveWhenItIsImpossibleToCreateConnectionBetweenNearestElements() throws Exception {
        when(firstShape.getX()).thenReturn(100);
        when(firstShape.getY()).thenReturn(100);
        when(firstShape.getElementName()).thenReturn(ELEMENT1);
        when(firstShape.getId()).thenReturn(ELEMENT1);

        when(secondShape.getX()).thenReturn(200);
        when(secondShape.getY()).thenReturn(100);
        when(secondShape.getElementName()).thenReturn(ELEMENT1);
        when(secondShape.getId()).thenReturn(ELEMENT2);

        when(thirdShape.getX()).thenReturn(300);
        when(thirdShape.getY()).thenReturn(100);
        when(thirdShape.getElementName()).thenReturn(ELEMENT2);

        when(shape.getShapes()).thenReturn(Arrays.asList(firstShape, secondShape, thirdShape));

        assertFalse(validator.canRemoveElement(shape, ELEMENT2, CONNECTION));
    }

}