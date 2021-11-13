package view;

import utils.Collector;
import utils.FileHelper;
import utils.Searcher;

import javax.management.BadStringOperationException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.TRAILING;

public class MainFrame {
    JFrame frame;
    File inputFile;
    File outputFolder;
    boolean isCollecting = false;
    public MainFrame(){
        prepareUI();
    }

    private void prepareUI(){
        frame = new JFrame();
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUI(JFrame frame) {
        GroupLayout layout =  new GroupLayout(frame.getContentPane());
        JTextField textField = new JTextField(20);

        JButton selectorButton = new JButton("choose");
        JTextField textField2 = new JTextField(20);
        JButton folderButton = new JButton("choose");
        JLabel fileLabel = new JLabel("input file:");
        JLabel folderLabel = new JLabel("output folder:");
        JButton collectBtn = new JButton("Collect");
        JProgressBar progressBar = new JProgressBar();

        selectorButton.addActionListener((event) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter  filter = new FileNameExtensionFilter(".txt","txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
                inputFile = fileChooser.getSelectedFile();
                textField.setText(inputFile.getAbsolutePath());
            }
        });

        folderButton.addActionListener((event) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
                outputFolder = fileChooser.getSelectedFile();
                textField2.setText(outputFolder.getAbsolutePath());
            }
        });

        collectBtn.addActionListener(event -> {
            if (inputFile==null || outputFolder==null)
                return;
            if (!isCollecting) {
                collectData(inputFile, outputFolder, progressBar);
                isCollecting = true;
            }
        });
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(TRAILING)
                        .addComponent(fileLabel)
                        .addComponent(folderLabel))
                .addGroup(layout.createParallelGroup()
                        .addComponent(textField)
                        .addComponent(textField2)
                        .addComponent(collectBtn)
                        .addComponent(progressBar))
                .addGroup(layout.createParallelGroup()
                        .addComponent(selectorButton)
                        .addComponent(folderButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(fileLabel)
                                .addComponent(textField)
                                .addComponent(selectorButton))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(folderLabel)
                                .addComponent(textField2)
                                .addComponent(folderButton))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(collectBtn))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(progressBar))
        );
        frame.pack();
    }

    private void collectData(File inputFile, File outputFolder,JProgressBar progressBar) {
            Runnable rc = () -> {
                try {
                    String[] words = FileHelper.getFileContent(inputFile);
                    progressBar.setMaximum(words.length);
                    progressBar.setValue(0);
                    for (int i = 0; i < words.length; i++) {
                        Collector collector = new Collector(Searcher.search(words[i]));
                        String data = collector.CollectData();
                        System.out.println(data.split("[\\[][۱۲۳۴۵۶۷۸۹]+[]]").length-data.split("[\\[({]+.*[])}]+").length);
//                        data = data.replaceAll("[\\[][۱۲۳۴۵۶۷۸۹]+[]]","");
                        data = data.replaceAll("[\\[({]+.*[])}]+","");
//                        data = data.replaceAll("[\\[][ابپتثجچحخدذرزژسشصضطظعغفقکگمنوهی۰۱۲۳۴۵۶۷۸۹][]]","");
                        FileHelper helper = new FileHelper(outputFolder);
                        String name = "04_fa_"+i;
                        helper.createFile(name,data);
                        progressBar.setValue(i+1);
                    }
                    isCollecting = false;
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            new Thread(rc).start();
        }
}
