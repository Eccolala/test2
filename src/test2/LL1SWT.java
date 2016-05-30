package test2;

import java.io.BufferedReader;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
public class LL1SWT {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Table table;
	private MessageBox messageBox;
	private File sourceFile = null;
	private LL1 mOpertion;
	private String Language = new String();
	private String analysisString = new String();
	private String result = new String();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LL1SWT window = new LL1SWT();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(570, 565);
		shell.setText("LL(1)\u5206\u6790\u5668");

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					newTextTable();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		menuItem.setText("\u65B0\u5EFA");
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openFile();
			}
		});
		menuItem_1.setText("\u6253\u5F00");
		MenuItem menuItem_3 = new MenuItem(menu, SWT.NONE);
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					analysis();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		menuItem_3.setText("\u5206\u6790");
		MenuItem menuItem_2 = new MenuItem(menu, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveFile();
			}
		});
		menuItem_2.setText("\u4FDD\u5B58");
		MenuItem menuItem_4 = new MenuItem(menu, SWT.NONE);
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		menuItem_4.setText("\u9000\u51FA");
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 10, 367, 112);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		text = new Text(scrolledComposite, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setContent(text);
		scrolledComposite
				.setMinSize(text.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(shell,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(383, 10, 161, 112);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		text_1 = new Text(scrolledComposite_1, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite_1.setContent(text_1);
		scrolledComposite_1.setMinSize(text_1.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		Language = "文法：\n"
				+ "（1）E->TG\n（2）G->+TG|-TG\n（3）G->ε\n（4）T->FS\n（5）S->*FS|/FS\n"
				+ "（6）S->ε\n（7）F->(E)\n（8）F->i";
		text_1.setText(Language);
		text_1.setEditable(false);
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 128, 534, 368);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(66);
		tblclmnNewColumn.setText("\u6B65\u9AA4");
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(114);
		tableColumn.setText("\u5206\u6790\u6808");
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(115);
		tblclmnNewColumn_1.setText("\u5269\u4F59\u8F93\u5165\u4E32");
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(115);
		tblclmnNewColumn_2.setText("\u6240\u7528\u4EA7\u751F\u5F0F");
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(115);
		tblclmnNewColumn_3.setText("\u52A8\u4F5C");

	}

	public void openFile() {
		FileDialog openFileDialog = new FileDialog(shell, SWT.OPEN);
		openFileDialog.setText("选择需要分析的文件");
		openFileDialog.setFilterExtensions(new String[] { "*.txt" });
		openFileDialog.setFilterNames(new String[] { "txt文本文件(*.txt)" });
		openFileDialog.setFilterPath("D:\\");
		String selectedOpenFile = openFileDialog.open();
		this.result = "";
		if (selectedOpenFile != null) {
			sourceFile = new File(selectedOpenFile);
			try {
				FileReader fileReader = new FileReader(sourceFile);
				BufferedReader reader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String lineString = null;
				while ((lineString = reader.readLine()) != null) {
					stringBuffer.append(lineString);
					stringBuffer.append("\n");
				}
				text.setText(stringBuffer.toString());
				shell.setText("LL(1)分析 - " + openFileDialog.getFileName());
				fileReader.close();
				table.removeAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("文件未载入！请重新打开需要分析的文件！");
			messageBox.setText("提示");
			messageBox.open();
		}
	}

	public void analysis() {
		mOpertion = new LL1();
		this.table.removeAll();
		this.analysisString = text.getText();
		boolean flag = false;
		for (int i = 0; i < analysisString.length(); i++) {
			if (mOpertion.checkChar(analysisString.charAt(0))) {
				flag = true;
			}
		}
		if (this.analysisString.equals("")) {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("请输入需要分析的字符串或打开需要分析的文件！");
			messageBox.setText("提示");
			messageBox.open();
		} else if (flag) {
			if (analysisString.charAt(analysisString.length() - 1) != '#') {
				analysisString += "#";
			}
			this.result = "";
			mOpertion.analysis(this.analysisString);
			for (int i = 0; i < mOpertion.getDataRow(); i++) {
				TableItem item = new TableItem(table, SWT.NULL);
				for (int j = 0; j < 5; j++) {
					item.setText(j, mOpertion.getDataStrings()[i][j]);
				}
			}
			this.result = mOpertion.getResult();
		} else {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("输入的字符串有错误！");
			messageBox.setText("提示");
			messageBox.open();
		}
	}

	public void newTextTable() {
		this.result = "";
		this.text.setText("");
		this.table.removeAll();
	}

	public void saveFile() {
		if (result != "") {
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter("Result.txt");
				fileWriter.write(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Runtime runtime = Runtime.getRuntime();
					messageBox = new MessageBox(shell);
					messageBox.setMessage("保存成功！");
					messageBox.setText("提示");
					fileWriter.flush();
					fileWriter.close();
					messageBox.open();
					sourceFile = new File("Result.txt");
					runtime.exec("rundll32 url.dll FileProtocolHandler "
							+ sourceFile.getAbsolutePath());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setMessage("未分析，保存失败！");
			messageBox.setText("提示");
			messageBox.open();
		}
	}
}