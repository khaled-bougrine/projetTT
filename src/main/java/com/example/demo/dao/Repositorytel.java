package com.example.demo.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.controller.FileConnection;
import com.example.demo.controller.Id;
import com.example.demo.model.Client2;

@org.springframework.stereotype.Repository
public class Repositorytel {
	List<Client2> table;
	ArrayList<String> enTete;

	// FileConnection fileConnection;

	public int ClientNumber() {
		return table.size();
	}



	public void setEntete(FileConnection fileConnection) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		ArrayList<String> list = new ArrayList<String>();
		Row row = s.getRow(0);
		if (!isRowEmpty(row)) {

			Iterator<Cell> icell = row.iterator();
			while (icell.hasNext()) {
				// System.out.println("c");

				Cell c = icell.next();

				if (c.getCellType() == CellType.STRING) {

					list.add(c.getStringCellValue());
				} else if (c.getCellType() == CellType.NUMERIC) {
					Object ob = (int) c.getNumericCellValue();
					list.add(ob.toString());

				} else {
					list.add("empty");
				}
			}
		}
		this.enTete = list;
	}

	public List<String> getEntete() {
		return this.enTete;

	}

	public List<Client2> getTable() {
		return table;
	}

	public List<String> EnteteLink(FileConnection fileConnection) {
		List<String> list = new ArrayList<String>();
		for (Object i : fileConnection.getList()) {
			
			list.add(this.enTete.get((int)i));

		}
		return list;
	}

	public List<String> EntetOther(FileConnection fileConnection) {
		List<String> list = new ArrayList<String>();
		int test;
		for (int j = 0; j < enTete.size(); j++) {
			test = 0;
			for (Object i : fileConnection.getList()) {
				if (j == (int) i) {
					test = 1;
					break;
				}
			}
			if (test == 0) {
				list.add(enTete.get(j));
			}

		}
		return list;

	}

	public void setTable(FileConnection fileConnection) throws Exception {

		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		List<Client2> table = new ArrayList<Client2>();
		Iterator<Row> irow = s.iterator();
		int i = 0;
		while (irow.hasNext()) {
			Row row = irow.next();
			if (i == 0) {
				i = i + 1;
			} else {

				if (!isRowEmpty(row)) {

					Client2 client = new Client2();
					client.setNb(row.getRowNum());
					Iterator<Cell> icell = row.iterator();
					while (icell.hasNext()) {
						// System.out.println("c");

						Cell c = icell.next();

						/*
						 * if (DateUtil.isCellDateFormatted(c)) { System.out.println("bd");
						 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						 * client.add(sdf.format(c.getDateCellValue())); } -
						 */ if (c.getCellType() == CellType.STRING) {
							// System.out.println();
							if (c.getHyperlink() == null)
								client.add(c.getStringCellValue());
							else
								client.add(c.getHyperlink().getAddress());

						} else if (c.getCellType() == CellType.NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(c)) {
								client.add(c.getLocalDateTimeCellValue().toLocalDate().toString());

							} else {
								// System.out.println("bi");

								Object ob = (int) c.getNumericCellValue();
								client.add(ob.toString());

							}
						} else {
							client.add("empty");
						}
					}
					System.out.println(s.getRow(0).getPhysicalNumberOfCells());
					while (client.getAttributesList().size() < s.getRow(0).getPhysicalNumberOfCells()) {
						client.add("empty");
					}

					table.add(client);

				}

			}
		}
		this.table = table;

	}

	public Set<String> getList(FileConnection fileConnection, boolean isId) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		Set<String> list = new HashSet<String>();
		Iterator<Row> irow = s.iterator();
		int i;
		if (isId) {
			System.out.println(fileConnection.getChamp1());
			   i =  fileConnection.getChamp1();
			
			  
			

		} else {
			System.out.println("0000000000000000000000c");
			  i =  (fileConnection.getChamp2());
			System.out.println("0000000000000000000000c");

			

		}
		int j = 0;

		while (irow.hasNext()) {
			if (j == 0) {
				j = j + 1;
				irow.next();
			} else {

				Row row = irow.next();
				if (!isRowEmpty(row)) {
					if (row.getCell(i).getCellType() == CellType.STRING)

						list.add(row.getCell(i).getStringCellValue());
				} else if (row.getCell(i).getCellType() == CellType.NUMERIC) {
					Object ob = (int) row.getCell(i).getNumericCellValue();
					list.add(ob.toString());

				}
			}
		}

		return list;
	}

	private static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();

		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}

		return isEmpty;
	}

	public Client2 recupererParId(int id, FileConnection fileConnection) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);

		Row row = s.getRow(id);
		Client2 client = new Client2();
		client.setNb(id);
		Iterator<Cell> icell = row.iterator();

		while (icell.hasNext()) {
			// System.out.println("c");

			Cell c = icell.next();

			if (c.getCellType() == CellType.STRING) {
				// System.out.println();
				if (c.getHyperlink() == null)
					client.add(c.getStringCellValue());
				else
					client.add(c.getHyperlink().getAddress());

			} else if (c.getCellType() == CellType.NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(c)) {
					client.add(c.getLocalDateTimeCellValue().toLocalDate().toString());

				} else {
					// System.out.println("bi");

					Object ob = (int) c.getNumericCellValue();
					client.add(ob.toString());

				}
			} else {
				client.add("empty");
			}
		}
		while (client.getAttributesList().size() < s.getRow(0).getPhysicalNumberOfCells()) {
			client.add("empty");

		}
		// System.out.println(s.getRow(0).getPhysicalNumberOfCells());

		return client;

	}

	public void delete(int id, FileConnection fileConnection) throws Exception {
		// nbclient-=1;
		// this.table.remove(id - 1);

		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		FileOutputStream fo = new FileOutputStream(fileConnection.getFileName());
		Row row = s.getRow(id);
		s.removeRow(row);

		s.shiftRows(id + 1, table.size() + 1, -1);

		wb.write(fo);
		fo.close();
		// System.out.println(table.size());

	}


	public void update(Client2 client, FileConnection fileConnection) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		FileOutputStream fo = new FileOutputStream(fileConnection.getFileName());
		Row row = s.getRow(client.getNb());

		for (int i = 0; i < client.size(); i++) {
			String string = client.getAttributesList().get(i);
			Cell cell = row.getCell(i);
			if (cell != null ) {
					if(string.equals("empty"))
				        	cell.setCellType(CellType.BLANK);
					else
						cell.setCellValue(string);
			
			} else  {
				Cell c = row.createCell(i);
				c.setCellValue(string);
			}
		}
		wb.write(fo);
		fo.close();

	}

	public void addClient(Client2 client, FileConnection fileConnection) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		FileOutputStream fo = new FileOutputStream(fileConnection.getFileName());
		Row row = s.createRow(client.getNb());
		  CellStyle style=wb.createCellStyle();
		  style.setBorderLeft(BorderStyle.THIN);
		  style.setBorderRight(BorderStyle.THIN);
		  style.setBorderTop(BorderStyle.THIN);
		  style.setBorderBottom(BorderStyle.THIN);
		int i = 0;
		for (String object : client.getAttributesList()) {
			if (!object.equals("empty")) {
				Cell cell = row.createCell(i, CellType.STRING);
				cell.setCellValue(object);
				i = i + 1;
				cell.setCellStyle(style);
			} else {
				Cell cell =row.createCell(i, CellType.BLANK);
				cell.setCellStyle(style);
				i = i + 1;

			}

		}
		wb.write(fo);
		fo.close();

	}

	private boolean isRow(Id id, FileConnection fileConnection, Row row) {
		if (id.getId().isEmpty()) {
			if (row.getCell((int)fileConnection.getChamp2()).getCellType() == CellType.STRING)
				return row.getCell((int)fileConnection.getChamp2()).getStringCellValue().equals(id.getName());
			else {
				Object ob = (int) row.getCell((int)fileConnection.getChamp2()).getNumericCellValue();
				return ob.toString().equals(id.getName());
			}

		} else {
			if (row.getCell((int)fileConnection.getChamp1()).getCellType() == CellType.NUMERIC) {
				Object ob = (int) row.getCell((int)fileConnection.getChamp1()).getNumericCellValue();
				return ob.toString().equals(id.getId());
			} else {
				return row.getCell((int)fileConnection.getChamp1()).getStringCellValue().equals(id.getId());

			}
		}

	}

	public void setTable(Id id, FileConnection fileConnection) throws Exception {
		FileInputStream fi = new FileInputStream(fileConnection.getFileName());
		Workbook wb = WorkbookFactory.create(fi, fileConnection.getPassWord());
		Sheet s = wb.getSheetAt(0);
		List<Client2> table = new ArrayList<Client2>();

		Iterator<Row> irow = s.iterator();
		int i = 0;

		while (irow.hasNext()) {
			if (i == 0) {

				irow.next();
				i = i + 1;
			} else {
				Row row = irow.next();
				if (!isRowEmpty(row)) {

					if (isRow(id, fileConnection, row)) {
						Client2 client = new Client2();
						client.setNb(row.getRowNum());

						Iterator<Cell> icell = row.iterator();

						while (icell.hasNext()) {
							// System.out.println("c");

							Cell c = icell.next();

							if (c.getCellType() == CellType.STRING) {
								// System.out.println();
								if (c.getHyperlink() == null)
									client.add(c.getStringCellValue());
								else
									client.add(c.getHyperlink().getAddress());

							} else if (c.getCellType() == CellType.NUMERIC) {
								if (HSSFDateUtil.isCellDateFormatted(c)) {
									client.add(c.getLocalDateTimeCellValue().toLocalDate().toString());

								} else {
									// System.out.println("bi");

									Object ob = (int) c.getNumericCellValue();
									client.add(ob.toString());

								}
							} else {
								client.add("empty");
							}
						}
						// System.out.println(s.getRow(0).getPhysicalNumberOfCells());
						while (client.getAttributesList().size() < s.getRow(0).getPhysicalNumberOfCells()) {
							client.add("empty");
						}

						table.add(client);

					}

				}

			}
		}
		this.table = table;

	}
}
