package me.lucko.jarrelocator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StandaloneMain {

    private static void usage() {
        System.err.println("Usage:");
        System.err.println("   java -jar jar-relocator.jar -in <input jar/class/dir path> -out <output jar/class/dir path> -rule[s] [<source package>:<target package>,]+");
    }

    private static String parameter(Enumeration<String> enumeration, String arg) {
        if (!enumeration.hasMoreElements()) {
            fatal("Command line argument " + arg + " does require parameter.");
        }
        return enumeration.nextElement();
    }

    private static void fatal(String s) {
        System.err.println(s);
        usage();
        System.exit(1);
    }

    public static void main(String[] args) {
        Enumeration<String> argse = Collections.enumeration(Arrays.asList(args));

        String input = null;
        String output = null;
        List<Relocation> rules = new ArrayList<>();

        while (argse.hasMoreElements()) {
            String currentOption = argse.nextElement();
            switch (currentOption) {
                case "-in":
                    input = parameter(argse, currentOption);
                    break;
                case "-out":
                    output = parameter(argse, currentOption);
                    break;
                case "-rules":
                case "-rule":
                    String param = parameter(argse, currentOption);
                    int num = 0;
                    for (String kv : param.split(",")) {
                        ++num;
                        String[] kvPair = kv.split(":");
                        if (kvPair.length != 2) {
                            fatal("Key-value pair #" + num + " '" + kv + "' does not follow [[k:v],]+ format.");
                        }
                        rules.add(new Relocation(kvPair[0], kvPair[1]));
                    }
                    break;
                default:
                    fatal("Unknown command line argument: " + currentOption);
            }
        }

        if (input == null) {
            fatal("Input parameter need to be specified.");
        }

        if (output == null) {
            fatal("Output parameter need to be specified.");
        }

        JarRelocator relocator = new JarRelocator(new File(input), new File(output), rules);

        try {
            relocator.run();
        } catch (Exception e) {
            throw new RuntimeException("Unable to relocate", e);
        }
    }

}