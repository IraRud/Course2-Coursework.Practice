package skypro.java.course2.practice;

import skypro.java.course2.practice.diary.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            scanner.nextLine();
                            TaskService.addTask(scanner);
                            break;
                        case 2:
                            scanner.nextLine();
                            TaskService.getAllByDate(scanner);
                            break;
                        case 3:

                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println(
                "Возможности ежедневника:\n1. Добавить задачу \n2. Получить задачу на указанный день \n3. Удалить задачу \n0. Выход ");
    }
}

