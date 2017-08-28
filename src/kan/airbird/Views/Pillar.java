package kan.airbird.Views;

import kan.airbird.Helpers.Asserts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pillar implements IDrawable {

	public static float BASE_HEIGHT = Asserts.pillar_end.getRegionHeight();
	public static float MIDDLE_WIDTH = 48;
	public static float BASE_WIDTH = 57;
	//public float HEIGHT = 0;
	
	private float _parentX = 0;
	private float _middleHeight = 0;
	
	private float _positionY = 0;
	private Sprite _bottom = new Sprite(Asserts.pillar_end);
	private Sprite _middle = new Sprite(Asserts.pillar_middle);
	private Sprite _top = new Sprite(Asserts.pillar_end);
	  
	public Pillar(float parentX, float positionY, float middleHeight) {
		//WIDTH = _bottom.getWidth();
		_positionY = positionY;
		_parentX = parentX;
		_middleHeight = middleHeight;
		_middle.setSize(MIDDLE_WIDTH, middleHeight);
		_top.setSize(BASE_WIDTH, _top.getHeight());
		_bottom.setSize(BASE_WIDTH, _bottom.getHeight());
		update(0);
	}
	
	public float getCenterX() {
		return _parentX + BASE_WIDTH / 2;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		_top.draw(spriteBatch);
		_middle.draw(spriteBatch);
		_bottom.draw(spriteBatch);
	}

	public void setParentX(float parentX) {
		_parentX = parentX;
		update(0);
	}
	
	@Override
	public void update(float timeInterval) {
		
		_bottom.setPosition(_parentX, _positionY);
		_middle.setPosition(_parentX + (BASE_WIDTH - _middle.getWidth()) / 2
				, _bottom.getHeight() + _positionY);
		_top.setPosition(_parentX, _middleHeight + _positionY + BASE_HEIGHT);
	}

	public boolean isIntersected(Rectangle rect) {
		return Intersector.overlaps(rect, _bottom.getBoundingRectangle())
			|| Intersector.overlaps(rect, _top.getBoundingRectangle())
			|| Intersector.overlaps(rect, _middle.getBoundingRectangle());
	}
}
