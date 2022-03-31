import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.*;
import java.util.Random;

public class MainPanel extends JPanel {

    ArrayList<Particle> particles = new ArrayList<Particle>();
    ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Random random = new Random();

    public MainPanel(int startingValue) {
        for (int i = 0; i < startingValue; i++){
            particles.add(new Particle(250,250,particles));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,0,0,500);
        g.drawLine(0,0,500,0);
        g.drawLine(500,500,500,0);
        g.drawLine(500,500,0,500);
        for (Particle p : particles) {
            p.draw(g);
        }
    }

    private void removeParticles(int diff){
        for(int i = 0 ; i < diff; i++) {
            particles.remove(random.nextInt(particles.size()));
        }
    }

    public void changeParticles(int num){
        if (num > particles.size()){
            for(int i = 0; i < num - particles.size(); i++){
                particles.add(new Particle(250, 250, particles));
            }
        }
        else if (num < particles.size()){
            removeParticles(particles.size() - num);
        }
    }

    public void run() throws InterruptedException {
        while (true) {
            repaint();
            revalidate();
            Thread.sleep(5);
            try {
                service.invokeAll(particles);
            }
            catch (ConcurrentModificationException ignored){
            }
        }
    }
}
