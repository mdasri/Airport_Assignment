package GUI;

import java.awt.*;
import javax.swing.JFrame;

public class airportGui extends JFrame {
    
    public static void main(String[] args) throws Exception{
        AirportFrame frame = new AirportFrame();
        frame.setTitle("Our Awesome Airport!");
        frame.setSize(550, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
