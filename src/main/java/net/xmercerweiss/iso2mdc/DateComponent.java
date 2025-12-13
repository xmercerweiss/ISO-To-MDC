package net.xmercerweiss.iso2mdc;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import net.xmercerweiss.mdc.ModernDigitalDate;


public class DateComponent extends JComponent
{
  // Class Constants
  private static final DateTimeFormatter DATE_FMT =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, y G");

  private static final Font FONT = new Font("Monospaced", Font.BOLD, 16);
  private static final int FONT_HEIGHT = FONT.getSize();

  // Constructors
  public DateComponent()
  {
    super();
    setFont(FONT);
  }

  // Public Methods
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    FontRenderContext context = g2.getFontRenderContext();
    String[] messages = getMessages();
    Point position = getInitMessagePosition(context, messages);
    for (String msg : messages)
    {
      g2.drawString(msg, position.x, position.y);
      position.move(position.x, position.y + FONT_HEIGHT);
    }
  }

  // Private Methods
  private String[] getMessages()
  {
    return new String[]{
      LocalDate.now().format(DATE_FMT),
      ModernDigitalDate.now().format(DATE_FMT),
    };
  }

  private Point getInitMessagePosition(FontRenderContext context, String[] messages)
  {
    String longest = Arrays.stream(messages)
      .max(Comparator.comparingInt(String::length))
      .orElse("");
    Rectangle2D bounds = FONT.getStringBounds(longest, context);
    return new Point(
      (int) (getWidth() - bounds.getWidth()) / 2,
      (getHeight() - FONT_HEIGHT) / 2
    );
  }
}
