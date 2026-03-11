import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class ResortRoomManagementSystem extends JFrame implements ActionListener {

    // Components
    private JTextField tfName, tfPhone, tfDays, tfPeople;
    private JComboBox<String> cbRoomType;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JButton btnBook, btnClear, btnDelete;
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JLabel lblTotal, lblTotalAmount;

    // Room prices
    private final int DELUXE_PRICE = 5000;
    private final int SUITE_PRICE = 8000;
    private final int STANDARD_PRICE = 3000;
    private final int PREMIUM_PRICE = 6500;

    public ResortRoomManagementSystem() {
        // Frame setup
        setTitle("Resort Room Management System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        // Title Label
        JLabel lblTitle = new JLabel("RESORT ROOM BOOKING");
        lblTitle.setBounds(300, 10, 400, 40);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(25, 25, 112));
        add(lblTitle);

        // Customer Name
        JLabel lblName = new JLabel("Customer Name:");
        lblName.setBounds(50, 70, 120, 25);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(170, 70, 200, 25);
        add(tfName);

        // Gender Selection
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(50, 110, 120, 25);
        add(lblGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(170, 110, 80, 25);
        rbMale.setBackground(new Color(240, 248, 255));
        add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(260, 110, 100, 25);
        rbFemale.setBackground(new Color(240, 248, 255));
        add(rbFemale);

        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        // Phone Number
        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 150, 120, 25);
        add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(170, 150, 200, 25);
        add(tfPhone);

        // Number of People
        JLabel lblPeople = new JLabel("Number of People:");
        lblPeople.setBounds(50, 190, 120, 25);
        add(lblPeople);

        tfPeople = new JTextField();
        tfPeople.setBounds(170, 190, 200, 25);
        add(tfPeople);

        // Room Type ComboBox
        JLabel lblRoom = new JLabel("Room Type:");
        lblRoom.setBounds(50, 230, 120, 25);
        add(lblRoom);

        String[] roomTypes = {"Standard - Rs.3000", "Deluxe - Rs.5000",
                "Premium - Rs.6500", "Suite - Rs.8000"};
        cbRoomType = new JComboBox<>(roomTypes);
        cbRoomType.setBounds(170, 230, 200, 25);
        add(cbRoomType);

        // Number of Days
        JLabel lblDays = new JLabel("Number of Days:");
        lblDays.setBounds(50, 270, 120, 25);
        add(lblDays);

        tfDays = new JTextField();
        tfDays.setBounds(170, 270, 200, 25);
        add(tfDays);

        // Buttons
        btnBook = new JButton("Book Room");
        btnBook.setBounds(50, 320, 120, 35);
        btnBook.setBackground(new Color(34, 139, 34));
        btnBook.setForeground(Color.WHITE);
        btnBook.addActionListener(this);
        add(btnBook);

        btnClear = new JButton("Clear");
        btnClear.setBounds(180, 320, 100, 35);
        btnClear.setBackground(new Color(255, 140, 0));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(this);
        add(btnClear);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(290, 320, 100, 35);
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(this);
        add(btnDelete);

        // Table for displaying bookings
        String[] columnNames = {"Name", "Gender", "Phone", "People", "Room Type", "Days", "Total Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBounds(50, 380, 850, 180);
        add(scrollPane);

        // Total Amount Display
        lblTotal = new JLabel("Total Revenue:");
        lblTotal.setBounds(600, 320, 120, 25);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTotal);

        lblTotalAmount = new JLabel("Rs. 0");
        lblTotalAmount.setBounds(720, 320, 150, 25);
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalAmount.setForeground(new Color(0, 100, 0));
        add(lblTotalAmount);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBook) {
            bookRoom();
        } else if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnDelete) {
            deleteBooking();
        }
    }

    private void bookRoom() {
        // Validation
        if (tfName.getText().isEmpty() || tfPhone.getText().isEmpty() ||
                tfDays.getText().isEmpty() || tfPeople.getText().isEmpty() ||
                (!rbMale.isSelected() && !rbFemale.isSelected())) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String name = tfName.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String phone = tfPhone.getText();
            int people = Integer.parseInt(tfPeople.getText());
            String roomType = (String) cbRoomType.getSelectedItem();
            int days = Integer.parseInt(tfDays.getText());

            // Calculate amount based on room type
            int roomPrice = 0;
            if (roomType.contains("Standard")) {
                roomPrice = STANDARD_PRICE;
            } else if (roomType.contains("Deluxe")) {
                roomPrice = DELUXE_PRICE;
            } else if (roomType.contains("Premium")) {
                roomPrice = PREMIUM_PRICE;
            } else if (roomType.contains("Suite")) {
                roomPrice = SUITE_PRICE;
            }

            int totalAmount = roomPrice * days;

            // Add to table
            Object[] rowData = {name, gender, phone, people, roomType, days, "Rs." + totalAmount};
            tableModel.addRow(rowData);

            // Update total revenue
            updateTotalRevenue();

            // Clear fields after booking
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Room Booked Successfully!\n" +
                            "Guests: " + people + " people\n" +
                            "Total: Rs." + totalAmount,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for days and people!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        tfName.setText("");
        tfPhone.setText("");
        tfDays.setText("");
        tfPeople.setText("");
        genderGroup.clearSelection();
        cbRoomType.setSelectedIndex(0);
    }

    private void deleteBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete!",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.removeRow(selectedRow);
        updateTotalRevenue();
        JOptionPane.showMessageDialog(this, "Booking Deleted Successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTotalRevenue() {
        int total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = (String) tableModel.getValueAt(i, 6);
            // Remove "Rs." and parse
            amountStr = amountStr.replace("Rs.", "").trim();
            total += Integer.parseInt(amountStr);
        }
        lblTotalAmount.setText("Rs. " + total);
    }

    public static void main(String[] args) {
        new ResortRoomManagementSystem();
    }
}