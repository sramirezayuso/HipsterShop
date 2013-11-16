package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order extends ModelObject{
	private Integer id;
	private String status;
	private CreditCard creditCard;	
	
	private String receivedDate;
	private String processedDate;
	private String shippedDate;
	private String deliveredDate;
	
	private Address address;	
	private Integer latitude;
	private Integer longitude;

	public Order( ){
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return status;
	}
	@Override
	public String toString() {
		return status;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
	}
	
	private Order(Parcel in) {
	}

	public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
		public Order createFromParcel(Parcel in) {
			return new Order(in);
		}

		public Order[] newArray(int size) {
			return new Order[size];
		}
	 };
}
