package com.tiw2019.buyer_seller.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@Document(collection = "transactions")
@JsonPropertyOrder({"card_number", "expiry_date","CVV"})
public class Transaction implements Serializable{
	    
	@Id
	private String id;

	private int card_number;
    
    private String expiry_date;
     
    private int CVV;
    
	public Transaction() {
		
	}

	@PersistenceConstructor
	public Transaction(int card_number, String expiry_date, int CVV) {
		this.card_number = card_number;
		this.expiry_date = expiry_date;
		this.CVV = CVV;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCard_number() {
		return card_number;
	}

	public void setCard_number(int card_number) {
		this.card_number = card_number;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public int getCVV() {
		return CVV;
	}

	public void setCVV(int cVV) {
		CVV = cVV;
	}
	

}
