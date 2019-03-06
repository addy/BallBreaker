package com.ballbreaker.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Rectangle extends Actor {
    protected GlyphLayout glyphLayout = new GlyphLayout();
    protected Texture texture;
    protected BitmapFont font;
    protected String text;

    public Rectangle(float x, float y, float width, float height, Color color, BitmapFont font, String text) {
        createTexture((int) width, (int) height, color);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);

        this.font = font;
        this.text = text;
    }

    private void createTexture(int width, int height, Color color) {
        Pixmap map = new Pixmap(width, height, Pixmap.Format.RGB888);
        map.setColor(color);
        map.fillRectangle(0, 0, width, height);
        texture = new Texture(map);
        map.dispose();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRow(float y) {
        setY(y);
    }

    public void setCol(float x) {
        setX(x);
    }

    protected void drawRectangle(Batch batch, float parentAlpha) {
        // The actual square
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawRectangle(batch, parentAlpha);

        // Text on square
        glyphLayout.setText(font, this.text);
        font.draw(batch, glyphLayout, getX() + getWidth() / 2 - glyphLayout.width / 2, getY() + getHeight() / 2 + glyphLayout.height / 2);
    }
}
