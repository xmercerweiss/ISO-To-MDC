package net.xmercerweiss.iso2mdc.dates;

import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoField.*;

public abstract class DatePicker extends JPanel
{
  // Class Constants
  public static final int PREFERRED_SIZE = 200;
  public static final Color SELECTED_DATE_COLOR = new Color(33, 57, 109);

  private static final DateTimeFormatter DATE_MONTH_YEAR_FMT =
    DateTimeFormatter.ofPattern("MMMM u");

  private static final DateTimeFormatter DATE_STRING_FMT =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, y G");

  // Instance Fields
  // Declared with package access for concrete subclasses
  final JPanel navPanel;
  final JLabel navLabel;
  final JPanel datePanel;
  ChronoLocalDate currentDate;
  ChronoLocalDate monthYear;
  Consumer<ChronoLocalDate> dateListener;

  // Constructors
  public DatePicker(String title, ChronoLocalDate date)
  {
    super(new BorderLayout());
    currentDate = date;
    monthYear = getCurrentMonthYear();
    initBorder(title);
    navPanel = initNavPanel();
    navLabel = initNavLabel();
    initNavButtons();
    datePanel = initDatePanel();
    renderContents();
  }

  // Public Methods
  public String getDateString()
  {
    return currentDate.format(DATE_STRING_FMT);
  }

  public void setDate(ChronoLocalDate date)
  {
    currentDate = getFormattedDate(date);
    monthYear = getCurrentMonthYear();
    renderContents();
  }

  public void setDateListener(Consumer<ChronoLocalDate> listener)
  {
    dateListener = listener;
  }

  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(PREFERRED_SIZE, PREFERRED_SIZE);
  }

  // Package Private Methods
  abstract void renderDatePanel();

  abstract ChronoLocalDate getFormattedDate(ChronoLocalDate date);

  // Private Methods
  private ChronoLocalDate getCurrentMonthYear()
  {
    return currentDate.minus(
      Period.ofDays(currentDate.get(DAY_OF_MONTH) - 1)
    );
  }

  private void initBorder(String title)
  {
    setBorder(
      BorderFactory.createTitledBorder(title)
    );
  }

  private JPanel initNavPanel()
  {
    JPanel panel = new JPanel(new GridBagLayout());
    add(panel, BorderLayout.PAGE_START);
    return panel;
  }

  private JLabel initNavLabel()
  {
    JLabel label = new JLabel(
      "",
      SwingConstants.CENTER
    );
    GridBagConstraints pos = new GridBagConstraints();
    pos.weightx = 1;
    pos.fill = GridBagConstraints.BOTH;
    pos.gridx = 2;
    pos.gridy = 0;
    navPanel.add(label, pos);
    return label;
  }

  private JPanel initDatePanel()
  {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(
      BorderFactory.createLoweredBevelBorder()
    );
    add(panel, BorderLayout.CENTER);
    return panel;
  }

  private void initNavButtons()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.fill = GridBagConstraints.VERTICAL;
    pos.gridy = 0;

    JButton minusYearButton = new JButton("<<");
    minusYearButton.setMargin(new Insets(0, 0, 0, 0));
    minusYearButton.addActionListener(_ -> incrementYear(-1));
    pos.gridx = 0;
    navPanel.add(minusYearButton, pos);

    JButton minusMonthButton = new JButton("<");
    minusMonthButton.addActionListener(_ -> incrementMonth(-1));
    minusMonthButton.setMargin(new Insets(0, 0, 0, 0));
    pos.gridx = 1;
    navPanel.add(minusMonthButton, pos);

    JButton plusMonthButton = new JButton(">");
    plusMonthButton.addActionListener(_ -> incrementMonth(1));
    plusMonthButton.setMargin(new Insets(0, 0, 0, 0));
    pos.gridx = 3;
    navPanel.add(plusMonthButton, pos);

    JButton plusYearButton = new JButton(">>");
    plusYearButton.addActionListener(_ -> incrementYear(1));
    plusYearButton.setMargin(new Insets(0, 0, 0, 0));
    pos.gridx = 4;
    navPanel.add(plusYearButton, pos);
  }

  private void incrementYear(int years)
  {
    monthYear = monthYear.plus(
      Period.ofYears(years)
    );
    renderContents();
  }

  private void incrementMonth(int months)
  {
    monthYear = monthYear.plus(
      Period.ofMonths(months)
    );
    renderContents();
  }

  private void renderContents()
  {
    renderNavLabel();
    renderDatePanel();
  }

  private void renderNavLabel()
  {
    navLabel.setText(monthYear.format(DATE_MONTH_YEAR_FMT));
  }
}
