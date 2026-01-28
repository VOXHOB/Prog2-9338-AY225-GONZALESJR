import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceCheck {
    public static void main(String[] args) {
        JFrame f = new JFrame("Attendance Tracker");
        f.setSize(700, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        JTextField name = new JTextField(), course = new JTextField();
        SignaturePanel sig = new SignaturePanel();

        JPanel form = new JPanel(new GridLayout(3,2,5,5));
        form.add(new JLabel("Name:")); form.add(name);
        form.add(new JLabel("Course/Year:")); form.add(course);
        form.add(new JLabel("Signature:")); form.add(sig);
        f.add(form, BorderLayout.NORTH);

        String[] cols = {"Name","Course/Year","Time In","Signature"};
        DefaultTableModel model = new DefaultTableModel(cols,0){
            @Override
            public Class<?> getColumnClass(int i){return i==3?ImageIcon.class:String.class;}
        };
        JTable table = new JTable(model); table.setRowHeight(80);
        f.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton submit = new JButton("Submit"), clear = new JButton("Clear"), exit = new JButton("Exit");
        btns.add(submit); btns.add(clear); btns.add(exit);
        f.add(btns, BorderLayout.SOUTH);

        submit.addActionListener(e->{
            if(name.getText().isEmpty()||course.getText().isEmpty()||sig.isBlank()){
                JOptionPane.showMessageDialog(f,"Fill all fields + sign!"); return;
            }
            BufferedImage img = sig.getImage();
            BufferedImage copy = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
            copy.createGraphics().drawImage(img,0,0,null);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            model.addRow(new Object[]{name.getText(),course.getText(),time,new ImageIcon(copy)});
            name.setText(""); course.setText(""); sig.clear();
        });
        clear.addActionListener(e->sig.clear());
        exit.addActionListener(e->f.dispose());

        f.setVisible(true);
    }
}

class SignaturePanel extends JPanel {
    private BufferedImage img; private Graphics2D g2; private int px,py; private boolean drawn=false;
    public SignaturePanel(){setPreferredSize(new Dimension(200,80)); setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter(){@Override
        public void mousePressed(MouseEvent e){px=e.getX();py=e.getY();}});
        addMouseMotionListener(new MouseMotionAdapter(){@Override
        public void mouseDragged(MouseEvent e){
            if(g2!=null){g2.drawLine(px,py,e.getX(),e.getY()); repaint(); px=e.getX(); py=e.getY(); drawn=true;}
        }});
    }
    @Override
    protected void paintComponent(Graphics g){super.paintComponent(g);
        if(img==null){img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
            g2=img.createGraphics(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            clear();}
        g.drawImage(img,0,0,null);
    }
    public void clear(){if(g2!=null){g2.setPaint(Color.WHITE); g2.fillRect(0,0,getWidth(),getHeight()); g2.setPaint(Color.BLACK); repaint(); drawn=false;}}
    public BufferedImage getImage(){return img;}
    public boolean isBlank(){return !drawn;}
}
