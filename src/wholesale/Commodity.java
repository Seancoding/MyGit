package wholesale;




/********************************
 * 
 * Commodity.java
 *Description: 商品信息
 ********************************/
public class Commodity {
	private String barcode;// 商品标识
	private String name;// 商品名称
	private String unit;// 计量单位
	private String category;//商品类别
	private String subCategory;//商品子类
	private double price;// 商品价格


	public Commodity(String barcode, String name, String unit, String category,String subCategory,double price) {
		this.barcode = barcode;
		this.name = name;
		this.unit = unit;
		this.category = category;
		this.subCategory = subCategory;
		this.price = price;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
