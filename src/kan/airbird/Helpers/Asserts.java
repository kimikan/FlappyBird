package kan.airbird.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Asserts {

	public static TextureRegion NewBG;
	public static AssetManager assetManager;
	public static TextureRegion backGround;
	public static Sound engineSound;
	public static Sound explosionSound;
	public static TextureRegion flappycopter1;
	public static TextureRegion flappycopter2;
	public static TextureRegion flappycopter3;
	public static TextureRegion flappycopter4;
	public static BitmapFont font;
	public static TextureRegion menu_background;
	public static TextureRegion pillar_end;
	public static TextureRegion pillar_middle;
	public static TextureRegion play_arrow;
	public static Sound pointSound;
	public static Preferences save = Gdx.app.getPreferences("My preferences");
	public static TextureRegion tap_to_fly;

	static {
		if (save.contains("highscore"))
			save.putInteger("highscore", 0);
	}

	public static void dispose() {
		font.dispose();
		pointSound.dispose();
		engineSound.dispose();
		explosionSound.dispose();
		assetManager.dispose();
	}
	
	public static void doneLoading() {
		TextureAtlas localTextureAtlas = new TextureAtlas(
				Gdx.files.internal("flappypack.pack"));
		backGround = localTextureAtlas.findRegion("bkng");
		
		//Texture texture = new Texture(Gdx.files.internal("background.png"));
		//backGround = new TextureRegion(texture);
		//texture.dispose();
		
		NewBG = localTextureAtlas.findRegion("bg");
		
		menu_background = localTextureAtlas.findRegion("menu_background");
		tap_to_fly = localTextureAtlas.findRegion("tap_to_fly");
		flappycopter1 = localTextureAtlas.findRegion("flappycopter1");
		flappycopter2 = localTextureAtlas.findRegion("flappycopter2");
		flappycopter3 = localTextureAtlas.findRegion("flappycopter3");
		flappycopter4 = localTextureAtlas.findRegion("flappycopter4");
		pillar_end = localTextureAtlas.findRegion("pillar_end");
		pillar_middle = localTextureAtlas.findRegion("pillar_middle");
		play_arrow = localTextureAtlas.findRegion("play_arrow");
		font = (BitmapFont) assetManager.get("8bitFont.fnt", BitmapFont.class);
		font.setUseIntegerPositions(false);
	}

	
	public static void load() {
		assetManager = new AssetManager();
		Texture.setAssetManager(assetManager);
		TextureLoader.TextureParameter localTextureParameter = new TextureLoader.TextureParameter();
		localTextureParameter.genMipMaps = true;
		localTextureParameter.minFilter = Texture.TextureFilter.Linear;
		localTextureParameter.magFilter = Texture.TextureFilter.Linear;
		engineSound = Gdx.audio.newSound(Gdx.files.internal("engine.wav"));
		pointSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
		explosionSound = Gdx.audio
				.newSound(Gdx.files.internal("explosion.mp3"));
		assetManager.load("8bitFont.fnt", BitmapFont.class);
		assetManager.finishLoading();
		doneLoading();
	}
}
