package com.theater.seating.domain;

public class CurrentIndex {

	private int mailRequestIndex;
	
	private int rowIndex;
	
	private int sectionIndex;

	public int getMailRequestIndex() {
		return mailRequestIndex;
	}

	public void setMailRequestIndex(int mailRequestIndex) {
		this.mailRequestIndex = mailRequestIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getSectionIndex() {
		return sectionIndex;
	}

	public void setSectionIndex(int sectionIndex) {
		this.sectionIndex = sectionIndex;
	}

	@Override
	public String toString() {
		return "CurrentIndex [mailRequestIndex=" + mailRequestIndex + ", rowIndex=" + rowIndex + ", sectionIndex="
				+ sectionIndex + "]";
	}
	
}
