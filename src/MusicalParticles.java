import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Hashtable;



public class MusicalParticles extends JFrame {

    JPanel sliderPanel = new JPanel();
    JSlider slider = new JSlider(1,100,1);
    MainPanel m = new MainPanel(slider.getValue());

    ChangeListener l = (ChangeEvent e) -> {
            m.changeParticles(slider.getValue());
    };

    public void createSliderPanel(){
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1,new JLabel("1"));
        labelTable.put(25,new JLabel("25"));
        labelTable.put(50,new JLabel("50"));
        labelTable.put(75,new JLabel("75"));
        labelTable.put(100,new JLabel("100"));
        slider.setOrientation(JSlider.VERTICAL);
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        slider.addChangeListener(l);
        sliderPanel.add(slider);

    }
    void setDefaultLook(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception e){
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception ignored){}
        }
    }

    public MusicalParticles() throws InterruptedException {
        super("Musical Particles");
        setDefaultLook();
        createSliderPanel();
        add(m);
        add(slider, BorderLayout.EAST);
        pack();
        setSize(541,534);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        m.run();

    }
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        new MusicalParticles();
    }



}