package com.ballbreaker.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Button extends Rectangle {
    private TextButton button;

    public Button(float x, float y, float width, float height, Color color, BitmapFont font, String text, ChangeListener listener) {
        super(x, y, width, height, color, font, text);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        this.button = new TextButton(text, style);
        this.button.setWidth(getWidth());
        this.button.setHeight(getHeight());
        this.button.setPosition(getX(), getY());
        this.button.addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //drawRectangle(batch, parentAlpha);
        button.draw(batch, parentAlpha);
    }
}
