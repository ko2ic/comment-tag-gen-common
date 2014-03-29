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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ItemsTagTest {

    private final ItemsTag target = new ItemsTag();

    @Test
    public void ifCall_interchange() {
        target.setContents("contents");

        target.setEnd(";");
        target.setSeparator(",");

        target.setUpperAll("upperAll");
        target.setUpperCamel("upperCamel");
        target.setUpperSnake("upperSnake");

        target.setLowerAll("lowerAll");
        target.setLowerCamel("lowerCamel");
        target.setLowerSnake("lowerSnake");

        ItemsTag arg = new ItemsTag();
        arg.setContents("contents2");

        arg.setUpperAll("upperAll2");
        arg.setUpperCamel("upperCamel2");
        arg.setUpperSnake("upperSnake2");

        arg.setLowerAll("lowerAll2");
        arg.setLowerCamel("lowerCamel2");
        arg.setLowerSnake("lowerSnake2");

        arg.setEnd(";2");
        arg.setSeparator(",2");

        target.interchange(arg);
        assertThat(target.getUpperAll(), is("upperAll2"));
        assertThat(target.getUpperCamel(), is("upperCamel2"));
        assertThat(target.getUpperSnake(), is("upperSnake2"));
        assertThat(target.getLowerAll(), is("lowerAll2"));
        assertThat(target.getLowerCamel(), is("lowerCamel2"));
        assertThat(target.getLowerSnake(), is("lowerSnake2"));

        assertThat(target.getEnd(), is(";2"));
        assertThat(target.getSeparator(), is(",2"));

        assertThat(target.getContents(), is("contents"));
    }

}
