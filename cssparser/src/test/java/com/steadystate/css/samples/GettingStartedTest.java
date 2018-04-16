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

import org.junit.Test;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;

/**
 * Test cases for the code we have on our 'Get Started' page.
 *
 * @author rbri
 */
public class GettingStartedTest {

    @Test
    public void introduction() throws Exception {
        InputSource source = new InputSource(new StringReader("h1 { background: #ffcc44; }"));
        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);

        CSSRuleList rules = sheet.getCssRules();
        for (int i = 0; i < rules.getLength(); i++) {
            final CSSRule rule = rules.item(i);

            System.out.println(rule.getCssText());
        }
    }

    @Test
    public void erroHandling() throws Exception {
        InputSource source = new InputSource(new StringReader("h1 { background: #red; }"));
        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());

        ErrorHandler errorHandler = new MyErrorHandler();
        parser.setErrorHandler(errorHandler);

        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
    }

    public static class MyErrorHandler implements ErrorHandler {

        public void warning(CSSParseException exception) throws CSSException {
            System.out.println("Warning: " + exception.getMessage());
        }

        public void error(CSSParseException exception) throws CSSException {
            System.out.println("Error: " + exception.getMessage());
        }

        public void fatalError(CSSParseException exception) throws CSSException {
            System.out.println("Fatal: " + exception.getMessage());
        }
    }

    @Test
    public void parseStyle() throws Exception {
        InputSource source = new InputSource(new StringReader("background: #ffcc44; margin-top: 4px"));
        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
        CSSStyleDeclaration decl = parser.parseStyleDeclaration(source);

        for (int i = 0; i < decl.getLength(); i++) {
            final String propName = decl.item(i);

            System.out.println("'" + propName + "' has value: '" + decl.getPropertyValue(propName) + "'");
        }
    }

}
