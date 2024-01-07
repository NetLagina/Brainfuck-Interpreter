package utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private final Map<Integer, File> fileManager;
    private final Map<String, Integer> indexManager;
    private int fileId;

    public FileManager() {
        fileManager = new HashMap<>();
        indexManager = new HashMap<>();
        fileId = 0;
    }

    public String add(File file, String filePath) {
        if (!fileManager.containsValue(file)) {
            indexManager.put(filePath, fileId);
            fileManager.put(fileId++, file);
            return file.getName();
        } else {
            for (int i = 0; i < fileId; i++) {
                if (file == fileManager.get(i)) {
                    indexManager.put(filePath, i);
                    return file.getName();
                }
            }
        }
        return "New tab";
    }

    public File get(String filePath) {
        if (indexManager.containsKey(filePath)) {
            return fileManager.get(indexManager.get(filePath));
        }
        return new File(".");
    }

    public boolean remove(String filePath) {
        if (indexManager.containsKey(filePath)) {
            fileManager.remove(indexManager.get(filePath));
            indexManager.remove(filePath);
            return true;
        }
        return false;
    }

    public String load(File file) {
        String code = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            while (br.ready()) {
                code = code + br.readLine() + System.lineSeparator();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    public boolean save(File file, String code) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            bw.write(code);
            bw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
