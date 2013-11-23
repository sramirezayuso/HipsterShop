package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetProductsBySubcategoryId extends MethodObject implements Parcelable {
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

	private GetProductsBySubcategoryId(Parcel in) {
		in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<GetProductsBySubcategoryId> CREATOR = new Parcelable.Creator<GetProductsBySubcategoryId>() {
		public GetProductsBySubcategoryId createFromParcel(Parcel in) {
			return new GetProductsBySubcategoryId(in);
		}

		public GetProductsBySubcategoryId[] newArray(int size) {
			return new GetProductsBySubcategoryId[size];
		}
	};
}
