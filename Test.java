package wholesale;

public class Test {
	public static void main(String[] args){
		//�����ʽ�ַ���
		String testJson1 = "['ITEM000001' + 'ITEM000001','ITEM000001','ITEM000001','ITEM000001','ITEM000000','ITEM000000','ITEM000003-5','ITEM000002-2','ITEM000000','ITEM000000','ITEM000000','ITEM000000']";
		String testJson2 = "['ITEM000001','ITEM000001','ITEM000003-3','ITEM000002','ITEM000001','ITEM000001']";
		//������Ϣ
		String testDiscountJson = "[{type:'BUY_TEN_DISCOUNT_0.95',barcodes:['ITEM000000','ITEM000001']}]";
	
		CashRegister cr = new CashRegister();
		//ʶ�������Ʒ 
		String discountC = cr.identify(testDiscountJson);
		//��ȡ��������
		cr.getInput(testJson1);
		//�����ܼ۲���ӡСƱ
		cr.compute(discountC);
	}
}
