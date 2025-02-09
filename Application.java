package MVC.src;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class Application {
    public static void main(String[] args) {

        Controller.connectDB();

        JFrame frame = new JFrame();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel idLabel = new JLabel("id");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 1);
        panel.add(idLabel, c);

        JTextField idText = new JTextField(5);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(idText, c);

        JLabel infoLabel = new JLabel();
        c.gridx = 1;
        c.gridy = 3;
        panel.add(infoLabel, c);

        JButton fixBtn = new JButton("fix");
        c.gridx = 1;
        c.gridy = 2;
        fixBtn.setEnabled(false);
        panel.add(fixBtn, c);

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(errorLabel, c);

        idText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateID();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateID();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateID();
            }

            private void validateID() {
                String vldText = Controller.validateInput(idText.getText());
                if (vldText != "") {
                    errorLabel.setText(vldText);
                    fixBtn.setEnabled(false);
                    infoLabel.setText("");
                } else {
                    errorLabel.setText("");
                    fixBtn.setEnabled(true);
                    infoLabel.setText(Controller.getInfo(idText.getText()));
                }
            }
        });

        fixBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.fix(idText.getText());
                String vldText = Controller.validateInput(idText.getText());
                if (vldText != "") {
                    errorLabel.setText("");
                    fixBtn.setEnabled(false);
                    infoLabel.setText(Controller.getInfo(idText.getText()));
                } else {
                    errorLabel.setText("");
                    fixBtn.setEnabled(true);
                    infoLabel.setText(Controller.getInfo(idText.getText()));
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
