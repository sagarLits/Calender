package com.lits.calender.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lits.calender.Service.CalenderEventService;
import com.lits.calender.entity.CalenderEvent;


@RestController
@RequestMapping("/api/events")
@CrossOrigin("*")
public class CalenderEventController {
	 @Autowired
	    private CalenderEventService calenderEventService;

	    // ✅ Create Event
	    @PostMapping
	    public ResponseEntity<CalenderEvent> createEvent(@RequestBody CalenderEvent event) {
	        event.setCalenderEventId(UUID.randomUUID().toString());
	        CalenderEvent savedEvent = calenderEventService.addEvent(event);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
	    }

	    // ✅ Update Event
	    @PutMapping("/{id}")
	    public ResponseEntity<CalenderEvent> updateEvent(
	            @PathVariable("id") String id,
	            @RequestBody CalenderEvent event) {
	        CalenderEvent updatedEvent = calenderEventService.updateEvent(id, event);
	        return ResponseEntity.ok(updatedEvent);
	    }

	    // ✅ Delete Event
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteEvent(@PathVariable("id") String id) {
	        calenderEventService.deleteEvent(id);
	        return ResponseEntity.ok("Event deleted successfully");
	    }

	    // ✅ Get All Events
	    @GetMapping
	    public ResponseEntity<List<CalenderEvent>> getAllEvents() {
	        List<CalenderEvent> events = calenderEventService.getAllEvents();
	        return ResponseEntity.ok(events);
	    }

	    // ✅ Get Event by Id
	    @GetMapping("/{id}")
	    public ResponseEntity<CalenderEvent> getEventById(@PathVariable("id") String id) {
	        CalenderEvent event = calenderEventService.getEventById(id);
	        return ResponseEntity.ok(event);
	    }

	    // ✅ Get Events by Month & Year (RESTful: query params, not path params)
	    @GetMapping("/search")
	    public ResponseEntity<List<CalenderEvent>> getEventsByMonthAndYear(
	            @RequestParam int year,
	            @RequestParam int month) {
	        List<CalenderEvent> events = calenderEventService.getEventsByMonth(year, month);
	        return ResponseEntity.ok(events);
	    }

	    // ✅ Exception Handling
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred: " + e.getMessage());
	    }
}