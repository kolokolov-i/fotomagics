package com.lilpeace.fotomagics;

import com.lilpeace.fotomagics.filters.Filter;
import com.lilpeace.fotomagics.filters.FilterFabric;

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
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener {

    FilterChain filterChain;

    ImageHolder imageHolder;
    JLabel nameLabel;
    JList paletteList;
    JFileChooser openChooser, saveChooser;

    public MainFrame(){
        super("FotoMagics");
        setSize(800, 600);

        imageHolder = new ImageHolder();
        add(imageHolder);
        JPanel sidePanel = buildSidePanel();
        add(sidePanel, BorderLayout.EAST);

        FileFilter fileFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        openChooser = new JFileChooser(System.getProperty("user.dir"));
        openChooser.setFileFilter(fileFilter);
        openChooser.setMultiSelectionEnabled(false);
        saveChooser = new JFileChooser(System.getProperty("user.dir"));
        saveChooser.setFileFilter(fileFilter);
        saveChooser.setMultiSelectionEnabled(false);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel buildSidePanel() {
        JPanel sidePanel = new JPanel();
        JPanel toolBar = new JPanel();
//        toolBar.setLayout(new FlowLayout());
        JButton btnOpen = new JButton("Открыть");
        btnOpen.setActionCommand("open");
        btnOpen.addActionListener(this);
        JButton btnSave = new JButton("Сохранить");
        btnSave.setActionCommand("save");
        btnSave.addActionListener(this);
        toolBar.add(btnOpen);
        toolBar.add(btnSave);
        toolBar.setMaximumSize(new Dimension(200, 48));
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.add(toolBar);
        nameLabel = new JLabel(" ");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        sidePanel.add(nameLabel);
        // TODO compare button
        JPanel palette = new JPanel();
        palette.setLayout(new BoxLayout(palette, BoxLayout.PAGE_AXIS));
        ArrayList<FilterFabric> fabrics = Filter.getFabrics();
        paletteList = new JList(fabrics.toArray());
        paletteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paletteList.setLayoutOrientation(JList.VERTICAL);
        JButton btnFilter = new JButton("Применить фильтр");
        btnFilter.setActionCommand("filter");
        btnFilter.addActionListener(this);
        JScrollPane listScroller = new JScrollPane(paletteList);
        listScroller.setPreferredSize(new Dimension(50, 40));
        palette.add(listScroller);
        palette.add(btnFilter);
        sidePanel.add(palette);
        sidePanel.add(Box.createVerticalGlue());
        return sidePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "open":
                openFileAction();
                break;
            case "save":
                saveFileAction();
                break;
            case "filter":
                filterAction();
                break;
        }
    }

    private void filterAction() {
        FilterFabric fabric = (FilterFabric) paletteList.getSelectedValue();
        if(fabric == null){
            return;
        }
        Filter filter = fabric.getInstance();
        filterChain.add(filter);
        imageHolder.setImage(filterChain.getResultImage());
        imageHolder.repaint();
    }

    private void openFileAction() {
        int i = openChooser.showOpenDialog(this);
        if(i == JFileChooser.APPROVE_OPTION){
            File file = openChooser.getSelectedFile();
            try {
                BufferedImage sourceImage = ImageIO.read(file);
                nameLabel.setText(file.getName());
                filterChain = new FilterChain(sourceImage);
                imageHolder.setImage(sourceImage);
                imageHolder.repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка открытия файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAction() {
        int i = saveChooser.showSaveDialog(this);
        if(i == JFileChooser.APPROVE_OPTION){
            File file = saveChooser.getSelectedFile();
            try{
                ImageIO.write(filterChain.getResultImage(), "jpg", file);
            }
            catch (IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка сохранения файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
