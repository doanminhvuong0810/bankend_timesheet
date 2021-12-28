package com.vti.railway12.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.vti.railway12.audit.Auditable;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee")
public class Employee extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(message = "Email không đúng định dạng")
	private String email;

	@NotEmpty(message = "Thiếu UserName")
	private String userName;

	@NotEmpty(message = "Thiếu FullName")
	private String fullName;


	@ManyToOne
	@JoinColumn(name = "Department_ID")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "Position_ID")
	private Position position;
	
	@UpdateTimestamp
	private Date updateAt;

	@CreationTimestamp
	private Date createDate;
	
	public Employee() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id + "  	"  + this.fullName;
	}
	

	
		
	
}