import processing.core.PApplet;

public class Sketch extends PApplet {

  float[] snowX = new float[42];
  float[] snowY = new float[42];
  boolean[] blnHideStatus = new boolean[42];
  int snowDiameter = 10;
  int snowSpeed = 2;

  float playerX, playerY;
  int playerSize = 20;
  int lives = 3;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(255);
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
    }
    playerX = width / 2;
    playerY = height - 50;
  }

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

  public void snow() {
    for (int i = 0; i < snowX.length; i++) {
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

  void drawLives() {
    fill(255, 0, 0); 
    for (int i = 0; i < lives; i++) {
      rect(width - 30 - i * 30, 30, 25, 25);
    }
  }

  public void keyPressed() {
    if (keyCode == UP) {
      snowSpeed++; 
    } else if (keyCode == DOWN) {
      snowSpeed = max(1, snowSpeed - 1); 
    }
  }
}
