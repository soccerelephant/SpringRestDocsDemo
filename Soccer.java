package com.example;

public class Soccer {

	private String name;
	private Long id;

	public Soccer() {
	}

	public Soccer(Long id, String text) {
		super();
		this.id = id;
		this.name = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String text) {
		this.name = text;
	}

}