package net.xmercerweiss.iso2mdc;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import net.xmercerweiss.mdc.ModernDigitalDate;


public class DateComponent extends JComponent
{
  // Class Constants
  private static final int DATE_X = 100;
  private static final int DATE_Y = 40;

  private static final int DEFAULT_WIDTH = 300;
  private static final int DEFAULT_HEIGHT = 200;

  private static final DateTimeFormatter DATE_FMT =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, y G");

  // Public Methods
  public void paintComponent(Graphics g)
  {
    int fontHeight = g.getFont().getSize();
    g.drawString(
      LocalDate.now().format(DATE_FMT),
      DATE_X,
      DATE_Y
    );
    g.drawString(
      ModernDigitalDate.now().format(DATE_FMT),
      DATE_X,
      DATE_Y + fontHeight
    );
  }

  public Dimension getPreferredSize()
  {
    return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }
}
