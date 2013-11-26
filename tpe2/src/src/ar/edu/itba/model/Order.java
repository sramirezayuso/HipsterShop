package ar.edu.itba.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Order extends ModelObject {
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
	

	public String getStatusString(){
		switch(Integer.getInteger(status)){
		case 1 : return "created"; 
		case 2 : return "confirmed"; 
		case 3 : return "moving";
		case 4 : return "shipped"; 
		}
		
		return "";
	}

	private SpecificOrder[] items;

	public Order() {
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return status;
	}

	public String getStatus() {
		return status;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public String getProcessedDate() {
		return processedDate;
	}

	public String getShippedDate() {
		return shippedDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public Address getAddress() {
		return address;
	}

	public SpecificOrder[] getItems() {
		return items;
	}

	public ArrayList<SpecificOrder> getListItems() {
		ArrayList<SpecificOrder> products = new ArrayList<SpecificOrder>();

		for (SpecificOrder each : items)
			products.add(each);

		return products;
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

	public int getTotalPrice() {
		int total = 0;

		for (SpecificOrder each : items) {
			if (each.getQuantity() != null && each.getPrice() != null)
				total += each.getQuantity() * each.getPrice();
		}

		return total;
	}
}
