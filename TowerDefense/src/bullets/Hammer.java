package bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

public class Hammer extends Bullet
{
	
	// change WIDTH and HEIGHT for each new Bullet
	private final int WIDTH = 8, HEIGHT = 16;
	private final int CALLS_BETWEEN_TOGGLE = 3;
	private Texture[] tex;
	private int current_tex, tex_count;
	
	public Hammer(Enemy target, float center_x, float center_y)
	{
		this.center_x = center_x;
		this.center_y = center_y;
		collider = new Rectangle(center_x, center_y, WIDTH, HEIGHT);
		this.target = target;
		active = true;
		
		// attributes - change for each new Bullet
		tex = new Texture[4];	// four sprites to simulate animation
		tex[0] = new Texture("data/bullets/hammer/hammer_left_up.png");
		tex[1] = new Texture("data/bullets/hammer/hammer_left_left.png");
		tex[2] = new Texture("data/bullets/hammer/hammer_left_down.png");
		tex[3] = new Texture("data/bullets/hammer/hammer_left_right.png");
		current_tex = 0;
		tex_count = CALLS_BETWEEN_TOGGLE;
		
		damage = 100;
		speed = 5;
	}
	
	public void render(SpriteBatch batch)
	{
		if (active)
		{
			batch.draw(tex[current_tex], collider.x, collider.y);
			if (--tex_count < 0)
			{
				current_tex = (current_tex + 1) % tex.length;	// call next sprite
				tex_count = CALLS_BETWEEN_TOGGLE;	// reset tex_count
			}
		}
	}
	
	public void update()
	{
		if (active)
		{
			if (target == null)
				active = false;
			else
			{
				float xE = (target.getCollider().getWidth()/2 + target.getX()) - center_x;
				float yE = (target.getCollider().getHeight()/2 + target.getY()) - center_y;
				float hE = (float)Math.sqrt(xE*xE + yE*yE);
				collider.x += (xE/hE)*speed;
				collider.y += (yE/hE)*speed;
				if (collider.overlaps(target.getCollider()))
				{// hit target
					target.subHealth(damage);
					active = false;
				}
			}
		}
	}
	
}
