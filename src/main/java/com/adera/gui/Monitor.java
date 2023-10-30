package com.adera.gui;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.util.Conversor;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Monitor extends JFrame {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel cpuUse;
    private JLabel memoryUse;

    public Monitor() {
        setTitle("Monitoramento");
        setBounds(0, 0, 500, 300);
        setResizable(false);
        setContentPane(contentPane);

        var looca = new Looca();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cpuUse.setText(String.format("%.2f%%", looca.getProcessador().getUso()));
                var memory = looca.getMemoria();
                var usagePercentage = (double) (memory.getEmUso() * 100) / memory.getTotal();

                memoryUse.setText(String.format("%.2f%%", usagePercentage));
            }
        };

        java.util.Timer timer = new Timer();

        timer.schedule(task, new java.util.Date(), 100);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Monitor dialog = new Monitor();
        dialog.setVisible(true);
    }
}
