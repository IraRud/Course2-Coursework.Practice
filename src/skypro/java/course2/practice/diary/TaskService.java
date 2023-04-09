package skypro.java.course2.practice.diary;

import skypro.java.course2.practice.diary.tasks.*;
import skypro.java.course2.practice.exception.IncorrectArgumentException;
import skypro.java.course2.practice.exception.TaskNotFoundException;
import skypro.java.course2.practice.validate_utils.ValidateUtils;

import java.time.LocalDate;
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

                // завершение добавления задачи (выор дальнейшего действия)
                System.out.println("\nДля выхода нажмите Enter. Для добавления еще одной задачи введите \"+\". ");
                String str = scanner.nextLine();
                // проверка, что введен "+". В противном случае работа завершена
                if (str.trim().equalsIgnoreCase("+")) {
                    addTask(scanner);
                }

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
    private static Collection<Task> getAllByDate(LocalDate userDate) {
        // создаем стрим
        return actualTasks.values().stream()
                // фильтруем на основе введенной даты
                .filter(task -> task.isPresentIn(userDate))
                // выводим в консоль
                .collect(Collectors.toList());
    }

    // обработать полученные задачи на день (работа с пользователем, вызов в main)
    public static void getAllByDate(Scanner scanner) {
        try {
            // проверка на существование задач в ежеденевнике
            isActualTasksEmpty(actualTasks);
            try {
                System.out.println("На какой день необходимо проверить запланированные задачи?\n" +
                        "Введите дату в формате dd.MM.yyyy (например: 18.05.2023):");
                // приводим дату к нужному формату
                LocalDate userDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                // получаем коллекцию из задач на день
                Collection<Task> allTasksByDate = getAllByDate(userDate);

                // сообщение пользователю
                if (!allTasksByDate.isEmpty()) {
                    System.out.println("Список задач на " + userDate + ":");
                    allTasksByDate.forEach(System.out::println);
                } else {
                    System.out.println("На " + userDate + " задач не запланированно.");
                }
                System.out.println("\nДля выхода нажмите Enter.");
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
            throw new TaskNotFoundException("ОШИБКА: Пока ежедневник пуст.");
        }
    }


    // удалить задачу по id (возвращает удаленную задачу)
    private static Task removeTask(int id) {
        return actualTasks.remove(id);
    }

    // вывести удаленную задачу (работа с пользователем, вызов в main)
    public static void removeTask(Scanner scanner) {
        try {
            // проверка на существование задач в ежеденевнике
            isActualTasksEmpty(actualTasks);
            System.out.println("Введите id задачи, которую необходимо удалить: ");
            int id = isIdExist(scanner.nextInt());

            // удаляем задачу
            Task task = removeTask(id);
            System.out.println("Задача \"" + task.getTitle() + "\" была удалена.");
            scanner.nextLine();

            // завершение удаления задачи (выор дальнейшего действия)
            System.out.println("\nДля выхода нажмите Enter. Для удаления еще одной задачи введите \"-\". ");
            String str = scanner.nextLine();
            // проверка, что введен "-". В противном случае работа завершена
            if (str.trim().equalsIgnoreCase("-")) {
                removeTask(scanner);
            }

        // ловим ошибку при пустом списке задач
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // проверить, что id существует
    private static int isIdExist(int id) throws TaskNotFoundException {
        if (actualTasks.containsKey(id)) {
            return id;
        } else {
            throw new TaskNotFoundException("ОШИБКА: задачи с таким id не существует.");
        }
    }

}
