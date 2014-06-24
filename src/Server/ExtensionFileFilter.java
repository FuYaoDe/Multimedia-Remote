package Server;
import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter {

    private String[] extension;


    public ExtensionFileFilter(String[] s) {
        extension=s;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }

        String name = file.getName();
        // find the last
        int index = name.lastIndexOf(".");
        if (index == -1) {
            return false;
        } else if (index == name.length() - 1) {
            return false;
        } else{
            for(int i=0;i<extension.length;i++){
                boolean b=extension[i].equals(
                        name.substring(index + 1).toLowerCase());
                if(b){
                    return true;
                }
            }
            return false;
        }
    }
}