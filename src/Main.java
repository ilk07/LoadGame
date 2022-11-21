import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("C:/Users/Мишка/Desktop/Games/savegames/zip.zip", "C:/Users/Мишка/Desktop/Games/loadgames/");
        System.out.println(openProgress("C:/Users/Мишка/Desktop/Games/loadgames/save3.dat"));
    }

    public static GameProgress openProgress(String fileName) {
        GameProgress gameProgress = null;
        //открываем входной поток для чтения файла
        try (
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void openZip(String zipFile, String toDir) {
        File dir = new File(toDir);
        if (!dir.exists()) { //если папка не существует
            if (!dir.mkdir()) {
                System.out.println("Что-то поломалось...");
            }
        }

        if (dir.isDirectory()) {
            try (
                    ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry entry;
                String name;

                while ((entry = zin.getNextEntry()) != null) {
                    name = entry.getName();
                    FileOutputStream fout = new FileOutputStream(toDir + name);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    fout.close();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}