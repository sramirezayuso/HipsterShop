package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetProductsByName extends MethodObject implements Parcelable {
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

	private GetProductsByName(Parcel in) {
		in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<GetProductsByName> CREATOR = new Parcelable.Creator<GetProductsByName>() {
		public GetProductsByName createFromParcel(Parcel in) {
			return new GetProductsByName(in);
		}

		public GetProductsByName[] newArray(int size) {
			return new GetProductsByName[size];
		}
	};
}
