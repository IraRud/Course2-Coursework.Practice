package skypro.java.course2.practice.diary;

import skypro.java.course2.practice.diary.tasks.*;
import skypro.java.course2.practice.exception.IncorrectArgumentException;
import skypro.java.course2.practice.exception.TaskNotFoundException;
import skypro.java.course2.practice.validate_utils.ValidateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

abstract public class TaskService {

    private static final Map<Integer, Task> actualTasks = new HashMap<>();

    // добавить задачу
    public static void addTask(Scanner scanner) {
        try {
            System.out.println("Введите название задачи:");
            String title = ValidateUtils.checkString(scanner.nextLine());

            System.out.println("Введите описание задачи:");
            String description = ValidateUtils.checkString(scanner.nextLine());

            System.out.println("Введите тип задачи (личная/рабочая):");
            Type type = ValidateUtils.checkType(scanner.nextLine());

            try {
                System.out.println("Введите дату в формате dd.MM.yyyy HH:mm (например: 18.05.2023 21:23): ");
                // приводим дату к нужному формату
                LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                // создаем и добавляем в акутальный список задачу
                createTask(title, description, type, dateTime, scanner);
                scanner.nextLine();

                // завершение добавления задачи
                System.out.println("Для выхода нажмите Enter. Для добавления еще одной задачи введите \"+\". ");
                String str = scanner.nextLine();
                // проверка, что введен не Enter. В противном случае работа завершена
                if (str.trim().equalsIgnoreCase("+")) {
                    addTask(scanner);
                }
//                else {
//                    scanner.nextLine();
//                }
                // ловим ошибку при вводе даты
            } catch (DateTimeParseException e) {
                System.out.println("ОШИБКА: Неверный формат даты. Задача не добавлена.");

            }
        // ловим ошибку при вводе названия, заголовка, типа задачи
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage() + " Задача не добавлена.");
        }
    }

    // создать задачу
    private static void createTask(String title, String description, Type type, LocalDateTime dateTime, Scanner scanner) throws IncorrectArgumentException {
        try {
            System.out.println("Выберите тип задачи:\n1 - однократная, 2 - ежедневная, 3 - еженедельная, 4 - ежемесячная, 5 - ежегодная");
            switch (scanner.nextInt()) {
                case 1:
                    OneTimeTask oneTimeTask = new OneTimeTask(title, description, type, dateTime);
                    actualTasks.put(oneTimeTask.getId(), oneTimeTask);
                    printCreateIdTask(oneTimeTask);
                    break;
                case 2:
                    DailyTask dailyTask = new DailyTask(title, description, type, dateTime);
                    actualTasks.put(dailyTask.getId(), dailyTask);
                    printCreateIdTask(dailyTask);
                    break;
                case 3:
                    WeeklyTask weeklyTask = new WeeklyTask(title, description, type, dateTime);
                    actualTasks.put(weeklyTask.getId(), weeklyTask);
                    printCreateIdTask(weeklyTask);
                    break;
                case 4:
                    MonthlyTask monthlyTask = new MonthlyTask(title, description, type, dateTime);
                    actualTasks.put(monthlyTask.getId(), monthlyTask);
                    printCreateIdTask(monthlyTask);
                    break;
                case 5:
                    YearlyTask yearlyTask = new YearlyTask(title, description, type, dateTime);
                    actualTasks.put(yearlyTask.getId(), yearlyTask);
                    printCreateIdTask(yearlyTask);
                    break;
                default:
                    // если не выбран ни один вариант
                    System.out.println("ОШИБКА: Некорректное значение.");
                    break;
            }
        // ловим ошибку при вводе названия, заголовка, типа задачи
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage() + " Задача не создана.");
        }
    }

    // вывести при успешно созданной задаче
    private static void printCreateIdTask(Task task) {
        System.out.printf("Задача \"%s\" успешно создана! Id: %x\n", task.getTitle(), task.getId());
    }

    // получить задачи на день (возвращает задачи в виде коллекции)
    public static Collection<Task> getAllByDate(LocalDateTime userDateTime) {
        // создаем стрим
        return actualTasks.values().stream()
                // фильтруем на основе введенной даты
                .filter(task -> task.isPresentIn(userDateTime))
                // выводим в консоль
                .collect(Collectors.toList());
    }

    // обработать полученные задачи на день (работа интерфейса с пользователем)
    public static void getAllByDate(Scanner scanner) {
        try {
            isActualTasksEmpty(actualTasks);
            try {
                System.out.println("На какой день необходимо проверить запланированные задачи?\n" +
                        "Введите дату в формате dd.MM.yyyy HH:mm (например: 18.05.2023 21:23):");
                // приводим дату к нужному формату
                LocalDateTime userDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                // получаем коллекцию из задач на день
                Collection<Task> allTasksByDate = getAllByDate(userDateTime);

                // сообщение пользователю
                if (!allTasksByDate.isEmpty()) {
                    System.out.println("Список задач на " + userDateTime.toLocalDate() + ":");
                    allTasksByDate.stream()
                                    .forEach(System.out::println);
                } else {
                    System.out.println("На " + userDateTime.toLocalDate() + " задач не запланированно.");
                }
                System.out.println("Для выхода нажмите Enter.");
                scanner.nextLine();
            // ловим ошибку при вводе даты
            } catch (DateTimeParseException e) {
                System.out.println("ОШИБКА: Неверный формат даты. Поиск не может быть осуществлен.");
            }
        // ловим ошибку при пустом списке задач
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // проверить, что список задач непустой
    private static Map<Integer, Task> isActualTasksEmpty(Map<Integer, Task> map) throws TaskNotFoundException {
        if (!map.isEmpty()) {
            return map;
        } else {
            throw new TaskNotFoundException("ОШИБКА: Список запланированных задач пуст.");
        }
    }

//    public static void remove

}
