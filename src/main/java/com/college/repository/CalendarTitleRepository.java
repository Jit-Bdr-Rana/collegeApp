package com.college.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.college.model.Calendar;
import com.college.model.CalendarTitle;

public interface CalendarTitleRepository extends JpaRepository<CalendarTitle, Integer> {
	@Query("SELECT ct FROM CalendarTitle ct where ct.year=?1 and ct.month.id=?2 and ct.day=?3")
	public CalendarTitle  findCalendarTitleByYearMonthDay(int year,int month,int day);
}
