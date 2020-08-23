package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Repositorytel;

public class ClientMapping {
	private ArrayList<String> stringlist;
	private static int pppp;
	private int i;
	private String nb;
	private String updateOrAdd;
	private Integer id;
	

	

	public void setUpdate(String update) {
		this.updateOrAdd = update;
	}

	public ClientMapping() {
		super();
		this.stringlist = new ArrayList<String>();
	}

	public ArrayList<String> getStringlist() {
		return stringlist;
	}


	public void setStringlist(ArrayList<String> stringlist) {
		for (String string : stringlist) {
			this.stringlist.add(string);
			this.stringlist.add(string);
		}

	}

	public String getNb() {
		if (updateOrAdd.equals("update")) {

			String s = stringlist.get(i % (2 * pppp));
			i = i + 1;

			return s;
		} else {
			return this.nb;
		}
	}

	public void setNb(String nb) {

		this.nb = nb;

	}

	@Override
	public String toString() {
		return "ClientMapping [stringlist=" + stringlist + ", j=" + i + ", nb=" + nb + "pppp:" + pppp + "update:"+ updateOrAdd + "id" + id +"]";
	}

	public ArrayList<String> getlist() {
		String[] nns = this.nb.split(",");
		for (String st : nns) {

			if (!st.isEmpty())
				stringlist.add(st);
			else
				stringlist.add("empty");
		}

			while (stringlist.size() < pppp)

			{
				

				stringlist.add("empty");
			}

		
		System.out.println(stringlist);

		
		return stringlist;

	}

	public static int getPppp() {
		return pppp;
	}

	public static void setPppp(int n) {
		ClientMapping.pppp = n;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateOrAdd() {
		return updateOrAdd;
	}
	int j=0;
	public void setUpdateOrAdd( String string) {
		if(j==0) {
			this.updateOrAdd= string;
			j=j+1;
		}
		
	}
	
	

}
