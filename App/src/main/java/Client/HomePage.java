/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Client;

import Message.Request;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author mbera
 */
public class HomePage extends javax.swing.JFrame
{

	public static GroupChat groupChat;
	public static PersonalChat personalChat;
	
	public static DefaultListModel DLMUsers;
	public static DefaultListModel DLMAvailableProjects;
	public static DefaultListModel DLMAvailableProjectUsers;
	public static DefaultListModel DLMUserProjects;

	String userName;
	Client client;

	public HomePage(Client client)
	{
		initComponents();
		this.client = client;

		DLMUsers = new DefaultListModel();
		UserList.setModel(DLMUsers);

		DLMAvailableProjects = new DefaultListModel();
		AvailableProjectsList.setModel(DLMAvailableProjects);

		DLMAvailableProjectUsers = new DefaultListModel();
		ProjectMembersList.setModel(DLMAvailableProjectUsers);

		DLMUserProjects = new DefaultListModel();
		UserProjectList.setModel(DLMUserProjects);

		getUsers();
		getProjects();
		this.setLocationRelativeTo(null);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AvailableProjectsList = new javax.swing.JList<>();
        EnterAvaliableProjectButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ProjectMembersList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        UserProjectList = new javax.swing.JList<>();
        EnteringProjectChatButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ProjectNameTextField = new javax.swing.JTextField();
        CreateProjectButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserList = new javax.swing.JList<>();
        ChatwithUserButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AvailableProjectsList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                AvailableProjectsListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(AvailableProjectsList);

        EnterAvaliableProjectButton.setText("Enter Selected Project");
        EnterAvaliableProjectButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                EnterAvaliableProjectButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Project Members");

        ProjectMembersList.setToolTipText("");
        ProjectMembersList.setEnabled(false);
        jScrollPane3.setViewportView(ProjectMembersList);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2))
                    .addComponent(EnterAvaliableProjectButton)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EnterAvaliableProjectButton)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Available Projects", jPanel6);

        jScrollPane4.setViewportView(UserProjectList);

        EnteringProjectChatButton.setText("Enter Project Chat");
        EnteringProjectChatButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                EnteringProjectChatButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EnteringProjectChatButton)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EnteringProjectChatButton)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("My Projects", jPanel4);

        jLabel3.setText("Project Name");

        CreateProjectButton.setText("Create");
        CreateProjectButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                CreateProjectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CreateProjectButton)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProjectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ProjectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CreateProjectButton)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Create Project", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 330, 310));

        jLabel1.setText("All Users");

        jScrollPane1.setViewportView(UserList);

        ChatwithUserButton.setText("Chat with Selected");
        ChatwithUserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ChatwithUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ChatwithUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChatwithUserButton)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	public void getUsers()
	{
		Request request = new Request(Request.requestType.GetUsers);
		this.client.sendToServer(request);
	}

	public void getProjects()
	{
		Request request = new Request(Request.requestType.GetProjects);
		this.client.sendToServer(request);
	}

	public static void getProjectUsers(ArrayList<String> projectMemberList)
	{
		DLMAvailableProjectUsers.removeAllElements();
		for (String string : projectMemberList) {
			DLMAvailableProjectUsers.addElement(string);
		}
	}

	public static String generatePassword()
	{
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		int length = 5;
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			password.append(characters.charAt(index));
		}
		return password.toString();
	}

    private void EnterAvaliableProjectButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_EnterAvaliableProjectButtonActionPerformed
    {//GEN-HEADEREND:event_EnterAvaliableProjectButtonActionPerformed
		if (!AvailableProjectsList.isSelectionEmpty()) {
			String passwordInput = JOptionPane.showInputDialog(null, "Please enter a password");
			Request request = new Request(Request.requestType.EnterGroupChat);
			request.password = passwordInput;
			request.projectName = AvailableProjectsList.getSelectedValue();
			System.out.println(AvailableProjectsList.getSelectedValue());
			request.request = this.client.clientName;
			this.client.sendToServer(request);
		}
    }//GEN-LAST:event_EnterAvaliableProjectButtonActionPerformed

    private void AvailableProjectsListMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_AvailableProjectsListMouseClicked
    {//GEN-HEADEREND:event_AvailableProjectsListMouseClicked
		Request request = new Request(Request.requestType.GetProjectMembers);
		request.request = AvailableProjectsList.getSelectedValue();
		this.client.sendToServer(request);
    }//GEN-LAST:event_AvailableProjectsListMouseClicked

    private void CreateProjectButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_CreateProjectButtonActionPerformed
    {//GEN-HEADEREND:event_CreateProjectButtonActionPerformed
		String password = generatePassword();
		String projectName = ProjectNameTextField.getText();
		Request request = new Request(Request.requestType.CreateProject);
		request.request = projectName + "," + password + "," + this.client.clientName;
		this.client.sendToServer(request);
    }//GEN-LAST:event_CreateProjectButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
		this.client.Stop();
    }//GEN-LAST:event_formWindowClosing

    private void EnteringProjectChatButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_EnteringProjectChatButtonActionPerformed
    {//GEN-HEADEREND:event_EnteringProjectChatButtonActionPerformed
        if (!UserProjectList.isSelectionEmpty()) {
			String strings[] = UserProjectList.getSelectedValue().split(" -> ");
			Request request = new Request(Request.requestType.EnterGroupChat);
			request.projectName = strings[0];
			request.request = this.client.clientName;
			request.checkPassword = false;
			this.client.sendToServer(request);
		}
    }//GEN-LAST:event_EnteringProjectChatButtonActionPerformed

    private void ChatwithUserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ChatwithUserButtonActionPerformed
    {//GEN-HEADEREND:event_ChatwithUserButtonActionPerformed
		if (!UserList.isSelectionEmpty()) {
			String ToClient = UserList.getSelectedValue();
			Request request = new Request(Request.requestType.LoginPrivateChat);
			request.request = this.client.clientName + "," + ToClient;
			this.client.sendToServer(request);
		}
    }//GEN-LAST:event_ChatwithUserButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

			}
		});
	}

	public JTextField getProjectNameTextField()
	{
		return ProjectNameTextField;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> AvailableProjectsList;
    private javax.swing.JButton ChatwithUserButton;
    private javax.swing.JButton CreateProjectButton;
    private javax.swing.JButton EnterAvaliableProjectButton;
    private javax.swing.JButton EnteringProjectChatButton;
    private javax.swing.JList<String> ProjectMembersList;
    private javax.swing.JTextField ProjectNameTextField;
    private javax.swing.JList<String> UserList;
    private javax.swing.JList<String> UserProjectList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
