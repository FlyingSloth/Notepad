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
	private MenuItem menuFileSave, menuFileOpen;
	//text field
	private Text textField;
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
		final TabFolder tabfolder = new TabFolder(shell, SWT.TOP|SWT.BORDER);
		TabItem tabzero = new TabItem(tabfolder, SWT.NULL);
		tabzero.setText("new "+ documentnum);
		
		textField = new Text(tabfolder, SWT.WRAP| SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.RESIZE );
		tabzero.setControl(textField);
		
		tabfolder.setSize(shell.getSize().x, shell.getSize().y);
		
		//menuBar
		menuBar = new Menu(shell, SWT.BAR);
		
		menuFileHeader = new MenuItem(menuBar,SWT.CASCADE);
		menuFileHeader.setText("File");
		menuFile = new Menu(shell,SWT.DROP_DOWN);
		menuFileHeader.setMenu(menuFile);
		
		menuFileOpen = new MenuItem(menuFile, SWT.PUSH);
		menuFileOpen.setText("&Open");
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
	public void setDocumentNum()
	{
		this.documentnum++;
	}
	public void Show()
	{
		shell.open();
		while (!shell.isDisposed())
			if(display.readAndDispatch())
				display.sleep();
	}
}
