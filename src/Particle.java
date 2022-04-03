import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;


public class Particle implements Callable<Long>{
    int xPos;
    int yPos;


    Random random = new Random();
    int xDirection = random.nextInt(10) + 1;
    int yDirection = random.nextInt(10) + 1;
    Color[] colors = {Color.BLUE, Color.GREEN, Color.LIGHT_GRAY,Color.RED,
            Color.ORANGE,Color.YELLOW,Color.PINK,Color.BLACK};
    Color color;


    ArrayList<Particle> particles;

    public Particle(int xPos, int yPos, ArrayList<Particle> particles){
        this.color = colors[random.nextInt(colors.length)];
        this.xPos = xPos;
        this.yPos = yPos;
        this.particles = particles;
        if(random.nextBoolean()){
            xDirection = -xDirection;
        }
        if(random.nextBoolean()){
            yDirection = -yDirection;
        }
    }

    public void move() throws Exception{
        boolean hasCollided = false;
        if(xPos >= 480){
            xDirection = -random.nextInt(10)+ 1;
            hasCollided = true;
        }
        else if(xPos <= 0){
            xDirection = random.nextInt(10) + 1;
            hasCollided = true;
        }
        if(yPos >= 480){
            yDirection = -random.nextInt(10)+ 1;
            hasCollided = true;
        }
        else if(yPos <= 0) {
            yDirection = random.nextInt(10) + 1;
            hasCollided = true;
        }

        for(Particle particle: particles){
            if (this.hashCode() != particle.hashCode() && this.xPos == particle.xPos && this.yPos == particle.yPos) {
                xDirection = (-(xDirection / Math.abs(xDirection))) * (random.nextInt(10) + 1);
                this.yDirection = (-(yDirection / Math.abs(yDirection))) * (random.nextInt(10) + 1);
                hasCollided = true;
                break;
            }
        }

        if (hasCollided){
            playSound();
        }

        yPos += yDirection;
        xPos += xDirection;

    }

    public Long call() throws Exception{
        move();
        return 0L;
    }

    private void playSound(){
        String fileName;
        switch(color.getRGB()){
            //BLUE
            case -16776961:
                fileName = "a.wav";
                break;
            //GREEN
            case -16711936:
                fileName = "b.wav";
                break;
            //LIGHT_GRAY
            case -4144960:
                fileName="bb.wav";
                break;
            //RED
            case -65536:
                fileName = "c.wav";
                break;
            //ORANGE
            case -14336:
                fileName = "d.wav";
                break;
            //YELLOW
            case -256:
                fileName = "e.wav";
                break;
            //PINK
            case -20561:
                fileName = "eb.wav";
                break;
            //BLACK
            case -16777216:
                fileName = "f.wav";
                break;
            default:
                fileName = "g.wav";
                break;
        }
        SoundPlayer player = new SoundPlayer(MusicalParticles.class.getClassLoader().getResource(fileName));
        player.start();
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillOval(xPos, yPos, 20,20);
    }

}
