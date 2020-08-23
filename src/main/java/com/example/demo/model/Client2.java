package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.FileConnection;

public class Client2 {
	private ArrayList<String> attributesList;
	private int nb;

	
	
public int size() {
	return attributesList.size();
}
	public  int getNb() {
		return this.nb;
	}
	
	public void add(String string) {
		attributesList.add(string);
	}



	
	public ArrayList<String> getAttributesList() {
		return attributesList;
	}

	public void setAttributesList(ArrayList<String> attributesList) {
		this.attributesList = attributesList;
	}

	public Client2() {
		this.attributesList = new ArrayList<String>(); 
	//	setNumero(nb);
		//nb++;
	
	}
	public List<String> linklist(FileConnection fileConnection) {
		    List<String> list =new ArrayList<String>();
		    for(Object i:fileConnection.getList()) {
		    	 list.add(this.getAttributesList().get((int)i));
		    	
		    }
			return list;
	}
	public List<String> Other(FileConnection fileConnection){
		 List<String> list = new ArrayList<String>();
		 int test;
		 for(int j=0;j<attributesList.size();j++) {
			 test=0;
			 for(Object i:fileConnection.getList()) {
			    	if(j==(int)i) {
			    		test=1;
			    		break;
			    	}
			    }
			 if(test==0) {
				 list.add(attributesList.get(j));
			 }
			
			 
		 }
		 return list;
		 
		
	}
	
	
	
	@Override
	public String toString() {
		return "Client2 [attributesList=" + attributesList + ", nb=" + nb + "]";
	}
	public Client2(int n) {
		this.attributesList = new ArrayList<String>(n); 
	}

	public void setNb(int nb) {
		this.nb = nb;
	}








}
