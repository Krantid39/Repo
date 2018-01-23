package com.theater.seating.domain;

public class MailInfo {

	public MailInfo(){
		
	}
	
	public MailInfo(String name, Integer ticketCount){
		this.name = name;
		this.ticketCount = ticketCount;
	}
	private String name;
	
	private Integer ticketCount;
	
	private Boolean isConfirmed;
	
	private Integer mailReceiveOrder;
	
	private String reservationStatus;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public Integer getMailReceiveOrder() {
		return mailReceiveOrder;
	}

	public void setMailReceiveOrder(Integer mailReceiveOrder) {
		this.mailReceiveOrder = mailReceiveOrder;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	@Override
	public String toString() {
		return "MailInfo [name=" + name + ", ticketCount=" + ticketCount + ", isConfirmed=" + isConfirmed
				+ ", mailReceiveOrder=" + mailReceiveOrder + ", reservationStatus=" + reservationStatus + "]";
	}
	
}
