package grader;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class DispatchEventExample implements ActionListener{
	
	public static void main(String[] args) {
		Frame frame = new JFrame();
		frame.setLayout(new GridLayout(2, 1));
		JTextField jText = new JTextField();
		TextField text = new TextField();
		jText.addActionListener(new DispatchEventExample() );
		text.addActionListener(new DispatchEventExample() );
		frame.add(jText);
		frame.add(text);
		frame.pack();
		frame.setVisible(true);
//		text.dispatchEvent(new KeyEvent(text,
//		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
//		        0,
//		        KeyEvent.VK_UNDEFINED, '\n'));
//		KeyEvent hEvent = new KeyEvent(jText,
//		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
//		        0,
//		        KeyEvent.VK_UNDEFINED, 'H');

//		jText.dispatchEvent(new KeyEvent(jText,
//		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
//		        0,
//		        KeyEvent.VK_UNDEFINED, 'H'));
		jText.dispatchEvent(new KeyEvent(jText,
		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
		        0,
		        KeyEvent.VK_UNDEFINED, 'H'));
		text.dispatchEvent(new KeyEvent(text,
		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
		        0,
		        KeyEvent.VK_UNDEFINED, 'H'));
		jText.setCaretPosition(1);
		text.setCaretPosition(1);
//		KeyEvent eEvent = new KeyEvent(jText,
//		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
//		        0,
//		        KeyEvent.VK_UNDEFINED, 'E');
		jText.dispatchEvent(new KeyEvent(jText,
		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
		        0,
		        KeyEvent.VK_UNDEFINED, 'E'));
		text.dispatchEvent(new KeyEvent(text,
		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
		        0,
		        KeyEvent.VK_UNDEFINED, 'E'));
		jText.setCaretPosition(2);
		text.setCaretPosition(2);
//		ActionEvent actionEvent = new ActionEvent(text, ActionEvent.ACTION_PERFORMED, "HE");
//		text.dispatchEvent(actionEvent);
//		text.dispatchEvent(new ActionEvent(text, ActionEvent.ACTION_PERFORMED, "HE"));
//		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(actionEvent);
//		text.dispatchEvent(new ActionEvent(text, ActionEvent.ACTION_PERFORMED, "HE"));
//		text.setFocusable(true);
		jText.setActionCommand("foo");
		jText.postActionEvent();
		KeyEvent newLineEvent =  new KeyEvent(text,
		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
		        0,
		        KeyEvent.VK_UNDEFINED, '\n');
		text.dispatchEvent(newLineEvent);
		

//		System.out.println(KeyEvent.VK_ENTER);
//		text.dispatchEvent(new KeyEvent(text,
//		        KeyEvent.KEY_TYPED, System.currentTimeMillis(),
//		        0,
//		        KeyEvent.VK_UNDEFINED, (char) 13));
//		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}

}
