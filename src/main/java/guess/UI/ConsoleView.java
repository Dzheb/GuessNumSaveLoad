package guess.UI;

import java.nio.charset.Charset;
import java.util.Scanner;

import guess.Config;
import guess.View.View;

// консольный ввод вывод
public class ConsoleView implements View {
    Scanner in;
// запрос на продолжение игры


    public ConsoleView() {
        in = new Scanner(System.in, Charset.forName(Config.charSet));

    }

    // сообщение в консоль
    @Override
    public void geuessAnswer(String answer) {
        System.out.println(answer);
    }

    // ввод числа
    @Override
    public String guessAttempt() {
        String ans;
        System.out.printf("Введите число (cохранить/восстановить игру? (S/L): ");
        ans = in.nextLine();
        return ans.toUpperCase();

    }

    // запрос на продолжение игры
    // получение подтверждения или отказа
    public boolean answerYN() {
         System.out.printf("\nПродолжить игру (Y/N)?: ");
        var ans = in.nextLine().toUpperCase();
        if (ans != null && ans.equals("N")) {
             System.out.printf("\nИгра окончена.До свидания!\n");
            return false;
        } else
            return true;
    }

    @Override
    public void startGame() {

        System.out.print("\033[H\033[J");
        System.out.println("Игра 'Угадай число' ");
        System.out.printf("\nУгадайте число от %d до %d за %d попыток\n", Config.leftBound, Config.rightBound,
                Config.numTrials);

    }

    @Override
    public void gameSaved(String answer) {
        System.out.println(answer);
    }

}
