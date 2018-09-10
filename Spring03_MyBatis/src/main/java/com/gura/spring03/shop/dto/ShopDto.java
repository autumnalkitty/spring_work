package com.gura.spring03.shop.dto;

public class ShopDto {
	private int num; //상품번호
	private String name; //상품명
	private int price; //상품가격
	private int stock; //재고 개수
	private String id; //주문자 id
	public ShopDto() {}
	public ShopDto(int num, String name, int price, int stock, String id) {
		super();
		this.num = num;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
