package com.wufangnao.item;

import java.util.List;

public class City {
	
	private String id;
	private String name;
	private List<Area> listAreas;
	private String sortLetters;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Area> getListAreas() {
		return listAreas;
	}
	public void setListAreas(List<Area> listAreas) {
		this.listAreas = listAreas;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	

}
