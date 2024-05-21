import processing.core.PApplet;

/**
 * Sketch class represents a simple game where the player controls a character
 * to avoid falling snowflakes using the WASD keys. Snowflakes can also be clicked
 * with the mouse to remove them.
 */
public class Sketch extends PApplet {

  // Arrays to store snowflake positions and status
  float[] snowX = new float[42];
  float[] snowY = new float[42];
  boolean[] blnHideStatus = new boolean[42];

  // Snowflake and player properties
  int snowDiameter = 20; // Increase snowflake size for easier clicking
  int snowSpeed = 2;
  float playerX, playerY;
  int playerSize = 20;
  int lives = 3;

  /**
   * Settings function to set the size of the sketch window.
   */
  public void settings() {
    size(400, 400);
  }

  /**
   * Setup the sketch by initializing snowflake positions, player position,
   * and other variables.
   */
  public void setup() {
    background(255);
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
      blnHideStatus[i] = false;
    }
    playerX = width / 2;
    playerY = height - 50;
  }

  /**
   * Draw function that is called repeatedly to update the sketch.
   */
  public void draw() {
    if (lives == 0) {
      background(255);
    } else {
      background(0);
      snow();
      drawPlayer();
      drawLives();
    }
  }

  /**
   * Updates the position of snowflakes and checks for collisions with the player.
   */
  public void snow() {
    for (int i = 0; i < snowX.length; i++) {
      if (!blnHideStatus[i]) {
        fill(255);
        circle(snowX[i], snowY[i], snowDiameter);
        snowY[i] += snowSpeed;

        if (snowY[i] > height) {
          snowY[i] = 0;
        }

        // Collision detection with player
        if (dist(snowX[i], snowY[i], playerX, playerY) < snowDiameter / 2 + playerSize / 2) {
          snowY[i] = 0;
          lives--;
          if (lives == 0) {
            background(255);
          }
        }
      }
    }
  }

  /**
   * Draws the player character.
   */
  void drawPlayer() {
    fill(0, 0, 255);
    ellipse(playerX, playerY, playerSize, playerSize);

    // Player control with WASD keys
    if (keyPressed) {
      if (key == 'a' && playerX > playerSize / 2) {
        playerX -= 5;
      } else if (key == 'd' && playerX < width - playerSize / 2) {
        playerX += 5;
      } else if (key == 'w' && playerY > playerSize / 2) {
        playerY -= 5;
      } else if (key == 's' && playerY < height - playerSize / 2) {
        playerY += 5;
      }
    }
  }

  /**
   * Draws the remaining lives of the player.
   */
  void drawLives() {
    fill(255, 0, 0);
    for (int i = 0; i < lives; i++) {
      rect(width - 30 - i * 30, 30, 25, 25);
    }
  }

  /**
   * Increases or decreases snowflake speed based on key presses.
   */
  public void keyPressed() {
    if (keyCode == UP) {
      snowSpeed++;
    } else if (keyCode == DOWN) {
      snowSpeed = max(1, snowSpeed - 1);
    }
  }

  /**
   * Handles mouse clicks to remove snowflakes.
   */
  public void mousePressed() {
    for (int i = 0; i < snowX.length; i++) {
      if (dist(snowX[i], snowY[i], mouseX, mouseY) < snowDiameter / 2) {
        blnHideStatus[i] = true;
      }
    }
  }
}