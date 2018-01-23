package com.theater.seating.domain;

import java.util.ArrayList;
import java.util.List;

public class TheaterSection {

	private Integer totalSeatCount = 0;
	
	private Integer partiallyBlockedSeatCount = 0;
	
	private Integer confirmBlockedSeatCount = 0;
	
	private List<MailInfo> mailReqests = new ArrayList<MailInfo>();
	
	private Boolean isConfirmed = false;

	public Integer getTotalSeatCount() {
		return totalSeatCount;
	}

	public void setTotalSeatCount(Integer totalSeatCount) {
		this.totalSeatCount = totalSeatCount;
	}

	public Integer getPartiallyBlockedSeatCount() {
		return partiallyBlockedSeatCount;
	}

	public void setPartiallyBlockedSeatCount(Integer partiallyBlockedSeatCount) {
		this.partiallyBlockedSeatCount = partiallyBlockedSeatCount;
	}

	public Integer getConfirmBlockedSeatCount() {
		return confirmBlockedSeatCount;
	}

	public void setConfirmBlockedSeatCount(Integer confirmBlockedSeatCount) {
		this.confirmBlockedSeatCount = confirmBlockedSeatCount;
	}

	public List<MailInfo> getMailReqests() {
		return mailReqests;
	}

	public void setMailReqests(List<MailInfo> mailReqests) {
		this.mailReqests = mailReqests;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	@Override
	public String toString() {
		return "TheaterSection [totalSeatCount=" + totalSeatCount + ", partiallyBlockedSeatCount="
				+ partiallyBlockedSeatCount + ", confirmBlockedSeatCount=" + confirmBlockedSeatCount + ", mailReqests="
				+ mailReqests + ", isConfirmed=" + isConfirmed + "]";
	}

}
