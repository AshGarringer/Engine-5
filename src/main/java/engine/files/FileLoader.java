package engine.files;

import com.sun.tools.javac.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cookibot
 */
public class FileLoader {
    
    public static ArrayList<String> readLocalFile(String path){
        
        try{
            InputStream in = FileLoader.class.getResourceAsStream(path);

            ArrayList<String> list = new ArrayList<String>();

            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
            return list;
        }
        
        catch(Exception e){
            System.out.println("Could not load file '"+path+"'.");
            return new ArrayList<String>();
        }
        
    }
    public static String readLocalTxt(String path){
        try {
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);
            String line = null;
            while((line = reader.readLine()) != null){
                return line;
            }   return "0";
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return "0";
        }
    }
    public static void reWriteLocalFile(String s, String f){
        try {
            Path path = Paths.get(f);
            java.nio.file.Files.deleteIfExists(path);
            java.nio.file.Files.write(path, s.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            System.out.println("could not rewrite file");
        }
    }
    
    public static void initializeFolder(String folder){
        File file1 = new File(System.getProperty("user.home") +FileSystems.getDefault().getSeparator()+folder);
        if(!file1.exists())file1.mkdir();
    }
    public static void reWriteFile(String s, String f){
        try {
            Path path = Paths.get(System.getProperty("user.home")+FileSystems.getDefault().getSeparator()+f);
            java.nio.file.Files.deleteIfExists(path);
            java.nio.file.Files.write(path, s.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            System.out.println("could not rewrite file");
        }
    }
    public static String readTxt(String path){
        try {
            File file = new File(System.getProperty("user.home") +FileSystems.getDefault().getSeparator()+path);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);
            String line = null;
            while((line = reader.readLine()) != null){
                return line;
            }   return "0";
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return "0";
        }
    }
    public static ArrayList<String> readFullTxt(String path){
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.home") +FileSystems.getDefault().getSeparator()+path);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);
            String line = null;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }  
            return lines;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return lines;
        }
    }
    public static void initializeFile(String file){
        try{
            File file5 = new File(System.getProperty("user.home") +FileSystems.getDefault().getSeparator()+file+".txt");
            if(!file5.exists()){
                PrintWriter writer = new PrintWriter(System.getProperty("user.home")
                        +"/"+file+".txt", "UTF-8");
                writer.println("0");
                writer.close();
            }
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String separator(){
        return FileSystems.getDefault().getSeparator();
    }

}
