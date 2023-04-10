package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

// однократная задача
public class OneTimeTask extends Task {
    public OneTimeTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // истина, если дата создания задачи совпадает с датой, введенной пользователем
    @Override
    public boolean isPresentIn(LocalDate userDate) {
        return (getDateTime().toLocalDate().equals(userDate));
    }

    @Override
    public String toString() {
        return super.toString() + "; повторяемость: однократная";
    }
}
