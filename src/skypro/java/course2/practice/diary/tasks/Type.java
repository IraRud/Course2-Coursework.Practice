package skypro.java.course2.practice.diary.tasks;

// тип задачи (перечисление)
public enum Type {
    PERSONAL("личная"),
    WORK("рабочая");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
