package lessons.lightbot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import jlm.ui.WorldView;
import jlm.universe.World;

public class LightBotWorldView extends WorldView {
	private static final long serialVersionUID = 1674820378395646693L;
	
	public LightBotWorldView(World w) {
		super(w);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		LightBotWorld tw = (LightBotWorld) this.world;
		
		double ratio = Math.min(((double) getWidth())/tw.getWidth(), ((double)getHeight())/tw.getHeight());
		g2.scale(ratio, ratio);

		g2.translate(Math.abs((getWidth()-ratio*tw.getWidth())/2.), Math.abs((getHeight()-ratio*tw.getHeight())/2.));
		
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		//g2.fill(new Rectangle2D.Double(0.,0.,(double)getWidth(),(double)getHeight()));
		g2.fill(new Rectangle2D.Double(0.,0.,(double)tw.getWidth(),(double)tw.getHeight()));
		
/*		synchronized (((World) world).shapes) {
			Iterator<ShapeAbstract> it2 = ((TurtleWorld) world).shapes();
			while (it2.hasNext())
				it2.next().draw(g2);			
		}
		Iterator<Entity> it = world.entities();
		while (it.hasNext())
			drawTurtle(g2, (Turtle)it.next());
*/	
		
	}

	/*
	private void drawTurtle(Graphics2D g, Turtle b) {
		ImageIcon ic = ResourcesCache.getIcon("resources/kturtle.png");
		AffineTransform t = new AffineTransform(1.0, 0, 0, 1.0, b.getX()-ic.getIconWidth()/2., b.getY()-ic.getIconHeight()/2.);
		t.rotate(b.getHeadingRadian(), ic.getIconWidth()/2., ic.getIconHeight()/2.);
		g.drawImage(ic.getImage(), t, null);
	}*/

	@Override
	public boolean isWorldCompatible(World world) {
		return world instanceof LightBotWorld;
	}
}
