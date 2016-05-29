package wholesale;


import java.util.*;
/********************************
 * 
 *CommodityLibrary.java
 *Description: �����̵���Ʒ�嵥
 ********************************/
public class CommodityLibrary {
	Map<String,Commodity> commodities = new HashMap<String,Commodity>();
	
	public CommodityLibrary(){
	commodities.put("ITEM000000",new Commodity("ITEM000000","�ɿڿ���","ƿ","ʳƷ","̼������",3.00));
	commodities.put("ITEM000001",new Commodity("ITEM000001","ѩ��","ƿ","ʳƷ","̼������",3.00));
	commodities.put("ITEM000002",new Commodity("ITEM000002","ƻ��","��","ʳƷ","ˮ��",5.50));
	commodities.put("ITEM000003",new Commodity("ITEM000003","��ë��","��","�˶�����","����",1.00));
	}
	
	public Commodity getCommodity(String barcode){
		return commodities.get(barcode);
	}

}
