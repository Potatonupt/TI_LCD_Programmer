## **二．软件实现和代码编写**

代码分为图形界面和功能实现两部分。我们先看图形界面部分。

#### **图形界面部分：**

 

 

接着来看功能实现部分（这部分比较复杂，因为程序是一点点添加完成的，起初只实现比较简单的功能，然后不断添加，越来越复杂，如果过程看不懂，请结合算法分析部分的的流程图理解。）

#### **功能实现部分：**

本次大作业的基本要求只需实现10进制和16进制的基础算术运算，即不含括号的单步加减乘除运算。我们在做的时候，首先实现了单步加法运算，能实现加法，就能实现其他运算。

基本思路是：首先获取用户输入的操作数，接着进行运算，然后判断结果是否溢出并做对应的处理，最后显示答案。这里要**注意**，例如1+2=3。1是第一个操作数，+是操作符，2是第二个操作数，3是最后的答案。如果要实现连续运算，就必须把答案也就是answer赋给第一个操作数，这也就是最后的updateAnswer函数的作用。

核心函数如下：这五个函数分别是获取操作数，计算，处理溢出，显示答案和更新答案。

```java
getOperatorNumber();
calculate();
judgeOverflow();
displayAnswer();
updateAnswer();
```

首先来获取用户输入，在图形界面部分，我们每输入一个数字或者符号，都会通过displayIOput函数存放到tmp里。（这里只展示了该函数的部分功能）

```java
private void displayIOput(String s)
{
    if (isOperator)
        tmp = "";
    if (s.charAt(0) == '-')//负数情况
    {
        if (istmpdotted)
        {
            if (tmp.length() < 10)
                tmp = tmp + s;
        }
        else
        {
            if (tmp.length() < 9)
                tmp = tmp + s;
        }
        if (tmp.charAt(1) == '0' && tmp.length() > 2)//首位为0进行忽略
            tmp = "-" + tmp.substring(2);
        IOput.setText(tmp);
    }

}
```

字符串变量tmp保存着用户输入的字符串，我们要运算需要先将其转换成数字。这里要说明一下，本程序用来保存数据的数据类型是大数类型，这样可以精准计算结果并且大数类型的范围较大，便于对溢出进行处理。

```java
private BigDecimal first = new BigDecimal(0);
private BigDecimal second = new BigDecimal(0);
private BigDecimal answer = new BigDecimal(0);
```

其中，first是第一个操作数，second是第二个操作数，answer用来保存答案。

下面，我们将获取到的操作数赋给second，这里会进行判断，为了便于计算，我们的操作数永远储存十进制的值，所以当计算器的输入是十六进制的时候，会先调用进制转换函数再赋值。

```java
private void getOperatorNumber()           //获取当前操作数
{
    if(tmp.equals("."))
        operatorNumber = "0" + tmp;
    else
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
```

这里**注意**我们获取后赋值的永远是第二个操作数，因为第一个操作数要留给上一次运算的答案。

现在，我们有了操作数，就可以开始运算了。我们给+号运算符设定运算类型为1，接着送给calculate函数进行计算，同理我们规定，减，乘，除，与，或，异或，移位分别对应2，3，4，5，6，7，8。等号对应0。calculate函数是运算中心，如下：

```java
private void calculate()       //计算
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
            if(second.compareTo(BigDecimal.valueOf(0))==0)
            {
                OverFlow.setText("WARNING:CANNOT DIVIDE 0");
                break;
            }
            else {
                OverFlow.setText("");
                answer = first.divide(second, 8, RoundingMode.HALF_UP);
            }
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
```

只有除法与众不同，因为除数不能为0，所以会多一步判断。

另外，**注意**到calculate的switch选择的是lastOperator，也就是上一个运算符。记录本次和上次运算符的变量如下：

```java
private int lastOperator = 0;               //上一运算符
private int nowOperator = 0;                //本次运算符
```

