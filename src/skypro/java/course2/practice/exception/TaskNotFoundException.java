package skypro.java.course2.practice.exception;

// исключение для проверки, что список задач непустой
// проверяемое исключение, поэтому наследует Exception
public class TaskNotFoundException extends Exception{
    // достаточно одного конструктора для вывода сообщения об ошибке
    public TaskNotFoundException(String message) {
        super(message);
    }
}

