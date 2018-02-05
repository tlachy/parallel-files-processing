# parallel-files-processing

Write a Java program (standalone, with a main function) that processes files in a given input folder, filtering their lines to an output folder based on regular expression rules from a configuration file.

Each row in the configuration file gives a regular expression and the name of the rule.

In case of apache access logs for example we can have a rule: "notfound: .HTTP\s404.". The output folder will contain a file for each rule by its name, so the file named "notfound" will contain all matching rows (from all input files, merged).

You may use Java 7 or 8. It's important that the program processes the files as fast as possible, using all processor cores and accessing the disk optimally. There's plenty of memory, but there's no constraint on the size of the inputs, so you cannot assume that everything will fit in the memory. The number of rules in the configuration lines is around 10-30.

The command to run the program can look like this: java -Xmx2g -jar parallel-files-processing-1.0-SNAPSHOT-jar-with-dependencies.jar -c "c:\test\rules.txt" -i "c:\test\input" -o "c:\test\output"
