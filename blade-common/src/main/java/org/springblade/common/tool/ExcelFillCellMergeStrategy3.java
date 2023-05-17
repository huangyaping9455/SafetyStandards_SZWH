package org.springblade.common.tool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

public class ExcelFillCellMergeStrategy3 implements CellWriteHandler {

	private final int mergeRowCount; // 合并行数
	private final int firstColumnIndex; // 第一列的索引
	private final Set<Integer> mergeColumnIndexes; // 需要合并的列的索引
	private final boolean dynamicMerge; // 是否动态合并
	private Map<String, CellRangeAddress> mergeRanges; // 合并范围集合

	public ExcelFillCellMergeStrategy3(int mergeRowCount, int firstColumnIndex, boolean dynamicMerge, int... mergeColumnIndexes) {
		this.mergeRowCount = mergeRowCount;
		this.firstColumnIndex = firstColumnIndex;
		this.mergeColumnIndexes = new HashSet<>();
		for (int columnIndex : mergeColumnIndexes) {
			this.mergeColumnIndexes.add(columnIndex);
		}
		this.dynamicMerge = dynamicMerge;
		this.mergeRanges = new HashMap<>();
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
								 List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean isHead) {

		int curRowIndex = cell.getRowIndex();
		int curColIndex = cell.getColumnIndex();
		Sheet sheet = writeSheetHolder.getSheet();

		// 仅在合并行数后面的行中合并单元格
		if (curRowIndex > mergeRowCount) {
			// 如果这是第一列，则使用其值确定合并范围
			if (curColIndex == firstColumnIndex) {
				String cellValue = cell.getStringCellValue();
				CellRangeAddress mergeRange = mergeRanges.get(cellValue);
				if (mergeRange == null) {
					mergeRange = new CellRangeAddress(curRowIndex, curRowIndex + mergeRowCount - 1,
						firstColumnIndex, getMaxMergeColumnIndex(curColIndex));
					mergeRanges.put(cellValue, mergeRange);
				} else {
					mergeRange.setLastRow(curRowIndex + mergeRowCount - 1);
				}
			}
			// 如果需要动态合并，则根据第一列的值动态合并单元格
			else if (dynamicMerge) {
				if (isMergeColumn(curColIndex)) {
					Row row = sheet.getRow(curRowIndex);
					Cell firstCell = row.getCell(firstColumnIndex);
					String firstCellValue = firstCell.getStringCellValue();
					CellRangeAddress mergeRange = mergeRanges.get(firstCellValue);
					if (mergeRange != null) {
						// 如果这一行与上一行的第一列的值相同，则将当前单元格与上一行的单元格合并
						Row preRow = sheet.getRow(curRowIndex - 1);
						Cell preFirstCell = preRow.getCell(firstColumnIndex);
						String preFirstCellValue = preFirstCell.getStringCellValue();
						if (preFirstCellValue.equals(firstCellValue)) {
							CellRangeAddress preMergeRange = mergeRanges.get(preFirstCellValue);
							int lastMergeColumnIndex = getMaxMergeColumnIndex(curColIndex);
							preMergeRange.setLastColumn(lastMergeColumnIndex);
							mergeRange = preMergeRange;
						} else {
							mergeRange.setLastRow(curRowIndex + mergeRowCount - 1);
							sheet.addMergedRegionUnsafe(mergeRange);
						}
					}
				}
			}
			// 如果不需要动态合并，则根据选定的列静态合并单元格
			else {
				if (isMergeColumn(curColIndex)) {
					Row row = sheet.getRow(curRowIndex);
					Cell firstCell = row.getCell(firstColumnIndex);
					String firstCellValue = firstCell.getStringCellValue();
					CellRangeAddress mergeRange = mergeRanges.get(firstCellValue);
					if (mergeRange != null) {
						mergeRange.setLastColumn(getMaxMergeColumnIndex(curColIndex));
						sheet.addMergedRegionUnsafe(mergeRange);
					}
				}
			}
		}
	}

	// 判断是否需要合并该列
	private boolean isMergeColumn(int colIndex) {
		return mergeColumnIndexes.contains(colIndex);
	}

	// 获取需要合并的最大列的索引
	private int getMaxMergeColumnIndex(int curColIndex) {
		int maxMergeColumnIndex = curColIndex;
		for (int mergeColumnIndex : mergeColumnIndexes) {
			if (mergeColumnIndex > curColIndex) {
				break;
			}
			maxMergeColumnIndex = mergeColumnIndex;
		}
		return maxMergeColumnIndex;
	}

	// 表格中有空单元格时需要调用该方法以保证合并单元格的正确性
	public void mergeEmptyCell(Sheet sheet) {
		for (CellRangeAddress mergeRange : mergeRanges.values()) {
			int firstRow = mergeRange.getFirstRow();
			int lastRow = mergeRange.getLastRow();
			int firstCol = mergeRange.getFirstColumn();
			int lastCol = mergeRange.getLastColumn();
			for (int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					row = sheet.createRow(rowIndex);
				}
				for (int colIndex = firstCol; colIndex <= lastCol; colIndex++) {
					Cell cell = row.getCell(colIndex);
					if (cell == null) {
						cell = row.createCell(colIndex, CellType.BLANK);
					}
				}
			}
			sheet.addMergedRegionUnsafe(mergeRange);
		}
	}
}
