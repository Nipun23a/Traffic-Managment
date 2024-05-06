package main.java.com.mycompany;

import main.java.com.mycompany.UI.AnalyzeApplication;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AnalyzeApplication();
            }
        });
    }
}