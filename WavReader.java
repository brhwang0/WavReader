// WAV File Data Reading
// Program to read WAV file and return corresponding data

import java.io.*;
import java.awt.*;
import java.math.*;

public class WavReader {
	
	static String hexString = "";
	
	// Helper function to reverse a string
	public static String reverseString(String s) {
		String newString = "";
		for (int i = s.length() - 1; i > -1; i--) {
			newString += s.charAt(i);
		}
		return newString;
	}
	
	// Helper function to obtain the little endian form of a string
	public static String littleEndian(String s) {
		String newString = "";
		for (int i = 0; i <= s.length() - 1; i += 2) {
			newString += s.charAt(i + 1);
			newString += s.charAt(i);
		}
		return newString;
	}
	
	// Helper function to convert a string in hex for decimal
	public static String hexToDec(String s) {
		return new BigInteger(s, 16).toString(10);
	}
	
	// Converts a hex value in little endian to decimal
	public static String convert(String s) {
		s = reverseString(s);
		s = littleEndian(s);
		s = hexToDec(s);
		return s;
	}
	
	public static void main(String[] args) {
		
		// Asking user for WAV file
		FileDialog dialog = new FileDialog((Frame)null, "Please choose a WAV file.");
		dialog.setMode(FileDialog.LOAD);
		dialog.setFile("*.wav;");
		dialog.setVisible(true);
		String file = dialog.getFile();

		// Reading WAV File
		System.out.println("Reading WAV Data for file '" + file + "'. Please wait one moment.\n");
		try {
			BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(file));
			ByteArrayOutputStream wavData = new ByteArrayOutputStream();
			
			int read;
			byte[] buffer = new byte[1024];
			while ((read = inFile.read(buffer)) > 0) {
				wavData.write(buffer, 0, read);
			}
			wavData.flush();
			
			// Create a byte array to store the data then convert it to hex
			byte[] byteData = wavData.toByteArray();
			String[] hexData = new String[byteData.length];
			
			for (int i = 0; i < byteData.length; i++ ) {
				Byte b = byteData[i];
				String s = String.format("%02x", b);
				hexData[i] = s;
			}
			
			for (int i = 0; i < hexData.length; i++) {
				hexString += hexData[i];
			}
		}
		catch (Exception e) {
			System.out.println("Error! Please try again.");
			System.exit(1);
		}
		
		
		// Printing file data
		System.out.println("Data for file '" + file + "' proccessed. Information provided below:\n");
		
		// Chunk Size
		String chunkSize = hexString.substring(8, 16);
		System.out.println("Chunk Size: " + convert(chunkSize));
		
		// Subchunk Size
		String subchunkSize = hexString.substring(32, 40);
		System.out.println("Subchunk Size: " + convert(subchunkSize));
		
		// Audio Format
		String aFormat = hexString.substring(40, 44);
		System.out.println("Audio Format: " + convert(aFormat));
		
		// Number of Channels
		String numChannels = hexString.substring(44, 48);
		System.out.println("Number of Channels: " + convert(numChannels));
		
		// Sampling Rate
		String samplingRate = hexString.substring(48, 56);	
		System.out.println("Sampling Rate: " + convert(samplingRate));
		
		// Byte Rate
		String byteRate = hexString.substring(56, 64);
		System.out.println("Byte Rate: " + convert(byteRate));
		
		// Block Align
		String blockAlign = hexString.substring(64, 68);
		System.out.println("Block Align: " + convert(blockAlign));
		
		// Bits per Sample
		String bps = hexString.substring(68, 72);
		System.out.println("Bits per Sample: " + convert(bps));
		
		// Subchunk 2 Size
		String subchunk2Size = hexString.substring(80, 88);
		System.out.println("Subchunk 2 Size: " + convert(subchunk2Size));
		
		dialog.dispose();
		System.exit(0);
	}
}