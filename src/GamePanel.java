import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;


public class GamePanel extends JPanel implements MouseListener, ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 1;
    boolean running = true;
    static final int numberOfRings = 11;//-1
    static final int ringSize = 30;
    static final int pointsPerRing = 10;
    final int[] x = new int[numberOfRings];
    final int[] y = new int[numberOfRings];
    final int[] d = new int[numberOfRings];
    static int velocityX = 3;
    static int velocityY = 5;
    static final int MAX_SPEED = 15;
    static final float accelerationPerBounce = 1;
    int score = 0;
    Timer timer;
    Random random;


    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.setBackground(Color.darkGray);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                hit(getMousePosition().x, getMousePosition().y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        for (int i = numberOfRings - 1; i >= 0; i--) {
            d[i] = i * ringSize;
            x[i] = (SCREEN_WIDTH / 2) - (d[i] / 2);
            y[i] = (SCREEN_HEIGHT / 2) - (d[i] / 2);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {

        for (int i = numberOfRings - 1; i >= 0; i--) {
            if (i % 2 == 1)
                g.setColor(Color.red);
            else
                g.setColor(Color.white);

            g.fillOval(x[i], y[i], d[i], d[i]);
            g.setColor(Color.black);
            g.drawOval(x[i], y[i], d[i], d[i]);
        }
        g.setColor(Color.yellow);
        g.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : " + score, (SCREEN_WIDTH - metrics1.stringWidth("Score : " + score)) / 2, (SCREEN_HEIGHT - metrics1.getHeight()) / 10);

    }

    public void move() {
        for (int i = numberOfRings - 1; i >= 0; i--) {
            x[i] += velocityX;
            y[i] += velocityY;
        }
    }

    public void checkCollision() {
        int t = numberOfRings - 1;
        if (x[t] < 0 || x[t] + d[t] > SCREEN_WIDTH)//x[t] is the left corner center of circle is at x[t]+d[t]/2 and same for y
        {
            velocityX *= -accelerationPerBounce;

        }
        if (y[t] < 0 || y[t] + d[t] > SCREEN_HEIGHT) {
            velocityY *= -accelerationPerBounce;
        }
    }


    public void hit(int mouseX, int mouseY) {
        double distanceFromCenter;
        int centerX = x[0] + d[0] / 2;
        int centerY = y[0] + d[0] / 2;

        distanceFromCenter = sqrt((mouseX - centerX) * (mouseX - centerX) + (mouseY - centerY) * (mouseY - centerY));
        score += points(distanceFromCenter);
    }

    public int points(double distanceFromCenter) {
        if (distanceFromCenter > numberOfRings * ringSize / 2) {
            return 0;
        } else {
            return (int) (numberOfRings-(distanceFromCenter/(ringSize/2)))*pointsPerRing;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        hit(getMousePosition().x, getMousePosition().y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollision();
        repaint();
    }
}