package com.example.app_ban_hang_tot_nghiep.model;

import java.util.List;

public class FavoriteItem{
	private List<String> image;
	private int amount;
	private String color;
	private String smell;
	private String mfg;
	private int importPrice;
	private String measure;
	private String size;
	private int price;
	private String product_id;
	private int V;
	private String _id;
	private String exp;

	public List<String> getImage(){
		return image;
	}

	public int getAmount(){
		return amount;
	}

	public String getColor(){
		return color;
	}

	public String getSmell(){
		return smell;
	}

	public String getMfg(){
		return mfg;
	}

	public int getImportPrice(){
		return importPrice;
	}

	public String getMeasure(){
		return measure;
	}

	public String getSize(){
		return size;
	}

	public int getPrice(){
		return price;
	}

	public String getProductId(){
		return product_id;
	}

	public int getV(){
		return V;
	}

	public String getId(){
		return _id;
	}

	public String getExp(){
		return exp;
	}
}