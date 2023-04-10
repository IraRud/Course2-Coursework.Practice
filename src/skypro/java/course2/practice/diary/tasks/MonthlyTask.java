package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

// ежемесячная задача
public class MonthlyTask extends Task {
    public MonthlyTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // истина, если число (1-31) месяца создания задачи совпадает с числом месяца, введенным пользователем
    @Override
    public boolean isPresentIn(LocalDate userDate) {
        return (getDateTime().getDayOfMonth() == userDate.getDayOfMonth());
    }

    @Override
    public String toString() {
        return super.toString() + "; повторяемость: ежемесячная";
    }
}