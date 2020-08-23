package com.example.demo.controller;

public class Id {
	private String id;
	private String name;

	public Id() {
		super();
	}
	



	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}







	@Override
	public String toString() {
		return "Id [id=" + id + ", name=" + name + "]";
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}
	
	

}
