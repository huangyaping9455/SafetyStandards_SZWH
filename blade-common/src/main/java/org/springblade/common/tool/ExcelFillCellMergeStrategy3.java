package org.springblade.common.tool;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFillCellMergeStrategy3 implements CellWriteHandler {

	private final int firstColumnIndex;
	private final int[] mergeColumnIndexes;
	private final int mergeRowCount;

	private Map<String, CellRangeAddress> mergeRanges;

	public ExcelFillCellMergeStrategy3(int firstColumnIndex, int[] mergeColumnIndexes, int mergeRowCount) {
		this.firstColumnIndex = firstColumnIndex;
		this.mergeColumnIndexes = mergeColumnIndexes;
		this.mergeRowCount = mergeRowCount;
		this.mergeRanges = new HashMap<>();
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
								 List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean isHead) {
		int curRowIndex = cell.getRowIndex();
		int curColIndex = cell.getColumnIndex();

		// Only merge cells in rows below the first row to merge
		if (curRowIndex > mergeRowCount) {
			// If this is the first column, use its value to determine merge ranges
			if (curColIndex == firstColumnIndex) {
				String cellValue = cell.getStringCellValue();
				CellRangeAddress mergeRange = mergeRanges.get(cellValue);
				if (mergeRange == null) {
					// Create a new merge range for this value
					mergeRange = new CellRangeAddress(curRowIndex - mergeRowCount, curRowIndex, firstColumnIndex, firstColumnIndex);
					mergeRanges.put(cellValue, mergeRange);
				} else {
					// Expand the existing merge range
					mergeRange.setLastRow(curRowIndex);
				}
			} else {
				// Merge cell based on the first column's value
				String firstColumnValue = getCellValue(writeSheetHolder.getSheet().getRow(curRowIndex - mergeRowCount).getCell(firstColumnIndex));
				CellRangeAddress mergeRange = mergeRanges.get(firstColumnValue);
				if (mergeRange != null) {
					int mergeRangeRowCount = mergeRange.getLastRow() - mergeRange.getFirstRow() + 1;
					int columnIndexInMergeRange = curColIndex - mergeColumnIndexes[0];
					if (columnIndexInMergeRange >= 0 && columnIndexInMergeRange < mergeColumnIndexes.length) {
						int mergeRangeLastRowIndex = curRowIndex - (curRowIndex - mergeRange.getFirstRow()) % mergeRangeRowCount;
						mergeRangeLastRowIndex = Math.min(mergeRangeLastRowIndex, mergeRange.getLastRow());
						if (mergeColumnIndexes.length == 1) {
							// Merge cells based on the first column's value, and merge rows based on the specified row count
							if (curRowIndex == mergeRangeLastRowIndex) {
								CellRangeAddress cellRangeAddress =
									new CellRangeAddress(mergeRange.getFirstRow(), curRowIndex, curColIndex, curColIndex);
								mergeRanges.remove(firstColumnValue);
								writeSheetHolder.getSheet().addMergedRegion(cellRangeAddress);
							}
						} else {
							// Merge cells based on the first column's value, and merge rows based on the sum of the specified row counts
							int mergeRangeRowCountSum = mergeRowCount;
							for (int i = 1; i < mergeColumnIndexes.length; i++) {
								int mergeRowCountForColumn = mergeRowCount;
								if (mergeColumnIndexes[i] == curColIndex) {
									mergeRowCountForColumn = mergeRowCount;
								} else {
									String mergeRowCountCellVal = getCellValue(writeSheetHolder.getSheet().getRow(mergeRange.getFirstRow() - 1).getCell(mergeColumnIndexes[i]));
									if (mergeRowCountCellVal != null && mergeRowCountCellVal.matches("\\d+")) {
										mergeRowCountForColumn = Integer.parseInt(mergeRowCountCellVal);
									}
								}
								mergeRangeRowCountSum += mergeRowCountForColumn;
							}
							if ((curRowIndex - mergeRange.getFirstRow()) % mergeRangeRowCountSum == mergeRangeRowCountSum - 1) {
								CellRangeAddress cellRangeAddress =
									new CellRangeAddress(mergeRange.getFirstRow(), curRowIndex, curColIndex, curColIndex);
								mergeRanges.remove(firstColumnValue);
								writeSheetHolder.getSheet().addMergedRegion(cellRangeAddress);
							}
						}
					}
				}
			}
		}
	}

	private String getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
}
