package wholesale;


import java.util.*;
/********************************
 * 
 *CommodityLibrary.java
 *Description: 建立商店商品清单
 ********************************/
public class CommodityLibrary {
	Map<String,Commodity> commodities = new HashMap<String,Commodity>();
	
	public CommodityLibrary(){
	commodities.put("ITEM000000",new Commodity("ITEM000000","可口可乐","瓶","食品","碳酸饮料",3.00));
	commodities.put("ITEM000001",new Commodity("ITEM000001","雪碧","瓶","食品","碳酸饮料",3.00));
	commodities.put("ITEM000002",new Commodity("ITEM000002","苹果","斤","食品","水果",5.50));
	commodities.put("ITEM000003",new Commodity("ITEM000003","羽毛球","个","运动器材","球类",1.00));
	}
	
	public Commodity getCommodity(String barcode){
		return commodities.get(barcode);
	}

}
