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
 *Description: �����������ʹ�ӡСƱ��������ģ��
 ********************************/
public class CashRegister {
	public final String LINE = "-";//�ض���λ��Ʒ�ָ���
	public double totalPrice;//��Ʒ�ܼ�
	
	CommodityLibrary coml = new CommodityLibrary();//��ʼ����Ʒ��

	Map<String,Integer> count = new HashMap<String,Integer>();//�洢��Ʒ�Ͷ�Ӧ����
	
	/*
	 * Description: ��������ʽȡ�����������е�����Ʒ�� Input: String���͵�JSON�ַ��� str
	 * 
	 */	
	public void getInput(String str){
		String target = "\\w{4}\\d{6}\\-?\\d?";//ƥ����Ʒ��Ŀ�ַ�����ʽ
		
		Pattern pattern = Pattern.compile(target);
		ArrayList<String> s = new ArrayList<String>();
		Matcher matcher = pattern.matcher(str);
		
		while (matcher.find()){
			String item = matcher.group();
			s.add(item);
			//��ȡ��ͬ������λ��Ʒ
			if(item.contains(LINE)){
				String[] itemArray = item.split(LINE);
				item = itemArray[0];
				int num = Integer.parseInt(itemArray[1]);
				this.caculate(item, num);//�洢��HashMap��
			}else{
				this.caculate(item,1);//�洢��HashMap��
			}
		}
	}

	/*
	 * Description: ������Item�а�������Ϣʹ��HashMap�洢 ����������� Input: String item, int payNum
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
	 * Description: ��ȡ������Ϣ����������ʽʶ���������Ʒ�� Input: String���͵�JSON�ַ��� str
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
		return s.toString();//���ش�����Ʒ��������
	}
	
	/*
	 * Description: ������Ʒ�ܼ۲����������Żݴ�ӡ�� Input: String s
	 * 
	 */
	public void compute(String s){
		int quantity = 0;//������Ʒ������		
		Iterator iter = count.entrySet().iterator();
		
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = (String)entry.getKey();
			int num = (int)entry.getValue();
			if(s.contains(key))
				quantity = quantity + num;
		}
		//���������ڵ���10ʱ��95��
		if(quantity >= 10)
			printSale(s);
		else
			print();
		
	}
	/*
	 * Description: û���Ż�ʱ��ӡСƱ
	 * 
	 */
	public void print(){
		double subtotal;//һ����Ʒ�۸�
		
		System.out.print("*<ûǮ׬�̵�>�����嵥*");
		Iterator iter = count.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			String key = (String) entry.getKey();
			int num = (int)entry.getValue();
			Commodity c = coml.getCommodity(key.toString());
			 subtotal = c.getPrice() * num;
			totalPrice += subtotal;
			System.out.println("���ƣ� " + c.getName() + "��" + "���� " + num + c.getUnit()+ "��" +
					"���ۣ� " + c.getPrice() + "��" +"С�ƣ� " + subtotal + "��Ԫ��");
		}
		System.out.println("--------------------------------------------------");
		System.out.println("�ܼƣ� " + totalPrice +"��Ԫ��");
		System.out.println("--------------------------------------------------");
	}
	/*
	 * Description: ���Ż�ʱ��ӡСƱ
	 * 
	 */
	public void printSale(String s){
		DecimalFormat df = new DecimalFormat("0.00");
		double subtotal;
		double discount = 0.95;//�������ۿ�
		double savePrice = 0;//��ʡǮ��
		
		System.out.print("*<ûǮ׬�̵�>�����嵥*");
		Iterator iter = count.entrySet().iterator();
		while (iter.hasNext()) {
			double td;//������Ʒ��ʡǮ��
			double subtd;//������Ʒ�ܼ�		
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
				System.out.println("���ƣ� " + c.getName() +"��" + "���� " + num + c.getUnit()+"��" +
						"���ۣ� " + c.getPrice() +"��" + "С�ƣ� " + df.format(subtd) + "��Ԫ��" +"��" + "�Żݣ� " + df.format(td) +"��Ԫ��");
			}else{
				subtotal = c.getPrice() * num ;
				totalPrice += subtotal;
				System.out.println("���ƣ� " + c.getName() +"��" + "������ " + num + c.getUnit()+"��" +
						"���ۣ� " + c.getPrice() +"��" + "С�ƣ� " + df.format(subtotal) + "��Ԫ��");
			}
		}
		System.out.println("--------------------------------------------------");
		System.out.println("�ܼƣ� " + df.format(totalPrice) +"��Ԫ��" + "��" +"��ʡ�� " + df.format(savePrice) +"��Ԫ��");
		System.out.println("--------------------------------------------------");
	}
}
