import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.Locale;


public class TI_LCD_Programmer extends JFrame
{

    private void initButton()
    {

        a0Button.addActionListener(e ->
        {
            isOperator = false;
            displayIOput("0");
        });
        a1Button.addActionListener(e ->
        {
            isOperator = false;
            displayIOput("1");
        });
        a2Button.addActionListener(e ->
        {
            isOperator = false;
            displayIOput("2");
        });
        a3Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("3");
        });
        a4Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("4");
        });
        a5Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("5");
        });
        a6Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("6");
        });
        a7Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("7");
        });
        a8Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("8");
        });
        a9Button.addActionListener(e -> {
            isOperator = false;
            displayIOput("9");
        });
        AButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;

                displayIOput("A");
            }
        });
        bButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;
                displayIOput("B");
            }
        });
        CButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;
                displayIOput("C");
            }
        });
        dButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;
                displayIOput("D");
            }
        });
        EButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;
                displayIOput("E");
            }
        });
        FButton.addActionListener(e->{
            if(isHEX) {
                isOperator = false;
                displayIOput("F");
            }
        });
        dotButton.addActionListener(e -> {
            isOperator = false;
            displayIOput(".");
        });
        AddButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isOperator == true)               //如果上一个是运算符 直接切换
                {
                    nowoperator = 1;
                    getOperator2();
                }
                else
                {
                    isOperator = true;
                    nowoperator = 1;
                    getOperator2();
                    calculate();
                }
                displayIOput(answer.toString());
                updateAnswer();
            }
            else if(OperatingMode==1)
            {
                isOperator = true;
                displayIOput("+");
            }
        });
        SubButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isOperator == true)
                {
                    nowoperator = 2;
                    getOperator2();
                }
                else
                {
                    isOperator = true;
                    nowoperator = 2;
                    getOperator2();
                    calculate();
                }
                displayIOput(answer.toString());
                updateAnswer();
            }
            else if(OperatingMode==1)
            {
                isOperator = true;
                displayIOput("-");
            }
        });
        MulButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isOperator == true)
                {
                    nowoperator = 3;
                    getOperator2();
                }
                else
                {
                    isOperator = true;
                    nowoperator = 3;
                    getOperator2();
                    calculate();
                }
                displayIOput(answer.toString());
                updateAnswer();
            }
            else if(OperatingMode==1)
            {
                isOperator = true;
                displayIOput("*");
            }
        });
        DivButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isOperator == true)
                {
                    nowoperator = 4;
                    getOperator2();
                }
                else
                {
                    isOperator = true;
                    nowoperator = 4;
                    getOperator2();
                    calculate();
                }
                displayIOput(answer.toString());
                updateAnswer();
            }
            else if(OperatingMode==1)
            {
                isOperator = true;
                displayIOput("/");
            }
        });
        EqualButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                isOperator = true;
                nowoperator = 0;
                getOperator2();
                if (lastoperator != 0)
                {
                    equaloptmp = lastoperator;
                    equaltmp = second;
                }
                if (lastoperator == 0 && equaloptmp == -1)
                {
                    if (answer.compareTo(new BigDecimal(0)) != 0)
                        second = answer;
                }
                else if (lastoperator == 0)
                {
                    lastoperator = equaloptmp;
                    second = equaltmp;
                }
                calculate();
                displayIOput(answer.toString());
                updateAnswer();
            }
            else if(OperatingMode==1)
            {
                getInfix();
                translate();
            }
        });

        CEButton.addActionListener(e -> {
            //clear error 清除一位
            if(!tmp.isEmpty())
            {
                tmp=tmp.substring(0,tmp.length()-1);
                IOput.setText(tmp);
                if(tmp.isEmpty())
                    IOput.setText("0");
            }
        });
        ONorCLRButton.addActionListener(e -> {
            isON = true;
            //开机后清零 然后显示0；
            if(isONforCLR ==false)
            {
                isONforCLR =true;  //转变开机键功能
                IOput.setText("0");
                OverFlow.setText("");
            }
            else
            {
                clearall();
                IOput.setText("0");
                OverFlow.setText("");
            }
        });
        OFFButton.addActionListener(e -> {
            isON =false;
            isONforCLR =false;
            //清零 然后关机
            clearall();
            IOput.setText("");
        });
        HEXButton.addActionListener(e->{
            if(isON) {
                if (isHEX == false) {
                    isDEC = false;
                    isHEX = true;
                    DECLabel.setText("");
                    HEXLabel.setText("HEX");
                    if (IOput.getText().length() > 0) {
                        Integer temp = Integer.decode(IOput.getText());
                        IOput.setText(temp.toHexString(temp).toUpperCase(Locale.ROOT));
                    }
                } else
                    return;
            }
        });
        DECButton.addActionListener(e->{
            if(isON) {
                if (isDEC == false) {
                    isDEC = true;
                    isHEX = false;
                    DECLabel.setText("DEC");
                    HEXLabel.setText("");
//            if(IOput.getText().length()>0) {
//                Integer temp = Integer.parseInt(IOput.getText(),10);
//                IOput.setText(temp.toString(temp).toUpperCase(Locale.ROOT));
//            }
                    BigDecimal DECcacluate = new BigDecimal(0);//保存10进制换算结果
//            BigDecimal HEXcacluate=new BigDecimal(0);//保存16进制换算结果(以十进制模式表示)
                    String Text = IOput.getText();
                    for (int i = 0; i < IOput.getText().length(); i++) {
                        char a = Text.charAt(i);
                        int num = (int) Math.pow(16, (Text.length() - 1 - i));
                        switch (a) {
                            case '0':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(0));
                                break;
                            case '1':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(1 * num));
                                break;
                            case '2':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(2 * num));
                                break;
                            case '3':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(3 * num));
                                break;
                            case '4':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(4 * num));
                                break;
                            case '5':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(5 * num));
                                break;
                            case '6':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(6 * num));
                                break;
                            case '7':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(7 * num));
                                break;
                            case '8':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(8 * num));
                                break;
                            case '9':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(9 * num));
                                break;
                            case 'A':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(10 * num));
                                break;
                            case 'B':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(11 * num));
                                break;
                            case 'C':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(12 * num));
                                break;
                            case 'D':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(13 * num));
                                break;
                            case 'E':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(14 * num));
                                break;
                            case 'F':
                                DECcacluate = DECcacluate.add(BigDecimal.valueOf(15 * num));
                                break;
                        }
