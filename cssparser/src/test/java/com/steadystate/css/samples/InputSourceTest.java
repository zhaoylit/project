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

//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//
//import org.junit.Test;
//import org.w3c.css.sac.CSSException;
//import org.w3c.css.sac.CSSParseException;
//import org.w3c.css.sac.ErrorHandler;
//import org.w3c.css.sac.InputSource;
//import org.w3c.dom.css.CSSRule;
//import org.w3c.dom.css.CSSRuleList;
//import org.w3c.dom.css.CSSStyleSheet;
//
//import com.steadystate.css.parser.CSSOMParser;
//import com.steadystate.css.parser.SACParserCSS3;

/**
 * Test cases for the code we have on our 'Input Source' page.
 *
 * @author rbri
 */
public class InputSourceTest {

//    @Test
//    public void string() throws Exception {
//        InputSource source = new InputSource(new StringReader("h1 { background: #ffcc44; }"));
//        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
//
//        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
//        CSSRuleList rules = sheet.getCssRules();
//        for (int i = 0; i < rules.getLength(); i++) {
//            final CSSRule rule = rules.item(i);
//
//            System.out.println(rule.getCssText());
//        }
//    }
//
//    @Test
//    public void reader() throws Exception {
//        InputStream inStream = new FileInputStream("input.css");
//        try {
//            InputSource source = new InputSource(new InputStreamReader(inStream, "UTF-8"));
//
//            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
//
//            ErrorHandler errorHandler = new MyErrorHandler();
//            parser.setErrorHandler(errorHandler);
//
//            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
//            CSSRuleList rules = sheet.getCssRules();
//            for (int i = 0; i < rules.getLength(); i++) {
//                final CSSRule rule = rules.item(i);
//
//                System.out.println(rule.getCssText());
//            }
//        } finally {
//            inStream.close();
//        }
//    }
//
//    @Test
//    public void inputStream() throws Exception {
//        InputStream inStream = new FileInputStream("input.css");
//        try {
//            InputSource source = new InputSource();
//            source.setByteStream(inStream);
//            source.setEncoding("UTF-8");
//
//            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
//
//            ErrorHandler errorHandler = new MyErrorHandler();
//            parser.setErrorHandler(errorHandler);
//
//            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
//            CSSRuleList rules = sheet.getCssRules();
//            for (int i = 0; i < rules.getLength(); i++) {
//                final CSSRule rule = rules.item(i);
//
//                System.out.println(rule.getCssText());
//            }
//        } finally {
//            inStream.close();
//        }
//    }
//
//    public static class MyErrorHandler implements ErrorHandler {
//
//        public void warning(CSSParseException exception) throws CSSException {
//            System.out.println("Warning: " + exception.getMessage());
//        }
//
//        public void error(CSSParseException exception) throws CSSException {
//            System.out.println("Error: " + exception.getMessage());
//        }
//
//        public void fatalError(CSSParseException exception) throws CSSException {
//            System.out.println("Fatal: " + exception.getMessage());
//        }
//    }
}
