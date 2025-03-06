import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class PostApp extends JFrame {
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JPanel profilePanel;
    private Color darkRed = new Color(128, 0, 0);
    private Color brightRed = new Color(220, 20, 60);
    private Color brightYellow = new Color(255, 255, 0);
    private JLabel profilePicLabel;
    private JLabel usernameLabel;
    private JPanel starsPanel;
    private java.util.Map<String, java.util.List<String>> postReplies = new java.util.HashMap<>();

    public PostApp() {
        setTitle("Para Sa Komyuter - SAAN?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(darkRed);

        // Main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(darkRed);

        // Create profile panel
        createProfilePanel();

        // Header
        JLabel headerLabel = new JLabel("SAAN?", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Grid panel for posts
        gridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        gridPanel.setBackground(darkRed);

        // Add sample posts
        for (int i = 0; i < 1; i++) {
            addPost("Je", "Guys paano pumunta sa Forbes Park?");
            addPost("Jo", "Ano sasakyan papunta sa Megamall? galing QC Tatalon");
            addPost("Lemar", "paano pumunta ng Novaliches?");
            addPost("Nur", "paano pumunta sa BGC from Fishermall QC?");
            addPost("Miles", "Galing divisoria ano sasakyan papuntang antipolo");
            addPost("Rafael", "Naliligaw ako paano umuwi sa may SM San Lazaro dito ako 5th ave caloocan");
            addPost("Paul", "From Sampaloc Manila to MOA ano route papunta?");
        }

        // Wrap grid panel in scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(profilePanel, BorderLayout.WEST);

        add(mainPanel);
    }

    private void addPost(String username, String question) {
        JPanel postPanel = new JPanel(new BorderLayout(5, 5));
        postPanel.setBackground(brightRed);
        postPanel.setBorder(BorderFactory.createLineBorder(brightYellow, 2));

        // User info panel
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBackground(brightRed);

        // Profile picture
        JLabel profilePic;
        if (username.equals("Je") || username.equals("Miles") || username.equals("Lemar") || username.equals("Jo") || username.equals("Nur") || username.equals("Rafael") || username.equals("Paul")) {
            String imagePath = username.toLowerCase() + ".jpg";
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            profilePic = new JLabel(new ImageIcon(image));
            profilePic.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profilePic.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showExpandedImage(imageIcon.getImage(), username + "'s Profile Picture");
                }
            });
        } else {
            Image avatarImage = createDefaultAvatar(40);
            profilePic = new JLabel(new ImageIcon(avatarImage));
            profilePic.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profilePic.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showExpandedImage(avatarImage, username + "'s Profile Picture");
                }
            });
        }
        userPanel.add(profilePic);

        // Username
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.WHITE);
        userPanel.add(usernameLabel);

        postPanel.add(userPanel, BorderLayout.NORTH);

        // Question text
        JTextArea questionArea = new JTextArea(question);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setBackground(brightRed);
        questionArea.setForeground(Color.WHITE);
        questionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        postPanel.add(questionArea, BorderLayout.CENTER);

        // Reply button
        JButton replyButton = new JButton("Reply");
        replyButton.setBackground(brightYellow);
        replyButton.addActionListener(e -> {
            JDialog replyDialog = new JDialog(this, "Reply to " + username, true);
            replyDialog.setLayout(new BorderLayout(10, 10));
            replyDialog.getContentPane().setBackground(darkRed);

            JTextArea replyArea = new JTextArea(5, 30);
            replyArea.setLineWrap(true);
            replyArea.setWrapStyleWord(true);
            replyArea.setBackground(brightRed);
            replyArea.setForeground(Color.WHITE);
            replyArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JScrollPane scrollPane = new JScrollPane(replyArea);
            scrollPane.setBorder(BorderFactory.createLineBorder(brightYellow, 2));

            JButton submitButton = new JButton("Submit");
            submitButton.setBackground(brightYellow);
            submitButton.addActionListener(submitEvent -> {
                String reply = replyArea.getText().trim();
                if (!reply.isEmpty()) {
                    String postKey = username + ": " + question;
                    if (!postReplies.containsKey(postKey)) {
                        postReplies.put(postKey, new java.util.ArrayList<>());
                    }
                    postReplies.get(postKey).add(reply);
                    JOptionPane.showMessageDialog(replyDialog, "Reply submitted: " + reply);
                    replyDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(replyDialog, "Please enter a reply", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton viewRepliesButton = new JButton("View Replies");
            viewRepliesButton.setBackground(brightYellow);
            viewRepliesButton.addActionListener(viewEvent -> {
                String postKey = username + ": " + question;
java.util.List<String> replies = postReplies.getOrDefault(postKey, new java.util.ArrayList<>());
                
                JDialog viewDialog = new JDialog(this, "Replies to " + username, true);
                viewDialog.setLayout(new BorderLayout(10, 10));
                viewDialog.getContentPane().setBackground(darkRed);

                JTextArea repliesArea = new JTextArea(10, 30);
                repliesArea.setEditable(false);
                repliesArea.setLineWrap(true);
                repliesArea.setWrapStyleWord(true);
                repliesArea.setBackground(brightRed);
                repliesArea.setForeground(Color.WHITE);
                
                if (replies.isEmpty()) {
                    repliesArea.setText("No replies yet.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < replies.size(); i++) {
                        sb.append("Reply ").append(i + 1).append(":\n");
                        sb.append(replies.get(i)).append("\n\n");
                    }
                    repliesArea.setText(sb.toString());
                }

                JScrollPane repliesScrollPane = new JScrollPane(repliesArea);
                scrollPane.setBorder(BorderFactory.createLineBorder(brightYellow, 2));

                JButton closeButton = new JButton("Close");
                closeButton.setBackground(brightYellow);
                closeButton.addActionListener(event -> viewDialog.dispose());

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(darkRed);
                buttonPanel.add(closeButton);

                viewDialog.add(scrollPane, BorderLayout.CENTER);
                viewDialog.add(buttonPanel, BorderLayout.SOUTH);
                viewDialog.setSize(400, 300);
                viewDialog.setLocationRelativeTo(this);
                viewDialog.setVisible(true);
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(darkRed);
            buttonPanel.add(viewRepliesButton);

            replyDialog.add(scrollPane, BorderLayout.CENTER);
buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(darkRed);
            buttonPanel.add(submitButton);
            replyDialog.add(buttonPanel, BorderLayout.SOUTH);

            replyDialog.setSize(400, 300);
            replyDialog.setLocationRelativeTo(this);
            replyDialog.setVisible(true);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(brightRed);
        buttonPanel.add(replyButton);
        postPanel.add(buttonPanel, BorderLayout.SOUTH);

        gridPanel.add(postPanel);
    }

    private void createProfilePanel() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(brightRed);
        profilePanel.setBorder(BorderFactory.createLineBorder(brightYellow, 2));
        profilePanel.setPreferredSize(new Dimension(200, 0));
    
        // Profile picture
        try {
            String imagePath = "je.jpg";
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            profilePicLabel = new JLabel(new ImageIcon(image));
            profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            profilePicLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profilePicLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showExpandedImage(imageIcon.getImage(), "Profile Picture");
                }
            });
        } catch (Exception e) {
            // Fallback to default avatar if image loading fails
            profilePicLabel = new JLabel(new ImageIcon(createDefaultAvatar(80)));
            profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            profilePicLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profilePicLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showExpandedImage(createDefaultAvatar(400), "Profile Picture");
                }
            });
        }
        profilePanel.add(Box.createVerticalStrut(20));
        profilePanel.add(profilePicLabel);
    
        // Username with icon
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setBackground(brightRed);
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        userIcon.setForeground(Color.WHITE);
        usernameLabel = new JLabel("Je");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        usernamePanel.add(userIcon);
        usernamePanel.add(usernameLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(usernamePanel);
    
        // Trust Rating Stars
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ratingPanel.setBackground(brightRed);
        JLabel starIcon = new JLabel("⭐");
        starIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        starIcon.setForeground(brightYellow);
        ratingPanel.add(starIcon);
        starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        starsPanel.setBackground(brightRed);
        for (int i = 0; i < 5; i++) {
            JLabel starLabel = new JLabel("★");
            starLabel.setForeground(brightYellow);
            starLabel.setFont(new Font("Arial", Font.BOLD, 22));
            starsPanel.add(starLabel);
        }
        ratingPanel.add(starsPanel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(ratingPanel);
    
        // Comments Section
        JPanel commentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        commentsPanel.setBackground(brightRed);
        JLabel commentIcon = new JLabel("💬");
        commentIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        commentIcon.setForeground(Color.WHITE);
        JLabel commentsLabel = new JLabel("Comments (23)");
        commentsLabel.setForeground(Color.WHITE);
        commentsLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        commentsPanel.add(commentIcon);
        commentsPanel.add(commentsLabel);
        profilePanel.add(Box.createVerticalStrut(20));
        profilePanel.add(commentsPanel);
    
        // Saan Magkano? Section
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pricePanel.setBackground(brightRed);
        JLabel priceIcon = new JLabel("💰");
        priceIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        priceIcon.setForeground(Color.WHITE);
        JLabel priceLabel = new JLabel("Saan Magkano?");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        pricePanel.add(priceIcon);
        pricePanel.add(priceLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(pricePanel);
    
        // Komyuter Experience Section
        JPanel expPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        expPanel.setBackground(brightRed);
        JLabel expIcon = new JLabel("🚌");
        expIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        expIcon.setForeground(Color.WHITE);
        JLabel experienceLabel = new JLabel("User Experience");
        experienceLabel.setForeground(Color.WHITE);
        experienceLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        expPanel.add(expIcon);
        expPanel.add(experienceLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(expPanel);
    
        // Travel Time Section
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timePanel.setBackground(brightRed);
        JLabel timeIcon = new JLabel("⏱️");
        timeIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        timeIcon.setForeground(Color.WHITE);
        JLabel timeLabel = new JLabel("Travel Time");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        timePanel.add(timeLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(timePanel);
    
        // Your Route Section
        JPanel routePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        routePanel.setBackground(brightRed);
        JLabel routeIcon = new JLabel("🗺️");
        routeIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        routeIcon.setForeground(Color.WHITE);
        JLabel routeLabel = new JLabel("Your Route");
        routeLabel.setForeground(Color.WHITE);
        routeLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        routePanel.add(routeIcon);
        routePanel.add(routeLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(routePanel);
        profilePanel.add(Box.createVerticalStrut(20));
    }

    private Image createDefaultAvatar(int size) {
        // Create a simple circular avatar

        BufferedImage avatar = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = avatar.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(0, 0, size, size);
        g2d.dispose();
        return avatar;
    }

    private void showExpandedImage(Image image, String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(darkRed);

        // Scale the image to a larger size while maintaining aspect ratio
        int maxSize = 400;
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);
        double scale = Math.min((double) maxSize / originalWidth, (double) maxSize / originalHeight);
        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBorder(BorderFactory.createLineBorder(brightYellow, 2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(darkRed);
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(brightYellow);
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PostApp().setVisible(true);
        });
    }
}