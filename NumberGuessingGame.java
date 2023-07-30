import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGame extends JFrame {
    private JLabel promptLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private int randomNumber;
    private int maxAttempts;
    private int attemptsLeft;
    private int score;
    private int round;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        promptLabel = new JLabel("Guess the number between 1-100: ");
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        resultLabel = new JLabel("");
        attemptsLabel = new JLabel("");
        scoreLabel = new JLabel("");

        guessButton.addActionListener(new GuessButtonListener());

        add(promptLabel);
        add(guessField);
        add(guessButton);
        add(resultLabel);
        add(attemptsLabel);
        add(scoreLabel);

        initializeGame();
        
        // Increase font size for labels and button
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        promptLabel.setFont(labelFont);
        attemptsLabel.setFont(labelFont);
        scoreLabel.setFont(labelFont);
        resultLabel.setFont(labelFont);
        guessButton.setFont(labelFont);

        // Increase the size of the text field
        guessField.setFont(labelFont);
        guessField.setPreferredSize(new Dimension(200, 30)); // Increase width and height

        // Increase the button size
        Dimension buttonSize = new Dimension(150, 50);
        guessButton.setPreferredSize(buttonSize);

        // Increase the window size
        setPreferredSize(new Dimension(400, 250));

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void initializeGame() {
        maxAttempts = 5;           //maximum attempts to guess the number
        attemptsLeft = maxAttempts;
        score = 0;
        round = 1;
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        scoreLabel.setText("Score: " + score);
        resultLabel.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        guessField.setText("");
        guessField.requestFocus();
        randomNumber = generateRandomNumber(1, 100);   //limit of the generated random number
    }

    private int generateRandomNumber(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int guess;
            try {
                guess = Integer.parseInt(guessField.getText());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid guess. Please enter a number.");
                return;
            }

            if (guess < 0) {
                resultLabel.setText("Invalid number. Please enter a positive number.");
                return;
            }

            attemptsLeft--;

            if (guess == randomNumber) {
                resultLabel.setText("Congratulations! You guessed the correct number." +randomNumber);
                int roundScore = calculateScore(maxAttempts, attemptsLeft);
                score += roundScore;
                scoreLabel.setText("Your score is: " + score);
                guessField.setEnabled(false);
                guessButton.setEnabled(false);

                if (round < 3) {
                    int option = JOptionPane.showConfirmDialog(null, "Round " + round + " is over. Your score: " + score + "\n\nPlay the next round?", "Round Over", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        round++;
                        initializeGame();
                    } else {
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Game Over. Your final score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } else if (guess < randomNumber) {
                resultLabel.setText("The target number is higher than your guess.");
            } else {
                resultLabel.setText("The target number is lower than your guess.");
            }

            if (attemptsLeft == 0) {
                resultLabel.setText("Game over! You've used all your attempts.");
                guessField.setEnabled(false);
                guessButton.setEnabled(false);

                if (round < 3) {
                    int option = JOptionPane.showConfirmDialog(null, "Round " + round + " is over. Your score: " + score + "\n\nPlay the next round?", "Round Over", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        round++;
                        initializeGame();
                    } else {
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Game Over. Your final score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }

            attemptsLabel.setText("Attempts Left: " + attemptsLeft);
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    private static int calculateScore(int maxAttempts, int attemptsLeft) {
        int maxScore = 100;
        int score = maxScore - (attemptsLeft * (maxScore / maxAttempts));
        return score;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
