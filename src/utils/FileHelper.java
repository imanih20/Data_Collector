package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileHelper{
    public static String FILE_FORMAT = ".txt";
    private File file;
    public FileHelper(String path){
        init(path);
    }
    public FileHelper(File folder){
        if (folder.exists()&& folder.isDirectory()){
            file = folder;
        }
    }
    private void init(String path){
        File temp = new File(path);
        if (!temp.exists()){
            if (temp.mkdirs()){
                file = temp;
            }
        }else {
            if (temp.isFile()) return;
            file = temp;
        }
    }

    public File getFile() {
        return file;
    }

    public void createFile(String name, String content) throws IOException {
        File newFile = new File(file.getAbsolutePath()+"/"+name+FILE_FORMAT);
        if (newFile.exists()) return;
        if (!newFile.createNewFile()) return;
        PrintWriter writer = new PrintWriter(new FileOutputStream(newFile),true, StandardCharsets.UTF_8);
        writer.append(content);
        writer.close();
    }
    public static String[] getFileContent(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            builder.append(line);
        }
        String content = builder.toString();
        String[] arr;
        if (content.contains(","))
            arr = content.split(",");
        else if (content.contains("،"))
            arr = content.split("،");
        else
            arr = content.split(" ");
        return arr;
    }
}
