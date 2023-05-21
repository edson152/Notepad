import javax.swing.*;
import java.awt.*;
/*
    ...................
    Created by EDSON NYONI
    ...................
 */
class AboutDialog extends JDialog {
    private static final int ABOUT_WIDTH = 300;               // 窗口默认宽度
    private static final int ABOUT_HEIGHT = 220;              // 窗口默认高度
    private Icon about = new ImageIcon("src/Help_About.png");
    private JLabel desc = null;                               // 文本标签
    private JPanel panel = null;                              // 内容面板
    private JButton btn = null;                               // 按钮
    public AboutDialog(Frame frame, String title, boolean modal){
        super(frame,title,modal);                             // 将对话框放进当前框架，设置标题和模态
        init();
        registerListener();
    }
    private void init(){
        desc = new JLabel();                                  // 新建文本标签
        desc.setText("<html>HAVE FUN~ <font size=\"4px\"><u></u></font><br><br>Supports .txt format only<br><br><strong> CREATED BY EDSON NYONI</strong></html>");
        desc.setHorizontalAlignment(JLabel.CENTER);           // 设置文本标签位置
        panel = new JPanel();                                 // 新建内容面板
        btn = new JButton();
        btn.setIcon(about);
        panel.add(btn);
        this.add(desc);                                       // 文本默认在边界管理器中央
        this.add(panel,BorderLayout.SOUTH);                   // 将面板放在便捷管理器下方
        this.setSize(ABOUT_WIDTH,ABOUT_HEIGHT);               // 设置对话框大小
    }
    private void registerListener(){
        btn.addActionListener(e->{
            AboutDialog.this.dispose();                             // 退出对话框
        });
        Toolkit kit = Toolkit.getDefaultToolkit();            // 获取工具包
        Dimension screenSize = kit.getScreenSize();           // Dimension对象获取尺寸
        int screenWidth = screenSize.width;                   // 获取当前屏幕宽度
        int screenHeight = screenSize.height;                 // 获取当前屏幕高度
        int dialogWidth = this.getWidth();                    // 获取当前对话框宽度
        int dialogHeight = this.getHeight();                  // 获取当前对话框高度
        this.setLocation((screenWidth-dialogWidth)/2,
                (screenHeight-dialogHeight)/2);            // 设置窗口位置
        this.setVisible(true);                                // 设置可见
    }
}