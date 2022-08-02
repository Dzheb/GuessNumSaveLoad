package guess.Presenter;

import java.io.File;
import java.io.IOException;
import guess.Config;
import guess.Model.JModel;
import guess.UI.ConsoleView;

public class Presenter {
    private JModel gameState;
    ConsoleView consView = new ConsoleView();

    public Presenter(JModel gameState) {
        this.gameState = gameState;

    }

    // чтение переметров игры
    public JModel getGameState() {
        return gameState;
    }

    // установка переметров игры
    public void setGameState(JModel gameState) {
        this.gameState = gameState;
    }
    // инициация игры

    public void gameInit() {
        setGameState(new JModel());
        consView.startGame();

    }

    // продолжение игры
    public boolean continueGame() {
        return consView.answerYN();
    }

    // загрузка игры
    public void loadGame(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            JModel jm = new JModel();
            jm.fileRd(jm, filename);
            setGameState(jm);
            jm.readSavedGame();
        } else
            System.out.println("Нет сохранённой игры!");
    }

    // сохранение игры
    public void saveGame(String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        //
        gameState.fileWr(gameState, filename);

    }

    // ход игры - попытка угадать число
    public boolean getAttempt() {
        boolean res = false;
        // если запрос на сохранение/восстановление игры
        var guessnum = consView.guessAttempt();
        if (guessnum.equals("L")) {
            loadGame(Config.pathDB);
            consView.geuessAnswer(gameState.readSavedGame());
        } else if (guessnum.equals("S")) {
            saveGame(Config.pathDB);
            consView.gameSaved(String.format("\nИгра от %s %s сохранена.",
                    gameState.getGameDate(), gameState.getGameTime()));
        } else {
            var gn = Integer.parseInt(guessnum);
            gameState.setCurrRec(gameState.getCurrRec() + 1);
            if (gn == gameState.getSecretNum()) {
                res = true;
                consView.geuessAnswer(String.format("Вы выиграли и угадали число: %d за %d попыток!",
                        gameState.getSecretNum(), gameState.getCurrRec()));
            } else {
                var ga = String.format("\nВаше число %d %s загаданного у Вас осталось %d попыток!", gn,
                        gn > gameState.getSecretNum() ? "больше" : "меньше", Config.numTrials - gameState.getCurrRec());
                consView.geuessAnswer(ga);
                gameState.setCurrGuess(gn);
            }
        }
        return res;
    }

}
