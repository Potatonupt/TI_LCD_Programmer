import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Stack;

//javadoc!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//附上readme
//附上键盘和计算器按钮对应表
//
public class TI_LCD_Programmer extends JFrame
{
    public TI_LCD_Programmer()
    {
        initLayout();
        initKeyboard();
        initButton();
    }

    public void init()
    {
        this.setTitle("TI_LCD_Programmer");
        this.add(TI_LCD_Programmer);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((int) (kit.getScreenSize().getWidth() / 2 - 250), (int) (kit.getScreenSize().getHeight() / 2 - 500));
        this.pack();
        this.setFocusable(true);      //要写在visible前面
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initButton()
    {
        initNumberButton();
        initControlButton();
        initOperatorButton();
    }

    private void initOperatorButton()
    {
        ModeButton.addActionListener(e->{
            if(isON)
            {
                if (OperatingMode == 0)
                {
                    OperatingMode = 1;
                    showHEXrelatedButton();
                    OperationModeLabel.setText("Mixed");
                    OperationLabel.setText("");//混合运算不显示运算符
                    clearall();//切换运算模式后清零
                }
                else if(OperatingMode==1)
                {
                    OperatingMode = 0;
                    SHIFT_HEXtoDEC_DISPLAY();
                    OperationModeLabel.setText("Single");
                    clearall();//切换运算模式后清零
                }
            }
        });
        AddButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("+");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 1;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 1;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("+");
            }
        });

        SubButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("-");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 2;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 2;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("-");
            }
        });

        MulButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("×");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 3;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 3;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("*");
            }
        });

        DivButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("÷");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 4;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 4;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();
            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("/");
            }
        });

        ANDButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("&");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 5;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 5;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("&");
            }
        });

        ORButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("|");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 6;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 6;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("|");
            }
        });

        XORButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("^");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 7;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 7;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("^");
            }
        });

        SHFButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("<<");
                isNotEqualOperator =true;
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 8;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 8;
                    getOperatorNumber();
                    calculate();
                }
                displayAnswer();
                updateAnswer();

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("<");
            }
        });

        EqualButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("=");
                isNotEqualOperator=false;
                getCurrentText();
                calculate();
                displayAnswer();
                updateAnswer();
            }
            else if (OperatingMode == 1)
            {
                DECLabel.setText("DEC");
                HEXLabel.setText("");
                if (!isEqualOperator)
                {
                    isEqualOperator = true;
                    getInfix();
                    translate();
                    calculate_with_Parentheses();
                    displayIOput(result.toString());
                }
                else
                {
                    calculateLastResult();
                    displayIOput(result.toString());
                }

            }
        });

        HEXButton.addActionListener(e -> {
            if (isON)
            {
                //if(isEqualOperator==true)
                if(OperatingMode==0)
                {
                    if (!isHEX)
                    {
                        SHIFT_DECtoHEX_DISPLAY();
                        IOput.setText(radix10to16(IOput.getText()));
                    }
                }
                else if(OperatingMode==1)
                {
                    if(isEqualOperator)
                    {
                        DECLabel.setText("");
                        HEXLabel.setText("HEX");
                        IOput.setText(radix10to16(result.toString()));
                    }
                    else
                    {
                        displayIOput("H");
                    }
                }
            }
        });
        DECButton.addActionListener(e -> {
            if (isON)
            {
                if (OperatingMode == 0)
                {
                    if (!isDEC)
                    {
                        SHIFT_HEXtoDEC_DISPLAY();
                        IOput.setText(radix16to10(IOput.getText()));
                    }
                }
                else if(OperatingMode==1)
                {
                    if(isEqualOperator)
                    {
                        DECLabel.setText("DEC");
                        HEXLabel.setText("");
                        IOput.setText(result.toString());
                    }
                }
            }
        });
        a1SCButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("1'sC");
                if (isOperator)               //如果上一个是运算符 直接切换
                {
                    nowOperator = 0;
                    getOperatorNumber();
                }
                else
                {
                    isOperator = true;
                    nowOperator = 0;
                    getOperatorNumber();
                    calculate();
                }
                answer = answer.negate().subtract(new BigDecimal(1));
                displayAnswer();
                updateAnswer();
            }
