package com.gmail.tlachy;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import static java.nio.file.Files.list;
import static java.nio.file.Paths.get;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Map<String, Writer> regexWithWriters = createAndStartWriters(args[0], args[2]);

        Iterator<Path> iterator = list(get(args[1])).iterator();
        while (iterator.hasNext()){
            Thread thread = new Thread(new Reader(iterator.next(), regexWithWriters));
            thread.start();
            thread.join();
        }

        for(Writer writer : regexWithWriters.values()){
            writer.end();
        }

    }

    private static Map<String, Writer> createAndStartWriters(String rulesFile, String outputDir) throws IOException {

        Map<String, Writer> regexWithWriters = new HashMap<>();

        try (FileInputStream inputStream = new FileInputStream(rulesFile)) {
            Scanner sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String name = line.split(":")[0].trim();
                String regex = line.split(":")[1].trim();

                Writer writer = new Writer(outputDir, name);
                regexWithWriters.put(regex, writer);
                new Thread(writer).start();
            }
        }
        return regexWithWriters;
    }
}
