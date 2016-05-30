package test2;


public class LL1 {

	private char[] strChar;// ���봮
	private char[] stack = new char[1000];// ����ջ
	private int top = 0;// ����ջָ��
	private int strPointer = 0;// ���봮ָ��
	private int length;// ���봮����
	private String[][] analysisTable = new String[6][9];// ������
	private final char[] constChar = { 'i', '(', '+', '-', '*', '/', ')', '#' };// ���ַ�
	private String[][] dataStrings = new String[1000][5];// ������ݱ�
	private String tempString = new String();// ��ʱ�ַ���
	private String result = new String();// �ó��Ľ���ַ���
	private int dataRow = 0;// ���ݱ�����

	/*
	 * ���� i ( + - * / ) # E E->TG E->TG synch synch G G->+TG G->-TG G->�� G->�� T
	 * T->FS T->FS synch synch synch synch S S->�� S->�� S->*FS S->/FS S->�� S->�� F
	 * F->i F->(E) synch synch synch synch synch synch
	 */

	public LL1() {// ��ʼ����������
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				analysisTable[i][j] = new String();
				analysisTable[i][j] = " ";
			}
		}
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				dataStrings[i][j] = new String();
				dataStrings[i][j] = "";
			}
		}
		analysisTable[0][1] = "i";
		analysisTable[0][2] = "(";
		analysisTable[0][3] = "+";
		analysisTable[0][4] = "-";
		analysisTable[0][5] = "*";
		analysisTable[0][6] = "/";
		analysisTable[0][7] = ")";
		analysisTable[0][8] = "#";
		analysisTable[1][0] = "E";
		analysisTable[2][0] = "G";
		analysisTable[3][0] = "T";
		analysisTable[4][0] = "S";
		analysisTable[5][0] = "F";
		analysisTable[1][1] = "TG";
		analysisTable[1][2] = "TG";
		analysisTable[2][3] = "+TG";
		analysisTable[2][4] = "-TG";
		analysisTable[2][7] = "��";
		analysisTable[2][8] = "��";
		analysisTable[3][1] = "FS";
		analysisTable[3][2] = "FS";
		analysisTable[4][3] = "��";
		analysisTable[4][4] = "��";
		analysisTable[4][5] = "*FS";
		analysisTable[4][6] = "/FS";
		analysisTable[4][7] = "��";
		analysisTable[4][8] = "��";
		analysisTable[5][1] = "i";
		analysisTable[5][2] = "(E)";
		stack[top] = '#';
		stack[++top] = 'E';
	}

	public boolean checkChar(char needCheck) {// ����ַ��Ƿ����ַ�����
		for (int i = 0; i < constChar.length; i++) {
			if (needCheck == constChar[i]) {
				return true;
			}
		}
		return false;
	}

	public void printStack() {// �������ջ
		this.tempString = new String();
		for (int i = 0; i < top + 1; i++) {
			this.tempString += stack[i];
			System.out.print(stack[i]);
			result += stack[i];
		}
		System.out.print("\t\t");
		result += "\t\t";
	}

	public void printStr() {// ���ʣ�����봮
		this.tempString = new String();
		for (int i = 0; i < strPointer; i++) {
			System.out.print(" ");
			result += " ";
		}
		for (int i = strPointer; i < length; i++) {
			this.tempString += strChar[i];
			System.out.print(strChar[i]);
			result += strChar[i];
		}
		System.out.print("\t\t\t");
		result += "\t\t\t";
	}

	public void analysis(String line) {// LL(1)����
		strChar = line.toCharArray();
		length = strChar.length;
		String analysisString = new String();
		char ch = strChar[strPointer];
		char topX;
		int finish = 0, flag = 0;
		int row = 0, column = 0;
		System.out.print("����\t\t����ջ \t\tʣ���ַ� \t\t���ò���ʽ \t\t����\n");
		result += "����\t\t����ջ \t\tʣ���ַ� \t\t���ò���ʽ \t\t����\r\n";
		dataStrings[0][0] = dataRow++ + "";
		System.out.print(dataRow + "\t\t");
		result += dataRow + "\t\t";
		printStack();
		dataStrings[0][1] = tempString;
		printStr();
		dataStrings[0][2] = tempString;
		dataStrings[0][4] = "��ʼ��";
		result += "        \t\t��ʼ��\r\n";
		System.out.print("\n");
		do {
			topX = stack[top--];
			dataStrings[dataRow][0] = dataRow + "";
			System.out.print(dataRow + "\t\t");
			result += dataRow + "\t\t";
			for (int i = 0; i < 9; i++) {// �ж��Ƿ�Ϊ���ս��
				if (checkChar(topX)) {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {// ������ս��
				if (topX == '#') {
					finish = 1;
					System.out.println("over");
					result += "over\r\n";
					break;
				}
				if (topX == ch) {
					printStack();
					dataStrings[dataRow][1] = tempString;
					ch = strChar[++strPointer];
					printStr();
					dataStrings[dataRow][2] = tempString;
					System.out.print("ƥ��\n");
					dataStrings[dataRow][4] = "GETNEXT(I)";
					result += "        \t\tGETNEXT(I)\r\n";
					flag = 0;
					this.dataRow++;
				} else {
					printStack();
					dataStrings[dataRow][1] = tempString;
					printStr();
					dataStrings[dataRow][2] = tempString;
					System.out.print("����\n");
					result += "        \t\t����\r\n";
					this.dataRow++;
					break;
				}
			} else {
				for (int i = 0; i < 6; i++) {
					if (topX == analysisTable[i][0].charAt(0)) {
						row = i;
						break;
					}
				}
				for (int i = 0; i < 9; i++) {
					if (ch == analysisTable[0][i].charAt(0)) {
						column = i;
						break;
					}
				}
				analysisString = analysisTable[row][column];
				if (!analysisString.equals(" ")) {
					if (!analysisString.equals("��")) {
						for (int i = analysisString.length() - 1; i >= 0; i--) {
							stack[++top] = analysisString.charAt(i);
						}
					}
					printStack();
					dataStrings[dataRow][1] = tempString;
					printStr();
					dataStrings[dataRow][2] = tempString;
					System.out.print(analysisTable[row][0] + "->"
							+ analysisString + "\n");
					dataStrings[dataRow][3] = analysisTable[row][0] + "->"
							+ analysisString;
					result += analysisTable[row][0] + "->" + analysisString;
					if (!analysisString.equals("��")) {
						dataStrings[dataRow][4] = "POP,PUSH(" + analysisString
								+ ")";
						result += "\t\tPOP,PUSH(" + analysisString + ")\r\n";
					} else {
						dataStrings[dataRow][4] = "POP";
						result += "\t\tPOP\r\n";
					}
					if (stack[top] == ' ') {
						top--;
					}
					this.dataRow++;
				} else {
					printStack();
					dataStrings[dataRow][1] = tempString;
					printStr();
					dataStrings[dataRow][2] = tempString;
					System.out.print("����\n");
					result += "        \t\t����\r\n";
					this.dataRow++;
					break;
				}
			}
		} while (finish == 0);
	}

	public String[][] getDataStrings() {
		return dataStrings;
	}

	public void setDataStrings(String[][] dataStrings) {
		this.dataStrings = dataStrings;
	}

	public int getDataRow() {
		return dataRow;
	}

	public void setDataRow(int dataRow) {
		this.dataRow = dataRow;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}