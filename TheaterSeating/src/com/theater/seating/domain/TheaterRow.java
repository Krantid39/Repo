package com.theater.seating.domain;

import java.util.ArrayList;
import java.util.List;

public class TheaterRow {

	private List<TheaterSection> sections = new ArrayList<TheaterSection>();
	
	private Integer seatCount = 0;

	public List<TheaterSection> getSections() {
		return sections;
	}

	public void setSections(List<TheaterSection> sections) {
		this.sections = sections;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	@Override
	public String toString() {
		return "TheaterRow [sections=" + sections + ", seatCount=" + seatCount + "]";
	}
	
}
