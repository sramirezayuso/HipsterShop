package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetProductById extends MethodObject implements Parcelable {
	private Meta meta;
	private Product product;

	public Meta getMeta() {
		return meta;
	}

	public Product getProduct() {
		return product;
	}

	public String toString() {
		return meta.toString() + product.toString();
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelable(product, flags);
	}

	private GetProductById(Parcel in) {
		in.readParcelable(Product.class.getClassLoader());
	}

	public static final Parcelable.Creator<GetProductById> CREATOR = new Parcelable.Creator<GetProductById>() {
		public GetProductById createFromParcel(Parcel in) {
			return new GetProductById(in);
		}

		public GetProductById[] newArray(int size) {
			return new GetProductById[size];
		}
	};
}
