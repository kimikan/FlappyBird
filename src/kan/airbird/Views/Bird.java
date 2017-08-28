package kan.airbird.Views;

import kan.airbird.Helpers.Asserts;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bird implements IDrawable {

	public static final float FALL_ACCEL = -750.0F;
	public static final float FLY_ACCEL = 520.0F;
	public static final float MAX_FALL_VELOCITY = -300.0F;
	public static final float MAX_UP_VELOCITY = 200.0F;
	public static final float UP_VELOCITY = 115.0F;
	public static final float ROTATION_SPEED = 60f;
	
	public static final float WIDTH = 30.0f;
	public static final float HEIGHT = 30.0f;
	
	private Sprite _birdSprite;
	
	private TextureRegion[] _animFrames = new TextureRegion[4];
	private Animation _animation;
	
	private World _world;
	
	public Bird(World world) {
		
		_world = world;
		_animFrames[0] = Asserts.flappycopter1;
		_animFrames[1] = Asserts.flappycopter2;
		_animFrames[2] = Asserts.flappycopter3;
		_animFrames[3] = Asserts.flappycopter4;
		_animation = new Animation(0.05f, _animFrames);
		
		_birdSprite = new Sprite(_animFrames[0]);
		_birdSprite.setSize(WIDTH, HEIGHT);
		_birdSprite.setOrigin(WIDTH/2, HEIGHT/2);
		_birdSprite.setPosition(40, World.HEIGHT - 120);
		
	}
	
	public void reset() {
		_birdSprite.setPosition(40, World.HEIGHT - 120);
	}
	
	public Rectangle getRect() {
		Rectangle rect =  _birdSprite.getBoundingRectangle();
		rect.x += 4;
		rect.y += 2;
		rect.width -= 4;
		rect.height -= 6;
		
		return rect;
	}
	
	public float getCenterX() {
		return _birdSprite.getX() + WIDTH / 2;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {

		_birdSprite.draw(spriteBatch);

	}
	
	private boolean _isFlying = false;
	public void fly() {
		_velocityY = UP_VELOCITY;
		_isFlying = true;
		_noAccelTime = 0;
		Asserts.engineSound.loop(0.75F);
	}
	
	public void stop() {
		//_velocityY = UP_VELOCITY;
		_isFlying = false;
		Asserts.engineSound.stop();
	}

	private float _velocityY = 0;
	float _noAccelTime = 0;
	private void updatePosition(float timeInterval) {
		_noAccelTime += timeInterval;
		float x = _birdSprite.getX();
		float y = _birdSprite.getY();
		y += _velocityY * timeInterval;
		if(y <= World.FLOOR_HEIGHT) {
			y = World.FLOOR_HEIGHT;
			
			if(_world.isRunning())
				_world.stopGame();
		} else if(y >= World.HEIGHT - HEIGHT/2) {
			y = World.HEIGHT - HEIGHT/2;
		}
		
		if(_isFlying) {
			if(_velocityY < MAX_UP_VELOCITY) {
				if(_noAccelTime > 0.12f)
					_velocityY += timeInterval * FLY_ACCEL;
			}
		} else if(_velocityY > MAX_FALL_VELOCITY) {
			_velocityY += timeInterval * FALL_ACCEL;
		} 
		
		_birdSprite.setPosition(x, y);
	}
	
	
	private float _rotation = 0;
	private void updateRotation(float timeInterval) {
		float targetRotation = this._velocityY > 0 ?
				Math.abs(_velocityY * 20f / MAX_UP_VELOCITY)
				: -Math.abs(_velocityY * 60f / MAX_FALL_VELOCITY);
		if (_rotation != targetRotation) {
			if (_rotation <= targetRotation) {
				_rotation += timeInterval * ROTATION_SPEED;  
				if(_rotation > targetRotation) {
					_rotation = targetRotation;
				}
			} else {
			    _rotation -= timeInterval * ROTATION_SPEED;
			    if(_rotation < targetRotation) {
			    	_rotation = targetRotation;
			    }
			}  
	    }
		_birdSprite.setRotation(_rotation);
	}
	
	private float _elapsedTime = 0;
	private void updateAnim(float timeInterval) {
		_elapsedTime += timeInterval;
		TextureRegion currentRegion = _isFlying ? _animation.getKeyFrame(_elapsedTime, true)
				: this._animFrames[0];
		_birdSprite.setRegion(currentRegion);
	}
	
	@Override
	public void update(float timeInterval) {
		
		updateRotation(timeInterval);
		updatePosition(timeInterval);
		updateAnim(timeInterval);
		/*
	    setDesiredAngle();
	    this.noAccelTime = (timeInterval + this.noAccelTime);
	    if ((this.rotation != this.desiredAngle) && (!this.isDead))
	    {
	      if (this.rotation <= this.desiredAngle)
	        break label228;
	      this.rotation -= timeInterval * this.ANGLE_TURN_SPEED;
	      if (this.rotation < this.desiredAngle)
	        this.rotation = this.desiredAngle;
	    }
	    
	    if (!this.noGravity)
	    {
	      label90: if (!this.isFlying)
	        break label266;
	      if (this.noAccelTime > this.NO_ACCEL_TIME)
	        this.velocityY += 520.0F * timeInterval;
	    }
	    while (true)
	    {
	      if (this.velocityY < -300.0F)
	        this.velocityY = -300.0F;
	      if (this.velocityY > 200.0F)
	        this.velocityY = 200.0F;
	      this.y += timeInterval * this.velocityY;
	      if (this.y > 480.0F - this.height / 2.0F)
	        this.y = (480.0F - this.height / 2.0F);
	      if (this.y < 68.0F)
	      {
	        this.y = 68.0F;
	        setIsDead(true);
	      }
	      return;
	      label228: this.rotation += timeInterval * this.ANGLE_TURN_SPEED;
	      if (this.rotation > this.desiredAngle);
	      this.rotation = this.desiredAngle;
	      break label90:
	      label266: if (this.noAccelTime <= this.NO_ACCEL_TIME)
	        continue;
	      this.velocityY += -750.0F * timeInterval;
	    }*/
	}

}
