package kan.airbird.Screens;

import kan.airbird.Views.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class GameScreen extends ScreenAdapter implements InputProcessor, Disposable {

	private SpriteBatch _spriteBatch = new SpriteBatch();
	
	private OrthographicCamera _camera;
	
	private World _world;
	
	public GameScreen() {
		_camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
		_camera.position.set(World.WIDTH/2, World.HEIGHT/2, 0);
		_world = new World(_camera);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		_spriteBatch.dispose();
		_world.dispose();
		
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		_camera.update();
		_spriteBatch.setProjectionMatrix(_camera.combined);
		_world.update(delta);
		_spriteBatch.begin();
		_world.draw(_spriteBatch);
		_spriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}


	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		android.util.Log.i("xxx","xxx");
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return _world.touchDown(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		
		return _world.touchUp(arg0, arg1, arg2, arg3);
	}

}
