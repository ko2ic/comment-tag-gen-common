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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.GeneratingCodeSeedBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;
import com.github.ko2ic.plugin.eclipse.taggen.common.exceptions.InvalidCellIndexException;

/**
 * Holds some classes information used when generating code.
 * @author ko2ic
 */
public class EnumCodeSeed extends GeneratingCodeSeedBase {

    @Setter
    private EnumCellIndexHolder cellIndexInfo;

    /**
     * constructor.
     * @param basePackageName base of package
     * @param whetherPackageNameUsesSheet if true,use sheet
     * @param rowCellIndex
     * @param columnCellIndex
     */
    public EnumCodeSeed(String basePackageName, boolean whetherPackageNameUsesSheet, String columnCellIndex, String rowCellIndex) {
        super(basePackageName, whetherPackageNameUsesSheet, columnCellIndex, rowCellIndex);
    }

    @Override
    protected String putClassElements(Sheet sheet, String packageName) throws InvalidCellIndexException {
        String definedClassName = sheet.getStringCellValue(cellIndexInfo.getClassNameColumnIndex());

        NameRuleString javaClassName = new NameRuleString(definedClassName);

        String fullClassName = String.format("%s.%s", packageName, javaClassName.phraseClassName());
        EnumElements seed = createEnumElements(sheet, packageName, javaClassName.phraseClassName());
        put(fullClassName, seed);
        return fullClassName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ClassElementsItem instanceClassElementsItem(Sheet sheet) throws InvalidCellIndexException {
        return new EnumClassElementsItem(new NameRuleString(sheet.getStringCellValue(cellIndexInfo.getEnumNameColumnIndex())), sheet.getStringCellValue(cellIndexInfo.getEnumValueColumnIndex()),
                sheet.getStringCellValue(cellIndexInfo.getEnumCommentColumnIndex()));
    }

    /**
     * Creates EnumElements.
     * @param sheet a sheet
     * @param packageName
     * @param className the name used when generating
     * @return EnumElements
     * @throws InvalidCellIndexException
     */
    private EnumElements createEnumElements(Sheet sheet, String packageName, String className) throws InvalidCellIndexException {
        String classComment = sheet.getStringCellValue(cellIndexInfo.getClassCommentColumnIndex());
        EnumElements elements = new EnumElements(packageName, classComment, className);
        EnumClassElementsItem type = new EnumClassElementsItem(new NameRuleString(sheet.getStringCellValue(cellIndexInfo.getEnumNameColumnIndex())), sheet.getStringCellValue(cellIndexInfo
                .getEnumValueColumnIndex()), sheet.getStringCellValue(cellIndexInfo.getEnumCommentColumnIndex()));
        elements.addClassElementsItem(type);
        return elements;
    }

    @Override
    protected int getStartIndexRepeatingRow() {
        return cellIndexInfo.getStartRepeatingRowIndex();
    }

    /**
     * Holds enumeration elements.<br/>
     * @author Kouji Ishii
     */
    @RequiredArgsConstructor
    @Getter
    public class EnumElements implements ClassElements {

        private final String packageName;

        /** javadoc comment for class */
        private final String classComment;

        /** class name */
        private final String className;

        /** enum type list */
        private final List<EnumClassElementsItem> classElementsItems = new ArrayList<>();

        /**
         * Adds EnumAttribute.<br/>
         * @param type type
         */
        @Override
        public void addClassElementsItem(ClassElementsItem type) {
            classElementsItems.add((EnumClassElementsItem) type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<? extends ClassElementsItem> getClassElementsItem() {
            return classElementsItems;
        }

    }

    /**
     * Presents Enum element.<br/>
     * @author Kouji Ishii
     */
    @RequiredArgsConstructor
    @Getter
    public static class EnumClassElementsItem implements ClassElementsItem {

        /** enum name */
        private final NameRuleString name;

        /** enum value */
        private final String code;

        /** javdoc comment for the enum */
        private final String comment;

        public String getUpperCamelName() {
            return name.toUpperCamel();
        }

        public String getUpperSnakeName() {
            return name.phraseConstantName();
        }

        public String getUpperName() {
            return name.toUpperCase();
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class EnumCellIndexHolder {

        /** B Column of spreadsheet */
        private final Integer classCommentColumnIndex;

        /** C Column of spreadsheet */
        private final Integer classNameColumnIndex;

        /** D Column of spreadsheet */
        private final Integer enumNameColumnIndex;

        /** E Column of spreadsheet */
        private final Integer enumValueColumnIndex;

        /** F Column of spreadsheet */
        private final Integer enumCommentColumnIndex;

        /** 2 row of spreadsheet */
        private final Integer startRepeatingRowIndex;

    }
}
