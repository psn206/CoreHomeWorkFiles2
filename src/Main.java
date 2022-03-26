import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void saveGame(GameProgress save, int NamberSave, String way) {
        try (FileOutputStream fos = new FileOutputStream(way + "Save" + NamberSave + ".dat")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }

    public static void main(String[] args) {
        String way = "D://GameProgres//";
        File dir = new File(way);
        saveGame(new GameProgress(100, 1, 1, 25), 1, way);
        saveGame(new GameProgress(75, 2, 2, 50), 2, way);
        saveGame(new GameProgress(50, 3, 3, 75), 3, way);

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(way + "zip.zip"));
        ) {
            for (File item : dir.listFiles()) {
                FileInputStream fis = new FileInputStream(item);
                if (!item.getName().equals("zip.zip")) {
                    ZipEntry entry = new ZipEntry(way + item.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    fis.close();
                    System.out.println(item.delete());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


