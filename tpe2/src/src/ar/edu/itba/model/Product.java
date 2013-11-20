package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product extends ModelObject {

	private Integer id;
	private String name;
	private Integer price;
	private String[] imageUrl;
	private Category category;
	private Category subcategory;
	private Attribute[] attributes;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Integer getPrice() {
		return price;
	}

	public String[] getImageUrl() {
		return imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public Category getSubcategory() {
		return subcategory;
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

	private Product(Parcel in) {
	}

	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}
