import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final StringBuilder logBuilder = new StringBuilder();

    public static void main(String[] args) {
        File src = new File("C:\\Users\\Alfy\\My projects\\Games\\src");
        File res = new File("C:\\Users\\Alfy\\My projects\\Games\\res");
        File saveGames = new File("C:\\Users\\Alfy\\My projects\\Games\\saveGames");
        File temp = new File("C:\\Users\\Alfy\\My projects\\Games\\temp");
        File main = new File("C:\\Users\\Alfy\\My projects\\Games\\src\\main");
        File test = new File("C:\\Users\\Alfy\\My projects\\Games\\src\\test");
        File drawables = new File("C:\\Users\\Alfy\\My projects\\Games\\res\\drawables");
        File vectors = new File("C:\\Users\\Alfy\\My projects\\Games\\res\\vectors");
        File icons = new File("C:\\Users\\Alfy\\My projects\\Games\\res\\icons");

        List<File> directories = List.of(src, res, saveGames, temp, main, test, drawables, vectors, icons);
        directories.forEach(Main::createDirectory);

        File mainFile = new File(main + "\\Main.java");
        File utilsFile = new File(main + "\\Utils.java");
        File tempFile = new File(temp + "\\temp.txt");

        List<File> files = List.of(mainFile, utilsFile, tempFile);
        files.forEach(Main::createFile);

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(logBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameProgress gameProgress1 = new GameProgress(60, 13, 55, 12.3);
        GameProgress.saveGame(saveGames + "\\save1.dat", gameProgress1);
        GameProgress gameProgress2 = new GameProgress(99, 19, 67, 10.7);
        GameProgress.saveGame(saveGames + "\\save2.dat", gameProgress2);
        GameProgress gameProgress3 = new GameProgress(77, 23, 77, 19.5);
        GameProgress.saveGame(saveGames + "\\save3.dat", gameProgress3);
        List<String> zipEntryFilePaths = List.of(saveGames + "\\save1.dat", saveGames + "\\save2.dat", saveGames + "\\save3.dat");
        GameProgress.zipFiles(saveGames + "\\zipSave.zip",zipEntryFilePaths);

        List<File> filesToDelete = Arrays.stream(saveGames.listFiles())
                .filter(file -> !file.getName().contains(".zip"))
                .toList();

        filesToDelete.forEach(File::delete);
    }

    private static void createDirectory(File file) {
        try {
            file.mkdir();
            log("Успешно создан каталог: " + file.getAbsolutePath());
        } catch (Exception e) {
            log("Ошибка при создании каталога: " + file.getAbsolutePath() + " " + e.getMessage());
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
            log("Успешно создан файл: " + file.getAbsolutePath());
        } catch (Exception e) {
            log("Ошибка при создании файла: " + file.getAbsolutePath() + " " + e.getMessage());
        }
    }

    private static void log(String message) {
        logBuilder.append(message);
        logBuilder.append("\n");
    }


}

