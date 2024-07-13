
/*
 * Nama : Erland Agsya Agustian
 * Kelas : TRPL 1D
 * NIM : 2311083007
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;
import javax.swing.plaf.synth.Region;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class uas2 extends Application {
    private TextField namaPasienField;
    private ComboBox<Kelas> kelasComboBox;
    private TextField inapField;
    private TextField layananField;
    private ToggleGroup penangananGroup;
    private TextField biayaPersalinanField;
    private DatePicker masukDatePicker;
    private DatePicker keluarDatePicker;
    private TextField lamaInapField;
    private TextField totalPembayaranField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Erland Agsya Agustian 2311083007");

        // membuat layout utama
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        // membuat judul
        Label judul = new Label("Klinik Bersalin Sahabat Sehati");
        judul.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // membuat seksi "DATA PASIEN"
        StackPane dataPasienLabel = createLabelPane("DATA PASIEN");
        GridPane.setConstraints(dataPasienLabel, 0, 0);

        Label namaPasienLabel = new Label("NAMA PASIEN");
        namaPasienField = new TextField();
        GridPane.setConstraints(namaPasienLabel, 0, 1);
        GridPane.setConstraints(namaPasienField, 1, 1);

        Label kelasLabel = new Label("KELAS");
        kelasComboBox = new ComboBox<>();
        kelasComboBox.getItems().addAll(
                new Kelas("Kelas 1", 300000, 100000),
                new Kelas("Kelas 2", 400000, 150000),
                new Kelas("Kelas 3", 500000, 200000),
                new Kelas("Kelas 4", 600000, 250000));
        kelasComboBox.setCellFactory(lv -> new ListCell<Kelas>() {
            @Override
            protected void updateItem(Kelas item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNamaKelas());
            }
        });

        kelasComboBox.setButtonCell(new ListCell<Kelas>() {
            @Override
            protected void updateItem(Kelas item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNamaKelas());
            }
        });
        GridPane.setConstraints(kelasLabel, 0, 2);
        GridPane.setConstraints(kelasComboBox, 1, 2);

        // membuat seksi "BIAYA LAYANAN"
        StackPane biayaLayananLabel = createLabelPane("BIAYA LAYANAN");
        GridPane.setConstraints(biayaLayananLabel, 2, 0);

        Label inapLabel = new Label("INAP");
        inapField = new TextField();
        inapField.setEditable(false);
        GridPane.setConstraints(inapLabel, 2, 1);
        GridPane.setConstraints(inapField, 3, 1);

        Label layananLabel = new Label("LAYANAN");
        layananField = new TextField();
        layananField.setEditable(false);
        GridPane.setConstraints(layananLabel, 2, 2);
        GridPane.setConstraints(layananField, 3, 2);

        // membuat seksi "PENANGANAN"
        StackPane penangananLabel = createLabelPane("PENANGANAN");
        GridPane.setConstraints(penangananLabel, 0, 3);

        penangananGroup = new ToggleGroup();
        RadioButton dokterRadio = new RadioButton("Dokter");
        dokterRadio.setToggleGroup(penangananGroup);
        RadioButton bidanRadio = new RadioButton("Bidan");
        bidanRadio.setToggleGroup(penangananGroup);
        GridPane.setConstraints(dokterRadio, 0, 4);
        GridPane.setConstraints(bidanRadio, 1, 4);

        Label biayaPersalinanLabel = new Label("BIAYA PERSALINAN");
        biayaPersalinanField = new TextField();
        biayaPersalinanField.setEditable(false);
        GridPane.setConstraints(biayaPersalinanLabel, 0, 5);
        GridPane.setConstraints(biayaPersalinanField, 1, 5);

        // membuat seksi "HISTORY"
        StackPane historyLabel = createLabelPane("HISTORY");
        GridPane.setConstraints(historyLabel, 2, 3);

        Label masukLabel = new Label("MASUK");
        masukDatePicker = new DatePicker();
        GridPane.setConstraints(masukLabel, 2, 4);
        GridPane.setConstraints(masukDatePicker, 3, 4);

        Label keluarLabel = new Label("KELUAR");
        keluarDatePicker = new DatePicker();
        GridPane.setConstraints(keluarLabel, 2, 5);
        GridPane.setConstraints(keluarDatePicker, 3, 5);

        Label lamaInapLabel = new Label("LAMA INAP");
        lamaInapField = new TextField();
        lamaInapField.setEditable(false);
        GridPane.setConstraints(lamaInapLabel, 2, 6);
        GridPane.setConstraints(lamaInapField, 3, 6);

        // membuat tombol
        Button hitungButton = new Button("HITUNG");

        Button bersihkanButton = new Button("BERSIHKAN KOTAK");
        // mengatur fungsi tombol
        bersihkanButton.setOnAction(e -> handleButtonAction("BERSIHKAN KOTAK"));
        hitungButton.setOnAction(e -> {
            double total = hitungBiaya();
            totalPembayaranField.setText(String.valueOf("Rp." + total));
        });

        Button keluarButton = new Button("KELUAR");
        keluarButton.setOnAction(e -> handleButtonAction("KELUAR"));
        Button rincianButton = new Button("RINCIAN");
        rincianButton.setOnAction(e -> handleButtonAction("RINCIAN"));

        // Membuat Field Total Pembayaran
        totalPembayaranField = new TextField("TOTAL PEMBAYARAN");
        totalPembayaranField.setEditable(true);
        GridPane.setConstraints(totalPembayaranField, 0, 8, 2, 1);

        HBox buttonsBox = new HBox(10, hitungButton, bersihkanButton, keluarButton, rincianButton,
                totalPembayaranField);
        buttonsBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttonsBox, 0, 7, 4, 1);

        // menambahkan semua seksi ke dalam layout utama
        root.getChildren().addAll(
                dataPasienLabel, namaPasienLabel, namaPasienField, kelasLabel, kelasComboBox,
                biayaLayananLabel, inapLabel, inapField, layananLabel, layananField,
                penangananLabel, dokterRadio, bidanRadio, biayaPersalinanLabel, biayaPersalinanField,
                historyLabel, masukLabel, masukDatePicker, keluarLabel, keluarDatePicker, lamaInapLabel, lamaInapField,
                buttonsBox);

        VBox mainLayout = new VBox(10, judul, root);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // fungsi untuk handle tombol
    private void handleButtonAction(String label) {
        switch (label) {
            case "BERSIHKAN KOTAK":
                clearFields();
                break;
            case "HITUNG":
                double totalBiaya = hitungBiaya();
                totalPembayaranField.setText("TOTAL PEMBAYARAN: Rp." + totalBiaya);
                break;
            case "KELUAR":
                keluar();
                break;
            case "RINCIAN":
                RincianBiaya();
                break;
        }
    }

    // Fungsi untuk menampilkan rincian biaya
    private void RincianBiaya() {
        String nama = namaPasienField.getText();

        // Pengecekan field nama
        if (nama == null || nama.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama pasien tidak boleh kosong",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Pengecekan penanganan yang dipilih
        RadioButton selectedPenanganan = (RadioButton) penangananGroup.getSelectedToggle();
        if (selectedPenanganan == null) {
            JOptionPane.showMessageDialog(null, "Pilih Bidan / Dokter",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nakes = selectedPenanganan.getText();

        // Menghitung lama inap
        long hari = hitungLamaInap();
        if (hari <= 0) {
            JOptionPane.showMessageDialog(null, "Lama inap harus lebih dari 0",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mendeklarasikan variabel biayaPersalinan di luar blok if-else
        double biayaPersalinan = 0.0;

        // Menghitung biaya persalinan berdasarkan jenis penanganan
        if (nakes.equals("Dokter")) {
            biayaPersalinan = 100000; // Biaya persalinan jika ditangani oleh dokter
        } else if (nakes.equals("Bidan")) {
            biayaPersalinan = 50000; // Biaya persalinan jika ditangani oleh bidan
        }

        // Menghitung biaya inap
        Kelas selectedKelas = kelasComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas == null) {
            JOptionPane.showMessageDialog(null, "Pilih Kelas", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double biayaInap = selectedKelas.getBiayaInap();

        // Menghitung biaya layanan
        double biayaLayanan = selectedKelas.getBiayaLayanan();

        // Menghitung total biaya
        double total = (biayaInap * hari) + (biayaLayanan * hari) + (biayaPersalinan * hari);

        // Menampilkan rincian biaya dalam dialog JOptionPane
        JOptionPane.showMessageDialog(null,
                "RINCIAN BIAYA PERAWATAN " + nama +
                        "\n" +
                        "\nDirawat Oleh " + nakes +
                        "\n" + selectedKelas +
                        "\nLama Inap : " + hari + " Hari" +
                        "\nBiaya Persalinan : Rp." + biayaPersalinan + " * " + hari + " Hari" +
                        "\nBiaya Inap : Rp." + biayaInap + " * " + hari + " Hari" +
                        "\nBiaya Layanan : Rp." + biayaLayanan + " * " + hari + " Hari" +
                        "\nTotal : Rp." + total);
    }

    // fungsi menghitung biaya pasien
    private double hitungBiaya() {
        if (namaPasienField.getText() == null || namaPasienField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nama pasien tidak boleh kosong");
            alert.showAndWait();
            return 0.0;
        }

        double biayaPersalinan = 0.0;
        long lamaInap = hitungLamaInap(); // Menghitung lama inap terlebih dahulu

        Kelas selectedKelas = kelasComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas != null) {
            double biayaInap = selectedKelas.getBiayaInap() * lamaInap;
            double biayaLayanan = selectedKelas.getBiayaLayanan() * lamaInap;
            inapField.setText("Rp." + selectedKelas.getBiayaInap());
            layananField.setText("Rp." + selectedKelas.getBiayaLayanan());

            if (penangananGroup.getSelectedToggle() != null) {
                RadioButton selectedPenanganan = (RadioButton) penangananGroup.getSelectedToggle();
                if (selectedPenanganan.getText().equals("Dokter")) {
                    biayaPersalinan = 100000;
                } else if (selectedPenanganan.getText().equals("Bidan")) {
                    biayaPersalinan = 50000;
                }
                biayaPersalinanField.setText("Rp." + biayaPersalinan);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Pilih Bidan / Dokter");
                alert.showAndWait();
                return 0.0;
            }

            double biayaNakes = biayaPersalinan * lamaInap; // Menghitung biaya perawatan oleh nakes
            double total = biayaInap + biayaNakes + biayaLayanan;
            totalPembayaranField.setText("TOTAL PEMBAYARAN: Rp." + total); // Menampilkan total biaya keseluruhan
            return total;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Pilih Kelas");
            alert.showAndWait();
            return 0.0;
        }
    }

    // menghitung berapa lama pasien menginap
    private long hitungLamaInap() {
        LocalDate date1 = masukDatePicker.getValue();
        LocalDate date2 = keluarDatePicker.getValue();

        if (date1 != null && date2 != null) {
            if (date1.isAfter(date2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inputan Salah");
                alert.showAndWait();
                return 0;
            }
            long selisih = ChronoUnit.DAYS.between(date1, date2);
            lamaInapField.setText(selisih + " Hari");
            if (selisih == 0) {
                selisih = 1;
                lamaInapField.setText(selisih + " Hari");
            }
            return selisih;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Pilih tanggal terlebih dahulu");
            alert.showAndWait();
            return 0;
        }
    }

    // fungsi tombol keluar
    private void keluar() {
        int dialog = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Keluar?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (dialog == JOptionPane.YES_NO_OPTION) {
            Platform.exit();
        }
    }

    // fungsi tombol bersihkanKotak
    private void clearFields() {
        namaPasienField.clear();
        kelasComboBox.getSelectionModel().clearSelection();
        inapField.clear();
        layananField.clear();
        penangananGroup.selectToggle(null);
        biayaPersalinanField.clear();
        masukDatePicker.setValue(null);
        keluarDatePicker.setValue(null);
        lamaInapField.clear();
        totalPembayaranField.clear();
    }

    private StackPane createLabelPane(String labelText) {
        Label label = new Label(labelText);
        Region background = new Region();
        background.setStyle("-fx-background-color: lightgray;");
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, label);
        StackPane.setAlignment(label, Pos.CENTER_LEFT);
        stackPane.setPadding(new Insets(5, 0, 5, 0));
        return stackPane;
    }

    // Class Kelas Kamar Pasien
    public static class Kelas {
        private final String namaKelas;
        private final double biayaInap;
        private final double biayaLayanan;

        public Kelas(String namaKelas, double biayaInap, double biayaLayanan) {
            this.namaKelas = namaKelas;
            this.biayaInap = biayaInap;
            this.biayaLayanan = biayaLayanan;
        }

        public String getNamaKelas() {
            return namaKelas;
        }

        public double getBiayaInap() {
            return biayaInap;
        }

        public double getBiayaLayanan() {
            return biayaLayanan;
        }

        @Override
        public String toString() {
            return namaKelas;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}