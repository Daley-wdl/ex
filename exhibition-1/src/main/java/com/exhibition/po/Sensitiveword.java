package com.exhibition.po;


/**
 * Sensitiveword entity. @author MyEclipse Persistence Tools
 */
public class Sensitiveword implements java.io.Serializable {

	// Fields

	private Integer id;
	private String words;

	// Constructors

	/** default constructor */
	public Sensitiveword() {
	}

	/** full constructor */
	public Sensitiveword(String words) {
		this.words = words;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWords() {
		return this.words;
	}

	public void setWords(String words) {
		this.words = words;
	}

}