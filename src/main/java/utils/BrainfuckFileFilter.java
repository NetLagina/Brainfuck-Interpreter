package utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class BrainfuckFileFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FileUtils.getExtension(f);
        if (extension != null) {
            return extension.equals(FileUtils.b) || extension.equals(FileUtils.bf);
        }

        return false;
    }

    public String getDescription() {
        return "Brainfuck Source Files (*.b;*.bf)";
    }

}
