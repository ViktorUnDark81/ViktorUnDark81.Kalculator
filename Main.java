package Java;

import java.util.Scanner;

public class Main {
    static boolean isRun = true;
    static Calculator calculator = new Calculator();

    public static void main(String[] args) throws Exception {
        while (isRun) {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a expression: ");
            String num = in.nextLine();
            System.out.println(calc(num));
        }
    }

    public static String calc(String input) throws Exception{
        return calculator.setExpression(input);
    }
}


class Calculator {
    private String a, b, sign;
    private SystemNum systemNum;
    private ReadInput readInput = new ReadInput();
    private NumericSystem numericSystem = new NumericSystem();
    private Converter converter = new Converter();

    public String setExpression(String s) throws Exception{
        readInput.textSplit(s);
        a = readInput.getNum1();
        b = readInput.getNum2();
        findNumFromRome(a);
        sign = numericSystem.checkSign(readInput.getSign());
        checkSystemNum();
        return expression(systemNum);
    }

    private void checkSystemNum() throws Exception {
        boolean isArab = numericSystem.checkArabNum(a) && numericSystem.checkArabNum(b);
        boolean isRome = numericSystem.checkRomeNum(a) && numericSystem.checkRomeNum(b);
        if (isArab || isRome) {
            if (numericSystem.checkArabNum(a)) {
                systemNum = SystemNum.ARAB;
            } else {
                systemNum = SystemNum.ROME;
            }
        } else {
            throw new Exception("Разные системы счисления");
        }
    }


    private String expression(SystemNum sysNum) throws Exception {
        int a = 0;
        int b = 0;
        int result = 0;
        String sResult = "";
        if (sysNum.equals(SystemNum.ROME)) {
            a = findNumFromRome(this.a);
            b = findNumFromRome(this.b);
        }
        if (sysNum.equals((SystemNum.ARAB))) {
            a = Integer.parseInt(this.a);
            b = Integer.parseInt(this.b);

        }
        checkNumRange(a);
        checkNumRange(b);
        if (sign.equals("+")) {
            result = a + b;
        }
        if (sign.equals("-")) {
            result = a - b;
        }
        if (sign.equals("*")) {
            result = a * b;
        }
        if (sign.equals("/")) {
            result = (a / b);
        }

        if (sysNum.equals(SystemNum.ROME)) {
            if (result == 0) {
                throw new Exception("в римской системе отсутствует ноль");
            }
            if (result < 0) {
                throw new Exception("в римской системе нет отрицательных чисел");
            } else {
                sResult = converter.checkOfNumber(result);
            }
        } else {
            sResult = Integer.toString(result);
        }
        return sResult;
    }


    private void checkNumRange(int num) throws Exception {
        if (num < 1 || num > 10) {
            throw new Exception("Число за диапазоном 1 - 10");
        }
    }

    private int findNumFromRome(String s) {
        int num = 0;
        for (int i = 0; i < 201; i++) {
            if (s.equals(converter.checkOfNumber(i))) {
                num = -i * -1;
            }
        }
        return num;
    }
}

class Converter {

    public String checkOfNumber(int num) {
        int units, dozens, hundreds;
        String res = "";
        if (num >= 100) {
            hundreds = num / 100;
            num -= hundreds * 100;
            res += conToHundreds(hundreds);
        }
        if (num >= 10) {
            dozens = num / 10;
            num -= dozens * 10;
            res += conToDozens(dozens);
        }
        if (num >= 1) {
            units = num;
            res += conToUnits(units);
        }
        return res;
    }


    public static String conToHundreds(int num) {
        String res = "";
        switch (num) {
            case 1:
                return "C";
            case 2:
                return "CC";
            case 3:
                return "CCC";
            case 4:
                return "CD";
            case 5:
                return "D";
            case 6:
                return "DC";
            case 7:
                return "DCC";
            case 8:
                return "DCCC";
            case 9:
                return "CM";
        }
        return res;
    }


    public static String conToDozens(int num) {
        String res = "";
        switch (num) {
            case 1:
                return "X";
            case 2:
                return "XX";
            case 3:
                return "XXX";
            case 4:
                return "XL";
            case 5:
                return "L";
            case 6:
                return "LX";
            case 7:
                return "LXX";
            case 8:
                return "LXXX";
            case 9:
                return "XC";
        }
        return res;
    }


    public static String conToUnits(int num) {
        String res = "";
        switch (num) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
        }
        return res;
    }
}

class NumericSystem {
    private boolean isArab;
    private boolean isRome;
    private char[] romeDigits = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};


    public boolean checkArabNum(String s) {
        boolean isArab = false;
        char[] charArr = s.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (isArabDigit(charArr[i])) {
                isArab = true;
            } else {
                isArab = false;
            }
        }
        return isArab;
    }

    ;


    public boolean checkRomeNum(String s) {
        boolean isRome = false;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (isRomeDigit(chars[i])) {
                isRome = true;
            } else {
                isRome = false;
            }
        }
        return isRome;
    }


    public boolean isRomeDigit(char c) {
        boolean b = false;
        for (int i = 0; i < romeDigits.length; i++) {
            if (c == romeDigits[i]) {
                b = true;
            }
        }
        return b;
    }


    public boolean isArabDigit(char c) {
        return c >= '0' && c <= '9';
    }

    
    public String checkSign(String s) throws Exception {
        char c = s.charAt(0);
        String res = "";
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            res = s;
        } else {
            throw new Exception("Отсутствует знак арифметической операции");
        }
        return res;
    }
}

class ReadInput {
    private String num1, num2, sign;

    public void textSplit(String s) throws Exception {
        String[] elements = s.split("\\s");
        if (elements.length == 3) {
            num1 = elements[0];
            num2 = elements[2];
            sign = elements[1];
        } else {
            throw new Exception();
        }
    }

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public String getSign() {
        return sign;
    }
}

enum SystemNum {
    ROME, ARAB
}

