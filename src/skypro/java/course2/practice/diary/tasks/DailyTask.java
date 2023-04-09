package skypro.java.course2.practice.diary.tasks;

import skypro.java.course2.practice.exception.IncorrectArgumentException;

import java.time.LocalDateTime;

// ежедневная задача
public class DailyTask extends Task {
    public DailyTask(String title, String description, Type type, LocalDateTime dateTime)
            throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    // всегда истина, так как ежедневные задачи будут выполнятся в любую дату
    @Override
    public boolean isPresentIn(LocalDateTime userDateTime) {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "ежедневная";
    }
}
