# Huffman Coder

The "Huffman Coder" is a personal project designed to perform text file compression using the Huffman coding algorithm. 
The program takes a file as input and applies Huffman coding to efficiently represent the data, resulting in a compressed output file.

Huffman coding is a widely used technique in information theory and data compression that assigns variable-length codes to input characters, 
with more frequent characters receiving shorter codes, thereby reducing the overall space required for storage or transmission.


## Features:
### File Compression<br/>
* This feature allows the program to efficiently reduce the size of a given file using the Huffman coding algorithm.

### File Decompression<br/>
* The program can also reverse the compression process, restoring the original file from the compressed version.

### Compression Bit Ratio Display<br/>
* This functionality provides information on the compression efficiency by showing
  the ratio of bits used in the compressed file compared to the original file.

### Display the Character Codes of Both Original & Compressed Files<br/>
* You can display the unique codes assigned to each character in the input file and the codes assigned to each character in the compressed file. 

### Huffman Tree Visualization<br/>
* This feature visually represents the Huffman tree used during the compression process, providing insight into the encoding structure.

## Getting Started:
### Prerequisites
* Java 16 or above</br>
* JavaFx 17.0.2</br>

### How to run the app
* Windows:</br>
Double-click the HuffmanCoder.exe file.</br>
</br>
* Using JAR File:<br>
1. Open your terminal.<br>
2. Navigate to the directory containing the Huffman-Coder.jar file.<br>
3. Execute the following command:<br>

       java -jar HuffmanCoder.jar

* Using an Integrated Development Environment (IDE):<br>
1. Download the repository to your computer.<br>
2. Open the application using an IDE like IntelliJ Idea or Eclipse.<br>
3. Locate and run the main method inside the AppLauncher.java Class.<br>
