package guess.Model;
// интерфейс сохранения восстановления игры в файл
// интефейс для сохранения в разных форматах файла
public interface FileIO {
    void fileWr(JModel md,String fn);
    void fileRd(JModel md,String fn);
  }
