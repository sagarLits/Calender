package com.lits.calender.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lits.calender.entity.CalenderEvent;
import com.lits.calender.exception.ResourceNotFoundException;
import com.lits.calender.repository.CalenderEventRepository;

@Service
public class CalenderEventService {

	@Autowired
	private CalenderEventRepository repo;

	// Create
	public CalenderEvent addEvent(CalenderEvent event) {
		String title = event.getTitle().toLowerCase();
		if (title.contains("birthday") || title.contains("anniversary")) {
			event.setRecurring(true);
		} else {
			event.setRecurring(false);
		}
		return repo.save(event);
	}

	// Update
	public CalenderEvent updateEvent(String calenderEventId, CalenderEvent event) {
		CalenderEvent old = repo.findById(calenderEventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + calenderEventId));

		old.setTitle(event.getTitle());
		old.setDescription(event.getDescription());
		old.setName(event.getName());
		old.setEventDate(event.getEventDate());
		String title = event.getTitle().toLowerCase();
		if (title.contains("birthday") || title.contains("anniversary")) {
			old.setRecurring(true);
		} else {
			old.setRecurring(false);
		}
		return repo.save(old);
	}

	// Delete
	public void deleteEvent(String calenderEventId) {
		CalenderEvent event = repo.findById(calenderEventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + calenderEventId));
		repo.delete(event);
	}

	// Get All
	public List<CalenderEvent> getAllEvents() {
		return repo.findAll();
	}

	// Get By Id
	public CalenderEvent getEventById(String calenderEventId) {
		return repo.findById(calenderEventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + calenderEventId));
	}

	// Get By Month & Year
	public List<CalenderEvent> getEventsByMonth(int year, int month) {
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		// 1. Fetch normal events in this month/year
		List<CalenderEvent> normalEvents = repo.findByMonthAndYear(start, end);

		// 2. Fetch recurring events (ignoring year)
		List<CalenderEvent> recurringEvents = repo.findRecurringEventsByMonth(month);

		// 3. Merge both
		List<CalenderEvent> allEvents = new ArrayList<>();
		allEvents.addAll(normalEvents);
		allEvents.addAll(recurringEvents);

		// 4. Sort by day of month
		allEvents.sort(Comparator.comparing(e -> e.getEventDate().getDayOfMonth()));
		return allEvents;
	}
}
