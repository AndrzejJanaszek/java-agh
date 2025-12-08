package com.example.koniary.services;

import com.example.koniary.model.Stable;

import java.io.*;
import java.util.List;

public class Serializer {

    public static void saveToFile(List<Stable> stables, File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(stables);
            System.out.println("Zapisano dane do pliku: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Stable> loadFromFile(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Stable>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
