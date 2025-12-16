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
  public static final int MINIMUM_SIZE = 180;

  private static final DateTimeFormatter DATE_MONTH_YEAR_FMT =
    DateTimeFormatter.ofPattern("MMMM u");

  private static final DateTimeFormatter DATE_STRING_FMT =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, y G");

  public static final Color SELECTED_DATE_COLOR = new Color(33, 57, 109);

  private static final String[] WEEKDAY_NAMES = {
    "Mon",
    "Tue",
    "Wed",
    "Thu",
    "Fri",
    "Sat",
    "Sun"
  };

  private static final Font WEEKDAY_FONT = new Font("Monospaced", Font.PLAIN, 12);

  // Instance Fields
  // Declared with package access for concrete subclasses
  final int startOfWeek;  // Position in WEEKDAY_NAMES of weekday at the start of each week
  final JPanel datePanel;
  final JPanel navPanel;
  final JLabel navLabel;
  final JPanel weekdayPanel;
  ChronoLocalDate currentDate;
  ChronoLocalDate monthYear;
  Consumer<ChronoLocalDate> dateListener;

  // Constructors
  public DatePicker(String title, ChronoLocalDate date, Consumer<ChronoLocalDate> listener, int weekday)
  {
    super(new GridBagLayout());
    startOfWeek = weekday;
    dateListener = listener;
    currentDate = date;
    monthYear = getCurrentMonthYear();
    initBorder(title);
    navPanel = initNavPanel();
    navLabel = initNavLabel();
    initNavButtons();
    weekdayPanel = initWeekdayPanel();
    initWeekdayLabels();
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

  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(PREFERRED_SIZE, PREFERRED_SIZE);
  }

  @Override
  public Dimension getMinimumSize()
  {
    return new Dimension(MINIMUM_SIZE, MINIMUM_SIZE);
  }

  // Package Private Methods
  abstract void renderDatePanel();

  abstract ChronoLocalDate getFormattedDate(ChronoLocalDate date);

  void callDateListener(ChronoLocalDate date)
  {
    if (dateListener != null)
      dateListener.accept(date);
  }

  JButton createDateButton(ChronoLocalDate date, int column)
  {
    JButton button = new JButton(String.valueOf(date.get(DAY_OF_MONTH)));
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setBorderPainted(false);
    button.addActionListener(_ -> callDateListener(date));
    if (date.isEqual(currentDate))
    {
      button.setBackground(SELECTED_DATE_COLOR);
      button.setForeground(Color.WHITE);
    }
    else
      button.setBackground(column % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
    return button;
  }

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
    GridBagConstraints pos = new GridBagConstraints();
    pos.gridx = 0;
    pos.gridy = 0;
    pos.fill = GridBagConstraints.BOTH;
    pos.weightx = 1;
    pos.weighty = 0;
    JPanel panel = new JPanel(new GridBagLayout());
    add(panel, pos);
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
  private JPanel initWeekdayPanel()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.gridx = 0;
    pos.gridy = 1;
    pos.weightx = 1;
    pos.weighty = 0;
    pos.fill = GridBagConstraints.BOTH;
    JPanel panel = new JPanel(new GridBagLayout());
    add(panel, pos);
    return panel;
  }

  private void initWeekdayLabels()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.gridx = 0;
    pos.gridy = 0;
    pos.weightx = 1;
    pos.fill = GridBagConstraints.BOTH;
    for (int i = startOfWeek; pos.gridx < 7; pos.gridx++)
    {
      JLabel label = new JLabel(WEEKDAY_NAMES[i], SwingConstants.CENTER);
      label.setFont(WEEKDAY_FONT);
      weekdayPanel.add(label, pos);
      i = (i + 1) % 7;
    }
  }

  private JPanel initDatePanel()
  {
    GridBagConstraints pos = new GridBagConstraints();
    pos.gridx = 0;
    pos.gridy = 2;
    pos.gridheight = 7;
    pos.fill = GridBagConstraints.BOTH;
    pos.weightx = 1;
    pos.weighty = 1;
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(
      BorderFactory.createLoweredBevelBorder()
    );
    add(panel, pos);
    return panel;
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
    revalidate();
    repaint();
  }

  private void renderNavLabel()
  {
    navLabel.setText(monthYear.format(DATE_MONTH_YEAR_FMT));
  }
}
