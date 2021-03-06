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

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;

/**
 * Presents &lt;items&gt; tag.
 * @author ko2ic
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class ItemsTag extends CommentTag {

    private String srcContents;

    private String contents;

    @Setter
    private String upperAll;

    @Setter
    private String upperCamel;

    @Setter
    private String upperSnake;

    @Setter
    private String lowerAll;

    @Setter
    private String lowerCamel;

    @Setter
    private String lowerSnake;

    @Setter
    private String separator;

    @Setter
    private String end;

    public void setContents(String contents) {
        this.contents = contents;
        if (srcContents == null) {
            srcContents = contents;
        }
    }

    public void replaceContent(List<? extends ClassElementsItem> attributes) {
    }

    public void interchange(ItemsTag srcTag) {
        setUpperAll((srcTag.getUpperAll()));
        setUpperSnake(srcTag.getUpperSnake());
        setUpperCamel(srcTag.getUpperCamel());
        setLowerAll((srcTag.getLowerAll()));
        setLowerSnake(srcTag.getLowerSnake());
        setLowerCamel(srcTag.getLowerCamel());

        setEnd((srcTag.getEnd()));
        setSeparator((srcTag.getSeparator()));
    }

    @Override
    public ItemsTag clone() {
        return (ItemsTag) super.clone();
    }
}
