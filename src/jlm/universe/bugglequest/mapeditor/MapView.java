package jlm.universe.bugglequest.mapeditor;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import jlm.universe.Direction;
import jlm.universe.Entity;
import jlm.universe.bugglequest.Buggle;
import jlm.universe.bugglequest.BuggleWorldCell;
import jlm.universe.bugglequest.exception.AlreadyHaveBaggleException;
import jlm.universe.bugglequest.ui.BuggleWorldView;


public class MapView extends BuggleWorldView implements EditionListener {

	private static final long serialVersionUID = -8303474674104829723L;
	private Editor editor;

	public MapView(Editor e) {
		super(e.getWorld());
		this.editor = e;

		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//				handleMouseEvt(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseEvt(e);
			}
			void handleMouseEvt(MouseEvent e) {
				int x = (int) ((e.getX() - getPadX()) / getCellWidth());
				int y = (int) ((e.getY() - getPadY()) / getCellWidth());

				BuggleWorldCell cell = (BuggleWorldCell) editor.getWorld().getCell(x, y);
				String cmd = editor.getCommand();

				if (cmd.equals("topwall")) {
					if (cell.hasTopWall()) 
						cell.removeTopWall();
					else
						cell.putTopWall();
				} else if (cmd.equals("leftwall")) {
					if (cell.hasLeftWall()) 
						cell.removeLeftWall();
					else
						cell.putLeftWall();
				} else if (cmd.equals("baggle")) {
					if (cell.hasBaggle()) 
						cell.pickUpBaggle();
					else
						try {
							cell.newBaggle();
						} catch (AlreadyHaveBaggleException e1) {
							System.out.println("The impossible did happen (yet again)");
							e1.printStackTrace();
						}
				} else if (cmd.equals("colors")) {
					if (cell.getColor().equals(editor.getSelectedColor())) 
						cell.setColor(Color.white);
					else
						cell.setColor(editor.getSelectedColor());
				} else if (cmd.equals("text")) {
					String inputValue = (String)JOptionPane.showInputDialog(null,
							"Choose a new message", "Change message",
							JOptionPane.QUESTION_MESSAGE, null,
							null,cell.getContent());
					
					if (inputValue == null) // cancel pressed
						return;
					cell.emptyContent();
					cell.addContent(inputValue);
				} else if (cmd.equals("buggle")) {
					Buggle thebuggle = null;
					for (Entity ent:editor.getWorld().getEntities()) {
						Buggle b = (Buggle) ent;
						if (b.getX() == x && b.getY() == y) {
							thebuggle = b;
							break;
						}
					}
					if (thebuggle == null) {
						editor.getWorld().addEntity(new Buggle(editor.getWorld(),"buggle"+editor.getWorld().getEntityCount(),
								x,y,Direction.NORTH,Color.black, Color.black));
					} else {
						editor.getWorld().setSelectedEntity(thebuggle);
					}
				}
			}
		};
		addMouseListener(mouseListener);
	}

	@Override
	public void worldEdited() {
		worldHasChanged();// use the callbacks of the regular view -- not really clean but efficient
	}

	@Override
	public void setSelectedEntity(Entity ent) {
		/* I'm too lazy to react to this */
	}
}
