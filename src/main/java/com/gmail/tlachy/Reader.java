package com.gmail.tlachy;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Reader implements Runnable {

    private final Path file;
    private final Map<String, Writer> regexWithWriter;

    public Reader(Path file, Map<String, Writer> regexWithWriter) {
        this.file = file;
        this.regexWithWriter = regexWithWriter;
    }


    public void run() {
        Scanner sc;

        try (FileInputStream inputStream = new FileInputStream(file.toFile())) {
            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                for(Entry<String, Writer> regexWithWriter : regexWithWriter.entrySet()){
                    if(line.matches(regexWithWriter.getKey())){
                        regexWithWriter.getValue().getQueue().put(line);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
