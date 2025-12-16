package net.xmercerweiss.iso2mdc.dates;

import java.awt.*;
import java.time.Period;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import java.util.function.Consumer;
import java.time.chrono.ChronoLocalDate;

import javax.swing.*;

import net.xmercerweiss.mdc.ModernDigitalDate;


public class ModernDigitalDatePicker extends DatePicker
{
  // Class Constants
  private static final String TITLE = "Modern Digital";

  private static final Color LEAP_DAY_COLOR = new Color(199, 22, 22);

  // Constructors
  public ModernDigitalDatePicker(Consumer<ChronoLocalDate> listener)
  {
    super(
      TITLE,
      ModernDigitalDate.now(),
      listener,
      0
    );
  }

  // Package Private Methods
  @Override
  ChronoLocalDate getFormattedDate(ChronoLocalDate date)
  {
    return ModernDigitalDate.from(date);
  }

  @Override
  void renderDatePanel()
  {
    datePanel.removeAll();

    GridBagConstraints pos = new GridBagConstraints();
    pos.fill = GridBagConstraints.BOTH;
    pos.weightx = 1;
    pos.weighty = 1;
    pos.gridy = 0;
    pos.gridx = 0;

    if (monthYear.get(MONTH_OF_YEAR) % 13 == 0)
      renderLeapDayMonth(pos);
    else
      renderStandardMonth(pos);
  }

  // Private Methods
  private void renderStandardMonth(GridBagConstraints pos)
  {
    ModernDigitalDate date = ModernDigitalDate.from(monthYear);
    while (date.getMonth() == monthYear.get(MONTH_OF_YEAR))
    {
      datePanel.add(createDateButton(date, pos.gridx), pos);
      date = (ModernDigitalDate) date.plus(Period.ofDays(1));
      pos.gridx++;
      if (pos.gridx >= 7)
      {
        pos.gridx = 0;
        pos.gridy += 1;
      }
    }
  }

  private void renderLeapDayMonth(GridBagConstraints pos)
  {
    ModernDigitalDate date = ModernDigitalDate.from(monthYear);
    if (date.getMonth() == 0)
      date = (ModernDigitalDate) date.minus(Period.ofMonths(1));
    while (date.getMonth() == 13)
    {
      datePanel.add(createDateButton(date, pos.gridx), pos);
      date = (ModernDigitalDate) date.plus(Period.ofDays(1));
      pos.gridx++;
      if (pos.gridx >= 7)
      {
        pos.gridx = 0;
        pos.gridy += 1;
      }
    }
    while (date.getMonth() == 0)
    {
      datePanel.add(createLeapDayButton(date, pos.gridx), pos);
      date = (ModernDigitalDate) date.plus(Period.ofDays(1));
      pos.gridx++;
    }
  }

  private JButton createLeapDayButton(ChronoLocalDate date, int column)
  {
    JButton button = new JButton("L" + date.get(DAY_OF_MONTH));
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setBorderPainted(false);
    button.addActionListener(_ -> callDateListener(date));
    if (date.isEqual(currentDate))
    {
      button.setBackground(LEAP_DAY_COLOR);
      button.setForeground(Color.WHITE);
    }
    else
    {
      button.setBackground(column % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
      button.setForeground(LEAP_DAY_COLOR);
    }
    return button;
  }
}
