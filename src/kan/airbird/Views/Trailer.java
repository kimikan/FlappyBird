package kan.airbird.Views;

import kan.airbird.MyApplication;
import kan.airbird.R;
import kan.airbird.Helpers.Asserts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Trailer implements IDrawable {
	
	public Sprite playAgain;
	public Sprite scoreBackground;
	
	private int _score;
	private int _bestScore;

	public static final int HEIGHT = 200;
	public static final int WIDTH = 200;
	
	public static float POSITION_X;
	public static final float POSITION_Y = 152.0F;
	
	public Runnable _playCallBack;
	
	private String _currentScoreDesp;
	private String _bestScoreDesp;
	
	public Trailer(Runnable runnable) {
		_currentScoreDesp = MyApplication.context.getResById(R.string.current_score);
		_bestScoreDesp = MyApplication.context.getResById(R.string.best_score);
		
		this.scoreBackground = new Sprite(Asserts.menu_background);
		this.scoreBackground.setSize(240.0F, 220.0F);
		POSITION_X = 160.0F - this.scoreBackground.getWidth() / 2.0F;
		this.scoreBackground.setPosition(
				POSITION_X, POSITION_Y);
		this.playAgain = new Sprite(Asserts.play_arrow);
		this.playAgain.setSize(61.0F, 61.0F);
		this.playAgain.setPosition(
				this.scoreBackground.getX() + this.scoreBackground.getWidth()
						/ 2.0F - this.playAgain.getWidth() / 2.0F,
				20.0F + this.scoreBackground.getY());
		
		_playCallBack = runnable;
	}


	public void setScore(int score, int best) {
		_score = score;
		_bestScore = best;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		
		this.scoreBackground.draw(spriteBatch);
		Asserts.font.draw(spriteBatch, _currentScoreDesp + _score, POSITION_X + 20, 
				POSITION_Y + scoreBackground.getHeight() - 20);
		
		Asserts.font.draw(spriteBatch, _bestScoreDesp + _bestScore, POSITION_X + 20, 
				POSITION_Y + scoreBackground.getHeight() - 60);
		playAgain.draw(spriteBatch);
	}

	@Override
	public void update(float timeInterval) {
		
	}
	
	public boolean touchDown(float x, float y) {
		
		boolean result =  playAgain.getBoundingRectangle().contains(x, y);
		if(result) {
			this._playCallBack.run();
		} 
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private void init() {

		Label.LabelStyle labelS = new Label.LabelStyle(Asserts.font, Color.WHITE);
		Label scoreLabel = new Label("Score:", labelS);
		Label bestLabel = new Label("Best:", labelS);
		
		Table table = new Table();
		

		ImageButton button = new ImageButton(new ImageButton.ImageButtonStyle());
		//button.setBackground("play_arrow");
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("touchDown 1");
				return false;
			}
		});
		table.row();
		Table table2 = new Table();
		table2.columnDefaults(2);
		table2.row();
		table2.add(scoreLabel);
		table2.row();
		table2.add(bestLabel);
		table.add(table2);
		table.row();
		table.add(button);
		table.setFillParent(true);
		table2.setFillParent(true);
		table.pack();
		
	}

}
