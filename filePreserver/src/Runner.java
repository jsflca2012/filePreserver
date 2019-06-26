import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Runner {
	File input;
	ArrayList<String> output = new ArrayList<String>();
	ArrayList<String> extensions = new ArrayList<String>();


	public Runner() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
				}
				//chooseFile();
				directorySelection();
			}
		});
	}

	public static void main(String[] args) {
		Runner run = new Runner();
	}
	
	public void directorySelection() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			input=chooser.getSelectedFile();
			extensions.addAll(Arrays.asList("mkv","mp4","mpg","avi","wmv","m4v","mov"));
			scanDir(input);
			System.out.println(output);
			fileWriter();
		} else {
			System.out.println("No Selection ");
			JOptionPane.showMessageDialog(null, "No selection.");
			directorySelection();
		}
	}

	public void chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			input = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + input.getAbsolutePath());

		}
		extensions.addAll(Arrays.asList("mkv","mp4","mpg","avi","wmv","m4v","mov"));
		scanDir(input.getParentFile());
		System.out.println(output);
		fileWriter();
	}

	public void scanDir(File fileName) {
		File[] fList = fileName.listFiles();
		//for (File file : fList) {
		for(int x=0; x<fList.length-1;x++) {
			if (fList[x]!=null&&fList[x].isFile()) {
				System.out.println(fList[x].getName().substring(fList[x].getName().lastIndexOf(".")+1));
				checkExtension(fList[x]);	
			}
			if (fList[x].isDirectory()) {
				scanDir(fList[x]);
			}
		}
	}

	public void checkExtension(File file) {
		
			String fileName=file.getName();
			if (extensions.indexOf(file.getName().substring(file.getName().lastIndexOf(".")+1))>=0) {
				output.add(file.getName());
			
		}
	}

	public void fileWriter() {
		try {
			new File(input.getParent() + "/" + "data.txt").delete();
			// Whatever the file path is.
			File statText = new File(input.getPath() + "/" + "data.txt");
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);
			for (int x = 0; x < output.size() - 1; x++) {
				w.write(output.get(x) + ", ");
			}
			w.write(output.get(output.size() - 1) + ".");
			w.close();
		} catch (IOException e1) {
			System.err.println("Problem writing to the file data.txt");
		}
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File(input.getPath() + "/" + "data.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
