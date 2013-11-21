package ar.edu.itba.model;


import android.os.Parcel;
import android.os.Parcelable;

public class GetOrderById extends MethodObject{
	private Meta meta;
	private Order order;
	
	public Meta getMeta() {
		return meta;
	}

	public Order getOrder() {
		return order;
	}

	public String toString() {
		return meta.toString() + order.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
	//	out.writeTypedList(products);
	}

	private GetOrderById(Parcel in) {
	//	in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<GetOrderById> CREATOR = new Parcelable.Creator<GetOrderById>() {
		public GetOrderById createFromParcel(Parcel in) {
			return new GetOrderById(in);
		}

		public GetOrderById[] newArray(int size) {
			return new GetOrderById[size];
		}
	};
}
