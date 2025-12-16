package net.xmercerweiss.iso2mdc;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
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

  private static final Font LABEL_FONT = new Font("Monospaced", Font.PLAIN, 12);

  // Instance Fields
  private final DatePicker isoDatePicker;
  private final DatePicker mdcDatePicker;
  private final JLabel isoDateLabel;
  private final JLabel mdcDateLabel;

  // Constructors
  public RootFrame()
  {
    super(TITLE);
    initFrame();
    isoDatePicker = initIsoDatePicker();
    mdcDatePicker = initMdcDatePicker();
    isoDateLabel = initIsoDateLabel();
    mdcDateLabel = initMdcDateLabel();
    renderNewDate(LocalDate.now());
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

  private DatePicker initIsoDatePicker()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.insets = new Insets(15, 30, 15, 15);
    pos.gridx = 0;
    pos.gridy = 0;
    pos.weighty = 1;
    pos.fill = GridBagConstraints.BOTH;
    DatePicker iso = new IsoDatePicker(this::renderNewDate);
    add(iso, pos);
    return iso;
  }

  private DatePicker initMdcDatePicker()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.insets = new Insets(15, 15, 15, 30);
    pos.gridx = 1;
    pos.gridy = 0;
    pos.weighty = 1;
    pos.fill = GridBagConstraints.BOTH;
    DatePicker mdc = new ModernDigitalDatePicker(this::renderNewDate);
    add(mdc, pos);
    return mdc;
  }

  private JLabel initIsoDateLabel()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.insets = new Insets(5, 0, 20, 0);
    pos.gridx = 0;
    pos.gridy = 1;
    pos.fill = GridBagConstraints.BOTH;
    JLabel label = new JLabel("", SwingConstants.CENTER);
    label.setFont(LABEL_FONT);
    add(label, pos);
    return label;
  }

  private JLabel initMdcDateLabel()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.insets = new Insets(5, 0, 20, 0);
    pos.gridx = 1;
    pos.gridy = 1;
    pos.fill = GridBagConstraints.BOTH;
    JLabel label = new JLabel("", SwingConstants.CENTER);
    label.setFont(LABEL_FONT);
    add(label, pos);
    return label;
  }

  private void renderNewDate(ChronoLocalDate date)
  {
    updateDatePickers(date);
    updateDateLabels();
  }

  private void updateDatePickers(ChronoLocalDate date)
  {
    isoDatePicker.setDate(date);
    mdcDatePicker.setDate(date);
  }

  private void updateDateLabels()
  {
    isoDateLabel.setText(isoDatePicker.getDateString());
    mdcDateLabel.setText(mdcDatePicker.getDateString());
  }
}
