package jdk.seminars;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nПроверка парадокса Монти-Холла на примере трех дверей, за одной из которых автомобиль\n" +
                "за двумя другими - коза.");
        System.out.println("\n1-й вариант: Игрок случайно выбирает одну из дверей и не меняет свое решение\n" +
                "и всегда остается при своём первом выборе.");
        System.out.println("\n2-й вариант: Игрок случайно выбирает одну из дверей далее ведущий открывает\n" +
                "дверь, за которой оказывается коза и предлагает игроку сменить выбор, на что игрок соглашается.");
        System.out.println("\nКакова вероятность игроку выбрать дверь за которой находится автомобиль,\n" +
                "по 1 и 2 варианту, при определенном количестве повторений?");
        Random rnd = new Random();
        Map<Integer, Boolean> result = new HashMap<>(); //HashMap с результами каждой итерации
        int maxSteps = 1000; // число итераций


        // реализация первого варианта
        double counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] doors = getDoors(); // меняем двери местами
            int selectedDoors = rnd.nextInt(3); // игрок выбирает одну из дверей
            if (doors[selectedDoors]) counterWin++; //проверяем выиграл ли он и сохраняем результат
            result.put(i, doors[selectedDoors]);
        }
        System.out.printf("\nРезультат при 1-ом варианте при %d повторений: %.3f%%\n",maxSteps, (counterWin / maxSteps * 100));

        // реализация второго врианта
        counterWin = 0;
        for (int i = 0; i < maxSteps; i++) {
            boolean[] doors = getDoors();// меняем двери местами
            int selectedDoor = rnd.nextInt(3);//игрок выбирает одну из дверей
            selectedDoor = getSecondBox(selectedDoor, doors); //игрок меняет выбранную дверь, по предложению ведущего
            if (doors[selectedDoor]) counterWin++;
            result.put(i+maxSteps, doors[selectedDoor]);//проверяем выиграл ли он и сохраняем результат
        }
        System.out.printf("\nРезультат по 2-му варианту при %d повторений: %.3f%%\n",maxSteps, (counterWin / maxSteps * 100));

        System.out.println("\nПроверка парадокса Монти-Холла - завершена. ");
    }

    /**
     * Возвращает индекс двери, которую предлагает открыть ведущий
     * @param userSelectedDoor
     * @param doors
     * @return
     */
    private static int getSecondBox(int userSelectedDoor, boolean[] doors) {
        Random rnd = new Random();
        int selectSecondDoor;
        if (doors[userSelectedDoor]){
            // начальный выбор игрока был правильный
            while (true){
                //предлагаем открыть любую из оставшихся дверей
                selectSecondDoor = rnd.nextInt(3);
                if(selectSecondDoor!=userSelectedDoor) return selectSecondDoor;
            }
        }else{
            //если начальный выбор был проигрышный,
            // то предлагаем дверь за которой автомобиль
            while (true){
                for (int i = 0; i < 3; i++) {
                    if(doors[i]) return i;
                }
            }
        }
    }

    /**
     * Возвращает массив дверей, за одной из которых автомобиль
     * @return
     */
    private static boolean[] getDoors() {

        Random rnd = new Random();
        boolean[] doors = new boolean[]{false, false, false};
        doors[rnd.nextInt(3)] = true;
        return doors;
    }
}