package view.bookingGUIcomponent;

import javax.swing.*;


abstract public class BookingPane {

    public static void setGroup(JPanel panel, JLabel[] labels, JComponent[] components) {
        GroupLayout groupLayout = new GroupLayout(panel);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        panel.setLayout(groupLayout);

        GroupLayout.SequentialGroup sequentialGroup1 = groupLayout.createSequentialGroup();
        GroupLayout.ParallelGroup parallelGroup1 = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        for(int i = 0; i < labels.length; i++) {
            parallelGroup1.addComponent(labels[i]);
        }
        GroupLayout.ParallelGroup parallelGroup2 = groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        for(int i = 0; i < components.length; i++) {
            parallelGroup2.addComponent(components[i]);
        }
        sequentialGroup1.addGroup(parallelGroup1).addGroup(parallelGroup2);
        groupLayout.setHorizontalGroup(sequentialGroup1);

        GroupLayout.SequentialGroup sequentialGroup2 = groupLayout.createSequentialGroup();
        for(int i = 0; i < labels.length; i++) {
            GroupLayout.ParallelGroup parallelGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            parallelGroup.addComponent(labels[i]);
            parallelGroup.addComponent(components[i]);
            sequentialGroup2.addGroup(parallelGroup);
        }
        groupLayout.setVerticalGroup(sequentialGroup2);
    }


}
