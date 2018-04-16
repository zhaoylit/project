/*
 * Copyright (C) 1999-2016 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.steadystate.css.samples;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.InputSource;

import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;

/**
 * Test cases for the code we have on our 'Get Started' page.
 *
 * @author rbri
 */
public class OutputFormattingTest {
    private static final String NEW_LINE = System.getProperty("line.separator");

    @Test
    public void outputFormatting() throws Exception {
        InputSource source = new InputSource(new StringReader("h1{background:rgb(7,42,0)}"));
        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) parser.parseStyleSheet(source, null, null);

        Assert.assertEquals("h1 { background: rgb(7, 42, 0) }", sheet.getCssText());

        Assert.assertEquals("h1 { background: #072a00 }", sheet.getCssText(new CSSFormat().setRgbAsHex(true)));

        Assert.assertEquals("h1 {" + NEW_LINE + "    background: rgb(7, 42, 0)" + NEW_LINE + "}",
                sheet.getCssText(new CSSFormat().setPropertiesInSeparateLines(4)));
    }
}