//                        System.out.println(DECcacluate);

                    }

                    IOput.setText(DECcacluate.toString());
                    if (isOverflow())
                        IOput.setText(IOput.getText().substring(IOput.getText().length() - 8, IOput.getText().length()));
                } else
                    return;
            }
        });
        OPButton.addActionListener(e ->
        {
            isOperator = true;
            displayIOput("(");
        });
        CPButton.addActionListener(e -> {
            isOperator = true;
            displayIOput(")");
        });

    }

    private void getInfix()
    {
        tmpfix=tmp;
        for (int i = 0; i < tmpfix.length(); i++)
        {
//            if ((tmpfix.charAt(i) >= '0' && tmpfix.charAt(i) <= '9') || tmpfix.charAt(i) == '+' || tmpfix.charAt(i) == '-' || tmpfix.charAt(i) == '*' || tmpfix.charAt(i) == '/' || tmpfix.charAt(i) == '(' || tmpfix.charAt(i) == ')')
//            {
//                infix += tmpfix.charAt(i);
//                continue;
//            }
//            if (tmpfix.charAt(i) == '_' && i + 1 < tmpfix.length() && tmpfix.charAt(i + 1) >= '0' && tmpfix.charAt(i + 1) <= '9')
//                infix += tmpfix.charAt(i);
            if(i + 1 < tmpfix.length()&&tmpfix.charAt(i) >= '0' && tmpfix.charAt(i) <= '9'&&tmpfix.charAt(i+1) >= '0' && tmpfix.charAt(i+1) <= '9')
                infix = infix + tmpfix.charAt(i) + "_";
            else
                infix = infix + tmpfix.charAt(i);
        }
        System.out.println(infix);
    }

    private void calculate()
    {
        switch (lastoperator)
        {
            case 1:answer=first.add(second);break;
            case 2:answer=first.subtract(second);break;
            case 3:answer=first.multiply(second);break;
            case 4:answer=first.divide(second);break;
            case 0:answer=second;break;

        }
    }

    private void getOperator2()           //获取当前操作数
    {
        operator2 = "" + tmp;
        if (!operator2.isEmpty())
            second = new BigDecimal(operator2);
        else
            second = new BigDecimal(0);
    }

    private void updateAnswer()    //将结果保存到first
    {
        tmp = "";
        first = answer;
        lastoperator = nowoperator;
    }
    private boolean isOverflow()//判断运算溢出但并未区分正负上下溢出
    {
        if(IOput.getText().length()>8)
        {
            OverFlow.setText("Warning:Operation Overflow!");
//            IOput.setText(tmp.substring(0,7));
            return true;
        }
        return false;
    }
    private void displayIOput(String s)
    {
        if(isON ==true)
        {
            if (OperatingMode == 0)
            {
                if (isOperator == true)
                    tmp = "";
                if (tmp.length() < 8)
                    tmp = tmp + s;
                IOput.setText(tmp);
                if (isOverflow())
                    IOput.setText(tmp.substring(tmp.length() - 8, tmp.length()));
            }
            else if (OperatingMode == 1)
            {
//                if (isOperator == false)
//                    tmpfix = tmpfix + s + "_";
//                else
//                    tmpfix = tmpfix + s;
                tmp = tmp + s;
                IOput.setText(tmp);
            }
        }
    }
    private void clearall()
    {
        isOperator = false;   //是否是运算符
        operator1 = "";            //操作数1
        operator2 = "";            //操作数2
        tmp = "";                  //用于在ioput中显示
        first=new BigDecimal(0);
        second=new BigDecimal(0);
        answer=new BigDecimal(0);
        lastoperator=0;               //上一运算符
        nowoperator=0;                //本次运算符
        tmpfix="";
        infix="";
        postfix="";
    }

    private int priority(char op)
    {
        int priority=-1;
        if (op == '*')
            priority = 5;
        if (op == '/')
            priority = 5;
        if (op == '+')
            priority = 2;
        if (op == '-')
            priority = 2;
        if (op == '(')
            priority = 0;
        return priority;
    }
    private boolean translate()
    {
        Stack<Character> s = new Stack<>();
        for (int i = 0; i < infix.length(); i++)
        {
            if ((infix.charAt(i) <= '9' && infix.charAt(i) >= '0') || infix.charAt(i) == '_')
            {    //如果是数字，直接入栈
                postfix += infix.charAt(i);
            }
            else
            {                        //否则不是字母
                if (s.empty())            //栈空则入站
                    s.push(infix.charAt(i));
                else if (infix.charAt(i) == '(')   //左括号入栈
                    s.push(infix.charAt(i));
                else if (infix.charAt(i) == ')')
                {  //如果是右括号，只要栈顶不是左括号，就弹出并输出
                    while (s.peek() != '(')
                    {
                        postfix += s.peek();
                        s.pop();
                    }
                    s.pop();                 //弹出左括号，但不输出
                }
                else
                {
                    while (priority(infix.charAt(i)) <= priority(s.peek()))
                    { //栈顶优先级大于等于当前运算符，则输出
                        postfix += s.peek();
                        s.pop();
                        if (s.empty())      //栈为空，停止
                            break;
                    }
                    s.push(infix.charAt(i));   //把当前运算符入栈
                }
            }
        }
        while (!s.empty())
        {      //最后，如果栈不空，则弹出所有元素并输出
            postfix += s.peek();
            s.pop();
        }
        return true;
    }









