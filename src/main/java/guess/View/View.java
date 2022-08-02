package guess.View;

public interface View {
    //
    void startGame();

    // ввод числа
    String guessAttempt();

    // вывод ответа
    void geuessAnswer(String answer);

    // предупреждение о сохранении игры
    void gameSaved(String answer);

}
