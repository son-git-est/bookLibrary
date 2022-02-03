package entity;

public class OrderDetails {
	
	private int orderId;
	private int bookid;
	private String bookTitle;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public OrderDetails(int orderId, int bookid) {
		super();
		this.orderId = orderId;
		this.bookid = bookid;
	}
	public OrderDetails(int bookid) {
		super();
		this.bookid = bookid;
	}
	public OrderDetails() {
		super();
	}
	
	
}
