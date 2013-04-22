import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class MainWindow 
{
	///
	//main components
	Display display= new Display();
	Shell shell= new Shell(display);
	//menubar
	private Menu menuBar, menuFile, menuEdit, menuAbout;
	private MenuItem menuFileHeader, menuEditHeader, menuAboutHeader;
	private MenuItem menuFileSave, menuFileOpen, menuFileNew;
	//RMB menu
	private Menu rmbMenuMain;
	private MenuItem menuOpenTab, menuCloseTab, menuCloseAllTabs;
	//text field
	private Text textField;
	//tabfolder
	final CTabFolder tabfolder;
	///
	
	//constant sizes
	private static int menuwidth = 40;
	private static int menuheigth = 40; 
	private static int menuitemwidth = 80;
	
	//tabnumber
	private static int documentnum = 1;
	
	public MainWindow() 
	{	
		shell.setMinimumSize(new Point(600,400));
		shell.setText("Notepad");
		
		//tabs
		tabfolder = new CTabFolder(shell, SWT.TOP|SWT.BORDER);
		CTabItem tabzero = new CTabItem(tabfolder, SWT.CLOSE);
		tabzero.setText("new "+ documentnum);
		
		textField = new Text(tabfolder, SWT.WRAP| SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE );
		tabzero.setControl(textField);
		
		tabfolder.setSize(shell.getSize().x, shell.getSize().y);
		tabfolder.addMouseListener(new MouseListener() 
		{
			
			@Override
			public void mouseUp(MouseEvent e) 
			{
				if (e.button == 3)
				{
					rmbMenuMain.setLocation(shell.getLocation().x+e.x, shell.getLocation().y+menuheigth+e.y);
					rmbMenuMain.setVisible(true);
				}
			}
			
			@Override
			public void mouseDown(MouseEvent e) 
			{
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) 
			{
				
			}
		});
		
		//menuBar
		menuBar = new Menu(shell, SWT.BAR);
		
		menuFileHeader = new MenuItem(menuBar,SWT.CASCADE);
		menuFileHeader.setText("File");
		menuFile = new Menu(shell,SWT.DROP_DOWN);
		menuFileHeader.setMenu(menuFile);
		
		menuFileOpen = new MenuItem(menuFile, SWT.PUSH);
		menuFileOpen.setText("&Open\tCtrl+O");
		menuFileOpen.setAccelerator(SWT.CTRL+'O');
		menuFileOpen.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				FileDialog fldialOpen = new FileDialog(shell, SWT.OPEN|SWT.MULTI);
				String[] names;
				if (fldialOpen.open() != null)
				{
					names = fldialOpen.getFileNames();
					for (int i = 0, n = names.length; i < n; i++) 
					{
						File file = new File(fldialOpen.getFilterPath()+"\\"+names[i]);
						OpenNewTab(file);
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		menuFileNew = new MenuItem(menuFile, SWT.PUSH);
		menuFileNew.setText("&New\tCtrl+N");
		menuFileNew.setAccelerator(SWT.CTRL+'N');
		menuFileNew.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				incDocumentNum();
				OpenNewTab(getDocumentNum());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				
			}
		});
		
		menuFileSave = new MenuItem(menuFile, SWT.PUSH);
		menuFileSave.setText("&Save");
		
		menuEditHeader = new MenuItem(menuBar, SWT.CASCADE);
		menuEditHeader.setText("Edit");
		menuEdit = new Menu(shell,SWT.DROP_DOWN);
		menuEditHeader.setMenu(menuEdit);
		
		menuAboutHeader = new MenuItem(menuBar, SWT.CASCADE);
		menuAboutHeader.setText("About");
		menuAbout = new Menu(shell,SWT.DROP_DOWN);
		menuAboutHeader.setMenu(menuAbout);
		
		//RMBMenu
		rmbMenuMain = new Menu(shell, SWT.POP_UP);
		menuOpenTab = new MenuItem(rmbMenuMain, SWT.DROP_DOWN);
		menuOpenTab.setText("Open new tab\tCtrl+N");
		menuCloseTab = new MenuItem(rmbMenuMain, SWT.DROP_DOWN);
		menuCloseTab.setText("Close tab\tCtrl+X");
		menuCloseAllTabs = new MenuItem(rmbMenuMain, SWT.DROP_DOWN);
		menuCloseAllTabs.setText("Close all tabs");
		
		tabfolder.setMenu(rmbMenuMain);
		
		menuOpenTab.setAccelerator(SWT.CTRL+'N');
		menuOpenTab.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				incDocumentNum();
				OpenNewTab(getDocumentNum());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				
			}
		});
		
		
		
		menuCloseTab.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				CloseTab(tabfolder.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menuCloseAllTabs.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				while (tabfolder.getChildren().length != 1)
				{
					CloseTab(tabfolder.getSelection());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.setMenuBar(menuBar);
		shell.pack();
		shell.setLayout(new FillLayout());
		shell.setBounds(100, 100, 400, 400);		
		shell.setVisible(true);
	}

	public int getDocumentNum()
	{
		return this.documentnum;
	}
	public void incDocumentNum()
	{
		this.documentnum++;
	}
	public void decDocumentNum()
	{
		this.documentnum--;
	}
	public void Show()
	{
		shell.open();
		while (!shell.isDisposed())
			if(display.readAndDispatch())
				display.sleep();
	}
	
	private void OpenNewTab(int num)
	{
		CTabItem newtab = new CTabItem(tabfolder, SWT.CLOSE);
		newtab.setText("new" + num);
		Text newtext = new Text(tabfolder, SWT.WRAP| SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE );
		newtab.setControl(newtext);
	}
	
	private void OpenNewTab(File input)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(input));
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null)
			{
				sb.append(line+"\n");
				line = reader.readLine();
			}
			Text newtext = new Text(tabfolder, SWT.WRAP| SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE);
			newtext.setText(sb.toString());
			CTabItem newtab = new CTabItem(tabfolder, SWT.CLOSE | SWT.FOCUSED);
			newtab.setText(input.getName());
			newtab.setControl(newtext);
			tabfolder.setSelection(newtab);
			reader.close();
		}
		catch(Exception e)
		{
			MessageBox msg = new MessageBox(shell);
			msg.setMessage(e.getMessage());
			msg.setText("Error");
			msg.open();
		}
	}
	
	private void CloseTab(CTabItem tab)
	{
		tab.dispose();
		decDocumentNum();
	}
}
