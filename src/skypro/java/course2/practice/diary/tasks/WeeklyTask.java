package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

// еженедельная задача
public class WeeklyTask extends Task {
    public WeeklyTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // истина, если день недели создания задачи совпадает с днем недели, введенным пользователем
    @Override
    public boolean isPresentIn(LocalDate userDate) {
        return (getDateTime().getDayOfWeek() == userDate.getDayOfWeek());
    }

    @Override
    public String toString() {
        return super.toString() + "; повторяемость: еженедельная";
    }
}
