package net.xmercerweiss.iso2mdc;

import java.awt.*;
import javax.swing.*;


public class ButtonPanel extends JPanel
{
  // Constructors
  public ButtonPanel()
  {
    super();
    initButtons();
  }

  // Private Methods
  private void initButtons()
  {
    JButton redButton = new JButton("Red");
    redButton.addActionListener(
      _ -> setBackground(Color.RED)
    );
    add(redButton);

    JButton blueButton = new JButton("Blue");
    blueButton.addActionListener(
      _ -> setBackground(Color.BLUE)
    );
    add(blueButton);

    JButton greenButton = new JButton("Green");
    greenButton.addActionListener(
      _ -> setBackground(Color.GREEN)
    );
    add(greenButton);
  }
}
