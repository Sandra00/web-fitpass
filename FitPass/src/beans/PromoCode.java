package beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


public class PromoCode {
	private String code;
	private LocalDateTime expirationDate;
	private int usesLeft;
	private double discountPercentage;
	
	public PromoCode() {
		super();
	}
	
	public PromoCode(String code, LocalDateTime expirationDate, int usesLeft, double discountPercentage) {
		super();
		this.code = code;
		this.expirationDate = expirationDate;
		this.usesLeft = usesLeft;
		this.discountPercentage = discountPercentage;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	public int getUsesLeft() {
		return usesLeft;
	}
	public void setUsesLeft(int usesLeft) {
		this.usesLeft = usesLeft;
	}
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
	
}
