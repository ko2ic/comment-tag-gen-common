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
package com.github.ko2ic.plugin.eclipse.taggen.common.exceptions;

import lombok.RequiredArgsConstructor;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Cell;

@RequiredArgsConstructor
public class InvalidCellIndexException extends AppException {

    private final Integer columnIndex;

    private final Integer rowIndex;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage() {
        if (columnIndex == null && rowIndex == null) {
            return "A invalid value was inputted.";
        }
        String column = "?";
        if (columnIndex != null) {
            column = Cell.convertToColumnName(columnIndex);
        }
        String row = "?";
        if (rowIndex != null) {
            row = String.valueOf(rowIndex + 1);
        }
        return String.format("There isn't a value in the cell of %s column %s row", column, row);
    }
}
