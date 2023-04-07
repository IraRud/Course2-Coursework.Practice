package skypro.java.course2.practice.validate_utils;

import skypro.java.course2.practice.exception.IncorrectArgumentException;
import skypro.java.course2.practice.task.Type;

import static skypro.java.course2.practice.task.Type.*;

// класс для проверки вводимых полей задачи
public class ValidateUtils {

    // проверка строковых значений (возвращаем строку)
    public static String checkString(String str) throws IncorrectArgumentException {
        if (str != null && !str.isBlank() && !str.isEmpty()) {
            // возвращаем строку
            return str;
        } else {
            // выбрасываем ошибку
            throw new IncorrectArgumentException("Некорректное значение текстового поля!");
        }
    }

    // отдельная проверка типа задачи (возвращаем тип задачи)
    public static Type checkType(String str) throws IncorrectArgumentException {
        Type type;
        if (str.trim().equalsIgnoreCase("личная")) {
            return type = PERSONAL;
        } else if (str.trim().equalsIgnoreCase("рабочая")) {
            return type = WORK;
        } else {
            throw new IncorrectArgumentException("Некорректное значение типа задачи!");
        }
    }
}
