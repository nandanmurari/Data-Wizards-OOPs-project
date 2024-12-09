import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class OopsdealApp extends JFrame {
    private Cart cart;
    private Coupon coupon;
    private JTextField totalPriceField;
    private JTextField couponCodeField;

    public OopsdealApp() {
        setTitle("Oopsdeal - Your Smart Shopping Partner");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        cart = new Cart();
        coupon = new Coupon("DISCOUNT25", 899.5, 500.0);

        // Products with images
        Product product1 = new Product(1, "Laptop", 2499.0, "C:\\Users\\nanda\\OneDrive\\Pictures\\Screenshot (3).png");
        Product product2 = new Product(2, "Smartphone", 1099.0, "C:\\Users\\nanda\\OneDrive\\Pictures\\Screenshot (2).png");
        cart.addProduct(product1, 1);
        cart.addProduct(product2, 1);

        // UI Components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonsPanel.setBackground(Color.BLACK);

        JButton viewCartButton = createStyledButton("View Cart");
        JButton applyCouponButton = createStyledButton("Apply Coupon");
        JButton orderButton = createStyledButton("Place Order");
        JButton userInfoButton = createStyledButton("User Info");

        buttonsPanel.add(viewCartButton);
        buttonsPanel.add(applyCouponButton);
        buttonsPanel.add(orderButton);
        buttonsPanel.add(userInfoButton);

        // Add background images
        JPanel imagesPanel = new JPanel(new GridLayout(1, 2));
        imagesPanel.setBackground(Color.BLACK);

        JLabel leftImage = new JLabel();
        JLabel rightImage = new JLabel();

        ImageIcon backgroundImage1 = new ImageIcon("C:\\Users\\nanda\\OneDrive\\Pictures\\image0_0.jpg");
        ImageIcon backgroundImage2 = new ImageIcon("C:\\Users\\nanda\\OneDrive\\Pictures\\Screenshot (4).png");

        leftImage.setIcon(new ImageIcon(backgroundImage1.getImage().getScaledInstance(500, 600, Image.SCALE_SMOOTH)));
        rightImage.setIcon(new ImageIcon(backgroundImage2.getImage().getScaledInstance(500, 600, Image.SCALE_SMOOTH)));

        imagesPanel.add(leftImage);
        imagesPanel.add(rightImage);

        // Add total price and coupon fields
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(new JLabel("Total Price:", JLabel.RIGHT));
        totalPriceField = new JTextField(15);
        totalPriceField.setEditable(false);
        bottomPanel.add(totalPriceField);

        bottomPanel.add(new JLabel("Coupon Code:", JLabel.RIGHT));
        couponCodeField = new JTextField(15);
        bottomPanel.add(couponCodeField);

        // Button actions
        viewCartButton.addActionListener(e -> showCartDetails());
        applyCouponButton.addActionListener(e -> applyCoupon());
        orderButton.addActionListener(e -> placeOrder());
        userInfoButton.addActionListener(e -> showUserInfo());

        // Add panels to main panel
        mainPanel.add(buttonsPanel, BorderLayout.WEST);
        mainPanel.add(imagesPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.WHITE, 2, true));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(150, 50));

        // Oval shape
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        return button;
    }

    private void showCartDetails() {
        JFrame cartFrame = new JFrame("Cart Details");
        cartFrame.setSize(800, 400);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.setLayout(new BorderLayout());

        JPanel cartPanel = new JPanel(new GridLayout(1, 2));
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();

            JPanel productPanel = new JPanel(new BorderLayout());
            ImageIcon productImage = new ImageIcon(product.getImagePath());
            JLabel imageLabel = new JLabel(new ImageIcon(productImage.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
            JLabel priceLabel = new JLabel(product.getName() + " - $" + product.getPrice(), JLabel.CENTER);
            priceLabel.setForeground(Color.BLACK);
            priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

            productPanel.add(imageLabel, BorderLayout.CENTER);
            productPanel.add(priceLabel, BorderLayout.SOUTH);
            cartPanel.add(productPanel);
        }

        JPanel pricePanel = new JPanel(new BorderLayout());
        JTextArea priceDetails = new JTextArea();
        priceDetails.setEditable(false);

        double totalPrice = cart.getTotalPrice();
        double discountedPrice = coupon.isApplicable(totalPrice) ? coupon.applyDiscount(totalPrice) : totalPrice;
        priceDetails.setText("Total Price Before Discount: $" + totalPrice + "\n"
                + "Discount Applied: $" + (totalPrice - discountedPrice) + "\n"
                + "Final Price: $" + discountedPrice);
        pricePanel.add(priceDetails, BorderLayout.NORTH);

        cartFrame.add(cartPanel, BorderLayout.CENTER);
        cartFrame.add(pricePanel, BorderLayout.SOUTH);
        cartFrame.setVisible(true);
    }

    private void applyCoupon() {
        String enteredCode = couponCodeField.getText().trim();
        if (coupon.getCode().equalsIgnoreCase(enteredCode)) {
            double totalPrice = cart.getTotalPrice();
            double discountedPrice = coupon.applyDiscount(totalPrice);
            totalPriceField.setText(String.format("%.2f", discountedPrice));
            JOptionPane.showMessageDialog(this, "Coupon applied successfully! New Total Price: $" + discountedPrice);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Coupon Code");
        }
    }

    private void placeOrder() {
        double totalPrice = cart.getTotalPrice();
        double discountedPrice = coupon.applyDiscount(totalPrice);
        JOptionPane.showMessageDialog(this, "Order placed successfully!\nFinal Price: $" + discountedPrice);
    }

    private void showUserInfo() {
        JOptionPane.showMessageDialog(this, "User Info:\nName: Vishal\nEmail: vishalp@gmail.com");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OopsdealApp().setVisible(true));
    }
}

class Product {
    private int productId;
    private String name;
    private double price;
    private String imagePath;

    public Product(int productId, String name, double price, String imagePath) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Coupon {
    private String code;
    private double discount;
    private double minPurchase;

    public Coupon(String code, double discount, double minPurchase) {
        this.code = code;
        this.discount = discount;
        this.minPurchase = minPurchase;
    }

    public String getCode() {
        return code;
    }

    public double applyDiscount(double total) {
        return total >= minPurchase ? total - discount : total;
    }

    public boolean isApplicable(double total) {
        return total >= minPurchase;
    }
}

class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
