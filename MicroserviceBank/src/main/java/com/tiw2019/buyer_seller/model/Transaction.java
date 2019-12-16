package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@Document(collection = "gr81.11.transactions")
@JsonPropertyOrder({"price", "card_number", "expiry_date","cvv"})
public class Transaction implements Serializable{
	    
	@Id
	private String id;

	private Double price;
	
	private String card_number;
    
    private String expiry_date;
     
    private String cvv;
    
    public Transaction() {
    }
    
	@PersistenceConstructor
	public Transaction(Double price, String card_number, String expiry_date, String cvv) {
		this.price = price;
		this.card_number = card_number;
		this.expiry_date = expiry_date;
		this.cvv = cvv;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	

}
