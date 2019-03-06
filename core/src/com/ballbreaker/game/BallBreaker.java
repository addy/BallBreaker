package com.ballbreaker.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ballbreaker.game.actors.Button;
import com.ballbreaker.game.actors.Rectangle;

import java.util.*;

public class BallBreaker extends ApplicationAdapter {
    private final Random random = new Random();
    private int height;
    private int width;
    private int squareSize;
    private BitmapFont font36;
    private long renderCount = 0;
    private long total = 0;

    private SpriteBatch batch;
    private Button button;
    private Queue<List<Rectangle>> rectangles = new LinkedList<List<Rectangle>>();

    @Override
    public void create() {
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        squareSize = ((width - 10) / 7) - 10;

        int buttonWidth = width / 5;
        int buttonHeight = height / 12;
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("InputMono-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36;
        font36 = generator.generateFont(parameter);
        generator.dispose();

        generateRow(1);
        button = new Button((width / 2) - (buttonWidth / 2), 100, buttonWidth, buttonHeight, Color.GOLD, font36, "Click Me!", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int size = rectangles.size();
                for (int i = size; i > 0; i--) {
                    List<Rectangle> list = rectangles.remove();
                    for (Rectangle rectangle : list) {
                        rectangle.setY(height - 100 - ((squareSize + 10) * i));
                    }
                    rectangles.add(list);
                }
                generateRow(size + 1);
            }
        });
    }

    private void generateRow(int level) {
        List<Rectangle> list = new ArrayList<Rectangle>();

        int x = 10, y = height - 100;
        for (int i = 1; i <= 7; i++) {
            if (i != 1) {
                x = x + squareSize + 10;
            }

            if (random.nextInt(2) == 1) {
                Integer blockHealth = level + random.nextInt(2);
                list.add(new Rectangle(x, y - (squareSize + 10), squareSize, squareSize, Color.PURPLE, font36, blockHealth.toString()));
            }
        }

        rectangles.add(list);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sprite stuff
        batch.begin();
        button.draw(batch, 1);

        if (renderCount < 5) {
            total++;
        }

        if (total != 0 && total % 120 == 0) {
            int size = rectangles.size() + 1;
            generateRow(size);
            for (int i = size; i > 0; i--) {
                List<Rectangle> list = rectangles.remove();
                for (Rectangle rectangle : list) {
                    rectangle.setY(height - 100 - ((squareSize + 10) * i));
                }
                rectangles.add(list);
            }

            total = 0;
            renderCount++;
        }

        int size = rectangles.size();
        for (int i = 0; i < size; i++) {
            List<Rectangle> list = rectangles.remove();
            for (Rectangle rectangle : list) {
                rectangle.draw(batch, 1);
            }
            rectangles.add(list);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
	}
}
