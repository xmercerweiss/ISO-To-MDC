package net.xmercerweiss.iso2mdc.dates;

import java.time.chrono.ChronoLocalDate;

import net.xmercerweiss.mdc.ModernDigitalDate;


public class ModernDigitalDatePicker extends DatePicker
{
  // Class Constants
  private static final String TITLE = "Modern Digital";

  // Constructors
  public ModernDigitalDatePicker()
  {
    super(
      TITLE,
      ModernDigitalDate.now()
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

  }
}
