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
package com.codenvy.ide.client.common;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;

import static com.codenvy.ide.client.TestUtil.getContentByPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Shnurenko
 */
@GwtModule("com.codenvy.ide.WSO2Editor")
public class ContentFormatterTest extends GwtTestWithMockito {

    private static final String PATH_TO_BEFORE_PARSE_CONTENT   = "/common/serialize/BeforeParse";
    private static final String PATH_TO_AFTER_PARSE_CONTENT    = "/common/serialize/AfterParse";
    private static final String PATH_TO_BEFORE_TRIM_CONTENT    = "/common/serialize/BeforeTrim";
    private static final String PATH_TO_AFTER_TRIM_CONTENT     = "/common/serialize/AfterTrim";
    private static final String PATH_TO_EMPTY_SEQUENCE_CONTENT = "/common/serialize/EmptySequence";

    @Test
    public void xmlShouldBeFormatted() throws Exception {
        String formattedXml = ContentFormatter.formatXML(getContentByPath(ContentFormatterTest.class, PATH_TO_BEFORE_PARSE_CONTENT));

        assertThat(formattedXml, equalTo(getContentByPath(ContentFormatterTest.class, PATH_TO_AFTER_PARSE_CONTENT)));
    }

    @Test
    public void xmlShouldBeTrimmed() throws Exception {
        String trimmedXml = ContentFormatter.trimXML(getContentByPath(ContentFormatterTest.class, PATH_TO_BEFORE_TRIM_CONTENT));

        assertThat(trimmedXml, equalTo(getContentByPath(ContentFormatterTest.class, PATH_TO_AFTER_TRIM_CONTENT)));
    }

    @Test
    public void xmlShouldBeFormattedWhenExistsOnlyEmptySequence() throws Exception {
        String formattedXml = ContentFormatter.formatXML(getContentByPath(ContentFormatterTest.class, PATH_TO_EMPTY_SEQUENCE_CONTENT));

        assertThat(formattedXml, equalTo(getContentByPath(ContentFormatterTest.class, PATH_TO_EMPTY_SEQUENCE_CONTENT)));
    }

}