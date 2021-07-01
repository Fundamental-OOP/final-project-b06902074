package object;

import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class Button extends JButton {
	public Button(Icon icon, Consumer<ActionEvent> click) {
		super(icon);
		this.addActionListener(e -> click.accept(e));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setContentAreaFilled(false);
	}
}
