package net.xmercerweiss.iso2mdc.dates;

import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import static java.time.temporal.ChronoField.*;


public class IsoDatePicker extends DatePicker
{
  // Class Constants
  private static final String TITLE = "Gregorian";

  // Constructors
  public IsoDatePicker(Consumer<ChronoLocalDate> listener)
  {
    super(
      TITLE,
      LocalDate.now(),
      listener,
      6
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
}
