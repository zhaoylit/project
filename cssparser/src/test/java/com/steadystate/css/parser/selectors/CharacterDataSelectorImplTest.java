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

package com.steadystate.css.parser.selectors;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.Selector;

import com.steadystate.css.format.CSSFormat;

/**
 * Testcases for {@link CharacterDataSelectorImplTest}.
 */
public class CharacterDataSelectorImplTest {

    /**
     * @throws Exception if any error occurs
     */
    @Test
    public void withoutValue() throws Exception {
        final CharacterDataSelectorImpl c = new CharacterDataSelectorImpl(null);
        Assert.assertEquals(Selector.SAC_CDATA_SECTION_NODE_SELECTOR, c.getSelectorType());
        Assert.assertNull(c.getData());

        Assert.assertEquals("", c.toString());

        Assert.assertEquals("", c.getCssText(null));
        Assert.assertEquals("", c.getCssText(new CSSFormat()));
    }

    /**
     * @throws Exception if any error occurs
     */
    @Test
    public void emptyValue() throws Exception {
        final CharacterDataSelectorImpl c = new CharacterDataSelectorImpl("");
        Assert.assertEquals(Selector.SAC_CDATA_SECTION_NODE_SELECTOR, c.getSelectorType());
        Assert.assertEquals("", c.getData());

        Assert.assertEquals("", c.toString());

        Assert.assertEquals("", c.getCssText(null));
        Assert.assertEquals("", c.getCssText(new CSSFormat()));
    }

    /**
     * @throws Exception if any error occurs
     */
    @Test
    public void withValue() throws Exception {
        final CharacterDataSelectorImpl c = new CharacterDataSelectorImpl("value");
        Assert.assertEquals(Selector.SAC_CDATA_SECTION_NODE_SELECTOR, c.getSelectorType());
        Assert.assertEquals("value", c.getData());

        Assert.assertEquals("value", c.toString());

        Assert.assertEquals("value", c.getCssText(null));
        Assert.assertEquals("value", c.getCssText(new CSSFormat()));
    }
}
