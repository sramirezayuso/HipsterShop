package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category extends ModelObject{
	
	private Integer id;
	private String name;
	private Attribute[] attributes;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Attribute[] getAttributes() {
		return attributes;
	}
	
	@Override
	public String toString() {
		return id + " - " + name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
	}
	
	public Category(String name, Attribute[] attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
	}

	private Category(Parcel in) {
	}

	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}

		public Category[] newArray(int size) {
			return new Category[size];
		}
	};

}
