package com.vti.railway12.entity;


import static java.time.Duration.between;
import static java.time.Duration.ofSeconds;
import static java.time.LocalTime.ofSecondOfDay;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Data;



@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "timesheet_register")
public class TimesheetRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "employee_id")
	  private Employee employee;
	  
	  @Column(name = "cost_hour", precision = 10, scale = 2)
	  private Double costHour;
	  
	  @Column(name = "time_in")
	  private LocalDateTime timeIn;
	  
	  @Column(name = "lunch_start")
	  private LocalDateTime lunchStart;

	  @Column(name = "lunch_end")
	  private LocalDateTime lunchEnd;

	  @Column(name = "time_out")
	  private LocalDateTime timeOut;
	  
	  @Column(name = "hours_worked")
	  private Duration hoursWorked;
	  
	  @Column(name = "extra_hours")
	  private Duration extraHours;
	  
	  @Column(name = "hours_journey")
	  private Duration hoursJourney;
	  
	  @Column(name = "notes")
	  private String notes;
	  
	  public String getHoursWorked() {
		    return formatDuration(hoursWorked.toMillis(), "HH:mm");
		  }
	  
	  public String getExtraHours() {
		    return formatDuration(extraHours.toMillis(), "HH:mm");
		  }
	  
	  public String getHoursJourney() {
		    return formatDuration(hoursJourney.toMillis(), "HH:mm");
		  }
	  
	  /**
	   * Calculate hours.
	   */
	  @PrePersist
	  @PreUpdate
	  public void calculateHours() {
	    resetValues();

	    long firstPeriod = between(timeIn, lunchStart).getSeconds();
	    long secondPeriod = between(lunchEnd, timeOut).getSeconds();
	    hoursWorked = ofSeconds(firstPeriod + secondPeriod);
	    
	    long extraHoursDuration = hoursWorked != null
	            ? hoursWorked.getSeconds() - hoursJourney.getSeconds() : BigDecimal.ZERO.intValue();
	    extraHours = extraHoursDuration > BigDecimal.ZERO.intValue()
	            ? ofSeconds(extraHoursDuration) : ofSeconds(BigDecimal.ZERO.intValue());
	    
	    
	  }
	  
	  private void resetValues() {
		    hoursWorked = ofSeconds((BigDecimal.ZERO.intValue()));
		    extraHours = ofSeconds(BigDecimal.ZERO.intValue());
		  }

}
