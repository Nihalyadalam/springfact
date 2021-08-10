package com.example.snowden.snowden.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.snowden.snowden.Constants;
import com.example.snowden.snowden.models.Fact;

public class FileOperation {

    public static List<Fact> readTsvFile(String path) {
        String contents;
        List<Fact> list = new ArrayList<>();
        try {
            File f = new File(path);
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((contents = br.readLine()) != null) {
                    String[] line_splits = contents.split("\t");
                    String id_fact = line_splits[0];
                    String sent = line_splits[1];
                    double value = 0.0;
                    list.add(new Fact(id_fact,sent,value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeFile(List<com.example.snowden.snowden.models.Fact> resultFact, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            int i = 1;
            if (file.exists()) {
                StringBuilder content = new StringBuilder();
                for (com.example.snowden.snowden.models.Fact f : resultFact) {
                    String output_line = "<" + Constants.URI_FACT+ f.getId()+ ">" + Constants.URI_TRUTH_VAL 
                    + "\"" + f.getValue() + "\""+"^^" + Constants.DATA_TYPE + " .\n";
                    content.append(output_line);
                    i++;
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(content.toString());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
