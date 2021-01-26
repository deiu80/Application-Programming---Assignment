//package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.net.MalformedURLException;
import java.awt.*;
import java.net.URL;
import javax.swing.Timer;
import java.util.*;



/*
main panel where all the classes are use and objects are drawn into the panel
 */


public class Main extends JFrame {
    public static void main(String[] args) throws MalformedURLException {
        JFrame frame = new JFrame("Student registration nr: 1906138");
        GameLoop gl = new GameLoop();
        frame.add(gl);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
