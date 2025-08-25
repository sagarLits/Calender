package com.lits.calender.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CalenderEvent {
	@Id
	private String calenderEventId;
	private String title; // Event type: BIRTHDAY, ANNIVERSARY, BODY_MEETING, BOARD_MEETING
	private String name; // Birthday person name / Meeting subject
	private LocalDate eventDate; // Single date
	private String description;

}
