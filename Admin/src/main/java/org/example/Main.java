package org.example;

import org.example.Views.HomeFrame;
import org.example.Views.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
