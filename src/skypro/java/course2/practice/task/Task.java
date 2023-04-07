package skypro.java.course2.practice.task;

import skypro.java.course2.practice.exception.IncorrectArgumentException;
import skypro.java.course2.practice.validate_utils.ValidateUtils;

import java.time.LocalDateTime;
import java.util.Objects;

// родительский класс для задач (абстрактный)
abstract public  class Task {
    private String title;   // заголовок
    private String description;   // описание
    private final Type type;   // тип задачи (личная, рабочая)
    LocalDateTime dateTime;   // время создания задачи
    private final Integer id;     // уникальное значение
    private static Integer idGenerator = 0;    // счетчик для id

    // с учетом возможной ошибки в вводе данных
    public Task(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        this.title = ValidateUtils.checkString(title);
        this.description = ValidateUtils.checkString(description);
        this.type = type;
        this.dateTime = dateTime;
        this.id = ++idGenerator;
    }

    public abstract boolean isPresentIn(LocalDateTime userDateTime);

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // изменение с учетом возможной ошибки в вводе данных
    public void setTitle(String title) throws IncorrectArgumentException {
        this.title = ValidateUtils.checkString(title);
    }

    // изменение с учетом возможной ошибки в вводе данных
    public void setDescription(String description) throws IncorrectArgumentException {
        this.description = ValidateUtils.checkString(description);
    }

    @Override
    public String toString() {
        return " id: " + id + ". " + title + ".\nОписание: " + description + ".\nВремя создания: " + type +
                "; тип задачи: " + dateTime+ ", повторяемость: ";
    }

    // задачи считаются одинаковыми в случае, если совпадают время создания и id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(dateTime, task.dateTime) && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, id);
    }
}
