package com.pages.page9;

import com.constants.Constants;
import com.gui.Gui;
import com.pages.Pages;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Core;
import org.opencv.core.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Simble webbcamera
public class Page9 extends JPanel implements Pages {
  private static final long serialVersionUID = 1L;
  private Gui gui;
  private JPanel tab9;
  private JPanel mainPanel = new JPanel();
  private JPanel buttonsPanel = new JPanel();
  private JButton openBtn = new JButton("Open File");
  private JButton playBtn = new JButton("Play");
  private JButton stopBtn = new JButton("Stop");
  private VideoPanel videoPanel;
  private File file;
  private String outPath = Constants.videoPath + "screenshots\\";
  public static List<String> results = new ArrayList<>();
  public static Rect rect = null;


  // Constructor
  public Page9(final Gui gui) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    this.gui = gui;
    tab9 = gui.getTab9();
    videoPanel = new VideoPanel(Constants.VIDEO_WIDTH, Constants.VIDEO_HEIGHT);
    initComponents();
    addListeners();
  }


  // Create ui component listeners
  private void addListeners() {
    //open btn
    openBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(Constants.videoPath);
        fc.setMultiSelectionEnabled(false);
        if (fc.showOpenDialog(gui) == JFileChooser.APPROVE_OPTION) {
          File f = fc.getSelectedFile();
          if (f != null) {
            file = f;
            playBtn.setEnabled(true);
          }
        }
      }
    });
    //play btn
    playBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // screenshotPath manipulations
        clearFolder(outPath);
        String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
        String fileOutPath = outPath + fileNameWithOutExt + "\\";
        new File(fileOutPath).mkdirs();
        // auto recognize on play
        videoPanel.play(file, fileOutPath);
        playBtn.setEnabled(false);
        stopBtn.setEnabled(true);
      }
    });
    //stop btn
    stopBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        videoPanel.stop();
        playBtn.setEnabled(true);
        stopBtn.setEnabled(false);
      }
    });
  }


  // Initialize UI components
  private void initComponents() {
    mainPanel.add(videoPanel);

    buttonsPanel.add(openBtn);
    buttonsPanel.add(playBtn);
    buttonsPanel.add(stopBtn);

    tab9.add(mainPanel);
    tab9.add(buttonsPanel);
    mainPanel.setPreferredSize(new Dimension(800, 500));
    buttonsPanel.setPreferredSize(new Dimension(800, 100));

    playBtn.setEnabled(false);
    stopBtn.setEnabled(false);
  }


  // Clear folder from old files
  public void clearFolder(String path) {
    try {
      FileUtils.deleteDirectory(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    new File(path).mkdirs();
  }
}
