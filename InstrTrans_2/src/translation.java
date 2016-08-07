import java.util.ArrayList;
import java.util.Map;

/*
 * �ر�ע����תָ�
 * ÿ��ָ���¼��������������ʼ��ţ���תָ���ȼ�¼�¶�Ӧ��ԭָ��ţ����ȫ������֮���ٸ���ԭָ��������תλ��
 * 
 *��������ʱ������ʹ��aload_0��this�������������ջ
 * 
 * */
public class translation {
	//���ָ�����Ϣ
	ArrayList<String> instruction; 
	int insNumber = 0; //dexָ����
	String className = "";
	String methodName = "";
	//�Ĵ��� -> ջ���
	Map <String,Integer> register;
	//�Ĵ�������
	Map <String,String> registerType;
	
	//java�ֽ���ָ����
	int classCodeNumber = 0;
	
	//-1?
	String []i6 = new String[]{"0x0","0x1","0x2","0x3","0x4","0x5"};
	
	//��ȡ��Ϣ
	public void getInformation(ArrayList<String> i, int in,String cn, String mn, Map <String,Integer> r, Map <String,String> rt){
		instruction = i;
		insNumber = in;
		className = cn;
		methodName = mn;
		register = r;
		registerType = rt;
	}
	
	//����
	public void translateIns(){
		switch(instruction.get(0)){
			case "nop":
				Main.dexToclass.put(insNumber, classCodeNumber);
				System.out.println(classCodeNumber+": "+"nop");
				classCodeNumber++;
				break;
			//const Ӧ��ֻ��int��            /16 ��չ��smail�ļ��ﴦ��?
			case "const/4":
			case "const/16":
			case "const/high16":
			case "const":
				Main.dexToclass.put(insNumber, classCodeNumber);
				char []tempchar = instruction.get(2).toCharArray();
				int tempint = 0;
				for(int i=2;i<tempchar.length;i++){
					tempint = tempint*10 + (tempchar[i] - '0');
				}
				if(tempint < 6){
					System.out.println(classCodeNumber+": "+"iconst_"+tempint);
					classCodeNumber++;
				}
				else{
					System.out.println(classCodeNumber+": "+"ldc            "+instruction.get(2));
					classCodeNumber++;
				}
				if(register.get(instruction.get(1)) < 4){
					System.out.println(classCodeNumber+": "+"istore_"+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				else{
					System.out.println(classCodeNumber+": "+"istore         "+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				break;
				
			case "const-wide/4":
			case "const-wide/16":
			case "const-wide/32":
			case "const-wide/high16":
			case "const-wide":
				System.out.println(classCodeNumber+": "+"ldc2_w         "+instruction.get(2));
				classCodeNumber++;
				if(register.get(instruction.get(1)) < 4){
					System.out.println(classCodeNumber+": "+"istore_"+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				else{
					System.out.println(classCodeNumber+": "+"istore         "+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				break;
			
			case "const-string":
				System.out.println(classCodeNumber+": "+"ldc            "+instruction.get(2));
				classCodeNumber++;
				if(register.get(instruction.get(1)) < 4){
					System.out.println(classCodeNumber+": "+"istore_"+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				else{
					System.out.println(classCodeNumber+": "+"istore         "+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				break;
			//������������
			case "const-string/jumbo":
				System.out.println(classCodeNumber+": "+"ldc_w          "+instruction.get(2));
				classCodeNumber++;
				if(register.get(instruction.get(1)) < 4){
					System.out.println(classCodeNumber+": "+"istore_"+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				else{
					System.out.println(classCodeNumber+": "+"istore         "+register.get(instruction.get(1)));
					classCodeNumber++;
				}
				break;
				
			case "const-class":
				break;
			case "const-class/jumbo":
				break;
			
			default:
				break;
		}
	}
	
}
