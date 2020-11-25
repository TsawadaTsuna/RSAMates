import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Ventana extends JFrame {
    class Menu extends JPanel {
        RSA rsa;
        int p;
        int q;
        JList<String> listaAlice, listaBob;
        JLabel decAlice,decBob;
        JTextField textAlice,textBob;
        JButton enviarAlice,enviarBob,decifAlice,decifBob;
        public Menu(){
            super();
            ArrayList<Integer> primos=new ArrayList<>();
            primos.add(101);
            primos.add(103);
            primos.add(107);
            primos.add(109);
            primos.add(113);
            this.setPreferredSize(new Dimension(800,800));
            this.setLayout(null);
            Random ran=new Random();
            int p=ran.nextInt(primos.size());
            int q=ran.nextInt(primos.size());
            while (q==p){
                q=ran.nextInt(primos.size());
            }
            rsa=new RSA(primos.get(p),primos.get(q));
            System.out.println(rsa.n);
            System.out.println(rsa.e);
            System.out.println(rsa.d);
            this.p=primos.get(p);
            this.q=primos.get(q);
            DefaultListModel<String> modAlice=new DefaultListModel<>();
            DefaultListModel<String> modBob=new DefaultListModel<>();
            listaAlice=new JList<>(modAlice);
            listaBob=new JList<>(modBob);
            listaAlice.setBounds(100,100,200,400);
            listaBob.setBounds(500,100,200,400);
            decAlice=new JLabel();
            decBob=new JLabel();
            decAlice.setBounds(100,500,200,100);
            decBob.setBounds(500,500,200,100);
            textAlice=new JTextField();
            textBob=new JTextField();
            textAlice.setBounds(100,600,200,50);
            textBob.setBounds(500,600,200,50);
            enviarAlice=new JButton("Enviar");
            enviarBob=new JButton("Enviar");
            enviarAlice.setBounds(100,700,100,70);
            enviarBob.setBounds(500,700,100,70);
            decifAlice=new JButton("Decifrar");
            decifBob=new JButton("Decifrar");
            decifAlice.setBounds(200,700,100,70);
            decifBob.setBounds(600,700,100,70);
            enviarAlice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!textAlice.getText().isEmpty()){
                        modBob.addElement(rsa.cifrar(textAlice.getText(),rsa.e,rsa.n));
                    }
                }
            });
            enviarBob.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!textBob.getText().isEmpty()){
                        modAlice.addElement(rsa.cifrar(textBob.getText(),rsa.e,rsa.n));
                    }
                }
            });
            decifAlice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!listaAlice.isSelectionEmpty()){
                        decAlice.setText(rsa.decifrar(listaAlice.getSelectedValue(),rsa.d,rsa.n));
                    }
                }
            });
            decifBob.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!listaBob.isSelectionEmpty()){
                        decBob.setText(rsa.decifrar(listaBob.getSelectedValue(),rsa.d,rsa.n));
                    }
                }
            });

            this.add(listaAlice);
            this.add(listaBob);
            this.add(decAlice);
            this.add(decBob);
            this.add(textAlice);
            this.add(textBob);
            this.add(enviarAlice);
            this.add(enviarBob);
            this.add(decifAlice);
            this.add(decifBob);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Llaves:",380,100);
            g.drawString("n: "+rsa.n,380,120);
            g.drawString("e: "+rsa.e,380,140);
            g.drawString("d: "+rsa.d,380,160);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Alice",100,50);
            g.drawString("Bob",500,50);
        }
    }
    Menu m;
    public Ventana(){
        super("RSA");
        m=new Menu();
        this.add(m);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Ventana v=new Ventana();
    }
}


