package com.theater.seating.domain;

import java.util.ArrayList;
import java.util.List;

public class TheaterLayout {

	private List<TheaterRow> rows = new ArrayList<TheaterRow>();

	public List<TheaterRow> getRows() {
		return rows;
	}

	public void setRows(List<TheaterRow> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "TheaterLayout [rows=" + rows + "]";
	}
	
}
