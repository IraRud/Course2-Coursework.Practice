package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

// ежегодная задача
public class YearlyTask extends Task{
    public YearlyTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // истина, если число (1-31) месяца и сам месяц создания задачи совпадают с числом месяца
    // и самим месяцом, введенным пользователем
    @Override
    public boolean isPresentIn(LocalDate userDate) {
        return (getDateTime().getDayOfMonth() == userDate.getDayOfMonth() &&
                getDateTime().getMonth() == userDate.getMonth());
    }

    @Override
    public String toString() {
        return super.toString() + "; повторяемость: ежегодная";
    }
}
