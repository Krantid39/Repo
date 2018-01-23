package com.theater.seating.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.theater.seating.domain.CurrentIndex;
import com.theater.seating.domain.MailInfo;
import com.theater.seating.domain.TheaterLayout;
import com.theater.seating.domain.TheaterRow;
import com.theater.seating.domain.TheaterSection;
import com.theater.seating.util.CommonConstants;

public class SeatingService {

	public TheaterSection findSeat(List<MailInfo> mailRequests, TheaterLayout layout, CurrentIndex currIndex){
		if(null != mailRequests.get(currIndex.getMailRequestIndex()).getIsConfirmed()){
			return null;
		}
		//find next minimum fit
		TheaterSection section = findNextMinimumFit(layout, currIndex, mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount());
		if(null == section){
			mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.NO_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName()));
			for(TheaterRow row : layout.getRows()){
				if(row.getSeatCount() >= mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()){
					mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.CALL_BACK, mailRequests.get(currIndex.getMailRequestIndex()).getName()));					
				}
			}
			mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(true);
			return null;
		}
		if(section.getIsConfirmed()){
			if(null == incrementCurrentIndex(mailRequests, layout, currIndex)){
				return null;
			}
		} else {
			if(section.getTotalSeatCount() >= mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()){
				if(section.getTotalSeatCount() == mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()){
					if(section.getTotalSeatCount() - section.getPartiallyBlockedSeatCount() == 0 ){
						if(layout.getRows().size() > currIndex.getRowIndex()){
							if(layout.getRows().get(currIndex.getRowIndex()).getSections().size() > currIndex.getSectionIndex()){
								currIndex.setSectionIndex((currIndex.getSectionIndex() + 1));
							} else{
								currIndex.setRowIndex(currIndex.getRowIndex()+1);
								currIndex.setSectionIndex(0);
							}
							TheaterSection section2 = findSeat(mailRequests, layout, currIndex);
							if(null == section2){
								for(MailInfo mailReq : section.getMailReqests()){
									mailReq.setIsConfirmed(false);
								}
								section.getMailReqests().clear();
								section.setPartiallyBlockedSeatCount(0);
								section.setConfirmBlockedSeatCount(mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount());
								section.setIsConfirmed(true);
								section.getMailReqests().add(mailRequests.get(currIndex.getMailRequestIndex()));
								return section;
							} else {
								return null;
							}
						} else if(layout.getRows().size() == currIndex.getRowIndex() && layout.getRows().get(currIndex.getRowIndex()).getSections().size() > currIndex.getSectionIndex()){
							currIndex.setSectionIndex((currIndex.getSectionIndex() + 1));
							return findSeat(mailRequests, layout, currIndex);
						} else{
							mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(true);
							mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.NO_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName()));
							return null;
						}
					} else {
						for(MailInfo mi : section.getMailReqests()){
							mi.setIsConfirmed(null);
							mi.setReservationStatus(null);
						}
						section.getMailReqests().clear();
						section.setConfirmBlockedSeatCount(mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount());
						section.setIsConfirmed(true);
						mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(true);
						mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.CONFIRM_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName(), currIndex.getRowIndex()+1, currIndex.getSectionIndex()+1));
						section.getMailReqests().add(mailRequests.get(currIndex.getMailRequestIndex()));
						currIndex.setMailRequestIndex(-1);
						return section;
					}
				} else{

					//find combo fit
					List<MailInfo> temp = new ArrayList<MailInfo>();
					for(int i = 0; i < mailRequests.size(); i++){
						if(i == currIndex.getMailRequestIndex()){
							continue;
						}
						temp.add(mailRequests.get(i));
					}
					List<MailInfo> bestFits = findComboFit(temp, section.getTotalSeatCount() - section.getPartiallyBlockedSeatCount() - mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount());
					if(null == bestFits || 0 != bestFits.size()){
						if((section.getPartiallyBlockedSeatCount() + mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()) < section.getTotalSeatCount()){
							for(MailInfo mi : bestFits){
								mi.setIsConfirmed(false);
								mi.setReservationStatus(MessageFormat.format(CommonConstants.CONFIRM_RESERVATION, mi.getName(), currIndex.getRowIndex()+1, currIndex.getSectionIndex()+1));
								section.getMailReqests().add(mi);
								section.setPartiallyBlockedSeatCount(section.getPartiallyBlockedSeatCount() + mi.getTicketCount());
							}
							mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(false);
							mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.CONFIRM_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName(), currIndex.getRowIndex()+1, currIndex.getSectionIndex()+1));
							section.setPartiallyBlockedSeatCount((section.getPartiallyBlockedSeatCount() + mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()));
							section.getMailReqests().add(mailRequests.get(currIndex.getMailRequestIndex()));
						} else{
							if(null == incrementCurrentIndex(mailRequests, layout, currIndex)){
								return null;
							}
						}
					} else if(section.getTotalSeatCount() - section.getPartiallyBlockedSeatCount() >= mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()){
						mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(false);
						mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.CONFIRM_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName(), currIndex.getRowIndex()+1, currIndex.getSectionIndex()+1));
						section.setPartiallyBlockedSeatCount((section.getPartiallyBlockedSeatCount() + mailRequests.get(currIndex.getMailRequestIndex()).getTicketCount()));
						section.getMailReqests().add(mailRequests.get(currIndex.getMailRequestIndex()));
					} else{
						if(null == incrementCurrentIndex(mailRequests, layout, currIndex)){
							return null;
						}
					}
				}
			} else{
				if(null == incrementCurrentIndex(mailRequests, layout, currIndex)){
					return null;
				}
			}
		}
		return null;
	}

	public TheaterSection findNextMinimumFit(TheaterLayout layout, CurrentIndex currIndex, int seatsWanted){
		while(currIndex.getRowIndex() < layout.getRows().size()){
			while(currIndex.getSectionIndex() < layout.getRows().get(currIndex.getRowIndex()).getSections().size()){
				TheaterSection section = layout.getRows().get(currIndex.getRowIndex()).getSections().get(currIndex.getSectionIndex());
				if(section.getTotalSeatCount() >= seatsWanted){
					return section;
				}
				currIndex.setSectionIndex(currIndex.getSectionIndex()+1);
			}
			currIndex.setRowIndex(currIndex.getRowIndex()+1);
			currIndex.setSectionIndex(0);
		}
		return null;
	}

	public List<MailInfo> findComboFit(List<MailInfo> requests, int availableSeats){
		List<MailInfo> pickCombo = new ArrayList<MailInfo>();
		for(MailInfo request : requests){
			if((null == request.getIsConfirmed() || !request.getIsConfirmed()) && availableSeats >= request.getTicketCount()){
				pickCombo.add(request);
			}
		}
		List<MailInfo> fittingCombo = new ArrayList<MailInfo>();
		findFitRecursive(pickCombo,availableSeats,new ArrayList<MailInfo>(), fittingCombo);
		return fittingCombo;
	}

	public void findFitRecursive(List<MailInfo> requests, int target, List<MailInfo> partial, List<MailInfo> fittingCombo) {
		int s = 0;
		for (MailInfo x: partial) s += x.getTicketCount();
		if (s == target){
			fittingCombo.addAll(partial);
		}
		if (s >= target)
			return;
		for(int i=0;i<requests.size();i++) {
			List<MailInfo> remaining = new ArrayList<MailInfo>();
			for (int j=i+1; j<requests.size();j++){
				if(null == requests.get(j).getIsConfirmed()){
					remaining.add(requests.get(j));					
				}
			}
			List<MailInfo> partial_rec = new ArrayList<MailInfo>(partial);
			partial_rec.add(requests.get(i));
			findFitRecursive(remaining,target,partial_rec, fittingCombo);
		}
	}
	
	public TheaterSection incrementCurrentIndex(List<MailInfo> mailRequests, TheaterLayout layout, CurrentIndex currIndex){
		TheaterSection section = null;
		if(layout.getRows().size() > currIndex.getRowIndex()){
			if(layout.getRows().get(currIndex.getRowIndex()).getSections().size() > currIndex.getSectionIndex()){
				currIndex.setSectionIndex((currIndex.getSectionIndex() + 1));
			} else{
				currIndex.setRowIndex(currIndex.getRowIndex()+1);
				currIndex.setSectionIndex(0);
			}
			section = findSeat(mailRequests, layout, currIndex);		
		} else if(layout.getRows().size() == currIndex.getRowIndex() && layout.getRows().get(currIndex.getRowIndex()).getSections().size() > currIndex.getSectionIndex()){
			currIndex.setSectionIndex((currIndex.getSectionIndex() + 1));
			section = findSeat(mailRequests, layout, currIndex);
		} else{
			mailRequests.get(currIndex.getMailRequestIndex()).setIsConfirmed(true);
			mailRequests.get(currIndex.getMailRequestIndex()).setReservationStatus(MessageFormat.format(CommonConstants.NO_RESERVATION, mailRequests.get(currIndex.getMailRequestIndex()).getName()));
			section = null;
		}
		
		return section;
	}
}
