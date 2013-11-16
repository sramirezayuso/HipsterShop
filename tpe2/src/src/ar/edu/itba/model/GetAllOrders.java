package ar.edu.itba.model;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


public class GetAllOrders extends MethodObject{
	private Meta meta;
	private List<Order> orders;
	
	public Meta getMeta() {
		return meta;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public String toString() {
		return meta.toString() + orders.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.getOrders());
	}
	
	private GetAllOrders(Parcel in) {
		in.readTypedList(this.getOrders(), Order.CREATOR);
	}

	public static final Parcelable.Creator<GetAllOrders> CREATOR = new Parcelable.Creator<GetAllOrders>() {
		public GetAllOrders createFromParcel(Parcel in) {
			return new GetAllOrders(in);
		}

		public GetAllOrders[] newArray(int size) {
			return new GetAllOrders[size];
		}
	 };	
}
