package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SignIn extends MethodObject implements Parcelable {
	private Meta meta;
	private String authenticationToken;

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void writeToParcel(Parcel out, int flags) {
//		out.writeTypedList(products);
	}

	private SignIn(Parcel in) {
//		in.readTypedList(products, Product.CREATOR);
	}

	public static final Parcelable.Creator<SignIn> CREATOR = new Parcelable.Creator<SignIn>() {
		public SignIn createFromParcel(Parcel in) {
			return new SignIn(in);
		}

		public SignIn[] newArray(int size) {
			return new SignIn[size];
		}
	};
}
