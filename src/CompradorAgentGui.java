import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class CompradorAgentGui extends javax.swing.JFrame {

    private CompradorAgent myAgent;
    private int incremento=20;
    private ArrayList<Puja> pujas;

    public CompradorAgentGui(CompradorAgent a) {

        myAgent=a;
        pujas = new ArrayList<>();

        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(myAgent.getLocalName());


        /*Acción botón*/
        jButton1.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = libro.getText().trim();
                    String price = precio.getText().trim();

                    if(! myAgent.getTodos().containsKey(title)){
                        myAgent.updateCatalogue(title, Integer.parseInt(price));
                        libro.setText("");
                        precio.setText("");

                        /**
                         * Colocamos los campos de la subasta
                         */
                        JTextField puja = new JTextField();
                        puja.setText(title + " " + price);
                        puja.setFont(new java.awt.Font("Cantarell", 1, 14));
                        puja.setSize(80,30);
                        puja.setLocation(5, incremento);
                        puja.setEnabled(false);
                        puja.setAutoscrolls(true);
                        puja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        puja.setForeground(new java.awt.Color(0, 0, 0));
                        jPanel2.add(puja);

                        JTextField estado = new JTextField();
                        estado.setText("En espera");
                        estado.setFont(new java.awt.Font("Cantarell", 1, 14));
                        estado.setSize(250,30);
                        estado.setLocation(1, incremento);
                        estado.setEnabled(false);
                        estado.setAutoscrolls(true);
                        estado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        estado.setForeground(new java.awt.Color(0, 0, 0));
                        jPanel3.add(estado);

                        JButton aceptar = new JButton();
                        aceptar.setText("SALIR");
                        aceptar.setFont(new java.awt.Font("Cantarell", 1, 14));
                        aceptar.setForeground(new java.awt.Color(254, 254, 254));
                        aceptar.setBackground( new Color(71,103,176));
                        aceptar.setSize(80,30);
                        aceptar.setLocation(1, incremento);
                        aceptar.repaint();
                        jPanel4.add(aceptar);
                        jPanel4.repaint();

                        aceptar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                salirActionPerformed(evt,title, estado, aceptar,puja);
                            }
                        });

                        incremento+=40;
                        Puja aux = new Puja(title,puja,estado,aceptar);
                        pujas.add(aux);
                    }
                    else{
                        libro.setText("");
                        precio.setText("");
                        JOptionPane.showMessageDialog(CompradorAgentGui.this, "La subasta de " + title + " ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(CompradorAgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } );
    }

    /**
     * Actualiza estados pujas
     */
    public void actualizarEstado(String libro,String estado){
        for(Puja aux: pujas){
            if(aux.getLibro().equals(libro)){
                aux.getEstado().setText(estado);
                this.repaint();
            }
        }
    }

    public void actualizarEstadoPerdedor(String libro){
        for(Puja aux: pujas){
            if(aux.getLibro().equals(libro)){
                aux.getEstado().setText(aux.getEstado().getText() + ". Perdedor de ronda");
                this.repaint();
            }
        }
    }

    public void actualizarEstadoGanador(String libro){
        for(Puja aux: pujas){
            if(aux.getLibro().equals(libro)){
                aux.getEstado().setText(aux.getEstado().getText() + ". Ganador de ronda");
                this.repaint();
            }
        }
    }

    public void terminar(String libro){
        for(Puja aux: pujas){
            if(aux.getLibro().equals(libro)){
                aux.getAceptar().setVisible(false);
                aux.getEstado().setBackground(new Color(199, 35, 21));
                this.repaint();
            }
        }
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
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 103, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 274, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 102, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void salirActionPerformed(java.awt.event.ActionEvent evt, String libro, JTextField estado, JButton b, JTextField puja){

        /**
         * Salir de la subasta
         */
        if(b.getText().equals("SALIR")){
            estado.setText("Salido");
            b.setText("ENTRAR");
            b.repaint();
            myAgent.salirSubasta(libro);
        }
        /**
         * Entrar de nuevo en la subasta
         */
        else if(b.getText().equals("ENTRAR")){
            estado.setText("En espera");
            b.setText("SALIR");
            b.repaint();
            int precio = Integer.parseInt(puja.getText().split(" ")[1]);
            myAgent.updateCatalogue(libro,precio);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField libro;
    private javax.swing.JTextField precio;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;

    // End of variables declaration

    private class Puja{
        private String libro;
        private JTextField puja;
        private JTextField estado;
        private JButton aceptar;

        public Puja(String libro, JTextField puja, JTextField estado, JButton aceptar) {
            this.libro = libro;
            this.puja = puja;
            this.estado = estado;
            this.aceptar = aceptar;
        }

        public String getLibro() {
            return libro;
        }

        public JTextField getPuja() {
            return puja;
        }

        public JTextField getEstado() {
            return estado;
        }

        public JButton getAceptar() {
            return aceptar;
        }

        public void setLibro(String libro) {
            this.libro = libro;
        }

        public void setPuja(JTextField puja) {
            this.puja = puja;
        }

        public void setEstado(JTextField estado) {
            this.estado = estado;
        }

        public void setAceptar(JButton aceptar) {
            this.aceptar = aceptar;
        }
    }
}
