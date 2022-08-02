package guess.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import org.json.JSONObject;
import guess.Config;

// модель для реализации сохранения игры в JSON

public class JModel implements FileIO {

    int numRec;
    int currRec;
    int secretNum;
    int currGuess;
    String gameDate;
    String gameTime;

    public JModel() {
        // инициализация загаданного числа, номер попытки, общего количества
        // попыток,даты, времени сохранения
        this.secretNum = ThreadLocalRandom.current().nextInt(Config.leftBound, Config.rightBound + 1);
        this.currRec = 0;
        this.numRec = Config.numTrials;
        this.gameDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.gameTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    }

    // чтение предположения числа
    public int getCurrGuess() {
        return currGuess;
    }

    // запись предположения числа
    public void setCurrGuess(int currGuess) {
        this.currGuess = currGuess;
    }

    // чтение загаданного числа
    public int getSecretNum() {
        return secretNum;
    }

    // запись загаданного числа
    public void setSecretNum(int secretNum) {
        this.secretNum = secretNum;
    }

    // чтение номера попытки
    public int getCurrRec() {
        return currRec;
    }

    // загаданного номера попытки
    public void setCurrRec(int currRec) {
        this.currRec = currRec;
    }

    // установление даты записи игры
    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    // запись даты записи игры
    public String getGameDate() {
        return gameDate;
    }

    // установление записи времени игры
    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    // чтение времени записи игры
    public String getGameTime() {
        return gameTime;
    }

    // формирование строки JSON
    @Override
    public String toString() {
        JSONObject jo = new JSONObject();
        jo.put("date", gameDate);
        jo.put("time", gameTime);
        jo.put("total", numRec);
        jo.put("current", currRec);
        jo.put("guess", currGuess);
        jo.put("secret", secretNum);

        return jo.toString();
    }

    // запись в файл
    @Override
    public void fileWr(JModel mg, String fn) {
        try (FileWriter writer = new FileWriter(fn, false)) {
            writer.append(mg.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // чтение JSON строки сохранённой игры из файла
    @Override
    public void fileRd(JModel md, String fn) {
        try (FileReader fr = new FileReader(fn)) {
            BufferedReader reader = new BufferedReader(fr);
            String currLine = reader.readLine();
            JSONObject jsonModel = new JSONObject(currLine.toString());
            this.numRec = Integer.parseInt(jsonModel.optString("total"));
            this.currRec = Integer.parseInt(jsonModel.optString("current"));
            this.currGuess = Integer.parseInt(jsonModel.optString("guess"));
            this.setSecretNum(Integer.parseInt(jsonModel.optString("secret")));
            this.setGameDate(jsonModel.optString("date"));
            this.setGameTime(jsonModel.optString("time"));
            // this.setCurrAnswer(jsonModel.optString("answer"));

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public String readSavedGame() {
        return String.format("\nИгра от %s %s восстановлена\nВаше число %d %s загаданного у Вас осталось %d попыток!",
                gameDate, gameTime, currGuess,
                currGuess > secretNum ? "больше" : "меньше", Config.numTrials - currRec);

    }
}
