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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Cell;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.common.exceptions.InvalidCellIndexException;
import com.google.common.base.Strings;

/**
 * Handles a class information to generate code.
 * @author ko2ic
 */
@RequiredArgsConstructor
public abstract class GeneratingCodeSeedBase {

    private final Map<String, ClassElements> map = new HashMap<>();

    private final String basePackageName;

    private final boolean whetherPackageNameUsesSheet;

    @Getter(value = AccessLevel.PROTECTED)
    private final String columnCellIndex;

    @Getter(value = AccessLevel.PROTECTED)
    private final String rowCellIndex;

    /**
     * Makes a class information used when generating code.
     * @param sheet sheet
     * @throws InvalidCellIndexException
     */
    public void grow(Sheet sheet) throws InvalidCellIndexException {

        String packageName = null;

        if (whetherPackageNameUsesSheet) {
            packageName = sheet.getSheetName();
        } else {
            packageName = getPackageName(sheet);
        }

        if (!Strings.isNullOrEmpty(basePackageName)) {
            packageName = basePackageName + "." + packageName;
        }

        String fullClassName = null;
        sheet.setStartRowIndex(getStartIndexRepeatingRow());

        sheet.getCurrentRow();
        sheet.recoverBrokenSerial();
        fullClassName = putClassElements(sheet, packageName);

        for (@SuppressWarnings("unused")
        Row row : sheet) {
            if (sheet.brokenSerial()) {
                sheet.recoverBrokenSerial();
                fullClassName = putClassElements(sheet, packageName);
            } else {
                if (containsKey(fullClassName)) {
                    ClassElements seed = get(fullClassName);
                    ClassElementsItem type = instanceClassElementsItem(sheet);
                    seed.addClassElementsItem(type);
                } else {
                    fullClassName = putClassElements(sheet, packageName);
                }
            }
        }
    }

    protected abstract ClassElementsItem instanceClassElementsItem(Sheet sheet) throws InvalidCellIndexException;

    /**
     * Puts seed that holds a class information.
     * @param sheet
     * @param packageName
     * @return package name + class name that key of map.
     * @throws InvalidCellIndexException
     */
    protected abstract String putClassElements(Sheet sheet, String packageName) throws InvalidCellIndexException;

    /**
     * Gets row to start repeating (specifies non-empty row) .
     * @return non-empty row index
     */
    protected abstract int getStartIndexRepeatingRow();

    public String getPackageName(Sheet sheet) throws InvalidCellIndexException {
        return sheet.getStringCellValue(Cell.convertToColumnIndex(columnCellIndex), Integer.valueOf(rowCellIndex) - 1);
    }

    public Map<String, ClassElements> harvest() {
        return Collections.unmodifiableMap(map);
    }

    protected boolean containsKey(String key) {
        return map.containsKey(key);
    }

    protected ClassElements get(String key) {
        return map.get(key);
    }

    protected void put(String className, ClassElements elements) {
        map.put(className, elements);
    }
}
