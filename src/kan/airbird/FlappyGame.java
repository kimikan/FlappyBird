package kan.airbird;

import kan.airbird.Helpers.Asserts;
import kan.airbird.Screens.GameScreen;

import com.badlogic.gdx.Game;

public class FlappyGame extends Game {

	@Override
	public void create() {
		Asserts.load();
		this.setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		Asserts.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
		Asserts.load();
	}
	
}
