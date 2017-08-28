package kan.airbird.Views;

import java.util.LinkedList;

import kan.airbird.MyApplication;
import kan.airbird.R;
import kan.airbird.Helpers.Asserts;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class World implements IDrawable, Disposable {

	public static final int TRAP_DISTANCE = 200;
	public static final int FLOOR_HEIGHT = 68;
	public static final int GAP_HEIGHT = 85;
	public static final int MIN_TRAP_HEIGHT = 48;
	public static final int HEIGHT = 480;
	public static final int WIDTH = 320;
	
	public static final float SPEED = -120.0F; //Means backward 120.
	  
	private Sprite _backGround1;
	private Sprite _backGround2;
	
	private Sprite _pushToStart;
	
	private Trailer _trailer;
	
	private Traps _traps = new Traps();
	private Bird _bird;
	
	private int _score = 0;
	
	private Camera _camera;
	
	private String _tip;
	
	public enum GameState {
		Idle, Running, Dead;
		GameState() {}
	}
	
	public boolean isRunning() {
		return _gameState == GameState.Running;
	}
	
	public World(Camera camera) {
		_tip = MyApplication.context.getResById(R.string.tap_to_start);
		_camera = camera;
		_bird = new Bird(this);
		_backGround1 = new Sprite(Asserts.NewBG);
		_backGround2 = new Sprite(Asserts.NewBG);
		_pushToStart = new Sprite(Asserts.menu_background);
		_pushToStart.setSize(240.0F, 140.0F);
		_pushToStart.setPosition(WIDTH / 2.0F - _pushToStart.getWidth() / 2.0F, 200.0F);
		
		_backGround1.setPosition(0, 0);
		_backGround1.setSize(WIDTH + 0.02f, HEIGHT);
		
		_backGround2.setPosition(WIDTH, 0);
		_backGround2.setSize(WIDTH + 0.02f, HEIGHT);
		
		_trailer = new Trailer(new Runnable() {

			@Override
			public void run() {
				startGame();
			}
		});
	}
	
	public GameState _gameState = GameState.Idle;
	public void startGame() {
		MyApplication.context.setAdVisible(false);
		_traps.reset();
		_bird.reset();
		_score = 0;
		_gameState = GameState.Running;
	}
	
	private int updateScore() {
		int highScore = 0;
		final String HIGHSCORE = "highscore";
	    try
	    {
	      highScore = Asserts.save.getInteger(HIGHSCORE);
	      if (highScore < _score) {
	        Asserts.save.putInteger(HIGHSCORE, _score);
	        System.out.println("Flushed");
	        Asserts.save.flush();
	      }
	    } catch (Exception localException) {
	      System.out.println(" ");
	    }
	    
		return highScore;
	}
	
	public void stopGame() {
		
		MyApplication.context.setAdVisible(true);
		_gameState = GameState.Dead;
		Asserts.engineSound.stop();
	    Asserts.explosionSound.play(1.0F);
	    final int maxScore = updateScore();
	     
		_trailer.setScore(_score, maxScore);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		_backGround1.draw(spriteBatch);
		_backGround2.draw(spriteBatch);
		_traps.draw(spriteBatch);
		_bird.draw(spriteBatch);
		
		if(_gameState == GameState.Idle) {
			_pushToStart.draw(spriteBatch);
			Asserts.font.draw(spriteBatch, _tip, this._pushToStart.getX() + 12, 
					_pushToStart.getY() + _pushToStart.getHeight() - 40);
		} else if (_gameState == GameState.Running) {
			String paramString = String.valueOf(_score);
			 Asserts.font.draw(spriteBatch, paramString, 
					 160.0F - Asserts.font.getBounds(paramString).width / 2.0F, 350.5F);
		} else {
			_trailer.draw(spriteBatch);
		}
	}

	@Override
	public void update(float timeInterval) {
		if(_gameState == GameState.Running || _gameState == GameState.Idle) {
			float p1 = _backGround1.getX();
		    float p2 = _backGround2.getX();
		    float newP1 = p1 + SPEED * timeInterval;
		    float newP2 = p2 + SPEED * timeInterval;
		    if (newP1 <= -WIDTH) { 
		    	while(newP1 < WIDTH/2)
		    		newP1 += WIDTH/2;
		    }
		    if (newP2 <= -WIDTH ) {
		    	while(newP2 < WIDTH/2)
		    		newP2 += WIDTH/2;
		    }
	    
		    _backGround1.setPosition(newP1, _backGround1.getY());
		    _backGround2.setPosition(newP2, _backGround2.getY());
		    
		    if(_gameState == GameState.Running)
		    	_traps.update(timeInterval);
	    }
		
		if(_gameState != GameState.Idle)
			_bird.update(timeInterval);
	}

	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		if(_gameState == GameState.Running)
			_bird.fly();
		else if(_gameState == GameState.Idle) {
			this.startGame();
		} else if(_gameState == GameState.Dead) {
			Vector3 vec = new Vector3(arg0, arg1, 0);
			_camera.unproject(vec);
			return _trailer.touchDown(vec.x, vec.y);
		}
		return false;
	}
	
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		_bird.stop();
		return false;
	}
	

	@Override
	public void dispose() {
	}
	
	class Traps implements IDrawable {

		LinkedList<Trap> _traps = new LinkedList<Trap>();
		public Traps() {
			_traps.add(new Trap(WIDTH + TRAP_DISTANCE));
			_traps.add(new Trap(WIDTH + TRAP_DISTANCE * 2));
			_traps.add(new Trap(WIDTH + TRAP_DISTANCE * 3));
		}
		
		@Override
		public void draw(SpriteBatch spriteBatch) {
			for(Trap trap : _traps) {
				trap.draw(spriteBatch);
			}
		}
		
		public void reset() {
			_traps.get(0).setPositionX(WIDTH + TRAP_DISTANCE);
			_traps.get(1).setPositionX(WIDTH + TRAP_DISTANCE * 2);
			_traps.get(2).setPositionX(WIDTH + TRAP_DISTANCE * 3);
			
			for(Trap trap : _traps) {
				trap.cleanScoreMark();
			}
		}

		@Override
		public void update(float timeInterval) {
			
			for(Trap trap: _traps) {
				if(trap.isIntersected(_bird.getRect())) {
					stopGame();
					break;
				}
			}
			
			Trap first = _traps.getFirst();
			
			if(first.getPositionX() < -Pillar.BASE_WIDTH) {
				Trap last = _traps.getLast();
				_traps.removeFirst();
				
				first.setPositionX(last.getPositionX() + TRAP_DISTANCE);
				first.reset();
				_traps.addLast(first);
			}
			
			for(Trap trap : _traps) {
				trap.update(timeInterval);
				if(trap.shouldGivePoint(_bird.getCenterX())) {
					_score ++;
					Asserts.pointSound.play(1.0F);
				}
				
			}
		}
		
	}

}
