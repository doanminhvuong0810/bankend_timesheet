package com.vti.railway12.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "position")
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private PositionName positionName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "position",cascade = {CascadeType.REMOVE})
	private Set<Employee> listAccount = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PositionName getPositionName() {
		return positionName;
	}

	public void setPositionName(PositionName positionName) {
		this.positionName = positionName;
	}

	
	
}
