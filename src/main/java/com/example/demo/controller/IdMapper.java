package com.example.demo.controller;

import java.util.ArrayList;

public class IdMapper {
	//private ArrayList<String> hh;

	private String id;
	private String name;
	private ArrayList<String> list;
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "IdMapper [id=" + id +  name + "]";
	}


	public ArrayList<String> getList() {
		return list;
	}


	public void setList(ArrayList<String> list) {
		this.list = list;
	}





	
	
	
	
	

}
