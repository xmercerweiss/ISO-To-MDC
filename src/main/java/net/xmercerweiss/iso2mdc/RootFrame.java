package net.xmercerweiss.iso2mdc;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.time.chrono.ChronoLocalDate;

import net.xmercerweiss.iso2mdc.dates.*;


public class RootFrame extends JFrame
{
  // Class Constants
  private static final Toolkit TK = Toolkit.getDefaultToolkit();

  private static final int MAX_WIDTH = (int) TK.getScreenSize().getWidth();
  private static final int MAX_HEIGHT = (int) TK.getScreenSize().getHeight();
  private static final int INIT_WIDTH = 1000;
  private static final int INIT_HEIGHT = 600;

  private static final String TITLE = "ISO to MDC";

  // Instance Fields
  private final List<DatePicker> datePickers;

  // Constructors
  public RootFrame()
  {
    super(TITLE);
    initFrame();
    datePickers = initDatePickers();
    pack();
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
    setLayout(new GridBagLayout());
    setVisible(true);
  }

  private List<DatePicker> initDatePickers()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.insets = new Insets(5, 10, 5, 10);
    pos.gridy = 0;

    DatePicker isoPicker = new IsoDatePicker();
    isoPicker.setDateListener(this::updateDatePickers);
    pos.gridx = 0;
    add(isoPicker, pos);

    DatePicker mdcPicker = new ModernDigitalDatePicker();
    mdcPicker.setDateListener(this::updateDatePickers);
    pos.gridx = 1;
    add(mdcPicker, pos);

    return List.of(
      isoPicker,
      mdcPicker
    );
  }

  private void updateDatePickers(ChronoLocalDate newDate)
  {
    for (DatePicker picker : datePickers)
      picker.setDate(newDate);
  }
}
