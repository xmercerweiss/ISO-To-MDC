package net.xmercerweiss.iso2mdc.dates;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import static java.time.temporal.ChronoField.*;


public class IsoDatePicker extends DatePicker
{
  // Class Constants
  private static final String TITLE = "Gregorian";
  private static final int START_OF_WEEK = 7;  // Sunday

  // Constructors
  public IsoDatePicker()
  {
    super(
      TITLE,
      LocalDate.now()
    );
  }

  // Package Private Methods
  @Override
  ChronoLocalDate getFormattedDate(ChronoLocalDate date)
  {
    return LocalDate.from(date);
  }

  @Override
  void renderDatePanel()
  {
    datePanel.removeAll();
    datePanel.repaint();
    LocalDate date = LocalDate.from(monthYear);
    int currentMonth = monthYear.get(MONTH_OF_YEAR);

    GridBagConstraints pos = new GridBagConstraints();
    pos.fill = GridBagConstraints.BOTH;
    pos.weightx = 1;
    pos.weighty = 1;
    pos.gridy = 0;
    pos.gridx = date.getDayOfWeek().getValue() % 7;

    while (date.getMonthValue() == currentMonth)
    {
      datePanel.add(createDateButton(date, pos.gridx), pos);
      date = date.plusDays(1);
      pos.gridx += 1;
      if (pos.gridx >= 7)
      {
        pos.gridx = 0;
        pos.gridy += 1;
      }
    }
  }

  // Private Methods
  private JButton createDateButton(LocalDate date, int column)
  {
    JButton button = new JButton(String.valueOf(date.getDayOfMonth()));
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setBorderPainted(false);
    button.addActionListener(
      _ -> dateListener.accept(date)
    );
    if (date.isEqual(currentDate))
    {
      button.setBackground(SELECTED_DATE_COLOR);
      button.setForeground(Color.WHITE);
    }
    else
      button.setBackground(
        column % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY
      );
    return button;
  }
}
