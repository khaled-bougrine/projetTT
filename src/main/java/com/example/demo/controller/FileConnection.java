package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
@Service
public class FileConnection {
	private String fileName;
	private String passWord;
	private int champ1;
	private int champ2;
	private ArrayList<Integer> list; 

	@Override
	public String toString() {
		return "FileConnection [fileName=" + fileName + ", passWord=" + passWord + ", id=" + champ1 + ", name=" + champ2
				+ ", list=" + list + "]";
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public Workbook getReadConection() throws Exception {
		FileInputStream fi = new FileInputStream(fileName);
		return WorkbookFactory.create(fi,passWord);

	}
	public Workbook getWriteConection() throws Exception {
		FileInputStream fi = new FileInputStream(fileName);
		return WorkbookFactory.create(fi,passWord);

	}
	public Integer getChamp1() {
		return champ1;
	}
	public void setChamp1(Integer champ1) {
		this.champ1 = champ1;
	}
	public Integer getChamp2() {
		return champ2;
	}
	public void setChamp2(Integer champ2) {
		this.champ2 = champ2;
	}
	public ArrayList<Integer> getList() {
		return list;
	}
	public void setList(ArrayList<Integer> list) {
		this.list = list;
	}
	


	
	
	
	
	
	

}
