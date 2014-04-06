/*******************************************************************************
 * Copyright (c) 2014
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Kouji Ishii - initial implementation
 *******************************************************************************/
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed.EnumClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;
import com.github.ko2ic.plugin.eclipse.taggen.common.exceptions.InvalidCellIndexException;

@RunWith(Enclosed.class)
public class EnumCodeSeedTest {

    private final EnumCodeSeed target = new EnumCodeSeed("base", true, "", "");

    @Mocked
    private Sheet mockSheet;

    @Test
    public void ifCall_getStartIndexRepeatingRow() {
        int actual = target.getStartIndexRepeatingRow();
        assertThat(actual, is(3));
    }

    @Test
    public void ifCall_getPackageName() throws InvalidCellIndexException {
        new NonStrictExpectations() {
            {
                mockSheet.getStringCellValue(0, 1);
                result = "packageName";
            }
        };
        String actual = target.getPackageName(mockSheet);
        assertThat(actual, is("packageName"));

        new Verifications() {
            {
                mockSheet.getStringCellValue(0, 1);
            }
        };
    }

    @Test
    public void ifCall_instanceClassElementsItem() throws InvalidCellIndexException {
        new Expectations() {
            {
                mockSheet.getStringCellValue(3);
                result = "name";
                mockSheet.getStringCellValue(4);
                result = "code";
                mockSheet.getStringCellValue(5);
                result = "comment";
            }
        };
        EnumCodeSeed target = new EnumCodeSeed("base", true, "", "");
        EnumClassElementsItem actual = (EnumClassElementsItem) target.instanceClassElementsItem(mockSheet);
        EnumClassElementsItem expected = new EnumClassElementsItem(new NameRuleString("name"), "code", "comment");
        assertThat(actual.getCode(), is(expected.getCode()));
        assertThat(actual.getComment(), is(expected.getComment()));
        assertThat(actual.getName().toLowerCase(), is(expected.getName().toLowerCase()));
    }

    @Test
    public void ifCall_putClassElements() throws InvalidCellIndexException {

        new NonStrictExpectations() {
            {
                mockSheet.getStringCellValue(2);
                result = "definedClassName";
            }
        };

        String fullClassName = target.putClassElements(mockSheet, "packageName");
        assertThat(fullClassName, is("packageName.DefinedClassName"));
    }

    public static class InCaseOf_EnumClassElementsItemClass {

        EnumClassElementsItem target = new EnumClassElementsItem(new NameRuleString("test_test"), "", "");

        @Test
        public void ifCall_getUpperCamelName() {
            String actual = target.getUpperCamelName();
            assertThat(actual, is("TestTest"));
        }

        @Test
        public void ifCall_getUpperSnakeName() {
            String actual = target.getUpperSnakeName();
            assertThat(actual, is("TEST_TEST"));
        }

        @Test
        public void ifCall_getUpperName() {
            String actual = target.getUpperName();
            assertThat(actual, is("TEST_TEST"));
        }
    }

}
