package kan.airbird.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Trap implements IDrawable {

	private Pillar _up;
	private Pillar _down;
	
	private float _positionX;
	
	public Trap(float positionX) {
		_positionX = positionX;
		init();
	}
	
	public void setPositionX(float positionX) {
		_positionX = positionX;
		_up.setParentX(_positionX);
		_down.setParentX(_positionX);
	}
	
	public float getPositionX() {
		return _positionX;
	}
	
	public void reset() {
		init();
	}
	
	public void cleanScoreMark() {
		_hasGivenScore = false;
	}
	
	private boolean _hasGivenScore = false;
	public boolean shouldGivePoint(float positionX) {
		if(!_hasGivenScore) {
			if(positionX > this._up.getCenterX()) {
				_hasGivenScore = true;
				return true;
			}
		}

	    return false;
	}
	
	public boolean isIntersected(Rectangle rect) {
		return _up.isIntersected(rect)
			|| _down.isIntersected(rect);
	}
	
	public void init() {
		_hasGivenScore = false;
		float availableHeight = World.HEIGHT - World.FLOOR_HEIGHT 
				- World.GAP_HEIGHT - 4 * Pillar.BASE_HEIGHT;
		float ratio = (float)Math.random();
		float upMiddleHeight = World.MIN_TRAP_HEIGHT + ratio * (availableHeight - 2 * World.MIN_TRAP_HEIGHT);
		float downMiddleHeight = availableHeight - upMiddleHeight;
		
		_up = new Pillar(_positionX, World.FLOOR_HEIGHT + downMiddleHeight + 2 * Pillar.BASE_HEIGHT + World.GAP_HEIGHT
				, upMiddleHeight);
		_down = new Pillar(_positionX, World.FLOOR_HEIGHT, downMiddleHeight);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		_up.draw(spriteBatch);
		_down.draw(spriteBatch);
	}

	@Override
	public void update(float timeInterval) {
		setPositionX(_positionX + World.SPEED * timeInterval);
		_up.update(timeInterval);
		_down.update(timeInterval);
	}

}