//====================================================================================
//====================================================================================
    public void init()
    {
        this.setTitle("TI_LCD_Programmer");
        this.add(TI_LCD_Programmer);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((int) (kit.getScreenSize().getWidth()/2-250), (int) (kit.getScreenSize().getHeight()/2-500));
        this.pack();
        this.setFocusable(true);      //要写在visible前面
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public TI_LCD_Programmer()
    {
        ON_OFF_control();
        initLayout();
        initKeyboard();
        initButton();
//        a3Button.addKeyListener(new KeyAdapter()
//        {
//            @Override
//            public void keyReleased(KeyEvent e)
//            {
//                super.keyReleased(e);
//                int keycode = e.getKeyCode();
//                System.out.println(KeyStroke.getKeyStroke(keycode, 0, false));
//            }
//        });
//
//


    }

    private void ON_OFF_control()
    {
        ONorCLRButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                isON = true;
                //开机后清零 然后显示0；
                if (isONforCLR == false)
                {
                    isONforCLR = true;  //转变开机键功能
                    IOput.setText("0");
                    OverFlow.setText("");
                }
                else
                {
                    clearall();
                    IOput.setText("0");
                    OverFlow.setText("");
                }
            }
        });
        OFFButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                isON =false;
                isONforCLR =false;
                //清零 然后关机
                clearall();
                IOput.setText("");
            }
        });
    }
    
    private void initLayout()
    {
        setToolButton();
        setDigitalButton();
        setLabel();
        setText();
        setPanel();
    }

    private void initKeyboard()
    {

        JButton[] DIGIT={a0Button,a1Button,a2Button,a3Button,a4Button,a5Button,
                a6Button,a7Button,a8Button,a9Button,AddButton,SubButton,
                MulButton,DivButton,dotButton,EqualButton};//,AddButton,SubButton,MulButton,DivButton,dotButton,EqualButton,CEButton
        String[] pressed1={"pressed 0","pressed 1","pressed 2",
                "pressed 3","pressed 4","pressed 5",
                "pressed 6","pressed 7","pressed 8",
                "pressed 9","shift EQUALS","pressed MINUS",
                "shift 8","pressed SLASH","PERIOD","pressed EQUALS"
        };
        String[] pressed2={"pressed NUMPAD0",
                "pressed NUMPAD1", "pressed NUMPAD2", "pressed NUMPAD3", "pressed NUMPAD4",
                "pressed NUMPAD5", "pressed NUMPAD6", "pressed NUMPAD7", "pressed NUMPAD8",
                "pressed NUMPAD9", "pressed ADD", "pressed SUBTRACT", "pressed MULTIPLY",
                "pressed DIVIDE", "pressed DECIMAL", "pressed ENTER"
        };
        for(int i=0;i<DIGIT.length;i++)
        {
            int finalI = i;
            DIGIT[finalI].registerKeyboardAction(e->DIGIT[finalI].doClick(),KeyStroke.getKeyStroke(pressed1[i]),JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
        for(int i=0;i<DIGIT.length;i++)
        {
            int finalI = i;
            DIGIT[finalI].registerKeyboardAction(e->DIGIT[finalI].doClick(),KeyStroke.getKeyStroke(pressed2[i]),JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
        EqualButton.registerKeyboardAction(e -> EqualButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        CEButton.registerKeyboardAction(e -> CEButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        ONorCLRButton.registerKeyboardAction(e -> ONorCLRButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
        OFFButton.registerKeyboardAction(e -> OFFButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        DECButton.registerKeyboardAction(e->DECButton.doClick(),KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.SHIFT_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
        HEXButton.registerKeyboardAction(e->HEXButton.doClick(),KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.SHIFT_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
        OPButton.registerKeyboardAction(e->OPButton.doClick(),KeyStroke.getKeyStroke(KeyEvent.VK_9,InputEvent.SHIFT_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
        OPButton.registerKeyboardAction(e->CPButton.doClick(),KeyStroke.getKeyStroke(KeyEvent.VK_0,InputEvent.SHIFT_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a0Button.registerKeyboardAction(e -> a0Button.doClick(), KeyStroke.getKeyStroke("pressed 0"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a0Button.registerKeyboardAction(e -> a0Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD0"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a1Button.registerKeyboardAction(e -> a1Button.doClick(), KeyStroke.getKeyStroke("pressed 1"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a1Button.registerKeyboardAction(e -> a1Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD1"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a2Button.registerKeyboardAction(e -> a2Button.doClick(), KeyStroke.getKeyStroke("pressed 2"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a2Button.registerKeyboardAction(e -> a2Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD2"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a3Button.registerKeyboardAction(e -> a3Button.doClick(), KeyStroke.getKeyStroke("pressed 3"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a3Button.registerKeyboardAction(e -> a3Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD3"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a4Button.registerKeyboardAction(e -> a4Button.doClick(), KeyStroke.getKeyStroke("pressed 4"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a4Button.registerKeyboardAction(e -> a4Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD4"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a5Button.registerKeyboardAction(e -> a5Button.doClick(), KeyStroke.getKeyStroke("pressed 5"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a5Button.registerKeyboardAction(e -> a5Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD5"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a6Button.registerKeyboardAction(e -> a6Button.doClick(), KeyStroke.getKeyStroke("pressed 6"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a6Button.registerKeyboardAction(e -> a6Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD6"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a7Button.registerKeyboardAction(e -> a7Button.doClick(), KeyStroke.getKeyStroke("pressed 7"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a7Button.registerKeyboardAction(e -> a7Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD7"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a8Button.registerKeyboardAction(e -> a8Button.doClick(), KeyStroke.getKeyStroke("pressed 8"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a8Button.registerKeyboardAction(e -> a8Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD8"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a9Button.registerKeyboardAction(e -> a9Button.doClick(), KeyStroke.getKeyStroke("pressed 9"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        a9Button.registerKeyboardAction(e -> a9Button.doClick(), KeyStroke.getKeyStroke("pressed NUMPAD9"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        AddButton.registerKeyboardAction(e -> AddButton.doClick(), KeyStroke.getKeyStroke("pressed ADD"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        AddButton.registerKeyboardAction(e -> AddButton.doClick(), KeyStroke.getKeyStroke("pressed EQUALS"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        SubButton.registerKeyboardAction(e -> SubButton.doClick(), KeyStroke.getKeyStroke("pressed SUBTRACT"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        SubButton.registerKeyboardAction(e -> SubButton.doClick(), KeyStroke.getKeyStroke("pressed MINUS"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        MulButton.registerKeyboardAction(e -> MulButton.doClick(), KeyStroke.getKeyStroke("pressed MULTIPLY"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        DivButton.registerKeyboardAction(e -> DivButton.doClick(), KeyStroke.getKeyStroke("pressed DIVIDE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        dotButton.registerKeyboardAction(e -> dotButton.doClick(), KeyStroke.getKeyStroke("pressed DECIMAL"), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        dotButton.registerKeyboardAction(e -> dotButton.doClick(), KeyStroke.getKeyStroke("pressed PERIOD"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void setToolButton()//非数字按钮的设置
    {
        JButton[] ToolButton={
                DECButton,HEXButton,OCTButton,OFFButton,ONorCLRButton,
                STOButton,RCLButton,SUMButton,OPButton,CPButton,
                SHFButton,dButton,EButton,FButton,KButton,
                a1SCButton,AButton,bButton,CButton,DivButton,
                ORButton,MulButton,ANDButton,SubButton,XORButton,
                AddButton,CEButton,EqualButton};
        for(int i=0;i<ToolButton.length;i++)
        {
            Font buttonFont=new Font("Times New Romans",Font.BOLD,25);//设置字体
            ToolButton[i].setBorderPainted(false);//取消边框
            ToolButton[i].setBackground(new Color(62, 68, 79));//设置背景颜色
            ToolButton[i].setFocusPainted(false);//取消聚焦
            ToolButton[i].setForeground(new Color(255,255,255));//设置按钮上的字体颜色
            ToolButton[i].setFont(buttonFont);
            int finalI = i;
            ToolButton[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    ToolButton[finalI].setBackground(new Color(32, 38, 49));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    ToolButton[finalI].setBackground(new Color(62, 68, 79));
                }
            });
        }
    }
    public void setDigitalButton()//对数字按钮进行设置
    {
        JButton[] DigitalButton={
                a7Button,a8Button,a9Button,
                a4Button,a5Button,a6Button,
                a1Button,a2Button,a3Button,
                a0Button,dotButton,AddOrSubButton
        };
        Font buttonFont=new Font("Times New Romans",Font.BOLD,25);
        for(int i=0;i<DigitalButton.length;i++) {
            DigitalButton[i].setBorderPainted(false);
            DigitalButton[i].setBackground(new Color(188, 189, 194));
            DigitalButton[i].setFocusPainted(false);
            DigitalButton[i].setForeground(new Color(26, 23, 23));
            DigitalButton[i].setFont(buttonFont);
            int finalI = i;
            DigitalButton[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    DigitalButton[finalI].setBackground(new Color(138, 138, 138));

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    DigitalButton[finalI].setBackground(new Color(188, 189, 194));
                }
            });
        }
    }
    public void setLabel()//对标签进行设置
    {
        Font labelFont=new Font("Times New Romans",Font.BOLD,18);
        Font labelFont1=new Font("Times New Romans",Font.BOLD,20);
        Font BaseFont=new Font("Times New Romans",Font.BOLD,22);
        DECLabel.setFont(BaseFont);//设置字体样式
        DECLabel.setText("DEC");//设置默认的文本
        DECLabel.setForeground(Color.BLACK);//设置字体颜色
        DECLabel.setFont(BaseFont);
        DECLabel.setBorder(BorderFactory.createEmptyBorder());
        HEXLabel.setFont(BaseFont);//设置字体样式
//        HEXLabel.setText("HEX");//设置默认的文本
        HEXLabel.setForeground(Color.BLACK);//设置字体颜色
        HEXLabel.setFont(BaseFont);
        HEXLabel.setBorder(BorderFactory.createEmptyBorder());
        OverFlow.setFont(BaseFont);//设置字体样式
        OverFlow.setText("");//设置默认的文本
        OverFlow.setForeground(Color.BLACK);//设置字体颜色
        OverFlow.setFont(BaseFont);
        DECLabel.setBorder(BorderFactory.createEmptyBorder());
        TEXAS.setFont(labelFont1);
        TI.setFont(labelFont1);
        CM.setFont(labelFont);
        JLabel[] tempLabel={
                B0001,B0010,B0011,B0100,B0101,
                B0110,B0111,B1000,B1001,B1010,
                B1011,B1100,B1101,B1110,B1111,
                a2sC
        };
        for (JLabel jLabel : tempLabel)
        {
            jLabel.setFont(labelFont);
            jLabel.setForeground(Color.BLACK);
        }
    }
    public void setText()//对文本域进行设置
    {
        Font IOputFont=new Font("Times New Romans",Font.BOLD,65);
        IOput.setFont(IOputFont);//设置字体样式
        IOput.setBackground(new Color(0xECECED));//设置背景颜色
//        IOput.setDocument(new JTextFieldLimit(8));//设置JTextField输入文本长度不超过8位
        IOput.setBorder(BorderFactory.createEmptyBorder());//文本框不带边框
        EmptySpacer1.setBorder(BorderFactory.createEmptyBorder());
        EmptySpacer2.setBorder(BorderFactory.createEmptyBorder());
        MidSpacer.setBorder(BorderFactory.createEmptyBorder());
//        IOput.setText("12345678");
        Separator.setForeground(new Color(0xFFFFFF));
    }
    public void setPanel()//对面板进行设置
    {
//        TextPanel1.setBackground(new Color(0xFFFFFF));
//        TextPanel2.setBackground(new Color(0xFFFFFF));
//        ButtonPanel.setBackground(new Color(0xFFFFFF));
    }
//    class JTextFieldLimit extends PlainDocument//该类用来实现对JTextField输入文本长度的限制
//    {
//        private int limit;
//        public JTextFieldLimit(int limit)
//        {
//            super();
//            this.limit=limit;//最大输入长度
//        }
//        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
//        {
//            if(getLength()+str.length()<=limit)
//                super.insertString(offset, str, attr);
//        }
//    }










    private JPanel TextPanel1;
    private JTextField IOput;
    private JLabel TEXAS;
    private JSeparator Separator;
    private JLabel DECLabel;
    private JLabel HEXLabel;
    private JPanel ButtonPanel;
    private JButton DECButton;
    private JButton HEXButton;
    private JButton OCTButton;
    private JButton OFFButton;
    private JButton ONorCLRButton;
    private JButton STOButton;
    private JButton RCLButton;
    private JButton SUMButton;
    private JButton OPButton;
    private JButton CPButton;
    private JButton SHFButton;
    private JButton dButton;
    private JButton EButton;
    private JButton FButton;
    private JButton KButton;
    private JLabel B1010;
    private JLabel B1011;
    private JLabel B1100;
    private JButton a1SCButton;
    private JButton AButton;
    private JButton bButton;
    private JButton CButton;
    private JButton DivButton;
    private JLabel B0111;
    private JLabel B1000;
    private JLabel B1001;
    private JButton ORButton;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton MulButton;
    private JLabel B0100;
    private JLabel B0101;
    private JLabel B0110;
    private JButton ANDButton;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton SubButton;
    private JLabel B0001;
    private JLabel B0010;
    private JLabel B0011;
    private JButton XORButton;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton AddButton;
    private JLabel a2sC;
    private JButton CEButton;
    private JButton a0Button;
    private JButton dotButton;
    private JButton AddOrSubButton;
    private JButton EqualButton;
    private JLabel B1101;
    private JLabel B1110;
    private JLabel B1111;
    private JTextField MidSpacer;
    private JPanel TextPanel2;
    private JLabel TI;
    private JLabel CM;
    private JTextField EmptySpacer1;
    private JTextField EmptySpacer2;
    private JPanel TI_LCD_Programmer;
    private JLabel OverFlow;
    private int symbol = 1;
    private boolean isOperator = false;   //是否是运算符
    private String operator1 = "";            //操作数1
    private String operator2 = "";            //操作数2
    private String tmp = "";                  //用于在ioput中显示
    private BigDecimal first=new BigDecimal(0);
    private BigDecimal second=new BigDecimal(0);
    private BigDecimal answer=new BigDecimal(0);
    private int lastoperator=0;               //上一运算符
    private int nowoperator=0;                //本次运算符
    private boolean isON =false;
    private boolean isONforCLR =false;
    private BigDecimal equaltmp=new BigDecimal(0);
    private int equaloptmp=-1;
    private int OperatingMode=1;           //工作模式 0表示标准 1表示带括号
    private String infix ="";   //中缀表达式
    private String postfix ="";                //后缀表达式
    private String tmpfix="";
    private boolean isDEC;//是否是十进制模式
    private boolean isHEX;//是否十六进制模式
}
