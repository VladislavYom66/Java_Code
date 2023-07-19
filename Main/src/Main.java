import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    private static final Map<String, Integer> roman = new HashMap<>();

    static {
        roman.put("I", 1);
        roman.put("II", 2);
        roman.put("III", 3);
        roman.put("IV", 4);
        roman.put("V", 5);
        roman.put("VI", 6);
        roman.put("VII", 7);
        roman.put("VIII", 8);
        roman.put("IX", 9);
        roman.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: "); // Выводит на экран надпись
        String input = in.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRoman = isRomanNumeral(operand1) && isRomanNumeral(operand2);
        boolean isArabic = isArabicNumeral(operand1) && isArabicNumeral(operand2);

        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Используются разные системы исчисления");
        }

        int num1 = parseOperand(operand1);
        int num2 = parseOperand(operand2);

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль невозможно");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция");
        }

        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат римской цифры должен быть положительным числом");
            }
            return toRomanNumeral(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isValidOperand(String operand) {
        return isRomanNumeral(operand) || isArabicNumeral(operand);
    }

    private static boolean isRomanNumeral(String operand) {
        return roman.containsKey(operand);
    }

    private static boolean isArabicNumeral(String operand) {
        try {
            int num = Integer.parseInt(operand);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int parseOperand(String operand) {
        if (isRomanNumeral(operand)) {
            return roman.get(operand);
        } else {
            return Integer.parseInt(operand);
        }
    }

    private static String toRomanNumeral(int number) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : roman.entrySet()) {
            String romanNumeral = entry.getKey();
            int value = entry.getValue();
            while (number >= value) {
                sb.append(romanNumeral);
                number -= value;
            }
        }
        return sb.toString();
    }
}
