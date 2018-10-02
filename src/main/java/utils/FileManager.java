package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
	
	private final Map<Integer, File> fileManager;
	private final Map<Integer, Integer> indexManager;
	private int fileId;
	
	public FileManager() {
		fileManager = new HashMap<>();
		indexManager = new HashMap<>();
		fileId = 0;
	}
	
	public String add(File file, int fileIndex) {
		if (!fileManager.containsValue(file)) {
			indexManager.put(fileIndex, fileId);
			fileManager.put(fileId++, file);
			return file.getName();
		} else {
			for (int i = 0; i < fileId; i++) {
				if (file == fileManager.get(i)) {
					indexManager.put(fileIndex, i);
					return file.getName();
				}
			}
		}
		return "New tab";
	}
	
	public File get(int fileIndex) {
		if (indexManager.containsKey(fileIndex)) {
			return fileManager.get(indexManager.get(fileIndex));
		}
		return new File(".");
	}
	
	public boolean remove(int fileIndex) {
		if (indexManager.containsValue(fileIndex)) {
			fileManager.remove(indexManager.get(fileIndex));
			indexManager.remove(fileIndex);
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
