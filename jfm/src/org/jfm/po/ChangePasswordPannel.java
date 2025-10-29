/**
 * 
 */
package org.jfm.po;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import edu.asu.ser335.jfm.RolesSingleton;


/**
 * @author Nikhil Hiremath
 *
 */
public class ChangePasswordPannel extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel labelUsername = new JLabel("Enter username: ");
	private JLabel labelPassword = new JLabel("Enter password: ");
	private JLabel labelRole = new JLabel("Enter Role: ");
	private JLabel message;
	private JTextField textUsername = new JTextField(20);
	private JPasswordField fieldPassword = new JPasswordField(20);
	private JButton buttonChangePassword = new JButton("Submit");
	private JPanel newPanel;
	private JComboBox<String> roleList;

	public ChangePasswordPannel() {
		// create a new panel with GridBagLayout manager
		newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		// add components to the panel
		// UserName
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(labelUsername, constraints);

		constraints.gridx = 1;
		newPanel.add(textUsername, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		// Password
		newPanel.add(labelPassword, constraints);

		constraints.gridx = 1;
		newPanel.add(fieldPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;

		// Role
		newPanel.add(labelRole, constraints);
		constraints.gridx = 1;

		// drop down
		roleList = new JComboBox<String>(RolesSingleton.getRoleMapping().getDisplayRoles());

		// add to the parent container (e.g. a JFrame):
		newPanel.add(roleList, constraints);

		// System.out.println("Selected role: " + role);

		constraints.gridx = 0;
		constraints.gridy = 3;

		message = new JLabel();
		newPanel.add(message, constraints);
		constraints.gridx = 1;

		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		newPanel.add(buttonChangePassword, constraints);

		// Adding the listeners to components..

		buttonChangePassword.addActionListener(this);

		// set border for the panel
		newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Admin Panel"));

		// add the panel to this frame
		add(newPanel);

		pack();
		setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { //Part 1, Task 3
		String userName = textUsername.getText();
		// String userName = (String) roleList.getSelectedItem();
		String password = String.valueOf(fieldPassword.getPassword());
		String role = (String) roleList.getSelectedItem();

		try {
			if (userName.isEmpty() || password.isEmpty() || role.isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"All fields must be filled out.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			edu.asu.ser335.jfm.UsersSingleton users = edu.asu.ser335.jfm.UsersSingleton.getUsers();
			java.util.Map<String, String> userRoleMap = users.getUserRoleMapping();
			if (!userRoleMap.containsKey(userName)) {
				JOptionPane.showMessageDialog(this,
						"User does not exist.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String currentRole = userRoleMap.get(userName);
			if (!currentRole.equals(role)) {
				JOptionPane.showMessageDialog(this,
						"Role does not match user's current role.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean success = users.updatePasswordMapping(userName, password, role);
			if (success) {
				JOptionPane.showMessageDialog(this,
						"Password updated successfully!",
						"Success",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this,
						"Failed to update password.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error: " + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
