package com.hotelbooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class LoginForm extends JFrame{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JPanel LoginForm;

    public LoginForm() {
        setContentPane(LoginForm);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hotel Booking System");
        setLocationRelativeTo(null);
        setSize(300 , 200);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Staff staff = new Staff("Administrator");
                if (staff.login(txtUsername.getText() , String.valueOf(txtPassword.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    setVisible(false);
                    MainPage mainPage = new MainPage();
                    mainPage.setLocationRelativeTo(null);
                    mainPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null , "Invalid credentials. Please try again.");
                }
            }
        });
    }


}
