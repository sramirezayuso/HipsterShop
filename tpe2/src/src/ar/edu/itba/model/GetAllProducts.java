package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetAllProducts extends MethodObject implements Parcelable {
	private Meta meta;
	private List<Product> products;
	
	public Meta getMeta() {
		return meta;
	}

	public List<Product> getProducts() {
		return products;
	}

	public String toString() {
		return meta.toString() + products.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(products);
	}

	private GetAllProducts(Parcel in) {
		in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<GetAllProducts> CREATOR = new Parcelable.Creator<GetAllProducts>() {
		public GetAllProducts createFromParcel(Parcel in) {
			return new GetAllProducts(in);
		}

		public GetAllProducts[] newArray(int size) {
			return new GetAllProducts[size];
		}
	};
}
