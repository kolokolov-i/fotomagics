package com.lilpeace;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {

    ImageHolder iHolder;

    JFileChooser openChooser, saveChooser;

    public MainFrame(){
        super("PhotoMagics");
        setSize(800, 600);

        iHolder = new ImageHolder();
        add(iHolder);
        JPanel sidePanel = buildSidePanel();
        add(sidePanel, BorderLayout.EAST);

        FileFilter fileFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        openChooser = new JFileChooser(System.getProperty("user.dir"));
        openChooser.setFileFilter(fileFilter);
        openChooser.setMultiSelectionEnabled(false);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel buildSidePanel() {
        JPanel sidePanel = new JPanel();
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new FlowLayout());
        JButton btnOpen = new JButton("Открыть");
        btnOpen.setActionCommand("open");
        btnOpen.addActionListener(this);
        JButton btnSave = new JButton("Сохранить");
        btnSave.setActionCommand("save");
        btnSave.addActionListener(this);
        toolBar.add(btnOpen);
        toolBar.add(btnSave);
        sidePanel.add(toolBar);
        return sidePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "open":
                int i = openChooser.showOpenDialog(this);
                if(i == JFileChooser.APPROVE_OPTION){
                    File file = openChooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(file);
                        iHolder.setImage(image);
                        iHolder.repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Ошибка открытия файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "save":
                break;
        }
    }
}
