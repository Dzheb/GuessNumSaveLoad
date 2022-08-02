package guess.UI;

import guess.Config;
import guess.Model.JModel;
import guess.Presenter.Presenter;

public class App {
    public static void main(String[] args) {
        JModel jm = new JModel();
        Presenter game = new Presenter(jm);
        do {

            game.gameInit();
            do {
                if (game.getAttempt())
                    break;
            } while (jm.getCurrRec() <= Config.numTrials);

        } while (game.continueGame());

    }
}
