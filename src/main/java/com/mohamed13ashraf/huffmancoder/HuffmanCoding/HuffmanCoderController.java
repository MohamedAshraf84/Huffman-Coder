package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import com.mohamed13ashraf.huffmancoder.MyDataStructures.AVLMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HuffmanCoderController implements Initializable {

    private final String NO_COMPRESSION_DONE_MESSAGE = "No compression have been done yet.";
    private final String CHOOSE_FILE_ALERT_MESSAGE = "Select a file first.";
    private final String SAVE_LOCATION_ALERT_MESSAGE = "Specify location to save the output file.";
    private final Color LEAF_NODE_COLOR = Color.rgb(237, 130, 14);
    private final Color NON_LEAF_NODE_COLOR = Color.LIGHTBLUE;
    private final Color LINE_COLOR = Color.BLACK;
    private final Color TEXT_COLOR = Color.BLACK;
    @FXML
    private TextField inputFilePathTF,
                      outputFilePathTF;
    @FXML
    private RadioButton compressRadioBtn,
                        decompressRadioBtn;
    @FXML
    private Button saveBtn,
                   browseBtn,
                   runBtn,
                   showOriginalSizeBtn,
                   showSizeAfterCompressionBtn,
                   showCompressionRatioBtn,
                   showCodesBeforeCompressionBtn,
                   showCodesAfterCompressionBtn,
                   drawHuffmanTreeBtn;

    private File compressedFile,
                 decompressedFile;
    private File fileToCompress,
                 fileToDecompress;

    private boolean hasBeenCompressed;
    private boolean hasBeenDecompressed;
    private Stage stage;
    private HuffmanEncoder huffmanEncoder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        compressRadioBtn.setOnMouseClicked(e -> onCompressChosen());
        decompressRadioBtn.setOnMouseClicked(e -> onDecompressChosen());

        browseBtn.setOnMouseClicked(e -> onBrowseClicked());
        saveBtn.setOnMouseClicked(e -> onSaveFileClicked());

        runBtn.setOnMouseClicked(e -> onRunClicked());

        showOriginalSizeBtn.setOnMouseClicked(e -> showOriginalSize());
        showSizeAfterCompressionBtn.setOnMouseClicked(e -> showSizeAfterCompression());

        showCodesAfterCompressionBtn.setOnMouseClicked(e -> showCodesAfterCompression());
        showCodesBeforeCompressionBtn.setOnMouseClicked(e -> showCodesBeforeCompression());

        showCompressionRatioBtn.setOnMouseClicked(e -> showCompressionRatio());

        drawHuffmanTreeBtn.setOnMouseClicked(e -> drawHuffmanTree());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    void init()
    {
        hasBeenCompressed = false;
        hasBeenDecompressed = false;
        inputFilePathTF.setText("");
        outputFilePathTF.setText("");
        fileToCompress = null;
        fileToDecompress = null;
        compressedFile = null;
        decompressedFile = null;
    }

    void onCompressChosen() {

        init();

        String INPUT_FILE_COMPRESSION_PROMPT_MESSAGE = "Select a File to Compress";
        inputFilePathTF.setPromptText(INPUT_FILE_COMPRESSION_PROMPT_MESSAGE);
        String OUTPUT_FILE_COMPRESSION_PROMPT_MESSAGE = "Specify Location to Save the Compressed File";
        outputFilePathTF.setPromptText(OUTPUT_FILE_COMPRESSION_PROMPT_MESSAGE);
        runBtn.setText("RUN COMPRESSION");
    }

    void onDecompressChosen() {
        init();
        String INPUT_FILE_DECOMPRESSION_PROMPT_MESSAGE = "Select a File to Decompress";
        inputFilePathTF.setPromptText(INPUT_FILE_DECOMPRESSION_PROMPT_MESSAGE);
        String OUTPUT_FILE_DECOMPRESSION_PROMPT_MESSAGE = "Specify Location to Save the Decompressed File";
        outputFilePathTF.setPromptText(OUTPUT_FILE_DECOMPRESSION_PROMPT_MESSAGE);
        runBtn.setText("RUN DECOMPRESSION");
    }

    void onBrowseClicked() {

        FileChooser fileChooser = new FileChooser();

        if (compressRadioBtn.isSelected()) {
            List<String> extensions = new ArrayList<>();
            extensions.add("*.txt");
            extensions.add("*.docx");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files (Text)", extensions));

            fileToCompress = fileChooser.showOpenDialog(stage);

            if (fileToCompress != null)
                inputFilePathTF.setText(fileToCompress.getAbsolutePath());

        } else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", "*.huf"));

            fileToDecompress = fileChooser.showOpenDialog(stage);

            if (fileToDecompress != null)
                inputFilePathTF.setText(fileToDecompress.getAbsolutePath());
        }
    }

    void onSaveFileClicked() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter;

        if (compressRadioBtn.isSelected()) {
            extFilter = new FileChooser.ExtensionFilter("Huffman Files (*.huf)", "*.huf");
            fileChooser.getExtensionFilters().add(extFilter);

            compressedFile = fileChooser.showSaveDialog(stage);
            if (compressedFile != null)
                outputFilePathTF.setText(compressedFile.getAbsolutePath());

        } else {
            List<String> extensions = new ArrayList<>();
            extensions.add("*.txt");
            extensions.add("*.docx");

            extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt, *.docx)", extensions);
            fileChooser.getExtensionFilters().add(extFilter);

            decompressedFile = fileChooser.showSaveDialog(stage);
            if (decompressedFile != null)
                outputFilePathTF.setText(decompressedFile.getAbsolutePath());

        }
    }

    public void compress()
    {
        if (fileToCompress == null) {
            showAlertMessage("Message", CHOOSE_FILE_ALERT_MESSAGE);
            return;
        }

        String COMPRESSION_DONE_MESSAGE = "Compression has been completed.";

        if (hasBeenCompressed)
        {
            showAlertMessage("Message", COMPRESSION_DONE_MESSAGE);
            return;
        }

        if (outputFilePathTF.getText().isEmpty()) {
            showAlertMessage("Message", SAVE_LOCATION_ALERT_MESSAGE);
            return;
        }

        huffmanEncoder = new HuffmanEncoder(fileToCompress);

        String encodedMessageBitSequence = huffmanEncoder.encode();
        int encodedMessageLength = encodedMessageBitSequence.length();

        BitSet encodedMessage = convertStringToBitSet(encodedMessageBitSequence);

        try ( FileOutputStream outputStream = new FileOutputStream(compressedFile);
              ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream) ) {

            HuffmanResult huffmanResult = new HuffmanResult(encodedMessageLength, encodedMessage,
                    huffmanEncoder.getHuffmanTree());

            objectOutputStream.writeObject(huffmanResult);

            hasBeenCompressed = true;
            showAlertMessage("Message", COMPRESSION_DONE_MESSAGE);

        } catch (IOException e) {
            showAlertMessage("Message", "Compression Failed.");
        }
    }

    private BitSet convertStringToBitSet(String encodedMessageBitSequence) {
        int encodedMessageLength = encodedMessageBitSequence.length();
        BitSet encodedMessage = new BitSet(encodedMessageLength);

        for (int i = 0; i < encodedMessageLength; ++i) {
            if (encodedMessageBitSequence.charAt(i) == '1') {
                encodedMessage.set(i);
            }
        }
        return encodedMessage;
    }

    public void decompress() {

        String DECOMPRESSION_DONE_MESSAGE = "Decompression has been completed.";

        if (hasBeenDecompressed)
        {
            showAlertMessage("Message", DECOMPRESSION_DONE_MESSAGE);
            return;
        }

        if (fileToDecompress == null) {
            showAlertMessage("Message", CHOOSE_FILE_ALERT_MESSAGE);
            return;
        }

        if (outputFilePathTF.getText().isEmpty()) {
            showAlertMessage("Message", SAVE_LOCATION_ALERT_MESSAGE);
            return;
        }

        try ( FileInputStream inputStream = new FileInputStream(fileToDecompress.getAbsolutePath());
              ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
              FileOutputStream outputFile = new FileOutputStream(decompressedFile) ) {

            HuffmanResult huffmanResult = (HuffmanResult) objectInputStream.readObject();
            HuffmanDecoder huffmanDecoder = new HuffmanDecoder(huffmanResult);

            String message = huffmanDecoder.decode();
            outputFile.write(message.getBytes());

            hasBeenDecompressed = true;
            showAlertMessage("Message", DECOMPRESSION_DONE_MESSAGE);

        } catch (IndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            showAlertMessage("Message", "Decompression Failed.");
        }
    }

    public void showCompressionRatio() {

        if (hasBeenCompressed) {
            long a = fileToCompress.length();
            long b = compressedFile.length();
            double c = (a - b) / (double) a;

            showAlertMessage("Compression Ratio", String.format("Compression done with %,.2f %%", c * 100.0));
        } else {
            showAlertMessage("Message", NO_COMPRESSION_DONE_MESSAGE);
        }
    }

    public void showSizeAfterCompression()
    {

        if (hasBeenCompressed) {
            long fileSize = compressedFile.length();
            showAlertMessage(
                    "Compressed File Size",
                    String.format(
                            "%,d Bytes | " +
                                    "%,.2f KB | " +
                                    "%,.2f MB",
                            fileSize, fileSize / 1024.0, fileSize / 1048576.0)
            );
        } else {
            showAlertMessage("Message", NO_COMPRESSION_DONE_MESSAGE);
        }
    }

    public void showOriginalSize()
    {
        if (fileToCompress != null) {
            long fileSize = fileToCompress.length();
            showAlertMessage(
                    "Original File Size",
                    String.format("%,d Bytes | " +
                                    "%,.2f KB | " +
                                    "%,.2f MB",
                            fileSize, fileSize / 1024.0, fileSize / 1048576.0)
            );
        } else {
            showAlertMessage("Message", CHOOSE_FILE_ALERT_MESSAGE);
        }
    }

    public void showCodesBeforeCompression()
    {

        if (!hasBeenCompressed) {
            showAlertMessage("Message", "Apply compression first.");
            return;
        }

        TableView<CodesTableRow> codesBeforeCompression = createTableView();
        ObservableList<CodesTableRow> data = FXCollections.observableArrayList();

        AVLMap<Byte, Integer> frequencyTable = huffmanEncoder.getFrequencyTable();

        for (AVLMap.AVLNode<Byte, Integer> entry : frequencyTable.entrySet()) {
            byte character = entry.getKey();
            String frequency = String.valueOf(entry.getValue());
            String bitSequence = String.format("%8s", Integer.toBinaryString(character & 0xFF))
                                       .replace(' ', '0');

            data.add(new CodesTableRow((char) character, frequency, bitSequence));
        }

        codesBeforeCompression.setItems(data);
        showTableViewInStage(codesBeforeCompression, "Original Codes");
    }

    public void showCodesAfterCompression() {

        if (!hasBeenCompressed) {
            showAlertMessage("Message", NO_COMPRESSION_DONE_MESSAGE);
            return;
        }

        TableView<CodesTableRow> codesAfterCompression = createTableView();
        ObservableList<CodesTableRow> data = FXCollections.observableArrayList();

        AVLMap<Byte, Integer> frequencyTable = huffmanEncoder.getFrequencyTable();
        AVLMap<Byte, String> huffmanCodes = huffmanEncoder.getHuffmanCodes();

        for (AVLMap.AVLNode<Byte, Integer> entry : frequencyTable.entrySet()) {
            byte character = entry.getKey();
            String frequency = String.valueOf(entry.getValue());
            String bitSequence = huffmanCodes.get(character);

            data.add(new CodesTableRow((char) character, frequency, bitSequence));
        }

        codesAfterCompression.setItems(data);
        showTableViewInStage(codesAfterCompression, "Huffman Codes");
    }


    private TableView<CodesTableRow> createTableView() {
        TableView<CodesTableRow> tableView = new TableView<>();

        TableColumn<CodesTableRow, String> characterColumn = new TableColumn<>("Character");
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("character"));

        TableColumn<CodesTableRow, String> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        TableColumn<CodesTableRow, String> bitSequenceColumn = new TableColumn<>("Bit Sequence");
        bitSequenceColumn.setCellValueFactory(new PropertyValueFactory<>("bitSequence"));

        tableView.getColumns().addAll(characterColumn, frequencyColumn, bitSequenceColumn);
        return tableView;
    }

    private void showTableViewInStage(TableView<CodesTableRow> tableView, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setWidth(270);
        stage.setScene(new Scene(new VBox(tableView)));
        stage.show();
    }

    public void onRunClicked() {
        if (compressRadioBtn.isSelected())
            compress();
        else
            decompress();
    }

    public void drawHuffmanTree() {
        if (hasBeenCompressed) {

            ScrollPane scrollPane = new ScrollPane();
            Pane pane = new Pane();

            Stage huffmanTreeStage = new Stage();
            scrollPane.setContent(pane);
            drawTree(pane, huffmanEncoder.getHuffmanTree(), 600, 50, 1200, 1);

            Scene scene = new Scene(scrollPane, 1200, 600);

            huffmanTreeStage.setTitle("Huffman Tree");
            huffmanTreeStage.setScene(scene);
            huffmanTreeStage.show();

        } else {
            showAlertMessage("Message", NO_COMPRESSION_DONE_MESSAGE);
        }
    }

    private void drawTree(Pane pane, TreeNode node, double x, double y, double hGap, int level) {

        int NODE_RADIUS = 15;
        Circle circle = new Circle(x, y, NODE_RADIUS, NON_LEAF_NODE_COLOR);

        Text text = new Text(String.valueOf(node.frequency()));

        if (node.isLeaf()) {
            circle = new Circle(x, y, NODE_RADIUS, LEAF_NODE_COLOR);
            circle.setStroke(Color.GREEN);
            text.setText(String.valueOf((char) node.character()));
            if (node.character() == 13) {
                text.setText("CR");
            } else if (node.character() == 10) {
                text.setText("LF");
            } else if (node.character() == 32) {
                text.setText("SP");
            }
        }

        Bounds charBounds = text.getLayoutBounds();

        text.setStyle("-fx-font-weight: bold;");
        text.setFill(TEXT_COLOR);
        text.setX(x - charBounds.getWidth() / 2f);
        text.setY(y + charBounds.getHeight() / 4f);
        pane.getChildren().addAll(circle, text);

        int LEVEL_HEIGHT = 80;
        if (node.left() != null) {

            double leftX = x - hGap / (Math.pow(2, level + 1));
            double leftY = y + LEVEL_HEIGHT;
            Line line = new Line(x, y + NODE_RADIUS, leftX, leftY - NODE_RADIUS);
            Text zero = new Text("0");
            zero.setFill(TEXT_COLOR);
            zero.setX(leftX + (x - leftX) / 2f);
            zero.setY(y + NODE_RADIUS + (leftY - 2 * NODE_RADIUS - y) / 2);
            line.setStroke(LINE_COLOR);
            pane.getChildren().addAll(line, zero);
            drawTree(pane, node.left(), leftX, leftY, hGap, level + 1);
        }

        if (node.right() != null) {

            double rightX = x + hGap / (Math.pow(2, level + 1));
            double rightY = y + LEVEL_HEIGHT;
            Line line = new Line(x, y + NODE_RADIUS, rightX, rightY - NODE_RADIUS);
            Text one = new Text("1");
            one.setFill(TEXT_COLOR);
            one.setX(x + (rightX - x) / 2);
            one.setY(y + NODE_RADIUS + (rightY - 2 * NODE_RADIUS - y) / 2);
            line.setStroke(LINE_COLOR);
            pane.getChildren().addAll(line, one);
            drawTree(pane, node.right(), rightX, rightY, hGap, level + 1);
        }
    }

    void showAlertMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Node content = alert.getDialogPane().lookup(".content");
        content.setStyle( "-fx-font-weight: bold;" +
                          "-fx-alignment: center-left;" +
                          "-fx-font-size: 17px;" +
                          "-fx-padding: 20px 20px 20px 5px;");
        alert.showAndWait();
    }
}