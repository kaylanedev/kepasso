package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.AuthController;

public class TelaCadastro extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmaSenha;
    private JButton btnCadastrar;
    private JButton btnVoltar;
    private AuthController controller;

    public TelaCadastro() {
        controller = new AuthController();

        setTitle("KeePasso - Novo Cadastro");

        // --- ABRIR EM ECRÃ INTEIRO ---
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Fundo Gradiente
        JPanel panelBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 31, 48), 0, getHeight(), new Color(15, 16, 25));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelBackground.setLayout(new GridBagLayout());
        add(panelBackground);

        // Cartão Central de Cadastro
        JPanel panelCard = new JPanel();
        panelCard.setOpaque(false);
        panelCard.setLayout(new GridBagLayout());
        panelCard.setPreferredSize(new Dimension(400, 540));
        panelBackground.add(panelCard);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        JLabel lblTitulo = new JLabel("Criar Conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelCard.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Preencha seus dados abaixo", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(160, 174, 192));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 15, 0);
        panelCard.add(lblSub, gbc);

        // Campos
        panelCard.add(criarLabel("Nome"), ajustarGbc(gbc, 2));
        txtNome = criarTextField();
        panelCard.add(txtNome, ajustarGbc(gbc, 3));

        panelCard.add(criarLabel("E-mail"), ajustarGbc(gbc, 4));
        txtEmail = criarTextField();
        panelCard.add(txtEmail, ajustarGbc(gbc, 5));

        panelCard.add(criarLabel("Senha"), ajustarGbc(gbc, 6));
        txtSenha = criarPasswordField();
        panelCard.add(txtSenha, ajustarGbc(gbc, 7));

        panelCard.add(criarLabel("Confirmar Senha"), ajustarGbc(gbc, 8));
        txtConfirmaSenha = criarPasswordField();
        panelCard.add(txtConfirmaSenha, ajustarGbc(gbc, 9));

        // Botões
        btnCadastrar = criarBotaoEstilizado("Finalizar Cadastro", new Color(16, 185, 129), new Color(52, 211, 153));
        gbc.gridy = 10; gbc.insets = new Insets(20, 0, 8, 0);
        panelCard.add(btnCadastrar, gbc);

        btnVoltar = criarBotaoOutline("Voltar ao Login");
        gbc.gridy = 11; gbc.insets = new Insets(0, 0, 0, 0);
        panelCard.add(btnVoltar, gbc);

        btnCadastrar.addActionListener(e -> acaoSalvar());
        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(226, 232, 240));
        return label;
    }

    private JTextField criarTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(45, 55, 72));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setPreferredSize(new Dimension(0, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(74, 85, 104), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    private JPasswordField criarPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(45, 55, 72));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setPreferredSize(new Dimension(0, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(74, 85, 104), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    private GridBagConstraints ajustarGbc(GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.insets = (y % 2 == 0) ? new Insets(6, 0, 2, 0) : new Insets(0, 0, 2, 0);
        return gbc;
    }

    private JButton criarBotaoEstilizado(String texto, Color corPadrao, Color corHover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? corHover : corPadrao);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton criarBotaoOutline(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(160, 174, 192));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(160, 174, 192));
            }
        });
        return btn;
    }

    private void acaoSalvar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String confirmaSenha = new String(txtConfirmaSenha.getPassword());

        String resultado = controller.cadastrarUsuario(nome, email, senha, confirmaSenha);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this, "Cadastro validado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Aviso de Validação", JOptionPane.WARNING_MESSAGE);
        }
    }
}