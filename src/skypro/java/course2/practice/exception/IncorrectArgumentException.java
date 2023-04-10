package skypro.java.course2.practice.exception;

// исключение для проверки корректности вводимых полей задач
// проверяемое исключение, поэтому наследует Exception
public class IncorrectArgumentException extends Exception {

    // достаточно одного конструктора для вывода сообщения об ошибке
    public IncorrectArgumentException(String message) {
        super(message);
    }

}
