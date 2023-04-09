package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDateTime;

// ежемесячная задача
public class MonthlyTask extends Task {
    public MonthlyTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // истина, если число (1-31) месяца создания задачи совпадает с числом месяца, введенным пользователем
    @Override
    public boolean isPresentIn(LocalDateTime userDateTime) {
        return (getDateTime().getDayOfMonth() == userDateTime.getDayOfMonth());
    }

    @Override
    public String toString() {
        return super.toString() + "ежемесячная";
    }
}