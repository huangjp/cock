package cock.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

	/**
	 * @param rs
	 *            数据
	 * @param xlsName
	 *            文件名
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void resultSetToExcel(String title, ResultSet rs,
			String xlsName) throws SQLException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row;
		HSSFCell cell;

		ResultSetMetaData md = rs.getMetaData();
		int nColumn = md.getColumnCount();

		int iRow = 0;
		if (title != null) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, nColumn - 1));
			row = sheet.createRow(iRow);
			cell = row.createCell(0);
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title);
			iRow++;
		}
		row = sheet.createRow(iRow);
		// 写入各个字段的名称
		for (int i = 1; i <= nColumn; i++) {
			cell = row.createCell(i - 1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(md.getColumnLabel(i));
		}
		iRow++;
		// 写入各条记录，每条记录对应Excel中的一行
		while (rs.next()) {
			row = sheet.createRow(iRow);
			for (int j = 1; j <= nColumn; j++) {
				cell = row.createCell((j - 1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (rs.getObject(j) != null)
					cell.setCellValue(rs.getObject(j).toString());
			}
			iRow++;
		}
		FileOutputStream fo = new FileOutputStream(xlsName);
		workbook.write(fo);
		fo.flush();
		fo.close();
	}
	
	public static <T> void ListToExcel(String title, List<T> list,
			String xlsName, String[] colNames) throws SQLException, IOException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row;
		HSSFCell cell;
		int nColumn = colNames.length;
		int iRow = 0;
		if (title != null) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, nColumn - 1));
			row = sheet.createRow(iRow);
			cell = row.createCell(0);
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title);
			iRow++;
		}
		row = sheet.createRow(iRow);
		// 写入各个字段的名称
		for (int i = 0; i < nColumn; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(colNames[i]);
		}
		iRow++;
		// 写入各条记录，每条记录对应Excel中的一行
		for(int i = 0; i < list.size(); i++) {
			row = sheet.createRow(iRow);
			T t = list.get(i);
			Map<String, Object> map = MyUtil.castMap(t);
			int j = 0;
			for(String key : map.keySet()) {
				Object value = map.get(key);
				if(value != null && j < nColumn) {
					cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(value.toString());
					j++;
				}
			}
			iRow++;
		}
		FileOutputStream fo = new FileOutputStream(xlsName);
		workbook.write(fo);
		fo.flush();
		fo.close();
	}

	public static void resultSetToExcel(String title, ResultSet rs,
			File xlsFile, int[] length) throws SQLException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row;
		HSSFCell cell;

		ResultSetMetaData md = rs.getMetaData();
		int nColumn = md.getColumnCount();

		int iRow = 0;
		if (title != null) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, nColumn - 1));
			row = sheet.createRow(iRow);
			cell = row.createCell(0);
			CellStyle style = workbook.createCellStyle();

			HSSFFont font = workbook.createFont();
			font.setFontName("黑体");
			font.setFontHeightInPoints((short) 16);// 设置字体大小

			style.setFont(font);// 选择需要用到的字体格式

			style.setAlignment(CellStyle.ALIGN_CENTER);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title);
			iRow++;
		}
		row = sheet.createRow(iRow);
		// 写入各个字段的名称
		for (int i = 0; i < nColumn; i++) {
			cell = row.createCell(i);
			CellStyle style = workbook.createCellStyle();

			HSSFFont font = workbook.createFont();
			font.setFontName("仿宋_GB2312");
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			font.setFontHeightInPoints((short) 12);
			if (i < length.length)
				sheet.setColumnWidth(i, length[i]);
			style.setFont(font);
			cell.setCellStyle(style);

			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(md.getColumnLabel(i + 1));
		}
		iRow++;
		// 写入各条记录，每条记录对应Excel中的一行
		while (rs.next()) {
			row = sheet.createRow(iRow);
			for (int j = 1; j <= nColumn; j++) {
				cell = row.createCell((j - 1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (rs.getObject(j) != null)
					cell.setCellValue(rs.getObject(j).toString());
			}
			iRow++;
		}
		FileOutputStream fo = new FileOutputStream(xlsFile);
		workbook.write(fo);
		fo.flush();
		fo.close();
	}
}