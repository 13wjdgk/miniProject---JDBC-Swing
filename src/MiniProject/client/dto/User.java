package MiniProject.client.dto;

import java.time.LocalDateTime;

import MiniProject.client.enums.GradeEnum;
import MiniProject.client.enums.RoleEnum;
import MiniProject.client.enums.StatusEnum;

public class User {
	private int userId;
	private String email;
	private String phone;
	private GradeEnum grade;
	private RoleEnum role;
	private String currentAddress;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private StatusEnum status;

	public User() {
		// 기본 생성자
	}

	public User(String email, String phone, GradeEnum grade, RoleEnum role,
		String currentAddress, LocalDateTime createdAt, LocalDateTime updatedAt, StatusEnum status) {
		this.email = email;
		this.phone = phone;
		this.grade = grade;
		this.role = role;
		this.currentAddress = currentAddress;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public GradeEnum getGrade() {
		return grade;
	}

	public void setGrade(GradeEnum grade) {
		this.grade = grade;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
}
