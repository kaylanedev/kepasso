package view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaPrincipal(String nomeUsuario) {
        setTitle("KeyPasso - Gerenciador de Senhas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel Principal Dark
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 14, 24), getWidth(), getHeight(), new Color(4, 6, 12));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panel.setLayout(new BorderLayout());

        // Mensagem de Boas-Vindas
        JLabel lblMensagem = new JLabel("🔑 Bem-vindo ao KeyPasso, " + nomeUsuario + "!", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblMensagem.setForeground(new Color(0, 136, 255));
        
        panel.add(lblMensagem, BorderLayout.CENTER);
        add(panel);
    }

    public TelaPrincipal() {
        this("Usuário");
    }
}