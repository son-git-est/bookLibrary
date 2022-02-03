package entity;

import java.util.Date;

public class Order {
	
	private int id;
	private int studentId;
	private Date submitDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Order(int id, int studentId, Date submitDate) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.submitDate = submitDate;
	}
	public Order() {
		// TODO Auto-generated constructor stub
	}
	

}
