package net.xmercerweiss.iso2mdc.widgets;

import java.awt.*;
import javax.swing.*;
import java.time.chrono.ChronoLocalDate;


public class DatePicker extends JPanel
{
  // Class Constants
  public static final int PREFERRED_SIZE = 500;

  private static final LayoutManager LAYOUT = new BorderLayout();

  // Instance Fields
  private final JPanel navPanel;
  private final JLabel navLabel;
  private final JPanel datePanel;
  private final int startOfWeek;
  private ChronoLocalDate date;

  // Constructors
  public DatePicker(String title, ChronoLocalDate startDate)
  {
    this(title, startDate, 7);
    // Sunday (7) is the default starting weekday
  }

  public DatePicker(String title, ChronoLocalDate startDate, int startOfWeek)
  {
    super(LAYOUT);
    date = startDate;
    this.startOfWeek = startOfWeek;
    initBorder(title);
    navPanel = initNavPanel();
    navLabel = initNavLabel();
    initNavButtons();
    datePanel = initDatePanel();
  }

  // Public Methods
  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(PREFERRED_SIZE, PREFERRED_SIZE);
  }

  // Private Methods
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
    JLabel label = new JLabel("Month YEAR");
    GridBagConstraints pos = new GridBagConstraints();
    pos.weightx = 1;
    pos.fill = GridBagConstraints.BOTH;
    pos.gridx = 2;
    pos.gridy = 0;
    pos.gridwidth = 5;
    navPanel.add(label, pos);
    return label;
  }

  private JPanel initDatePanel()
  {
    JPanel panel = new JPanel();
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
    minusYearButton.addActionListener(_ -> incrementYear(-1));
    pos.gridx = 0;
    navPanel.add(minusYearButton, pos);

    JButton minusMonthButton = new JButton("<");
    minusYearButton.addActionListener(_ -> incrementMonth(-1));
    pos.gridx = 1;
    navPanel.add(minusMonthButton, pos);

    JButton plusMonthButton = new JButton(">");
    plusMonthButton.addActionListener(_ -> incrementMonth(1));
    pos.gridx = 5;
    navPanel.add(plusMonthButton, pos);

    JButton plusYearButton = new JButton(">");
    plusYearButton.addActionListener(_ -> incrementYear(1));
    pos.gridx = 6;
    navPanel.add(plusYearButton, pos);
  }

  private void incrementYear(int years)
  {

  }

  private void incrementMonth(int months)
  {

  }

  private void updateNavLabel()
  {

  }
}