也就是说，我们按下操作符之后显示的答案应该是上一个运算符计算的结果。比如，1+2=，按下等号的时候应该计算+号。不仅是等号，1+2+3，按下第二个+号的时候才运算第一个加+。所以实际上calculate计算的永远是上一个运算符的结果。那么更新答案时不仅要更新first，还要更新lastOperator：

```java
private void updateAnswer()    //将结果保存到first，以便下一次运算
{
    istmpdotted=false;
    if(!isHEX) {
        dotButton.setEnabled(true);
        showdotButton();
    }
    if (tmp.length() != 0 && tmp.charAt(0) == '.')
        tmp = "0" + "";
    else
        tmp = "";
    first = answer;
    lastOperator = nowOperator;
}
```

普通运算符搞定了，还有一个特殊的符号就是等号。等号的核心函数与其他操作符有些不同

```
getCurrentText();
calculate();
judgeOverflow();
displayAnswer();
updateAnswer();
```

其中getCurrentText函数不仅调用getOperator函数获取了操作符，还会记录上一次运算的运算符和运算数以实现等号连按功能。例如，1+2按了等号之后应该结果为3，但如果什么都不输入继续按等号，计算器应执行最后一次运算的过程也就是+2，所以答案应为5，依次类推，7,9,11……。

```java
private void getCurrentText()    //记录上一次运算以便连按等号重复运算
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
```

其中，equaltmp用来记录最后一次运算的操作数。equaloptmp用来保存最后一次运算的操作符。函数的第一个if用来判断上一个符号是否是等号，如果不是的话就记录上一次的符号和操作数，第二个if用来判断，如果上一个符号是等号，且没有记录过操作符，那么直接显示数据本身（因为输入1=的时候没有上一个运算符，所以应该直接显示数据本身）。如果有记录的符号，则连按等号后执行最后一次记录的运算。

综上，现在实现了基本的单步运算，并且实现了等号可以连按的功能。这时候还要考虑到如果我先按了减号发现按错了，应该按的是加号，那么这时候不应该进行运算而应该直接切换运算符。于是，完成的操作符按钮监听事件如下，以加号为例，添加了一个if判断，判断应该是直接切换运算符还是进行计算。其余的运算符同理。

```java
AddButton.addActionListener(e -> {
    if (isON)
    {
        if(OverFlow.getText().equals("WARNING:CANNOT DIVIDE 0"))
            OverFlow.setText("");
        onDotandBit();
        if (OperatingMode == 0)
        {
            OperationLabel.setText("+");
            isNotEqualOperator = true;
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
            judgeOverflow();
            displayAnswer();
            updateAnswer();

        }
    }
});
```

等号的监听事件如下：

```java
EqualButton.addActionListener(e -> {
    if (isON)
    {
        if (OperatingMode == 0)
        {

            OperationLabel.setText("=");
            isNotEqualOperator = false;
            getCurrentText();
            calculate();
            judgeOverflow();
            displayAnswer();
            updateAnswer();
        }
    }
});
```

这里再补充一下calculate对and，or，xor，shf的计算，对加减乘除可以直接用大数类型的方法，而对位运算，我们自己写了四个函数，本质是利用java的位运算符直接运算再转换回大数类。**注意**本计算器没有右移，所以只能左移负数来表示右移，所以shf函数里要进行判断，如果是负数进行的是右移运算。

```java
private int ANDoperator()
{
    if (OperatingMode == 0)
    {
        Integer int_first = Integer.parseInt(first.toString());
        Integer int_second = Integer.parseInt(second.toString());
        return int_first & int_second;
    }
    return 0;
}

private int ORoperator()
{
    if (OperatingMode == 0)
    {
        Integer int_first = Integer.parseInt(first.toString());
        Integer int_second = Integer.parseInt(second.toString());
        return int_first | int_second;
    }
    return 0;
}

private int XORoperator()
{
    if (OperatingMode == 0)
    {
        Integer int_first = Integer.parseInt(first.toString());
        Integer int_second = Integer.parseInt(second.toString());
        return int_first ^ int_second;
    }
    return 0;
}

private int SHFoperator()
{
    if (OperatingMode == 0)
    {
        Integer int_first = Integer.parseInt(first.toString());
        Integer int_second = Integer.parseInt(second.toString());
        if (int_second.compareTo(0) > 0)
            return int_first << int_second;
        else
            return int_first >> -int_second;
    }
    return 0;
}
```

