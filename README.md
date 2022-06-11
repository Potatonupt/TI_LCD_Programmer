#### 特别注意，在其他同学的笔记本电脑上运行我们发现了计算器显示不全的问题，这些问题在我们电脑没有出现过。我们考虑是笔记本电脑分辨率以及缩放的问题。如果发现程序运行计算器的界面超出了屏幕范围，请在桌面->右键->显示设置，将分辨率调成1k，2k或4k标准，缩放调成百分百即可显示全。如果出现显示不全，分辨率尽量避免1.5k，2.5k。



本计算器按照大作业要求以及计算器原版说明书，完成了所有布置的要求。

包括：进制转换，单步基本运算，单步位运算，多步混合进制带括号的混合运算（含基本运算和位运算），溢出处理，以及对于小数点的相关优化。另外，我们新增了一些功能，比如可以移动显示，可以等号连乘。演示视频中可能有功能遗漏，也有一些功能不在大作业要求之内的，我们都写在了报告的代码分析部分，报告的代码分析部分较长，如果看的累可以结合算法分析的流程图来看。

**本项目使用idea，可以直接右键TI_LCD_Programmer这个文件夹，选使用idea打开，运行main函数即可。**

本项目使用git版本管理工具，仓库地址在[Potatonupt/TI_LCD_Programmer (github.com)](https://github.com/Potatonupt/TI_LCD_Programmer)，可以使用git clone命令进行克隆。

本计算器对键盘和计算器按钮进行了关联，键位对应表在同目录下的Excel表格里，**运行后要将输入法切换成英文的**，否则部分键盘按键小概率会失灵，如果切换后还不行，可能是输入法问题，这里建议使用windows自带的英文输入法，避免程序获得不到焦点。如果实在不行，请直接点击按钮使用。

计算器的单步模式支持操作符和进制转换以任意顺序输入。多步模式下一定要**注意**默认的输入都是十进制的，如果要输入十六进制的数一定要按一下hex按钮，他会放一个H进去表明下一个数是十六进制的，如果要输入的是十六进制负数，请先按2‘sC号再按hex。按等号得出的结果默认是十进制的，如果需查看十六进制的结果请按hex按钮，如果继续运算，虽然界面显示的是hex，但是输入的仍然是十进制数，输入十六进制依然需要按hex压一个H进去，界面在下一次按等号的时候会切换回dec显示。

由于时间有限，程序难免不完善，如果发现任何bug或问题需要改进，欢迎向我们提出。

