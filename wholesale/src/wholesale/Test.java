package wholesale;

public class Test {
	public static void main(String[] args){
		//输入格式字符串
		String testJson1 = "['ITEM000001' + 'ITEM000001','ITEM000001','ITEM000001','ITEM000001','ITEM000000','ITEM000000','ITEM000003-5','ITEM000002-2','ITEM000000','ITEM000000','ITEM000000','ITEM000000']";
		String testJson2 = "['ITEM000001','ITEM000001','ITEM000003-3','ITEM000002','ITEM000001','ITEM000001']";
		//促销信息
		String testDiscountJson = "[{type:'BUY_TEN_DISCOUNT_0.95',barcodes:['ITEM000000','ITEM000001']}]";
	
		CashRegister cr = new CashRegister();
		//识别促销商品 
		String discountC = cr.identify(testDiscountJson);
		//读取输入数据
		cr.getInput(testJson1);
		//计算总价并打印小票
		cr.compute(discountC);
	}
}
