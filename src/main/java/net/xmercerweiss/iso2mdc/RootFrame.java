package net.xmercerweiss.iso2mdc;

import java.awt.*;
import javax.swing.*;


public class RootFrame extends JFrame
{
  // Class Constants
  private static final Toolkit TK = Toolkit.getDefaultToolkit();

  private static final int MAX_WIDTH = (int) TK.getScreenSize().getWidth();
  private static final int MAX_HEIGHT = (int) TK.getScreenSize().getHeight();
  private static final int INIT_WIDTH = 1000;
  private static final int INIT_HEIGHT = 600;

  private static final String TITLE = "ISO to MDC";

  // Constructors
  public RootFrame()
  {
    super(TITLE);
    initFrame();
    initDate();
  }

  // Private Methods
  private void initFrame()
  {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setBounds(
      (MAX_WIDTH - INIT_WIDTH) / 2,
      (MAX_HEIGHT - INIT_HEIGHT) / 2,
      INIT_WIDTH,
      INIT_HEIGHT
    );
    setVisible(true);
  }

  private void initDate()
  {
    add(new ButtonPanel());
  }
}
