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

import java.util.List;

public class ExcelFillCellMergeStrategy implements CellWriteHandler {

	private int[] mergeColumnIndex;
	private int mergeRowIndex;
	private int mergeRowCount;

	public ExcelFillCellMergeStrategy() {
	}

	public ExcelFillCellMergeStrategy(int mergeRowIndex, int[] mergeColumnIndex, int mergeRowCount) {
		this.mergeRowIndex = mergeRowIndex;
		this.mergeColumnIndex = mergeColumnIndex;
		this.mergeRowCount = mergeRowCount;
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
								 List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean isHead) {
		int curRowIndex = cell.getRowIndex();
		int curColIndex = cell.getColumnIndex();
		if (curRowIndex >= mergeRowIndex) {
			for (int columnIndex : mergeColumnIndex) {
				if (curColIndex == columnIndex) {
					if ((curRowIndex - mergeRowIndex) % mergeRowCount == mergeRowCount - 1) {
						mergeRows(writeSheetHolder, curRowIndex, curColIndex);
					}
					break;
				}
			}
		}
	}

	private void mergeRows(WriteSheetHolder writeSheetHolder, int curRowIndex, int curColIndex) {
		Sheet sheet = writeSheetHolder.getSheet();
		CellRangeAddress cellRangeAddress =
			new CellRangeAddress(curRowIndex - mergeRowCount + 1, curRowIndex, curColIndex, curColIndex);
		sheet.addMergedRegion(cellRangeAddress);
	}
}
