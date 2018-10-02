package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class BrainfuckFileFilter extends FileFilter {

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = FileUtils.getExtension(f);
		if (extension != null) {
			if (extension.equals(FileUtils.b) || extension.equals(FileUtils.bf)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}
	
	public String getDescription() {
        return "Brainfuck Source Files (*.b;*.bf)";
    }

}
