package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order extends ModelObject{
	private String id;
	private String address;
	private String receivedDate;
	private String processedDate;
	private String shippedDate;
	private String deliveredDate;
	private String latitude;
	private String longitude;

	public Order(String address){
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return address;
	}
	@Override
	public String toString() {
		return address;
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
