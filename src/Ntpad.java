import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
    ...................
    Created by EDSON NYONI
    ...................
 */

public class Ntpad{
    public static void main(String[] args){
        new MyFrame("Untitled");
    }
}

class MyFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 700;             // 窗口默认宽度
    private static final int DEFAULT_HEIGHT = 450;            // 窗口默认高度
    private static final int DEFAULT_FONTSIZE = 20;           // 默认字体大小
    private JTextArea tv_area = null;                         // 多行文本框
    private JScrollPane s_pane = null;                        // 带滚动条的面板
    private JMenuBar mb = null;                               // 菜单栏
    private JToolBar toolBar1 = null;                         // 顶部工具栏
    private JToolBar toolBar2 = null;                         // 底部status栏
    private JMenu m_file = null;                              // 文件菜单
    private JMenu m_edit = null;                              // 编辑菜单
    private JMenu m_format = null;                            // 格式菜单
    private JMenu m_help = null;                              // 帮助菜单
    private JPopupMenu popupMenu = null;                      // 右键弹出式菜单
    private Icon toolBar_Save = null;                         // 工具栏保存图标
    private Icon toolBar_Cut = null;                          // 工具栏剪切图标
    private Icon toolBar_Copy = null;                         // 工具栏复制图标
    private Icon toolBar_Paste = null;                        // 工具栏粘贴图标
    private Icon statusBar_status = null;                     // 状态栏图标
    private Icon toolBar_more_font = null;                    // 工具栏字体图标
    private Icon m_help_icon = null;                          // 关于菜单项图标
    private JFileChooser chooser = null;                      // 文件选择对象
    private File file = null;                                 // 文件对象
    private UndoManager manager = null;                       // 撤销操作管理对象
    private String codestyle = "UTF-8";                       // 编码格式
 
 
    /*----------------文件菜单项---------------------*/
    private JMenuItem file_new = null;                        // 新建
    private JMenuItem file_open = null;                       // 打开
    private JMenuItem file_save = null;                       // 保存
    private JMenuItem file_exit = null;                       // 退出
    /*----------------文件菜单项---------------------*/
 
 
    /*----------------编辑菜单项---------------------*/
    private JMenuItem edit_cut = null;                        // 剪切
    private JMenuItem edit_copy = null;                       // 复制
    private JMenuItem edit_paste = null;                      // 粘贴
    private JMenuItem edit_clear = null;                      // 清空当前内容
    private JMenuItem edit_undo = null;                       // 撤销
    /*----------------编辑菜单项---------------------*/
 
 
    /*----------------格式菜单项---------------------*/
    private JMenuItem format_chooseFontColor = null;          // 选择字体颜色
    private JMenuItem format_chooseFont = null;               // 选择字体
    /*----------------格式菜单项---------------------*/
 
 
    /*----------------帮助菜单项---------------------*/
    private JMenuItem help_about = null;                      // 关于
    /*----------------帮助菜单项---------------------*/
 
 
    /*----------------工具栏项目---------------------*/
    private JButton button_save = null;
    private JButton button_copy = null;
    private JButton button_paste = null;
    private JButton button_cut = null;
    private JLabel moreOption = null;
    private JCheckBox check_bold = null;                      // 加粗选项
    private JCheckBox check_italic = null;                    // 斜体选项
    /*----------------工具栏项目---------------------*/
 
 
    /*----------------弹出菜单项---------------------*/
    private JMenuItem popCut = null;
    private JMenuItem popCopy = null;
    private JMenuItem popPaste = null;
    /*----------------弹出菜单项---------------------*/
 
    /*----------------状态栏项----------------------*/
    private JLabel label = null;
    private JLabel labelTime = null;
    private JLabel labelCodeStyle = null;
    /*----------------状态栏项----------------------*/
 
 
    public MyFrame(String title){
        super(title);   //窗体标题
        init();
        registerListener();
    }
 
    //初始化相关组件
    private void init(){
        tv_area = new JTextArea();                            // 创建一个多行文本框
        tv_area.setFont(new Font("Courier Prime", Font.PLAIN, 18));
        tv_area.setCaretColor(Color.GREEN);                   // 光标颜色
        tv_area.setSelectedTextColor(Color.ORANGE);           // 选中字体颜色
        tv_area.setSelectionColor(Color.CYAN);                // 选中背景颜色
        tv_area.setLineWrap(true);                            // 自动换行
        tv_area.setTabSize(4);
        tv_area.getDocument().
                addUndoableEditListener(manager);             // 设置文本框编辑监听（可撤销）
        s_pane = new JScrollPane(tv_area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  // 创建一个滚动条面板
        s_pane.setOpaque(false);                              // 设置面板透明
        s_pane.getViewport().setOpaque(false);
        mb = new JMenuBar(); //初始化菜单栏
        manager = new UndoManager();                          // 创建一个撤销管理对象
        Container cpan = this.getContentPane();               // 用Container对象获取当前框架的内容面板
        cpan.add(s_pane,BorderLayout.CENTER);                 // 将多行文本框添加到面板中央
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);           // 设置窗体大小
 
 
        /*----------------设置图标----------------------*/
        toolBar_Save = new ImageIcon("src/ToolBar_Save.png");
        toolBar_Cut = new ImageIcon("src/ToolBar_Cut.png");
        toolBar_Copy = new ImageIcon("src/ToolBar_Copy.png");
        toolBar_Paste = new ImageIcon("src/ToolBar_Paste.png");
        toolBar_more_font = new ImageIcon("src/more_font.png");
        statusBar_status = new ImageIcon("src/statusBar.png");
        m_help_icon = new ImageIcon("src/info.png");
        /*----------------设置图标----------------------*/
 
 
        /*----------------文件菜单----------------------*/
        m_file = new JMenu("file(F)");
        m_file.setMnemonic('F');
        file_new = new JMenuItem("New(N)");
        file_new.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        file_new.setMnemonic('N');
        file_open = new JMenuItem("Open(O)");
        file_open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        file_save = new JMenuItem("Save(S)");
        file_save.setEnabled(false);
        file_save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        file_exit = new JMenuItem("Exit");
        m_file.add(file_new);
        m_file.add(file_open);
        m_file.add(file_save);
        m_file.addSeparator();
        m_file.add(file_exit);
        /*----------------文件菜单----------------------*/
 
 
        /*----------------编辑菜单----------------------*/
        m_edit = new JMenu("Edit(E)");
        m_file.setMnemonic('E');
        edit_cut = new JMenuItem("Cut(X)");
        edit_cut.setEnabled(false);
        edit_cut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        edit_copy = new JMenuItem("Copy(C)");
        edit_copy.setEnabled(false);
        edit_copy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        edit_paste = new JMenuItem("Paste(V)");
        edit_paste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        edit_clear = new JMenuItem("Clear");
        edit_clear.setEnabled(false);
        edit_undo = new JMenuItem("Undo(Z)");
        edit_undo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        m_edit.add(edit_cut);
        m_edit.add(edit_copy);
        m_edit.add(edit_paste);
        m_edit.addSeparator();
        m_edit.add(edit_clear);
        m_edit.addSeparator();
        m_edit.add(edit_undo);
        /*----------------编辑菜单----------------------*/
 
 
        /*----------------格式菜单----------------------*/
        m_format = new JMenu("Format(M)");
        m_file.setMnemonic('M');
        format_chooseFont = new JMenuItem("Font Select");
        format_chooseFontColor = new JMenuItem("Font colour");
        m_format.add(format_chooseFont);
        m_format.add(format_chooseFontColor);
        /*----------------格式菜单----------------------*/
 
 
        /*----------------帮助菜单----------------------*/
        m_help = new JMenu("Help(H)");
        m_file.setMnemonic('H');
        help_about = new JMenuItem("About",m_help_icon);
        m_help.add(help_about);
        /*----------------帮助菜单----------------------*/
 
 
        /*----------------右键菜单----------------------*/
        popupMenu = new JPopupMenu();
        popCut = new JMenuItem("Cut",toolBar_Cut);
        popCopy = new JMenuItem("Copy",toolBar_Copy);
        popPaste = new JMenuItem("Paste",toolBar_Paste);
        popupMenu.add(popCut);
        popupMenu.add(popCopy);
        popupMenu.add(popPaste);
        /*----------------右键菜单----------------------*/
 
 
 
        mb.add(m_file);                                       // 菜单栏添加相应菜单file
        mb.add(m_edit);                                       // 菜单栏添加相应菜单edit
        mb.add(m_format);                                     // 菜单栏添加相应菜单format
        mb.add(m_help);                                       // 菜单栏添加相应菜单help
 
 
 
        /*----------------工具栏-----------------------*/
        toolBar1 = new JToolBar();
        toolBar1.setMargin(new Insets(1,1,1,1));
                                                              // 设置按钮间距
        button_save = new JButton(toolBar_Save);
        button_save.setEnabled(false);
        button_save.setToolTipText("Save current Content");
        button_copy = new JButton(toolBar_Copy);
        button_copy.setEnabled(false);
        button_copy.setToolTipText("Copy selected content");
        button_paste = new JButton(toolBar_Paste);
        button_paste.setToolTipText("Paste");
        button_cut = new JButton(toolBar_Cut);
        button_cut.setEnabled(false);
        button_cut.setToolTipText("Cut selected content");
        moreOption = new JLabel("More-->",toolBar_more_font,SwingConstants.CENTER);
        check_bold = new JCheckBox("<html><p style=\"font-weight:bold;\">Bold</p><html>");
        check_bold.setEnabled(false);
        check_bold.setSelected(false);
        check_italic = new JCheckBox("<html><i>Italic</i></html>");
        check_italic.setEnabled(false);
        check_italic.setSelected(false);
        toolBar1.addSeparator();
        toolBar1.add(button_save);
        toolBar1.addSeparator(new Dimension(20,2));
        toolBar1.add(button_copy);
        toolBar1.addSeparator(new Dimension(20,2));
        toolBar1.add(button_paste);
        toolBar1.addSeparator(new Dimension(20,2));
        toolBar1.add(button_cut);
        toolBar1.addSeparator(new Dimension(290,2));
        toolBar1.add(moreOption);
        toolBar1.add(check_bold);
        toolBar1.add(check_italic);
        toolBar1.setFloatable(false);
 
        /*----------------工具栏-----------------------*/
 
 
        /*----------------状态栏-----------------------*/
        toolBar2 = new JToolBar();
        toolBar2.setFloatable(false);
 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
        label = new JLabel("current word counter:0",statusBar_status,SwingConstants.CENTER);
        labelTime = new JLabel("Date: "+sdf.format(new Date()));
        labelCodeStyle = new JLabel("Coding: "+codestyle);
 
        toolBar2.add(label);
        toolBar2.addSeparator(new Dimension(180,5));
        toolBar2.add(labelTime);
        toolBar2.addSeparator(new Dimension(180,5));
        toolBar2.add(labelCodeStyle);
        /*----------------状态栏-----------------------*/
 
 
        this.add(toolBar1,BorderLayout.NORTH);                // 将工具栏添加到框架中
        this.add(toolBar2,BorderLayout.SOUTH);
 
 
        this.setJMenuBar(mb);                                 // 将菜单栏添加到框架中
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                                                              // 设置关闭时动作
        Toolkit kit = Toolkit.getDefaultToolkit();            // 获取工具包
        Dimension screenSize = kit.getScreenSize();           // Dimension对象获取尺寸
        int screenWidth = screenSize.width;                   // 获取当前屏幕宽度
        int screenHeight = screenSize.height;                 // 获取当前屏幕高度
        int windowsWidth = this.getWidth();                   // 获取当前窗体宽度
        int windowsHeight = this.getHeight();                 // 获取当前窗体高度
        this.setLocation((screenWidth-windowsWidth)/2,
                (screenHeight-windowsHeight)/2);           // 设置窗口位置
        this.setVisible(true);                                // 设置窗体可见
    }
 
    //监听器注册区
    private void registerListener(){
        tv_area.addMouseListener(new MouseAdapter() {         // 为文本框添加鼠标监听器
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }
 
            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }
        });
 
        tv_area.getDocument().addDocumentListener(new DocumentListener() {
            @Override                                         // 监听文本区改变
            public void insertUpdate(DocumentEvent e) {
                isItemsAvalible();                            // 一旦文本有改变就设置各按钮的可用性
                changeTextLengthStatus();                     // 实时显示文本字数
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isItemsAvalible();                            // 一旦文本有改变就设置各按钮的可用性
                changeTextLengthStatus();                     // 实时显示文本字数
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                changeTextLengthStatus();                     // 实时显示文本字数
                isItemsAvalible();                            // 一旦文本有改变就设置各按钮的可用性
            }
        });
        file_new.addActionListener(e -> newFile());           // 注册新建文件监听器
        file_open.addActionListener(e -> openFile());         // 注册打开文件监听器
        file_save.addActionListener(e -> saveFile());         // 注册保存文件监听器
        file_exit.addActionListener(e -> exit());             // 注册退出程序监听器
        edit_clear.addActionListener(e -> clearAll());
        edit_undo.addActionListener(e -> undo());
        edit_cut.addActionListener(e -> tv_area.cut());
        edit_copy.addActionListener(e -> tv_area.copy());
        edit_paste.addActionListener(e -> tv_area.paste());
        format_chooseFont.addActionListener(e -> setTextFont());
        format_chooseFontColor.addActionListener(e -> setTextColor());
        help_about.addActionListener(e -> new AboutDialog(MyFrame.this,
                "About-NotePad",true));
        button_save.addActionListener(e -> saveFile());
        button_copy.addActionListener(e -> tv_area.copy());
        button_paste.addActionListener(e -> tv_area.paste());
        button_cut.addActionListener(e -> tv_area.cut());
        check_italic.addActionListener(e -> checkBox_setFont());
        check_bold.addActionListener(e -> checkBox_setFont());
        popCut.addActionListener(e -> tv_area.cut());
        popCopy.addActionListener(e -> tv_area.copy());
        popPaste.addActionListener(e -> tv_area.paste());
        this.addWindowListener(new WindowAdapter() {       //为框架添加退出保存的监视器
            @Override
            public void windowClosing(WindowEvent e) {
                String exitMessage = "Do you want to Save？";
                if(tv_area.getText().equals(""))
                    System.exit(0);
                else{
                    if(JOptionPane.showConfirmDialog(null, exitMessage,"Tip",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                        saveFile();
                        System.exit(0);
                    }else
                        System.exit(0);
                }
            }
        });
    }
 
 
    private void newFile(){                                   // 新建文件触发函数
        if(!tv_area.getText().equals("")){
            int res = JOptionPane.showConfirmDialog
                    (null,"Current work not saved ,Do you want to Save?",
                            "info",JOptionPane.YES_NO_OPTION);   // 储存选择结果
            if(res==JOptionPane.YES_OPTION){
                saveFile();
                tv_area.setText("");
                this.setTitle("untitled");
                file = null;
            }else{
                tv_area.setText("");                          // 取消则清空页面
                this.setTitle("untitled");
                file = null;
            }
        }
    }
 
    private void clearAll(){                                  // 清空当前页面触发函数
        tv_area.setText("");
    }          // 清空页面触发函数
 
    private void openFile(){                                  // 打开文件触发函数
        try {
            chooser = new JFileChooser("/Users/1kasshole/Desktop/");
                                                              // 设置打开时的默认目录
            chooser.setFileFilter(new filter());              // 设置格式过滤器
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {      // 点击打开按钮
                file = chooser.getSelectedFile();
                int length = (int) file.length();
                FileReader reader = new FileReader(file);
                char[] ch = new char[length];
                reader.read(ch);                              // 将文件读进char数组
                reader.close();
                tv_area.setText(new String(ch).trim());       // 删除字符串的头尾空白符
                setTitle(file.getName());                     // 框架标题设置为文件名
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
    private void saveFile(){                                  // 文件保存触发函数
        if (file == null)
            try {
                chooser = new JFileChooser(
                        "/Users/1kasshole/Desktop/");
                chooser.setFileFilter(new filter());
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectfile =
                            chooser.getSelectedFile();        // 获得文件
                    String end = chooser.getFileFilter().getDescription();
                                                              // 获得被选中的过滤器中的文件扩展名
                    File newFile;
                    if (selectfile.getAbsolutePath().toUpperCase().endsWith(end.toUpperCase())) {
                        // 如果文件是以选定扩展名结束的，则使用原名
                        newFile = selectfile;
                    } else {                                  // 否则加上选定的扩展名
                        newFile = new File(selectfile.getAbsolutePath()+ end);
                    }
                    try {
                        if (!newFile.exists()) {
                            newFile.createNewFile();
                        }
                        FileWriter writer = new FileWriter(newFile);
                        char[] arry = tv_area.getText().toCharArray();
                        writer.write(arry);
                        writer.flush();
                        writer.close();
                        setTitle(newFile.getName());
                        file = newFile;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        else
            try {
                FileWriter writer = new FileWriter(file);
                char[] arry = tv_area.getText().toCharArray();
                writer.write(arry);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }
 
    private void undo(){                                      // 撤销触发函数
        if (manager.canUndo()){
            manager.undo();
        }
    }
 
    private void exit(){                                      // 退出程序触发函数
        newFile();
        System.exit(0);
    }
 
    private void setTextFont(){                               // 选择字体触发函数
        try{
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
                                                              // 获取系统字体
            JList<String> fontNames = new JList<>(ge.getAvailableFontFamilyNames());
            int response = JOptionPane.showConfirmDialog(null,
                    new JScrollPane(fontNames), "Please select font (default: Courier Prime )",
                    JOptionPane.OK_CANCEL_OPTION);
            Object selectedFont = fontNames.getSelectedValue();
            if (response == JOptionPane.OK_OPTION && selectedFont != null)
                tv_area.setFont(new Font(fontNames.getSelectedValue(), Font.PLAIN, DEFAULT_FONTSIZE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 
    private void setTextColor(){                               // 字体颜色选择触发函数
        Color color = JColorChooser.showDialog(null, "**Please select font colour**",
                Color.WHITE);
        tv_area.setForeground(color);
    }
 
    // 文件格式过滤
    private class filter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            String name = file.getName();
            name.toLowerCase();
            return name.endsWith(".txt") || file.isDirectory();
        }
 
        public String getDescription() {
            return ".txt";
        }
    }
 
    // 监视系统剪切板,暂时无用
    private boolean isClipboardEmpty(){
        Transferable transferable = Toolkit.getDefaultToolkit().
                getSystemClipboard().getContents(null);
                                                               // 获取内容
        if(transferable!=null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
            try{
                return false;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }
 
    private void maybeShowPopup(MouseEvent e){                 // 监听鼠标
        if(e.isPopupTrigger()){
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
        }
    }
 
    private void changeTextLengthStatus(){                     // 文本监听
        String content = tv_area.getText().trim();
        label.setText("Current word Count:"+content.length());
    }
 
    private void isItemsAvalible(){                                   // 监视文本区并设置各功能项是否可用
        String content = tv_area.getText();
        if(content.equals("")){
            edit_cut.setEnabled(false);
            edit_clear.setEnabled(false);
            edit_copy.setEnabled(false);
            file_save.setEnabled(false);
            button_save.setEnabled(false);
            button_copy.setEnabled(false);
            button_cut.setEnabled(false);
            check_bold.setEnabled(false);
            check_italic.setEnabled(false);
        }else{
            file_save.setEnabled(true);
            edit_cut.setEnabled(true);
            edit_clear.setEnabled(true);
            edit_copy.setEnabled(true);
            button_save.setEnabled(true);
            button_copy.setEnabled(true);
            button_cut.setEnabled(true);
            check_bold.setEnabled(true);
            check_italic.setEnabled(true);
        }
    }
 
    private void checkBox_setFont(){                           // 复选框监听
        if(check_bold.isSelected()){
            check_italic.setEnabled(false);
            tv_area.setFont(new Font(tv_area.getFont().getName(),Font.BOLD,DEFAULT_FONTSIZE));
        }
        if(check_italic.isSelected()){
            check_bold.setEnabled(false);
            tv_area.setFont(new Font(tv_area.getFont().getName(),Font.ITALIC,DEFAULT_FONTSIZE));
        }
        if(!check_bold.isSelected()){
            check_italic.setEnabled(true);
            if(!check_italic.isSelected())
                tv_area.setFont(new Font(tv_area.getFont().getName(),Font.PLAIN,DEFAULT_FONTSIZE));
        }
        if(!check_italic.isSelected()){
            check_bold.setEnabled(true);
            if(!check_bold.isSelected())
                tv_area.setFont(new Font(tv_area.getFont().getName(),Font.PLAIN,DEFAULT_FONTSIZE));
        }
    }
}