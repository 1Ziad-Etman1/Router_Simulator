module com.example.standard_huffman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.standard_huffman to javafx.fxml;
    exports com.example.standard_huffman;
}