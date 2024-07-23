import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipArchiveFilePath, List<String> zipEntryFilePaths) {

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipArchiveFilePath))) {
            for(String zipEntryFilePath : zipEntryFilePaths){
                try (FileInputStream zipEntryFileInputStream = new FileInputStream(zipEntryFilePath)){
                    ZipEntry currentZipEntry = new ZipEntry(extractFileName(zipEntryFilePath));
                    zipOutputStream.putNextEntry(currentZipEntry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[zipEntryFileInputStream.available()];
                    zipEntryFileInputStream.read(buffer);
                    // добавляем содержимое к архиву
                    zipOutputStream.write(buffer);
                    // закрываем текущую запись для новой записи
                    zipOutputStream.closeEntry();

                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String extractFileName(String zipEntryFilePath) {
        String[] filePathParts = zipEntryFilePath.split("\\\\");
        return filePathParts.length > 1 ? filePathParts[filePathParts.length - 1] : null;
    }
}