//            else if (OperatingMode == 1)
//            {
//                isOperator = true;
//                displayIOput("+");
//            }
        });
        a2SCButton.addActionListener(e -> {
            if (OperatingMode == 0)
            {
                OperationLabel.setText("2'sC");
                if(!isNotEqualOperator)
                {

                    if (isOperator)               //如果上一个是运算符 直接切换
                    {
                        nowOperator = 0;
                        getOperatorNumber();
                    }
                    else
                    {
                        isOperator = true;
                        nowOperator = 0;
                        getOperatorNumber();
                        calculate();
                    }
                    answer = answer.negate();
                    displayAnswer();
                    updateAnswer();
                }
                else
                {
                    nowOperator = 0;
                    getOperatorNumber();
                    second = second.negate();
                    if (isDEC)
                    {
                        IOput.setText(second.toString());
                        tmp = "" + second.toString();
                    }
                    else if (isHEX)
                    {
                        IOput.setText(radix10to16(second.toString()));
                        tmp = "" + radix10to16(second.toString());
                    }
                    isNotEqualOperator = false;
                }

            }
            else if (OperatingMode == 1)
            {
                isOperator = true;
                displayIOput("\u00AD");
            }
        });
    }

    private void initNumberButton()
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
        AButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("A");
                }
            }
            else if(OperatingMode==1)
                displayIOput("A");
        });
        bButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("B");
                }
            }
            else if(OperatingMode==1)
                displayIOput("B");
        });
        CButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("C");
                }
            }
            else if(OperatingMode==1)
                displayIOput("C");
        });
        dButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("D");
                }
            }
            else if(OperatingMode==1)
                displayIOput("D");
        });
        EButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("E");
                }
            }
            else if(OperatingMode==1)
                displayIOput("E");
        });
        FButton.addActionListener(e -> {
            if(OperatingMode==0)
            {
                if (isHEX)
                {
                    isOperator = false;
                    displayIOput("F");
                }
            }
            else if(OperatingMode==1)
                displayIOput("F");
        });
        dotButton.addActionListener(e -> {
            isOperator = false;
            displayIOput(".");
        });
        OPButton.addActionListener(e ->
        {
            if(OperatingMode!=0) {
                isOperator = true;
                displayIOput("(");
            }
        });
        CPButton.addActionListener(e -> {
            if(OperatingMode!=0) {
                isOperator = true;
                displayIOput(")");
            }
        });
    }

    private void initControlButton()
    {
        ONorCLRButton.addActionListener(e -> {
            isON = true;
            if(OperatingMode==0)
                OperationModeLabel.setText("Single");
            else
                OperationModeLabel.setText("Mixed");
            //开机后清零 然后显示0；
            if (!isONforCLR)
            {
                isONforCLR = true;  //转变开机键功能
                isDEC = true;
                IOput.setText("0");
                OverFlow.setText("");
                DECLabel.setText("DEC");
                HEXLabel.setText("");
            }
            else
            {
                clearall();
                IOput.setText("0");
                OperationLabel.setText("");
                OverFlow.setText("");
            }
        });
        OFFButton.addActionListener(e -> {
            isHEX=false;
            isDEC=false;
            isON = false;
            isONforCLR = false;
            //清零 然后关机
            clearall();
            OperationLabel.setText("");
            OperationModeLabel.setText("");
            IOput.setText("");
            DECLabel.setText("");
            HEXLabel.setText("");
            OverFlow.setText("");
            hideHEXrelatedButton();
        });
        CEButton.addActionListener(e -> {
            //clear error 清除一位
            if (!tmp.isEmpty())
            {
                tmp = tmp.substring(0, tmp.length() - 1);
                IOput.setText(tmp);
                if (tmp.isEmpty())
                    IOput.setText("0");
            }
        });
    }

    private void displayAnswer()
    {
        if (isDEC)
            displayIOput(answer.toString());
        if (isHEX)
            displayIOput(radix10to16(answer.toString()));
    }

    private void calculateLastResult()
    {
        if (record_last_operator == 1)
            result = result.add(record_last_number);
        if (record_last_operator == 2)
            result = result.subtract(record_last_number);
        if (record_last_operator == 3)
            result = result.multiply(record_last_number);
        if (record_last_operator == 4)
            result = result.divide(record_last_number, 0, RoundingMode.HALF_UP);
        if (record_last_operator == 5)
        {
            tmp1 = result;
            tmp2 = record_last_number;
            result = new BigDecimal(ANDoperator());
        }
        if (record_last_operator == 6)
        {
            tmp1 = result;
            tmp2 = record_last_number;
            result = new BigDecimal(ORoperator());
        }
        if (record_last_operator == 7)
        {
            tmp1 = result;
            tmp2 = record_last_number;
            result = new BigDecimal(XORoperator());
        }
        if (record_last_operator == 8)
        {

            tmp1 = result;
            tmp2 = record_last_number;
            result = new BigDecimal(SHFoperator());
        }
        if (record_last_operator == 9)
        {
            //这里可能有问题，但是我不想思考了，而且也用不到 测试了一个-1并没有什么问题
            result=result.negate();
        }

    }

    private String radix10to16(String s)
    {
        if (s.length() > 0)
        {
            Integer temp = Integer.decode(s);
            return Integer.toHexString(temp).toUpperCase(Locale.ROOT);
        }
        else
            return "";
    }

    private String radix16to10(String s)
    {
        int DECcacluate = 0;
//        System.out.println(s);
        for (int i = 0; i < s.length(); i++)
        {
            int num = (int) Math.pow(16, (s.length() - 1 - i));
            switch (s.charAt(i))
            {
                case '0':
                    DECcacluate += 0;
                    break;
                case '1':
                    DECcacluate += num;
                    break;
                case '2':
                    DECcacluate += 2 * num;
                    break;
                case '3':
                    DECcacluate += 3 * num;
                    break;
                case '4':
                    DECcacluate += 4 * num;
                    break;
                case '5':
                    DECcacluate += 5 * num;
                    break;
                case '6':
                    DECcacluate += 6 * num;
                    break;
                case '7':
                    DECcacluate += 7 * num;
                    break;
                case '8':
                    DECcacluate += 8 * num;
                    break;
                case '9':
                    DECcacluate += 9 * num;
                    break;
                case 'A':
                    DECcacluate += 10 * num;
                    break;
                case 'B':
                    DECcacluate += 11 * num;
                    break;
                case 'C':
                    DECcacluate += 12 * num;
                    break;
                case 'D':
                    DECcacluate += 13 * num;
                    break;
                case 'E':
                    DECcacluate += 14 * num;
                    break;
                case 'F':
                    DECcacluate += 15 * num;
                    break;
            }
        }
        return DECcacluate + "";
    }

    private void SHIFT_DECtoHEX_DISPLAY()
    {
        isHEX = true;
        isDEC = false;
        DECLabel.setText("");
        HEXLabel.setText("HEX");
        showHEXrelatedButton();
    }

    private void SHIFT_HEXtoDEC_DISPLAY()
    {
        isDEC = true;
        isHEX = false;
        DECLabel.setText("DEC");
        HEXLabel.setText("");
        hideHEXrelatedButton();
    }

    private void showHEXrelatedButton()
    {
        Font buttonFont = new Font("Times New Romans", Font.BOLD, 25);//设置字体
        JButton[] HexButton = {AButton, bButton, CButton, dButton, EButton, FButton};//a1SCButton,a2SCButton,ORButton,ANDButton,XORButton,SHFButton
        for (JButton hexBUtton : HexButton)
        {
            hexBUtton.setBorderPainted(false);//取消边框
            hexBUtton.setBackground(new Color(62, 68, 79));//设置背景颜色
            hexBUtton.setFocusPainted(false);//取消聚焦
            hexBUtton.setForeground(new Color(255, 255, 255));//设置按钮上的字体颜色
            hexBUtton.setFont(buttonFont);
            hexBUtton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    super.mouseEntered(e);
                    hexBUtton.setBackground(new Color(32, 38, 49));
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    super.mouseExited(e);
                    hexBUtton.setBackground(new Color(62, 68, 79));
                }
            });
        }
    }

    private void hideHEXrelatedButton() {
        Font buttonFont = new Font("Times New Romans", Font.BOLD, 25);//设置字体
        JButton[] HexButton = {AButton, bButton, CButton, dButton, EButton, FButton};
        for (JButton hexBUtton : HexButton) {
            hexBUtton.setBorderPainted(false);//取消边框
            hexBUtton.setBackground(new Color(62, 68, 79));//设置背景颜色
            hexBUtton.setFocusPainted(false);//取消聚焦
            hexBUtton.setForeground(new Color(62, 68, 79));//设置按钮上的字体颜色
            hexBUtton.setFont(buttonFont);
            hexBUtton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    super.mouseEntered(e);
                    hexBUtton.setBackground(new Color(62, 68, 79));
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    super.mouseExited(e);
                    hexBUtton.setBackground(new Color(62, 68, 79));
                }
            });
        }
    }

    private void getCurrentText()
    {
        isOperator = true;
        nowOperator = 0;
        getOperatorNumber();
        if (lastOperator != 0)
        {
            equaloptmp = lastOperator;
            equaltmp = second;
        }
        if (lastOperator == 0 && equaloptmp == -1)
        {
            if (answer.compareTo(new BigDecimal(0)) != 0)
                second = answer;
        }
        else if (lastOperator == 0)
        {
            lastOperator = equaloptmp;
            second = equaltmp;
        }
    }

    private void getOperatorNumber()           //获取当前操作数
    {
        operatorNumber = "" + tmp;
        if (!operatorNumber.isEmpty())
        {
            if (isDEC)
                second = new BigDecimal(operatorNumber);
            else if (isHEX)
                second = new BigDecimal(radix16to10(operatorNumber));
        }
        else
            second = new BigDecimal(0);
    }

    private void calculate()
    {
        switch (lastOperator)
        {
            case 1:
                answer = first.add(second);
                break;
            case 2:
                answer = first.subtract(second);
                break;
            case 3:
                answer = first.multiply(second);
                break;
            case 4:
                answer = first.divide(second, 2, RoundingMode.HALF_UP);
                break;
            case 0:
                answer = second;
                break;
            case 5:
                answer = new BigDecimal(ANDoperator());
                break;
            case 6:
                answer = new BigDecimal(ORoperator());
                break;
            case 7:
                answer = new BigDecimal(XORoperator());
                break;
            case 8:
                answer = new BigDecimal(SHFoperator());
                break;
        }
    }

    private void updateAnswer()    //将结果保存到first
    {
        tmp = "";
        first = answer;
        lastOperator = nowOperator;
    }

    private void getInfix()
    {
        tmpfix = tmp;
        for (int i = 0; i < tmpfix.length(); i++)
        {
            if(tmpfix.charAt(i)=='H')
            {
                i++;
                String hextmp = "";
                while (i<tmpfix.length()&&((tmpfix.charAt(i)>='0'&&tmpfix.charAt(i)<='9')||(tmpfix.charAt(i)>='A'&&tmpfix.charAt(i)<='F')))
                {
                    hextmp += tmpfix.charAt(i);
                    i++;
                }
                String inttmp = radix16to10(hextmp);
                for (int j = 0; j < inttmp.length(); j++)
                {
                    infix = infix + inttmp.charAt(j) + "_";
                }
                infix = infix.substring(0, infix.length() - 1);
                continue;
            }
            if (i + 1 < tmpfix.length()
                    && (tmpfix.charAt(i) >= '0' && tmpfix.charAt(i) <= '9')
                    && (tmpfix.charAt(i + 1) >= '0' && tmpfix.charAt(i + 1) <= '9'))
                infix = infix + tmpfix.charAt(i) + "_";
            else
                infix = infix + tmpfix.charAt(i);
        }
    }

    private int ANDoperator()
    {
        if(OperatingMode==0)
        {
            Integer int_first = Integer.parseInt(first.toString());
            Integer int_second = Integer.parseInt(second.toString());
            return int_first & int_second;
        }
        else if(OperatingMode==1)
        {
            Integer int_first = Integer.parseInt(tmp1.toString());
            Integer int_second = Integer.parseInt(tmp2.toString());
            return int_first & int_second;
        }
        return 0;
    }

    private int ORoperator()
    {
        if(OperatingMode==0)
        {
            Integer int_first = Integer.parseInt(first.toString());
            Integer int_second = Integer.parseInt(second.toString());
            return int_first | int_second;
        }
        else if(OperatingMode==1)
        {
            Integer int_first = Integer.parseInt(tmp1.toString());
            Integer int_second = Integer.parseInt(tmp2.toString());
            return int_first | int_second;
        }
        return 0;
    }

    private int XORoperator()
    {
        if(OperatingMode==0)
        {
            Integer int_first = Integer.parseInt(first.toString());
            Integer int_second = Integer.parseInt(second.toString());
            return int_first ^ int_second;
        }
        else if(OperatingMode==1)
        {
            Integer int_first = Integer.parseInt(tmp1.toString());
            Integer int_second = Integer.parseInt(tmp2.toString());
            return int_first ^ int_second;
        }
        return 0;
    }

    private int SHFoperator()
    {
        if(OperatingMode==0)
        {
            Integer int_first = Integer.parseInt(first.toString());
            Integer int_second = Integer.parseInt(second.toString());
            if (int_second.compareTo(0) > 0)
                return int_first << int_second;
            else
                return int_first >> -int_second;
        }
        else if(OperatingMode==1)
        {
            Integer int_first = Integer.parseInt(tmp1.toString());
            Integer int_second = Integer.parseInt(tmp2.toString());
            if (int_second.compareTo(0) > 0)
                return int_first << int_second;
            else
                return int_first >> -int_second;
        }
        return 0;
    }

    private void calculate_with_Parentheses()
    {
        Stack<BigDecimal> st = new Stack<>();
        for (int i = 0; i < postfix.length(); i++)
        {
            if (postfix.charAt(i) >= '0' && postfix.charAt(i) <= '9')
            {
                if (postfix.charAt(i + 1) == '_')
                {
                    tmp_when_calculate += postfix.charAt(i);
                    i++;
                }
                else
                {
                    tmp_when_calculate += postfix.charAt(i);
                    st.push(new BigDecimal(tmp_when_calculate));
                    tmp_when_calculate = "";
                }
            }

            if (postfix.charAt(i) == '+')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = tmp1.add(tmp2);
                st.push(result);


                record_last_operator = 1;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '-')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = tmp1.subtract(tmp2);
                st.push(result);


                record_last_operator = 2;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '*')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = tmp1.multiply(tmp2);
                st.push(result);


                record_last_operator = 3;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '/')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = tmp1.divide(tmp2, 2, RoundingMode.HALF_UP);
                st.push(result);

                record_last_operator = 4;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '&')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = new BigDecimal(ANDoperator());
                st.push(result);

                record_last_operator = 5;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '|')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = new BigDecimal(ORoperator());
                st.push(result);

                record_last_operator = 6;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '^')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = new BigDecimal(XORoperator());
                st.push(result);

                record_last_operator = 7;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '<')
            {
                tmp2 = st.peek();
                st.pop();
                tmp1 = st.peek();
                st.pop();
                result = new BigDecimal(SHFoperator());
                st.push(result);

                record_last_operator = 8;
                record_last_number = tmp2;
            }
            if (postfix.charAt(i) == '\u00AD')
            {
                tmp2 = st.peek();
                st.pop();
                result = tmp2.negate();
                st.push(result);

                record_last_operator = 9;
//                record_last_number = tmp2;
            }
        }

        result = st.peek();
        tmp = "";
    }

    private int priority(char op)
    {
        int priority = -1;
        //1	括号	()
        //2	正负号	        +、-	          从右到左
        //3	一元运算符	    ++、--、!	      从右到左
        //4	乘除	            *、/、%	          从左到右
        //5	加减	            +、-	          从左到右
        //6	移位运算	        >>、>>>、<<	      从左到右
        //7	比较大小	        >、<、>=、<=	      从左到右
        //8	比较是否相等	    ==、!=	          从左到右
        //9	按位与运算	    &	              从左到右
        //10按位异或运算	    ^	              从左到右
        //11按位或运算	    |	              从左到右
        //12逻辑与运算	    &&(简洁逻辑与)、&(非简洁逻辑与)	    从左到右
        //13逻辑或运算	    ||(简洁逻辑或)、|(非简洁逻辑或)	    从左到右
        //14三元运算符	    ? :	              从右到左
        //15赋值运算符	    =	              从右到左
        if (op == '\u00AD')              //这是负号不是减号 用的是输入法的连字符
            priority = 7;
        if (op == '*')
            priority = 6;
        if (op == '/')
            priority = 6;
        if (op == '+')
            priority = 5;
        if (op == '-')
            priority = 5;
        if (op == '<')
            priority = 4;
        if (op == '&')
            priority = 3;
        if (op == '^')
            priority = 2;
        if (op == '|')
            priority = 1;
        if (op == '(')
            priority = 0;

        return priority;
    }

    private void translate()
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
        System.out.println(postfix);
    }

    private boolean isOverflow()//判断运算溢出但并未区分正负上下溢出
    {
        if (IOput.getText().length() > 8)
        {
            OverFlow.setText("Warning:Operation Overflow!");
            return true;
        }
        return false;
    }

    private void displayIOput(String s)
    {
        if (isON)
        {
            if (OperatingMode == 0)
            {
                if (isOperator)
                    tmp = "";
                if (tmp.length() < 8)
                    tmp = tmp + s;
                IOput.setText(tmp);
                if (isOverflow())
                    IOput.setText(tmp.substring(tmp.length() - 8));
            }
            else if (OperatingMode == 1)
            {
                if (isEqualOperator)
                    tmp = "";
                tmp = tmp + s;
                IOput.setText(tmp);
            }
        }
    }

    private void clearall()
    {
        IOput.setText("0");
        isOperator = false;   //是否是运算符
        operatorNumber = "";            //操作数2
        tmp = "";                  //用于在ioput中显示
        first = new BigDecimal(0);
        second = new BigDecimal(0);
        answer = new BigDecimal(0);
        lastOperator = 0;               //上一运算符
        nowOperator = 0;                //本次运算符
        tmpfix = "";
        infix = "";
        postfix = "";
        tmp_when_calculate = "";
        isEqualOperator = false;
        record_last_number = new BigDecimal(0);
        record_last_operator = 0;
        equaloptmp = -1;
        equaltmp = new BigDecimal(0);
        tmp1 = new BigDecimal(0);
        tmp2 = new BigDecimal(0);
        isNotEqualOperator =false;
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

        JButton[] DIGIT = {a0Button, a1Button, a2Button, a3Button, a4Button, a5Button,
                a6Button, a7Button, a8Button, a9Button, AddButton, SubButton,
                MulButton, DivButton, dotButton, EqualButton};//,AddButton,SubButton,MulButton,DivButton,dotButton,EqualButton,CEButton
        String[] pressed1 = {"pressed 0", "pressed 1", "pressed 2",
                "pressed 3", "pressed 4", "pressed 5",
                "pressed 6", "pressed 7", "pressed 8",
                "pressed 9", "shift EQUALS", "pressed MINUS",
                "shift 8", "pressed SLASH", "PERIOD", "pressed EQUALS"
        };
        String[] pressed2 = {"pressed NUMPAD0",
                "pressed NUMPAD1", "pressed NUMPAD2", "pressed NUMPAD3", "pressed NUMPAD4",
                "pressed NUMPAD5", "pressed NUMPAD6", "pressed NUMPAD7", "pressed NUMPAD8",
                "pressed NUMPAD9", "pressed ADD", "pressed SUBTRACT", "pressed MULTIPLY",
                "pressed DIVIDE", "pressed DECIMAL", "pressed ENTER"
        };
        for (int i = 0; i < DIGIT.length; i++)
        {
            int finalI = i;
            DIGIT[finalI].registerKeyboardAction(e -> DIGIT[finalI].doClick(), KeyStroke.getKeyStroke(pressed1[i]), JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
        for (int i = 0; i < DIGIT.length; i++)
        {
            int finalI = i;
            DIGIT[finalI].registerKeyboardAction(e -> DIGIT[finalI].doClick(), KeyStroke.getKeyStroke(pressed2[i]), JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
        FButton.registerKeyboardAction(e -> FButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        EButton.registerKeyboardAction(e -> EButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        dButton.registerKeyboardAction(e -> dButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        CButton.registerKeyboardAction(e -> CButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        bButton.registerKeyboardAction(e -> bButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        AButton.registerKeyboardAction(e -> AButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        EqualButton.registerKeyboardAction(e -> EqualButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        CEButton.registerKeyboardAction(e -> CEButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        ONorCLRButton.registerKeyboardAction(e -> ONorCLRButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        ONorCLRButton.registerKeyboardAction(e -> ONorCLRButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.SHIFT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        OFFButton.registerKeyboardAction(e -> OFFButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        DECButton.registerKeyboardAction(e -> DECButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        HEXButton.registerKeyboardAction(e -> HEXButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.SHIFT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        OPButton.registerKeyboardAction(e -> OPButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_9, InputEvent.SHIFT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        OPButton.registerKeyboardAction(e -> CPButton.doClick(), KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.SHIFT_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
        ModeButton.registerKeyboardAction(e->ModeButton.doClick(),KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.SHIFT_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void setToolButton()//非数字按钮的设置
    {
        Font buttonFont = new Font("Times New Romans", Font.BOLD, 25);//设置字体
        JButton[] ToolButton = {
                DECButton, HEXButton, OCTButton, OFFButton, ONorCLRButton,
                STOButton, RCLButton, SUMButton, OPButton, CPButton, KButton, DivButton,  MulButton,  SubButton,
                AddButton, CEButton, EqualButton,ModeButton,a1SCButton,a2SCButton,ORButton,ANDButton,XORButton,SHFButton};
        for (int i = 0; i < ToolButton.length; i++)
        {
            //Font buttonFont = new Font("Times New Romans", Font.BOLD, 25);//设置字体
            ToolButton[i].setBorderPainted(false);//取消边框
            ToolButton[i].setBackground(new Color(62, 68, 79));//设置背景颜色
            ToolButton[i].setFocusPainted(false);//取消聚焦
            ToolButton[i].setForeground(new Color(255, 255, 255));//设置按钮上的字体颜色
            ToolButton[i].setFont(buttonFont);
            int finalI = i;
            ToolButton[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    super.mouseEntered(e);
                    ToolButton[finalI].setBackground(new Color(32, 38, 49));
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    super.mouseExited(e);
                    ToolButton[finalI].setBackground(new Color(62, 68, 79));
                }
            });
        }
        hideHEXrelatedButton();
    }

    public void setDigitalButton()//对数字按钮进行设置
    {
        JButton[] DigitalButton = {
                a7Button, a8Button, a9Button,
                a4Button, a5Button, a6Button,
                a1Button, a2Button, a3Button,
                a0Button, dotButton
        };
        Font buttonFont = new Font("Times New Romans", Font.BOLD, 25);
        for (int i = 0; i < DigitalButton.length; i++)
        {
            DigitalButton[i].setBorderPainted(false);
            DigitalButton[i].setBackground(new Color(188, 189, 194));
            DigitalButton[i].setFocusPainted(false);
            DigitalButton[i].setForeground(new Color(26, 23, 23));
            DigitalButton[i].setFont(buttonFont);
            int finalI = i;
            DigitalButton[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    super.mouseEntered(e);
                    DigitalButton[finalI].setBackground(new Color(138, 138, 138));

                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    super.mouseExited(e);
                    DigitalButton[finalI].setBackground(new Color(188, 189, 194));
                }
            });
        }
    }

    public void setLabel()//对标签进行设置
    {
        Font putFont = new Font("Times New Romans", Font.BOLD, 65);
        Font labelFont = new Font("Times New Romans", Font.BOLD, 18);
        Font labelFont1 = new Font("Times New Romans", Font.BOLD, 20);
        Font BaseFont = new Font("Times New Romans", Font.BOLD, 22);
        JLabel[] TextLabel={DECLabel,HEXLabel,OverFlow, OperationModeLabel};
        for(JLabel label:TextLabel)
        {
            label.setFont(BaseFont);
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createEmptyBorder());
        }
//        if(isON)
//        {
//            switch (lastoperator)
//            OperationLabel.setText();
//        }
        OperationLabel.setFont(putFont);
        OperationLabel.setForeground(Color.BLACK);
        OperationLabel.setBorder(BorderFactory.createEmptyBorder());
//        if(OperatingMode==0)
//            OperationModeLabel.setText("Singled");
//        else
//            OperationModeLabel.setText("Mixed");
//        DECLabel.setFont(BaseFont);//设置字体样式
////        DECLabel.setText("DEC");//设置默认的文本
//        DECLabel.setForeground(Color.BLACK);//设置字体颜色
//        DECLabel.setBorder(BorderFactory.createEmptyBorder());
//        HEXLabel.setFont(BaseFont);//设置字体样式
////        HEXLabel.setText("HEX");//设置默认的文本
//        HEXLabel.setForeground(Color.BLACK);//设置字体颜色
//        HEXLabel.setBorder(BorderFactory.createEmptyBorder());
//        OverFlow.setFont(BaseFont);//设置字体样式
////        OverFlow.setText("");//设置默认的文本
//        OverFlow.setForeground(Color.BLACK);//设置字体颜色
//        OverFlow.setBorder(BorderFactory.createEmptyBorder());

        TEXAS.setFont(labelFont1);
        TI.setFont(labelFont1);
        CM.setFont(labelFont);
        JLabel[] tempLabel = {
                B0001, B0010, B0011, B0100, B0101,
                B0110, B0111, B1000, B1001, B1010,
                B1011, B1100, B1101, B1110, B1111,
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
        Font IOputFont = new Font("Times New Romans", Font.BOLD, 65);
        IOput.setFont(IOputFont);//设置字体样式
        IOput.setBackground(new Color(0xECECED));//设置背景颜色
        IOput.setForeground(Color.BLACK);
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
    private JButton a2SCButton;
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
    private JLabel OperationLabel;
    private JLabel OperationModeLabel;
    private JButton ModeButton;
    private boolean isOperator = false;   //是否是运算符
    private String operatorNumber = "";            //操作数2
    private String tmp = "";                  //用于在ioput中显示
    private BigDecimal first = new BigDecimal(0);
    private BigDecimal second = new BigDecimal(0);
    private BigDecimal answer = new BigDecimal(0);
    private int lastOperator = 0;               //上一运算符
    private int nowOperator = 0;                //本次运算符
    private static boolean isON = false;
    private static boolean isONforCLR = false;
    private BigDecimal equaltmp = new BigDecimal(0);
    private int equaloptmp = -1;
    private int OperatingMode = 0;           //工作模式 0表示标准 1表示带括号
    private String infix = "";   //中缀表达式
    private String postfix = "";                //后缀表达式
    private String tmpfix = "";
    private String tmp_when_calculate = "";
    private boolean isDEC;//是否是十进制模式
    private boolean isHEX;//是否十六进制模式
    private BigDecimal result = new BigDecimal(0);
    private int record_last_operator = 0;
    private BigDecimal record_last_number = new BigDecimal(0);
    private boolean isEqualOperator = false;
    private BigDecimal tmp1 = new BigDecimal(0);
    private BigDecimal tmp2 = new BigDecimal(0);
    private boolean isNotEqualOperator =false;
}
