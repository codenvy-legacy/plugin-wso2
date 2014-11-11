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
package com.codenvy.ide.client.elements.mediators.payload;

import com.codenvy.ide.client.elements.AbstractElementTest;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.ARGS;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.FORMAT;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MEDIA_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType.JSON;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType.XML;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class PayloadFactoryTest extends AbstractElementTest<PayloadFactory> {

    private static final String ROOT_PATH      = "mediators/payload/";
    private static final String SERIALIZE_PATH = ROOT_PATH + "serialize/";

    private static final String    DESCRIPTION_DEFAULT_VALUE = "";
    private static final String    DESCRIPTION_VALUE         = "description";
    private static final MediaType MEDIA_TYPE_DEFAULT_VALUE  = XML;
    private static final List<Arg> ARGS_DEFAULT_VALUE        = new ArrayList<>();

    private static final String FORMAT_SERIALIZATION_IF_XML_VALUE      = "<format>\n" +
                                                                         "<inline xmlns=\"\"/>\n" +
                                                                         "</format>\n";
    private static final String FORMAT_FULL_SERIALIZATION_IF_XML_VALUE = "<format>\n" +
                                                                         "<a xmlns=\"\">text</a>\n" +
                                                                         "</format>\n";
    private static final String FORMAT_SERIALIZATION_IF_JSON_VALUE     = "<format>&lt;inline/&gt;</format>\n";
    private static final String ARG_SERIALIZATION_VALUE                = "<arg value=\"default\"/>\n";

    @Mock
    private Provider<Arg> argProvider;
    @Mock
    private Format        format;
    @Mock
    private Arg           arg;

    @Before
    public void setUp() throws Exception {
        when(resources.payloadFactory()).thenReturn(icon);
        when(argProvider.get()).thenReturn(arg);
        when(format.serialize(eq(false))).thenReturn(FORMAT_SERIALIZATION_IF_XML_VALUE);

        when(format.serialize(eq(true))).thenReturn(FORMAT_SERIALIZATION_IF_JSON_VALUE);

        when(arg.serialize()).thenReturn(ARG_SERIALIZATION_VALUE);

        entity = new PayloadFactory(resources, branchProvider, elementCreatorsManager, argProvider, format);
    }

    @Override
    public void elementTitleShouldBeInitializedWithDefaultValue() throws Exception {
        assertEquals(PayloadFactory.ELEMENT_NAME, entity.getTitle());
    }

    @Override
    public void elementNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(PayloadFactory.ELEMENT_NAME, entity.getElementName());
    }

    @Override
    public void serializeNameShouldBeDefinedWithDefaultValue() throws Exception {
        assertEquals(PayloadFactory.SERIALIZATION_NAME, entity.getSerializationName());
    }

    @Override
    public void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isPossibleToAddBranches());
    }

    @Override
    public void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception {
        assertFalse(entity.isRoot());
    }

    @Override
    public void elementIconShouldBeInitializedWithDefaultValue() throws Exception {
        verify(resources).payloadFactory();
        assertEquals(icon, entity.getIcon());
    }

    @Override
    public void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception {
        PayloadFactory otherElement = new PayloadFactory(resources, branchProvider, elementCreatorsManager, argProvider, format);
        assertFalse(entity.equals(otherElement));
    }

    @Override
    public void constructorPrepareOperationShouldBeDone() throws Exception {
        assertDefaultConfiguration();
    }

    private void assertDefaultConfiguration() {
        assertConfiguration(format, DESCRIPTION_DEFAULT_VALUE, ARGS_DEFAULT_VALUE, MEDIA_TYPE_DEFAULT_VALUE);
    }

    private void assertConfiguration(Format format, String description, List<Arg> args, MediaType mediaType) {
        assertEquals(format, entity.getProperty(FORMAT));
        assertEquals(description, entity.getProperty(DESCRIPTION));
        assertEquals(args, entity.getProperty(ARGS));
        assertEquals(mediaType, entity.getProperty(MEDIA_TYPE));
    }

    @Test
    public void serializationShouldBeDoneWithDefaultValues() throws Exception {
        assertContentAndValue(SERIALIZE_PATH + "Default", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneForDefaultRepresentation() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "Default"));

        assertDefaultConfiguration();
    }

    @Test
    public void serializationShouldBeDoneWhenMediaTypeIsJSON() throws Exception {
        entity.putProperty(MEDIA_TYPE, JSON);

        assertContentAndValue(SERIALIZE_PATH + "JSONMediaType", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenMediaTypeIsJSON() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "JSONMediaType"));

        assertConfiguration(format, DESCRIPTION_DEFAULT_VALUE, ARGS_DEFAULT_VALUE, JSON);
    }

    @Test
    public void serializationShouldBeDoneWhenMediaTypeIsNull() throws Exception {
        entity.putProperty(MEDIA_TYPE, null);

        assertContentAndValue(SERIALIZE_PATH + "MediaTypeIsNull", entity.serialize());
    }

    @Test
    public void serializationShouldBeDoneWithArgsParameter() throws Exception {
        entity.putProperty(ARGS, Arrays.asList(arg));

        assertContentAndValue(SERIALIZE_PATH + "ArgParam", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithArgsParameter() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "ArgParam"));

        verify(arg).applyAttributes(any(Node.class));
        assertConfiguration(format, DESCRIPTION_DEFAULT_VALUE, Arrays.asList(arg), XML);
    }

    @Test
    public void serializationShouldBeDoneWithFormatParameter() throws Exception {
        entity.putProperty(FORMAT, format);

        when(format.serialize(eq(false))).thenReturn(FORMAT_FULL_SERIALIZATION_IF_XML_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "FormatParam", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWithFormatParameter() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "FormatParam"));

        verify(format).applyAttributes(any(Node.class));
        assertConfiguration(format, DESCRIPTION_DEFAULT_VALUE, ARGS_DEFAULT_VALUE, XML);
    }

    @Test
    public void serializationShouldBeNotDoneWhenFormatIsNull() throws Exception {
        entity.putProperty(FORMAT, null);

        assertContentAndValue(SERIALIZE_PATH + "FormatIsNull", entity.serialize());
    }

    @Test
    public void deserializationShouldBeNotDoneWhenFormatIsNull() throws Exception {
        assertDefaultConfiguration();

        entity.putProperty(FORMAT, null);
        entity.deserialize(getContent(SERIALIZE_PATH + "Default"));

        assertConfiguration(null, DESCRIPTION_DEFAULT_VALUE, ARGS_DEFAULT_VALUE, XML);
    }

    @Test
    public void serializationShouldBeDoneWhenDescriptionIsChanged() throws Exception {
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "Description", entity.serialize());
    }

    @Test
    public void serializationShouldBeNotDoneWhenDescriptionIsNull() throws Exception {
        entity.putProperty(DESCRIPTION, null);

        assertContentAndValue(SERIALIZE_PATH + "Default", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenDescriptionIsChanged() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "Description"));

        assertConfiguration(format, DESCRIPTION_VALUE, ARGS_DEFAULT_VALUE, XML);
    }

    @Test
    public void serializationShouldBeDoneWhenAllPropertiesAreAdded() throws Exception {
        entity.putProperty(MEDIA_TYPE, XML);
        entity.putProperty(ARGS, Arrays.asList(arg));
        entity.putProperty(FORMAT, format);
        entity.putProperty(DESCRIPTION, DESCRIPTION_VALUE);

        when(format.serialize(eq(false))).thenReturn(FORMAT_FULL_SERIALIZATION_IF_XML_VALUE);

        assertContentAndValue(SERIALIZE_PATH + "Full", entity.serialize());
    }

    @Test
    public void deserializationShouldBeDoneWhenAllPropertiesAreAdded() throws Exception {
        assertDefaultConfiguration();

        entity.deserialize(getContent(SERIALIZE_PATH + "Full"));

        verify(arg).applyAttributes(any(Node.class));
        verify(format).applyAttributes(any(Node.class));

        assertConfiguration(format, DESCRIPTION_VALUE, Arrays.asList(arg), XML);
    }

    @Test
    public void xmlValueMediaTypeShouldBeReturned() throws Exception {
        assertEquals(XML, MediaType.getItemByValue("xml"));
    }

    @Test
    public void jsonValueOfMediaTypeShouldBeReturned() throws Exception {
        assertEquals(JSON, MediaType.getItemByValue("json"));
    }

}