实现了单步运算之后我们来实现进制转换功能，以十进制转十六进制为例，十六转十同理，在按下进制按钮后，界面会显示为hex，输出会调用我们写的进制转换函数将结果转换为十六进制。

这是hex按钮的监听事件函数：（dec按钮同理）

```java
HEXButton.addActionListener(e -> {
    if (isON)
    {
        //if(isEqualOperator==true)
        dotButton.setEnabled(false);
        showBitoperationButton();
        if (OperatingMode == 0)
        {
            if (!isHEX)
            {
                SHIFT_DECtoHEX_DISPLAY();
                IOput.setText(radix10to16(IOput.getText()));
            }
        }
    }
});
```

我们写了如下两个进制转换函数，其中，10转16的函数是调用java自带的方法，而这里的16转10的函数并没有调用系统有的，是我们自己手写的，根据位数分别乘16的多少次方。**原因是**如果用系统有的Integer或者Long里面的方法，很难控制范围，long太长，int又太短，在求补和进制转换的时候会出现问题，超过int的范围但是没有超过计算器最大能计算的范围。不过10转16就没有问题，因为计算器的十进制范围和int是一样的，反之则不行。

```java
private String radix10to16(String s)
{
    for (int i = 0; i < s.length(); i++)//转16进制去除小数点
    {
        if (s.charAt(i) == '.')
        {
            s = s.substring(0, i);
            break;
        }
    }
    if (s.length() > 0)
    {
        if(s.equals("-"))
            s="0";
        Integer temp = Integer.decode(s);
        if(!isOperator&&OperatingMode==0)
            tmp=Integer.toHexString(temp).toUpperCase(Locale.ROOT);
        return Integer.toHexString(temp).toUpperCase(Locale.ROOT);

    }
    else
        return "";
}
```

```java
private String radix16to10(String s) {
    int DECcalculate = 0;
    for (int i = 0; i < s.length(); i++) {
        int num = (int) Math.pow(16, (s.length() - 1 - i));
        switch (s.charAt(i)) {
            case '0':
                DECcalculate += 0;
                break;
            case '1':
                DECcalculate += num;
                break;
            case '2':
                DECcalculate += 2 * num;
                break;
            case '3':
                DECcalculate += 3 * num;
                break;
            case '4':
                DECcalculate += 4 * num;
                break;
            case '5':
                DECcalculate += 5 * num;
                break;
            case '6':
                DECcalculate += 6 * num;
                break;
            case '7':
                DECcalculate += 7 * num;
                break;
            case '8':
                DECcalculate += 8 * num;
                break;
            case '9':
                DECcalculate += 9 * num;
                break;
            case 'A':
                DECcalculate += 10 * num;
                break;
            case 'B':
                DECcalculate += 11 * num;
                break;
            case 'C':
                DECcalculate += 12 * num;
                break;
            case 'D':
                DECcalculate += 13 * num;
                break;
            case 'E':
                DECcalculate += 14 * num;
                break;
            case 'F':
                DECcalculate += 15 * num;
                break;
        }
    }
    if (!isOperator && OperatingMode == 0 && !isOver8bits&&!ONLYjudge)//先+后转进制条件下不可用
        tmp = "" + DECcalculate;
    return DECcalculate + "";
}
```

这样，单步运算和进制转换功能都已经实现了，下面实现多步混合运算，为了方便区分，我们设置了OperateMode来表示是哪个模式，0表示单步，1表示多步混合。

在多步混合模式下，不需要每个操作符运算了，按完等号后会一次性运算出结果。所以不管是符号还是数据按钮，现在都变成向tmp送字符，还是以加号为例，这是加号的最终版，只需要看模式为1的那部分：

