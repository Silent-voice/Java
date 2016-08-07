package op;

import op.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * �ڷ���ָ��ʱ��
 * 1.��������ָ���ȷ��ĳ���Ĵ�������ʱҪ��ʱ�޸�registerType
 * */


public class Main {
	
	static int LineNumber = 0;   //���
	static int constNumber = 1;
	//����С�ַ�����Ӧ�ı��
	//static Map <String,Integer> constants = new HashMap<String,Integer>();
	//�����ַ�����Ӧ�ı��
	static Map <String,Integer> constants = new HashMap<String,Integer>();
	static Map <String,String> constantsType = new HashMap<String,String>();
	
	static String className = "";
	static String methodName = "";
	
	//��¼��תָ���ź�����ת�ı�ǩ
	static Map <Integer,String> jumpAndtable = new HashMap<Integer,String>();
	//��¼��ǩ��������ָ��ı��
	static Map <String,Integer> tableAndnumber = new HashMap<String,Integer>();
	//��¼��תָ����������ת��Ŀ��ָ��ı��
	static Map <Integer,Integer> jumpToaim = new HashMap<Integer,Integer>();
	//��¼ÿ��dexָ��ı�źͷ����classָ����Ӧ�ı��
	static Map <Integer,Integer> dexToclass = new HashMap<Integer,Integer>();
	
	//linenember -> dex code number
	static Map <Integer,Integer> lineTonumber = new HashMap<Integer,Integer>();
	
	
	//����method����     
	static int registerNumber = 0;
	static Map <String,Integer> register = new HashMap<String,Integer>();
	//Ĭ��Ϊint
	static Map <String,String> registerType = new HashMap<String,String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String file = "G:\\ʵ��������\\baksmail\\out\\com\\example\\test\\MainActivity.smali";
		ArrayList<String> instruction;
		
		String regex1 = "[p,v]\\d+";
		String regex2 = ";->";
		
		ReadFile rf = new ReadFile(file);
		translation tr = new translation();
		
		//ÿ�������Ŀ�ʼ���
		int method_begin_number = 0;
		
		int i = 0;
		while(rf.readLine()){
			
			if(rf.ifNull()){
				continue;
			}
			
			instruction = rf.getInstruction();
			if(rf.ifNewMethod()){
				registerNumber = 0;
				register.clear();
				registerType.clear();
				registerNumber = 1;
				register.put("p0",0);
				registerType.put("p0", "this");
				
				method_begin_number = LineNumber;
				methodName = instruction.get(instruction.size()-1);
			}
			else if(instruction.get(0).equals(".class")){
				className = instruction.get(instruction.size()-1);
			}
			else if(instruction.get(0).equals(".local")){
				String []temp1 = new String[2];
				temp1 = instruction.get(2).split(":");
				registerType.put(instruction.get(1), temp1[1]);
			}
			else if(instruction.get(0).equals(".param")){
				if(!register.containsKey(instruction.get(1))){
					register.put(instruction.get(1), registerNumber);
					registerType.put(instruction.get(1), instruction.get(4));
					registerNumber++;
				}
			}
			//�����ǩ:
			else if(instruction.get(0).startsWith(":")){
				tableAndnumber.put(instruction.get(0), LineNumber+1);
			}
			else if(instruction.get(0).equals(".line")){
				int ln = Integer.parseInt(instruction.get(1));
				lineTonumber.put(ln, LineNumber+1);
			}
			else if(rf.ifAnInstruction(instruction.get(0))){
				i=1;
				//����ջ�ռ�
				if(i<instruction.size()){
					while(i<instruction.size() && instruction.get(i).matches(regex1)){
						if(register.containsKey(instruction.get(i))){
							i++;
						}
						else{
							register.put(instruction.get(i), registerNumber);
							registerType.put(instruction.get(i), "I");
							//System.out.println(registerNumber+": "+instruction.get(i));
							registerNumber++;
							i++;
						}
					}
					//��¼��תָ����Ϣ
					if(i<instruction.size() && instruction.get(i).startsWith(":")){
						jumpAndtable.put(LineNumber, instruction.get(i));
					}
				}
			}
			//��������ʱ��ͳһ����ָ��
			else if(instruction.get(0).equals(".end")){
				while(method_begin_number < LineNumber){
					instruction = rf.getInstruction(method_begin_number);
					if(rf.ifAnInstruction(instruction.get(0))){
						//����ָ���ָ����Ҳ����ȥ
						tr.getInformation(instruction, LineNumber, className, methodName, register, registerType);
						tr.translateIns();
					}
					method_begin_number++;
				}
			}
			
			LineNumber++;
		}
	}
}
