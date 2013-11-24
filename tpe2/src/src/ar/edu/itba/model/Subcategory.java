package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Subcategory extends ModelObject{
	
	private Integer id;
	private String name;
	private Category category;
	private Attribute[] attributes;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
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
	
	public Subcategory(String name, Attribute[] attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
	}

	private Subcategory(Parcel in) {
	}

	public static final Parcelable.Creator<Subcategory> CREATOR = new Parcelable.Creator<Subcategory>() {
		public Subcategory createFromParcel(Parcel in) {
			return new Subcategory(in);
		}

		public Subcategory[] newArray(int size) {
			return new Subcategory[size];
		}
	};

}
