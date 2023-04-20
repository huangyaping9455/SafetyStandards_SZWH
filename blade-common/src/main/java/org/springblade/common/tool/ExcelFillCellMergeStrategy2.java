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

public class ExcelFillCellMergeStrategy2 implements CellWriteHandler {

	/**
	 * 需要进行单元格合并的列数组
	 **/
	private int[] mergeColumnIndex;
	/**
	 * 单元格合并从第几行开始
	 **/
	private int mergeRowIndex;

	public ExcelFillCellMergeStrategy2() {
	}

	public ExcelFillCellMergeStrategy2(int mergeRowIndex, int[] mergeColumnIndex) {
		this.mergeRowIndex = mergeRowIndex;
		this.mergeColumnIndex = mergeColumnIndex;
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
								 List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean isHead) {
		int curRowIndex = cell.getRowIndex();
		int curColIndex = cell.getColumnIndex();
		if (curRowIndex > mergeRowIndex) {
			for (int i = 0; i < mergeColumnIndex.length; i++) {
				if (curColIndex == mergeColumnIndex[i]) {
					mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
					break;
				}
			}
		}
	}

	/**
	 * 当前单元格向上合并
	 *
	 * @param writeSheetHolder
	 * @param cell             当前单元格
	 * @param curRowIndex      当前行
	 * @param curColIndex      当前列
	 */
	private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
		Object curData =
			cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
		Row preRow = cell.getSheet().getRow(curRowIndex - 1);
		if (preRow == null) {
			// 当获取不到上一行数据时，使用缓存sheet中数据
			preRow = writeSheetHolder.getCachedSheet().getRow(curRowIndex - 1);
		}
		Cell preCell = preRow.getCell(curColIndex);
		Object preData =
			preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
		// 将当前单元格数据与上一个单元格数据比较
		Boolean dataBool = preData.equals(curData);
		if (dataBool) {
			Sheet sheet = writeSheetHolder.getSheet();
			List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
			boolean isMerged = false;
			for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
				CellRangeAddress cellRangeAddr = mergeRegions.get(i);
				// 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
				if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
					sheet.removeMergedRegion(i);
					cellRangeAddr.setLastRow(curRowIndex);
					sheet.addMergedRegion(cellRangeAddr);
					isMerged = true;
				}
			}
			// 若上一个单元格未被合并，则新增合并单元
			if (!isMerged) {
				CellRangeAddress cellRangeAddress =
					new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
				sheet.addMergedRegion(cellRangeAddress);
			}
		}
	}
}
