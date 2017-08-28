package kan.airbird.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IDrawable {

	void draw(SpriteBatch spriteBatch);
	
	void update(float timeInterval);
	
}
