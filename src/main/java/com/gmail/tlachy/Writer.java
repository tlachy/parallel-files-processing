package com.gmail.tlachy;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.nio.file.Files.createFile;

public class Writer implements Runnable {

    private final BlockingQueue<String> queue;
    private final String ruleName;
    private final String dir;
    private volatile boolean shouldEnd = false;

    public Writer(String dir, String ruleName) {
        this.ruleName = ruleName;
        this.dir = dir;
        this.queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {

        try (FileWriter fileWriter = new FileWriter(createOutputFile())) {

            while (true) {

                if(queue.isEmpty()){
                    if(shouldEnd){
                        break;
                    }
                    Thread.yield();
                    continue;
                }
                fileWriter.write(queue.take());
                fileWriter.flush();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createOutputFile(){
        Path path;
        try {
            path = createFile(Paths.get(dir + "/" + ruleName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path.toString();
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public void end() {
        this.shouldEnd = true;
    }
}
