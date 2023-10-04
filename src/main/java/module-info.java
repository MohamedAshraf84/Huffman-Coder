module com.mohamed13ashraf.huffmancoder {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mohamed13ashraf.huffmancoder to javafx.fxml;
    exports com.mohamed13ashraf.huffmancoder;
    exports com.mohamed13ashraf.huffmancoder.MyDataStructures;
    opens com.mohamed13ashraf.huffmancoder.MyDataStructures to javafx.fxml;
    exports com.mohamed13ashraf.huffmancoder.HuffmanCoding;
    opens com.mohamed13ashraf.huffmancoder.HuffmanCoding to javafx.fxml;
}