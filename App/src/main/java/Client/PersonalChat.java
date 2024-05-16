/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Client;

import Message.Request;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author mbera
 */
public class PersonalChat extends javax.swing.JFrame
{

	Client client;
	public static String ToClient;
	public static DefaultListModel DLMMessagesList;
	
	/**
	 * Creates new form PersonalChat
	 */
	public PersonalChat(Client client)
	{
		initComponents();
		this.client = client;
		this.setLocationRelativeTo(null);
		
		DLMMessagesList = new DefaultListModel();
		MessagesList.setModel(DLMMessagesList);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void getMessages()
	{
		Request request = new Request(Request.requestType.GetPrivateMessages);
		request.request = this.client.clientName + "," + ToClient;
		this.client.sendToServer(request);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        MessagesList = new javax.swing.JList<>();
        MessageTextField = new javax.swing.JTextField();
        SendMessageButton = new javax.swing.JButton();
        SendFileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(MessagesList);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 400, 230));
        jPanel1.add(MessageTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 252, 303, -1));

        SendMessageButton.setText("Send ");
        SendMessageButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SendMessageButtonActionPerformed(evt);
            }
        });
        jPanel1.add(SendMessageButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 85, -1));

        SendFileButton.setText("File");
        jPanel1.add(SendFileButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 85, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendMessageButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SendMessageButtonActionPerformed
    {//GEN-HEADEREND:event_SendMessageButtonActionPerformed
		String message = this.client.clientName + ":"+ MessageTextField.getText();
		Request request = new Request(Request.requestType.SendPersonalMessage);
		request.request = message;
		request.clientName = ToClient;
		this.client.sendToServer(request);
		MessageTextField.setText("");
		DLMMessagesList.addElement(message);
    }//GEN-LAST:event_SendMessageButtonActionPerformed

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
			java.util.logging.Logger.getLogger(PersonalChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PersonalChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PersonalChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PersonalChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MessageTextField;
    private javax.swing.JList<String> MessagesList;
    private javax.swing.JButton SendFileButton;
    private javax.swing.JButton SendMessageButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
