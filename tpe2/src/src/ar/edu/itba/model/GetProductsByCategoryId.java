package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetProductsByCategoryId extends MethodObject implements Parcelable {
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

	private GetProductsByCategoryId(Parcel in) {
		in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<GetProductsByCategoryId> CREATOR = new Parcelable.Creator<GetProductsByCategoryId>() {
		public GetProductsByCategoryId createFromParcel(Parcel in) {
			return new GetProductsByCategoryId(in);
		}

		public GetProductsByCategoryId[] newArray(int size) {
			return new GetProductsByCategoryId[size];
		}
	};
}
