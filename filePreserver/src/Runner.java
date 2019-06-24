import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;

public class Runner {
	File input;
	ArrayList<String> output = new ArrayList<String>();
	String[] extensions = { "mkv", "mp4", "mpg", "wmv" };

	public Runner() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				chooseFile();
			}
		});
	}

	public static void main(String[] args) {
		Runner run = new Runner();
	}

	public void chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			input = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + input.getAbsolutePath());

		}
		scanDir(input.getParentFile());
		System.out.println(output);
		fileWriter();
	}

	public void scanDir(File fileName) {
		File[] fList = fileName.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				checkExtension(file);
			}
			if (file.isDirectory()) {
				scanDir(file);
			}
		}
	}

	public void checkExtension(File file) {
		for (int x = 0; x < extensions.length; x++) {
			if (file.getName().substring(file.getName().lastIndexOf(".") + 1) == extensions[x]
					&& file.getName() != null) {
				output.add(file.getName());
			}
		}
	}

	public void fileWriter() {
		try {
			new File(input.getParent() + "/" + "data.txt").delete();
			// Whatever the file path is.
			File statText = new File(input.getParent() + "/" + "data.txt");
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
		;
		try {
			desktop.open(new File(input.getParent() + "/" + "data.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