```java
AddButton.addActionListener(e -> {
    if (isON)
    {
        if(OverFlow.getText().equals("WARNING:CANNOT DIVIDE 0"))
            OverFlow.setText("");
        onDotandBit();
        if (OperatingMode == 0)
        {
            ……
        }
        else if (OperatingMode == 1)
        {
            isOperator = true;
            displayIOput("+");
        }
    }
});
```

而等号就有了比较大的变化：

```java
EqualButton.addActionListener(e -> {
    if (isON)
    {
        if (OperatingMode == 0)
        {
			……
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
    }
});
```

在混合模式下，默认是十进制输入，按下等号后从tmp获取中缀表达式infix，将其转换为后缀表达式postfix，接着利用栈对后缀表达式进行计算，最后显示结果，同理，ifelse用来判断是否是连按的等号，如果等号连按，依然能重复执行最后一次的运算。如果是十六进制输入，hex按钮会向infix弹一个H用来标志这个数是十六进制，这样就完成了混合进制的带括号的运算。

获取中缀表达式的过程如下：

```java
private void getInfix()
{
    tmpfix = tmp;
    for (int i = 0; i < tmpfix.length(); i++)
    {
        if (tmpfix.charAt(i) == 'H')
        {
            i++;
            String hextmp = "";
            while (i < tmpfix.length() && ((tmpfix.charAt(i) >= '0' && tmpfix.charAt(i) <= '9') 
                                           || (tmpfix.charAt(i) >= 'A' && tmpfix.charAt(i) <= 'F')))
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
            i--;
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
```

获取很简单，赋值即可，但是这里执行了一个操作，**考虑到**如果数字大于两位数，比如15，在转后缀表达式时由于是以字符形式入栈，程序只能认为是1和5两个字符，所以我们增加了一个for循环用来在同一个数字的不同位之间加下划线以表示这是属于同一个数，比如15，获取之后就是1_5。这样就可以实现多位数的计算。

中缀表达式转后缀表达式用到了我们数据结构课所学的算法，利用栈的结构特性实现。首先根据运算优先级排好次序：

<img src="C:\Users\17419\Desktop\images\media\clip_image001.png" alt="img"  />

注意第一行这里是负号，与减号区分，用的是连字符符号实现

```java
private int priority(char op)
{
    int priority = -1;
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
```

接着是最核心的转换函数translate：

```java
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
```

中缀表达式转后缀表达式的规则是：从左到右遍历中缀表达式的每个数字和符号，若是数字就输出，即成为后缀表达式的一部分；若是符号，则判断其与栈顶符号的优先级，是右括号或优先级不高于栈顶符号（乘除优先加减）则栈顶元素依次出栈并输出，并将当前符号进栈，一直到最终输出后缀表达式为止。详细的可以看算法分析部分。

最后是对后缀表达式进行运算，[后缀表达式](https://so.csdn.net/so/search?q=后缀表达式&spm=1001.2101.3001.7020)的求值规则为：从左到右扫描后缀表达式，如果遇到操作数，将其压入栈中，如果遇到操作符，则从栈中弹出两个操作数，计算结果，然后把结果入栈，直到遍历完后缀表达式，则计算完成，此时的栈顶元素即为计算结果。

```java
private void calculate_with_Parentheses()
{
    Stack<BigDecimal> st = new Stack<>();
    for (int i = 0; i < postfix.length(); i++)
    {
        if (postfix.charAt(i) >= '0' && postfix.charAt(i) <= '9')
        {
            tmp_when_calculate += postfix.charAt(i);
            if (postfix.charAt(i + 1) == '_')
            {
                i++;
            }
            else
            {
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
        }
    }
    result = st.peek();
    tmp = "";
}
```

另外，还有一个函数是用来记录和运算最后一次运算以实现等号连按功能的。

```java
private void calculateLastResult()       //这个是多步运算中用来计算最后一次操作的函数，以实现连按等号重复最后一次运算
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
        result = result.negate();
    }

}
```
