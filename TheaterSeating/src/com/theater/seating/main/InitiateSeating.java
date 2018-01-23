package com.theater.seating.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.theater.seating.domain.CurrentIndex;
import com.theater.seating.domain.MailInfo;
import com.theater.seating.domain.ReadingStatus;
import com.theater.seating.domain.TheaterLayout;
import com.theater.seating.domain.TheaterRow;
import com.theater.seating.domain.TheaterSection;
import com.theater.seating.service.SeatingService;

public class InitiateSeating {

	public void fillMailInRequestInTheater(TheaterLayout tll, List<MailInfo> mailRequests, String enclosingPattern){
		SeatingService ss = new SeatingService();
		CurrentIndex crr = new CurrentIndex();
		while(crr.getMailRequestIndex() < mailRequests.size()){
			crr.setRowIndex(0);
			crr.setSectionIndex(0);
			ss.findSeat(mailRequests, tll, crr);
			crr.setMailRequestIndex(crr.getMailRequestIndex() + 1);
		}
		System.out.println(enclosingPattern);
		for(int i = 0; i<mailRequests.size(); i++){
			System.out.println(mailRequests.get(i).getReservationStatus());
		}
		System.out.println(enclosingPattern);
	}
	
	public static void main(String args[]) throws IOException{
		System.out.println("Welcome to Theater seating solutions!!");
		System.out.println("Please enter theater layout and mail in requests, start and end lines of input with quote<`> a.");
		System.out.println("Enter <exit> in a new line to exit.");
		String[] inputArgs = null;
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		try{
			String readingStatus = ReadingStatus.COMPLETE.toString();
			InitiateSeating is = new InitiateSeating();
			TheaterLayout tll = null;
			List<MailInfo> mailRequests = null;
			String enclosingPattern = null;
			while(true){
				inputArgs = scanner.readLine().split(" ");
				if(inputArgs[0].equals(enclosingPattern) && inputArgs.length == 1 && ReadingStatus.MAIL_IN_REQUEST.toString().equals(readingStatus)){
					readingStatus = ReadingStatus.COMPLETE.toString();
					is.fillMailInRequestInTheater(tll, mailRequests, enclosingPattern);
				} else if(inputArgs[0].contains("`") && inputArgs.length == 1 && ReadingStatus.COMPLETE.toString().equals(readingStatus)){
					readingStatus = ReadingStatus.START.toString();
					enclosingPattern = inputArgs[0];
					readingStatus = ReadingStatus.THEATER_LAYOUT.toString();
					tll = new TheaterLayout();
					mailRequests = new ArrayList<MailInfo>();
				} else if(ReadingStatus.THEATER_LAYOUT.toString().equals(readingStatus) && inputArgs[0].equals("")){
					if(tll.getRows().isEmpty()){
						throw new RuntimeException();
					}
					readingStatus = ReadingStatus.MAIL_IN_REQUEST.toString();
				} else if(ReadingStatus.THEATER_LAYOUT.toString().equals(readingStatus)){
					is.readLayout(inputArgs, tll);
				} else if(ReadingStatus.MAIL_IN_REQUEST.toString().equals(readingStatus)){
					is.readMailInRequest(inputArgs, mailRequests);
				} else if(!inputArgs[0].equals("") && "exit".equalsIgnoreCase(inputArgs[0].trim())){
					System.exit(0);
				} else if(!ReadingStatus.COMPLETE.toString().equals(readingStatus)){
					throw new RuntimeException();
				}
			}			
		} catch(Exception e){
			System.out.println("Invalid input. End of program.");
		} finally{
			scanner.close();
		}
	}
	
	public void readLayout(String[] inputArgs, TheaterLayout tll){
		TheaterRow row = new TheaterRow();
		TheaterSection section = null;
		for(String sectionCount : inputArgs){
			section = new TheaterSection();
			section.setTotalSeatCount(Integer.parseInt(sectionCount));
			row.setSeatCount(row.getSeatCount() + section.getTotalSeatCount());
			row.getSections().add(section);
		}
		tll.getRows().add(row);
	}
	
	public void readMailInRequest(String[] inputArgs, List<MailInfo> mailRequests){
		if(2 < inputArgs.length){
			throw new RuntimeException();
		}
		mailRequests.add(new MailInfo(inputArgs[0], Integer.parseInt(inputArgs[1])));
	}
}
