package com.codingboost.pomodoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

/**
 * @author codingboost.com
 */
public class Pomodoro extends JPanel {
    private static final int POMODORO_TIMELIMIT = 1500; // seconds, 25 minutes

    private static boolean shouldCount = true;
    private static int completedPomodoros = 0;
    private static int remaining = POMODORO_TIMELIMIT;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        graphics.setColor(Color.RED);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, 80, 80);
        g2d.fill(circle);

        Font font = graphics.getFont().deriveFont(20.0f);
        graphics.setFont(font);

        graphics.setColor(Color.WHITE);

        String minutes = String.valueOf((int) (remaining / 60));
        String seconds = String.valueOf(remaining % 60);
        if ((remaining % 60) <= 9) {
            seconds = "0" + seconds;
        }

        g2d.drawString(minutes + ":" + seconds, 15, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 80);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame(String.valueOf(completedPomodoros));

        frame.getContentPane().setPreferredSize(new Dimension(100, 140));
        frame.setLayout(new GridBagLayout());
        frame.setAlwaysOnTop(true);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridy = 0;
        c1.gridx = 0;
        Pomodoro m = new Pomodoro();
        frame.getContentPane().add(m, c1);

        c1.gridx = 0;
        c1.gridy = 1;
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            remaining = POMODORO_TIMELIMIT;
            shouldCount = true;
            completedPomodoros++;
            frame.setTitle(String.valueOf(completedPomodoros));
        });
        frame.getContentPane().add(button, c1);


        JButton buttonPause = new JButton("Pause");
        buttonPause.addActionListener(e -> shouldCount = !shouldCount);
        c1.gridx = 0;
        c1.gridy = 2;
        frame.getContentPane().add(buttonPause, c1);

        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        while (true) {
            if (shouldCount) {
                remaining--;
                if (remaining == 0) {
                    shouldCount = false;
                }
            }
            m.repaint();
            Thread.sleep(1000);
        }
    }
}