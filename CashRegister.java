package wholesale;


import java.util.Map;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import java.util.Iterator;


/********************************
 * 
 *CashRegister.java
 *Description: 负责接收输入和打印小票的收银机模块
 ********************************/
public class CashRegister {
	public final String LINE = "-";//特定单位商品分隔符
	public double totalPrice;//商品总价
	
	CommodityLibrary coml = new CommodityLibrary();//初始化商品库

	Map<String,Integer> count = new HashMap<String,Integer>();//存储商品和对应数量
	
	/*
	 * Description: 用正则表达式取出输入数据中单条商品， Input: String类型的JSON字符串 str
	 * 
	 */	
	public void getInput(String str){
		String target = "\\w{4}\\d{6}\\-?\\d?";//匹配商品条目字符串格式
		
		Pattern pattern = Pattern.compile(target);
		ArrayList<String> s = new ArrayList<String>();
		Matcher matcher = pattern.matcher(str);
		
		while (matcher.find()){
			String item = matcher.group();
			s.add(item);
			//读取不同计量单位商品
			if(item.contains(LINE)){
				String[] itemArray = item.split(LINE);
				item = itemArray[0];
				int num = Integer.parseInt(itemArray[1]);
				this.caculate(item, num);//存储到HashMap中
			}else{
				this.caculate(item,1);//存储到HashMap中
			}
		}
	}

	/*
	 * Description: 将单条Item中包含的信息使用HashMap存储 并计算出总数 Input: String item, int payNum
	 * 
	 */
	private void caculate(String item, int payNum) {
		int mCount = 0;

		if (count.get(item) != null) {
			mCount = count.get(item);
			mCount += payNum;
			count.put(item, mCount);
		} else {
			count.put(item, payNum);
		}

	}
	/*
	 * Description: 读取促销信息，用正则表达式识别出促销商品， Input: String类型的JSON字符串 str
	 * Output:String
	 * 
	 */
	public  String identify(String str){
		String target = "\\w{4}\\d{6}\\-?\\d?";
		
		Pattern pattern = Pattern.compile(target);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> s = new ArrayList<String>();
		
		while (matcher.find()){
			String item = matcher.group();
			s.add(item);
			if(item.contains(LINE)){
				String[] itemArray = item.split(LINE);
				s.add(itemArray[0]);
			}
		}
		return s.toString();//返回促销商品的条形码
	}
	
	/*
	 * Description: 计算商品总价并根据有无优惠打印， Input: String s
	 * 
	 */
	public void compute(String s){
		int quantity = 0;//批发商品的数量		
		Iterator iter = count.entrySet().iterator();
		
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = (String)entry.getKey();
			int num = (int)entry.getValue();
			if(s.contains(key))
				quantity = quantity + num;
		}
		//当数量大于等于10时打95折
		if(quantity >= 10)
			printSale(s);
		else
			print();
		
	}
	/*
	 * Description: 没有优惠时打印小票
	 * 
	 */
	public void print(){
		double subtotal;//一种商品价格
		
		System.out.print("*<没钱赚商店>购物清单*");
		Iterator iter = count.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = (String) entry.getKey();
			int num = (int)entry.getValue();
			Commodity c = coml.getCommodity(key.toString());
			 subtotal = c.getPrice() * num;
			totalPrice += subtotal;
			System.out.println("名称： " + c.getName() + "，" + "数量 " + num + c.getUnit()+ "，" +
					"单价： " + c.getPrice() + "，" +"小计： " + subtotal + "（元）");
		}
		System.out.println("--------------------------------------------------");
		System.out.println("总计： " + totalPrice +"（元）");
		System.out.println("--------------------------------------------------");
	}
	/*
	 * Description: 有优惠时打印小票
	 * 
	 */
	public void printSale(String s){
		DecimalFormat df = new DecimalFormat("0.00");
		double subtotal;
		double discount = 0.95;//批发价折扣
		double savePrice = 0;//节省钱数
		
		System.out.print("*<没钱赚商店>购物清单*");
		Iterator iter = count.entrySet().iterator();
		while (iter.hasNext()) {
			double td;//单种商品节省钱数
			double subtd;//单种商品总价		
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = (String)entry.getKey();
			int num =(int) entry.getValue();
			Commodity c = coml.getCommodity(key);
			if(s.contains(key)){
				subtotal = c.getPrice() * num ;
				subtd = subtotal * discount;
				td = subtotal * (1-discount);
				totalPrice += subtd;
				savePrice += td;
				System.out.println("名称： " + c.getName() +"，" + "数量 " + num + c.getUnit()+"，" +
						"单价： " + c.getPrice() +"，" + "小计： " + df.format(subtd) + "（元）" +"，" + "优惠： " + df.format(td) +"（元）");
			}else{
				subtotal = c.getPrice() * num ;
				totalPrice += subtotal;
				System.out.println("名称： " + c.getName() +"，" + "数量： " + num + c.getUnit()+"，" +
						"单价： " + c.getPrice() +"，" + "小计： " + df.format(subtotal) + "（元）");
			}
		}
		System.out.println("--------------------------------------------------");
		System.out.println("总计： " + df.format(totalPrice) +"（元）" + "，" +"节省： " + df.format(savePrice) +"（元）");
		System.out.println("--------------------------------------------------");
	}
}
