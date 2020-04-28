import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author pablo
 */
public class CompradorAgentGui extends javax.swing.JFrame {

    private CompradorAgent myAgent;

    public CompradorAgentGui(CompradorAgent a) {

        myAgent=a;

        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        /*Ocultamos campos*/
        puja1.setVisible(false);
        puja2.setVisible(false);
        puja3.setVisible(false);
        puja4.setVisible(false);
        puja5.setVisible(false);
        puja6.setVisible(false);
        puja7.setVisible(false);
        puja8.setVisible(false);

        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);
        estado1.setVisible(false);

        salir1.setVisible(false);
        salir2.setVisible(false);
        salir3.setVisible(false);
        salir4.setVisible(false);
        salir5.setVisible(false);
        salir6.setVisible(false);
        salir7.setVisible(false);
        salir8.setVisible(false);

        /*Acción botón*/
        jButton1.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = libro.getText().trim();
                    String price = precio.getText().trim();
                    myAgent.updateCatalogue(title, Integer.parseInt(price));
                    libro.setText("");
                    precio.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(CompradorAgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        libro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        precio = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        puja1 = new javax.swing.JLabel();
        estado1 = new javax.swing.JLabel();
        salir1 = new javax.swing.JButton();
        puja2 = new javax.swing.JLabel();
        salir2 = new javax.swing.JButton();
        estado2 = new javax.swing.JLabel();
        salir3 = new javax.swing.JButton();
        salir4 = new javax.swing.JButton();
        salir6 = new javax.swing.JButton();
        salir5 = new javax.swing.JButton();
        salir7 = new javax.swing.JButton();
        salir8 = new javax.swing.JButton();
        estado4 = new javax.swing.JLabel();
        puja3 = new javax.swing.JLabel();
        estado3 = new javax.swing.JLabel();
        puja4 = new javax.swing.JLabel();
        puja5 = new javax.swing.JLabel();
        puja6 = new javax.swing.JLabel();
        puja8 = new javax.swing.JLabel();
        puja7 = new javax.swing.JLabel();
        estado5 = new javax.swing.JLabel();
        estado6 = new javax.swing.JLabel();
        estado7 = new javax.swing.JLabel();
        estado8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(23, 138, 28));
        jPanel1.setBorder(null);
        jPanel1.setToolTipText("");
        jPanel1.setMaximumSize(new java.awt.Dimension(463, 383));
        jPanel1.setMinimumSize(new java.awt.Dimension(463, 383));

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LIBRO:");

        libro.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N
        libro.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(254, 254, 254));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PRECIO MÁX");

        precio.setFont(new java.awt.Font("Cantarell", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(71, 103, 176));
        jButton1.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(254, 254, 254));
        jButton1.setText("PUJAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(libro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(libro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        puja1.setText("libro1");

        estado1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado1.setText("jLabel4");

        salir1.setText("Salir1");

        puja2.setText("libro2");

        salir2.setText("Salir1");

        estado2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado2.setText("jLabel4");

        salir3.setText("Salir1");

        salir4.setText("Salir1");

        salir6.setText("Salir1");

        salir5.setText("Salir1");

        salir7.setText("Salir1");

        salir8.setText("Salir1");

        estado4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado4.setText("jLabel4");

        puja3.setText("libro1");

        estado3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado3.setText("jLabel4");

        puja4.setText("libro2");

        puja5.setText("libro2");

        puja6.setText("libro2");

        puja8.setText("libro2");

        puja7.setText("libro2");

        estado5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado5.setText("jLabel4");

        estado6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado6.setText("jLabel4");

        estado7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado7.setText("jLabel4");

        estado8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estado8.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(puja2)
                                                        .addComponent(puja1))
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(estado2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(salir2))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(estado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(salir1))))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja3)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir3))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja4)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado4, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir4))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja5)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado5, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir5))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja6)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado6, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir6))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja7)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado7, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir7))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(puja8)
                                                .addGap(26, 26, 26)
                                                .addComponent(estado8, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(salir8)))
                                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(puja1)
                                        .addComponent(estado1)
                                        .addComponent(salir1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(puja2)
                                        .addComponent(estado2)
                                        .addComponent(salir2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir3)
                                        .addComponent(puja3)
                                        .addComponent(estado3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir4)
                                        .addComponent(puja4)
                                        .addComponent(estado4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir5)
                                        .addComponent(puja5)
                                        .addComponent(estado5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir6)
                                        .addComponent(puja6)
                                        .addComponent(estado6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir7)
                                        .addComponent(puja7)
                                        .addComponent(estado7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(salir8)
                                        .addComponent(puja8)
                                        .addComponent(estado8))
                                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>



    // Variables declaration - do not modify
    private javax.swing.JLabel estado1;
    private javax.swing.JLabel estado2;
    private javax.swing.JLabel estado3;
    private javax.swing.JLabel estado4;
    private javax.swing.JLabel estado5;
    private javax.swing.JLabel estado6;
    private javax.swing.JLabel estado7;
    private javax.swing.JLabel estado8;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField libro;
    private javax.swing.JTextField precio;
    private javax.swing.JLabel puja1;
    private javax.swing.JLabel puja2;
    private javax.swing.JLabel puja3;
    private javax.swing.JLabel puja4;
    private javax.swing.JLabel puja5;
    private javax.swing.JLabel puja6;
    private javax.swing.JLabel puja7;
    private javax.swing.JLabel puja8;
    private javax.swing.JButton salir1;
    private javax.swing.JButton salir2;
    private javax.swing.JButton salir3;
    private javax.swing.JButton salir4;
    private javax.swing.JButton salir5;
    private javax.swing.JButton salir6;
    private javax.swing.JButton salir7;
    private javax.swing.JButton salir8;
    // End of variables declaration
}
